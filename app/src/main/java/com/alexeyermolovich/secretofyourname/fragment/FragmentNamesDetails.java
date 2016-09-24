package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.MainActivity;
import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.FullNameObject;
import com.alexeyermolovich.secretofyourname.model.NameObject;

/**
 * Created by ermolovich on 23.9.16.
 */

public class FragmentNamesDetails extends Fragment
        implements FactoryNames.OnGetNameListener {

    private View view;
    private Core core;
    private MainActivity mainActivity;

    private TextView textWarning;

    private FullNameObject fullNameObject;

    private MenuItem menuAddFavorite;
    private MenuItem menuDeleteFavorite;
    private NameObject nameObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_names_details, container, false);

        mainActivity = (MainActivity) getActivity();
        mainActivity.invalidateOptionsMenu();
        setHasOptionsMenu(true);

        core = Core.getInstance();

        nameObject = (NameObject) getArguments().getSerializable(FactoryNames.ARG_OBJECT);
        if (nameObject == null) {
            getFragmentManager().popBackStack();
        }

        textWarning = (TextView) view.findViewById(R.id.textWarning);

        core.getFactoryNames().setOnGetNameListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mainActivity.getSupportActionBar().setTitle(nameObject.getName());
        mainActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_names_details_menu, menu);

        menuAddFavorite = menu.findItem(R.id.action_add_favorite);
        menuDeleteFavorite = menu.findItem(R.id.action_delete_favorite);

        menuAddFavorite.setEnabled(true);
        menuDeleteFavorite.setEnabled(true);

        core.getFactoryNames().loadFullNameObject(nameObject.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.action_add_favorite:
                core.getFactoryNames().addFavorite(nameObject);
                return true;
            case R.id.action_delete_favorite:
                core.getFactoryNames().deleteFavorite(nameObject);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetFavorite(boolean favorite) {
        menuAddFavorite.setVisible(!favorite);
        menuDeleteFavorite.setVisible(favorite);
    }

    @Override
    public void onGetName(FullNameObject nameObject) {
        if (nameObject != null) {

        } else {
            textWarning.setVisibility(View.VISIBLE);
        }
    }
}
