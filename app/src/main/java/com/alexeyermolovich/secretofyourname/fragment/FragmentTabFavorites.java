package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.Core;
import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.adapter.ListFavoritesAdapter;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.NameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ermolovich on 24.9.16.
 */

public class FragmentTabFavorites extends FragmentTabAbstract
        implements FactoryNames.OnGetFavoriteNamesListener,
        ListViewCompat.OnItemClickListener {

    private ListFavoritesAdapter listFavoritesAdapter;
    private ListViewCompat listView;
    private TextView textWarning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listFavoritesAdapter = new ListFavoritesAdapter(Core.getInstance().getApplicationContext(), new ArrayList<NameObject>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_favorites, container, false);

        this.listView = (ListViewCompat) view.findViewById(R.id.listViewFavorites);
        listView.setAdapter(listFavoritesAdapter);
        listView.setOnItemClickListener(this);

        this.textWarning = (TextView) view.findViewById(R.id.textWarning);

        getCore().getFactoryNames().setOnGetFavoriteNamesListener(this);

        updateUI();

        return view;
    }

    @Override
    public void onGetFavoriteNames(boolean isSuccess) {
        if (isSuccess) {
            updateUI();
        } else {
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundGrey);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentNamesDetails fragmentNamesDetails = new FragmentNamesDetails();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FactoryNames.ARG_OBJECT, listFavoritesAdapter.getItem(position));
        changeFragment(fragmentNamesDetails, bundle, true, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    private void updateUI() {
        List<NameObject> listNames = getCore().getFactoryNames().getListFavoriteNames();
        listFavoritesAdapter.clear();
        if (listNames != null && listNames.size() != listFavoritesAdapter.getCount() && listNames.size() != 0) {
            textWarning.setVisibility(View.GONE);
            listView.setBackgroundResource(R.color.colorBackgroundMain);

            listFavoritesAdapter.addAll(listNames);
            listFavoritesAdapter.notifyDataSetChanged();
            return;
        } else if (listNames != null && listNames.size() == 0) {

            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundGrey);
        } else if (listNames == null) {
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundGrey);
        }
    }
}
