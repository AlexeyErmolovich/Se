package com.alexeyermolovich.secretofyourname.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ermolovich on 24.9.16.
 */

public class NameObject implements Serializable {
    public static final String UNKNOWN = "Unknown";
    public static final String MALE = "М";//0
    public static final String FEMALE = "Ж";//1

    @SerializedName("name")
    private String name;
    @SerializedName("sex")
    private String sex;

    public NameObject(String name, Byte sex) {
        this(name, sex == null ? null : sex == 0 ? MALE : FEMALE);
    }

    public NameObject(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public Byte getSexByte() {
        return sex == null ? null : sex.equals(MALE) ? Byte.valueOf("0") : Byte.valueOf("1");
    }
}
