package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.MainActivity;
import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.NameObject;

/**
 * Created by ermolovich on 23.9.16.
 */

public abstract class FragmentTabAbstract extends Fragment {

    private final String TAG = this.getClass().getName();

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    private MainActivity mainActivity;
    private Core core;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        this.mainActivity = (MainActivity) getActivity();
        this.core = Core.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart");
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

    protected void openFragmentDetails(NameObject item) {
        FragmentNamesDetails fragmentNamesDetails = new FragmentNamesDetails();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FactoryNames.ARG_OBJECT, item);
        changeFragment(fragmentNamesDetails, bundle, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }
}
