package com.alexeyermolovich.secretofyourname.model;

/**
 * Created by ermolovich on 24.9.16.
 */

public class FullNameObject extends NameObject {

    public FullNameObject() {
        super(NameObject.UNKNOWN, NameObject.UNKNOWN);
    }

    public FullNameObject(String name, String sex) {
        super(name, sex);
    }
}
