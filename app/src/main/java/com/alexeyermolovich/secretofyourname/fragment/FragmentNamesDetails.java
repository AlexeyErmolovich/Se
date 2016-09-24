package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.MainActivity;
import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.NameObject;

/**
 * Created by ermolovich on 23.9.16.
 */

public class FragmentNamesDetails extends Fragment {

    private View view;
    private Core core;
    private MainActivity mainActivity;

    private NameObject nameObject;

    private MenuItem addFavorite;
    private MenuItem deleteFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_names_details, container, false);

        mainActivity = (MainActivity) getActivity();
        core = Core.getInstance();

        nameObject = (NameObject) getArguments().getSerializable(FactoryNames.ARG_OBJECT);
        if (nameObject == null) {
            getFragmentManager().popBackStack();
        }

        mainActivity.getSupportActionBar().setTitle(nameObject.getName());

        mainActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_names_details_menu, menu);

        addFavorite = menu.findItem(R.id.action_add_favorite);
        deleteFavorite = menu.findItem(R.id.action_delete_favorite);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("TEST", "onOptionsItemSelected - Home");
                getFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
