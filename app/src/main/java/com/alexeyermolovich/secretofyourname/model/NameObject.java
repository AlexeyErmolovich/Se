package com.alexeyermolovich.secretofyourname.model;

import java.io.Serializable;

/**
 * Created by ermolovich on 24.9.16.
 */

public class NameObject extends NameObjectDao implements Serializable {

    public static final String UNKNOWN = "Unknown";
    public static final String MALE = "М";//0
    public static final String FEMALE = "Ж";//1

    public NameObject(String name, Byte sex) {
        super(name, sex);
    }

    public NameObject(String name, String sex) {
        super(name, sex.equals(MALE) ? Byte.valueOf("0") : sex.equals(FEMALE) ? Byte.valueOf("1") : null);
    }

    public String getSexName() {
        if (getSex() == null) {
            return null;
        } else if (getSex() == 0) {
            return MALE;
        } else if (getSex() == 1) {
            return FEMALE;
        } else {
            return null;
        }
    }
}
