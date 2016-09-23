package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.MainActivity;
import com.alexeyermolovich.secretofyourname.R;

/**
 * Created by ermolovich on 23.9.16.
 */

public abstract class FragmentTabAbstract extends Fragment {

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    private MainActivity mainActivity;
    private Core core;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mainActivity = (MainActivity) getActivity();
        this.core = Core.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        mainActivity.getSupportActionBar().setTitle(R.string.app_name);
    }

    public Core getCore() {
        return core;
    }
}
