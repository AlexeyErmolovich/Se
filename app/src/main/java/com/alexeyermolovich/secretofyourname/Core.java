package com.alexeyermolovich.secretofyourname;

import android.app.Application;
import android.util.Log;

import com.alexeyermolovich.secretofyourname.factory.FactoryNames;

/**
 * Created by ermolovich on 23.9.16.
 */

public class Core extends Application {

    private final String TAG = this.getClass().getName();

    private static Core instance;

    private FactoryNames factoryNames;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        instance = this;

        factoryNames = new FactoryNames(getApplicationContext());
        factoryNames.loadListNames();
    }

    public static Core getInstance() {
        return instance;
    }

    public FactoryNames getFactoryNames() {
        return factoryNames;
    }
}
