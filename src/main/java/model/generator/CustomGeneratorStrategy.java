package model.generator;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;

/**
 * Created by adam on 18/11/15.
 */
public class CustomGeneratorStrategy extends DefaultGeneratorStrategy {

    private static final String SCHEMA_NAME = "vegan_kitchen_api";

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        if (definition.getOutputName().equals(SCHEMA_NAME))
            return super.getJavaClassName(definition, mode) + "Schema";
        else if (Mode.DEFAULT == mode)
            return super.getJavaClassName(definition, mode) + "Table";
        else
            return super.getJavaClassName(definition, mode);
    }
}
