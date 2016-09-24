package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

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

        mainActivity.getSupportActionBar().setHomeButtonEnabled(false);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public Core getCore() {
        return core;
    }

    protected void changeFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, int transition) {
        detachFragmentIfExists(fragment.getClass().getName());
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction
                .remove(this)
                .add(R.id.container, fragment);

        if (addToBackStack)
            fragmentTransaction.addToBackStack(this.getClass().getName());

        if (transition != 0)
            fragmentTransaction.setTransition(transition);

        fragmentTransaction.commitAllowingStateLoss(); //TODO why not .commit()?
    }

    protected void detachFragmentIfExists(String fragmentTag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null) {
            getFragmentManager().beginTransaction().detach(fragment).commit();
        }
    }

}
