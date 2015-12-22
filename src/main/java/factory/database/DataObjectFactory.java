package factory.database;

import application.MyApplication;
import exception.DatabaseException;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableRecordImpl;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wendywang on 2015-11-08.
 *
 * Be able to connect the database
 */
public class DataObjectFactory {

    private static final String connectionUrl = MyApplication.properties.getProperty("mysql_url");
    private static final String mysqlConnectorDriver = MyApplication.properties.getProperty("mysql_connector_driver");
    private static final String mysqlUser = MyApplication.properties.getProperty("mysql_user");
    private static final String mysqlPassword = MyApplication.properties.getProperty("mysql_password");

    public static Connection getDatabaseConnection() throws DatabaseException {
        try{
            Class.forName(mysqlConnectorDriver).newInstance();
            return DriverManager.getConnection(connectionUrl, mysqlUser, mysqlPassword);
        } catch (IllegalAccessException|InstantiationException|ClassNotFoundException exception) {
            throw new DatabaseException(DatabaseException.DATABASE_DRIVER_INSTANTIATION_ERROR_MESSAGE);
        } catch (SQLException exception) {
            throw new DatabaseException(DatabaseException.DATABASE_CONNECTION_ERROR_MESSAGE);
        }
    }

    public static <E> E getDataObject(TableLike<?> table, Condition condition, Class<? extends E> aClass) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(table).where(condition).fetchAny().into(aClass);
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <E>List<E> getDataObjectList(TableLike<?> table, Condition condition, Class<? extends E> aClass) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(table).where(condition).fetchInto(aClass);
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <E>List<E> getDataObjectList(TableLike<?> table, Class<? extends E> aClass) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(table).fetchInto(aClass);
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <R extends UpdatableRecordImpl> void updateDataObject(Table<R> table, Object newObject) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            create.batchUpdate(create.newRecord(table, newObject)).execute();
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <R extends UpdatableRecordImpl<R>> void updateDataObjectList(Table<R> table, List<?> objectList) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<R> recordList = objectList.stream().map(object -> create.newRecord(table, object)).collect(Collectors.toList());
            create.batchUpdate(recordList).execute();
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <R extends UpdatableRecordImpl> void storeDataObject(Table<R> table, Object newObject) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            create.newRecord(table, newObject).store();
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <R extends UpdatableRecordImpl, T> T storeDataObject(Table<R> table, Object newObject, Field<T> field) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            R record = create.newRecord(table, newObject);
            record.store();
            return record.getValue(field);
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

    public static <R extends TableRecordImpl<R>> void storeDataObjectList(Table<R> table, List<?> objectList) throws DatabaseException {
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<R> recordList = objectList.stream().map(object -> create.newRecord(table, object)).collect(Collectors.toList());
            create.batchInsert(recordList).execute();
        }
        catch (Exception exception) {
            throw new DatabaseException("Database error: " + exception.getMessage());
        }
    }

}
