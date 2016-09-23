package com.alexeyermolovich.secretofyourname.model;

import java.io.Serializable;

/**
 * Created by ermolovich on 23.9.16.
 */

public class NameObject implements Serializable {

    public static final String UNKNOWN = "Unknown";
    public static final String MALE = "M";
    public static final String FEMALE = "F";

    private String name;
    private String sex;

    public NameObject() {

    }

    public NameObject(String name, String sex) {
        if (name != null) {
            this.name = name;
        } else {
            this.name = UNKNOWN;
        }
        if (sex != null && (sex.equals(MALE) || sex.equals(FEMALE))) {
            this.sex = sex;
        } else {
            this.name = UNKNOWN;
        }
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }
}
