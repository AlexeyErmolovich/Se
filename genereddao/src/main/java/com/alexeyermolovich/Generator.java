package com.alexeyermolovich;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String... args) throws Exception {
        Schema schema = new Schema(1, "com.alexeyermolovich.secretofyourname.model");

        Entity place = schema.addEntity("NameObjectDao");
        place.addStringProperty("name").primaryKey();
        place.addByteProperty("sex");

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

}
