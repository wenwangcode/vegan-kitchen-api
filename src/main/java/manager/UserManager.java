package manager;

import exception.DatabaseException;
import exception.InvalidUserCreationException;
import exception.SessionNotFoundException;
import factory.database.DataObjectFactory;
import model.User;
import model.mapping.tables.records.UserRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;

import static model.mapping.tables.UserTable.USER;

/**
 * Created by wendywang on 2015-11-14.
 */
public class UserManager {

    private final int MINIMUM_PASSWORD_LENGTH = 8;
    private final int MAXIMUM_PASSWORD_LENGTH = 255;
    private static SecureRandom secureRandom = new SecureRandom();

    /**
     * get user object based on user name and password
     * @param userName : user name
     * @param password : password
     * @return user object
     * @throws DatabaseException
     */
    private User getUser(String userName, String password) throws DatabaseException {
        try {
            Condition condition = USER.USER_NAME.equal(userName).and(USER.PASSWORD.equal(getEncryptPassword(password)));
            return DataObjectFactory.getDataObject(USER, condition, User.class);
        }
        catch (NoSuchAlgorithmException exception) {
            throw new DatabaseException("Unable access user data due to password encryption error.");
        }
    }

    /**
     * create user account, password assumed to be encrypted
     * @param user : new user to be created by user
     */
    public void createUser(User user) throws DatabaseException {
        DataObjectFactory.storeDataObject(USER, user);
        try (Connection connection = DataObjectFactory.getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            UserRecord userRecord = create.newRecord(USER, user);
            userRecord.store();
        }
        catch (SQLException |DataAccessException |DatabaseException exception) {
            throw new DatabaseException(DatabaseException.getDataRetrievalErrorMessage("failed insert new user into database."));
        }
    }

    /**
     * authenticate authorization token
     * @param authorization : authorization token issued in user login
     */
    public void authenticateUser(String authorization) {

    }

    /**
     * encrypt password using MD5 (no decryption is required, password validation should be done using the encrypted password)
     * @param password : un-encrypted password
     * @return : encrypted password
     * @throws NoSuchAlgorithmException
     */
    public String getEncryptPassword(String password) throws NoSuchAlgorithmException{
        String encryptedPassword;
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        byte[] passwordBytes = password.getBytes();
        byte[] digestedPasswordBytes = messageDigest.digest(passwordBytes);
        StringBuilder stringBuffer = new StringBuilder();
        for (byte digestedPasswordByte : digestedPasswordBytes) {
            stringBuffer.append(Integer.toHexString(0xff & digestedPasswordByte));
        }
        encryptedPassword = stringBuffer.toString();
        return encryptedPassword;
    }

    /**
     * create/update login session for user
     * @param userName : user name supplied by user
     * @param password : password supplied by user
     * @return : authorization token
     */
    public String login(String userName, String password) throws DatabaseException{
        // verity user name and password
        User user = getUser(userName, password);
        user.setAuthorizationToken(generateAuthorizationToken());
        user.setLastAccess(UInteger.valueOf(System.currentTimeMillis() / 1000L));
        return null;
    }

    /**
     * end a login session
     * @param authorization : authorization token of existing session
     */
    public void removeSession(String authorization) throws SessionNotFoundException {
        // TODO
    }

    public void validateNewUser(User user) throws InvalidUserCreationException, DatabaseException {
        if (!isEmail(user.getEmail())) {
            throw new InvalidUserCreationException("invalid email format.");
        }
        else if (!isValidPassword(user.getPassword())) {
            throw new InvalidUserCreationException("invalid password.");
        }
        else if (user.getUserId() != null) {
            throw new InvalidUserCreationException("invalid attempt to supply user id for user creation.");
        }
    }

    private boolean isEmail(String email) {
        return email.toUpperCase().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        return password.length() > MINIMUM_PASSWORD_LENGTH && password.length() < MAXIMUM_PASSWORD_LENGTH;
    }

    private String generateAuthorizationToken() {
        return new BigInteger(130, secureRandom).toString(32);
    }

}
