package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.R;

/**
 * Created by ermolovich on 24.9.16.
 */

public class FragmentTabFavorites extends FragmentTabAbstract {

    private ListViewCompat listView;
    private TextView textWarning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_favorites, container, false);

        this.listView = (ListViewCompat) view.findViewById(R.id.listViewFavorites);
        this.textWarning = (TextView) view.findViewById(R.id.textWarning);

        return view;
    }
}
