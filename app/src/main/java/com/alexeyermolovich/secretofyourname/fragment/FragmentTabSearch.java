package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.adapter.ListDataAdapter;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.NameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ermolovich on 25.9.16.
 */

public class FragmentTabSearch extends FragmentTabAbstract
        implements ListViewCompat.OnItemClickListener,
        Spinner.OnItemSelectedListener,
        EditText.OnEditorActionListener,
        TextWatcher, FactoryNames.OnSearchDataListener {

    private TextView textWarning;
    private EditText editTextSearch;
    private ListViewCompat listView;
    private ListDataAdapter listDataAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listDataAdapter = new ListDataAdapter(getCore().getApplicationContext(), new ArrayList<NameObject>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_search, container, false);

        textWarning = (TextView) view.findViewById(R.id.textWarning);

        editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(this);
        editTextSearch.setOnEditorActionListener(this);
        editTextSearch.clearFocus();

        listView = (ListViewCompat) view.findViewById(R.id.listViewSearch);
        listView.setOnItemClickListener(this);
        listView.setAdapter(listDataAdapter);

        getCore().getFactoryNames().setOnSearchDataListener(this);

        updateUI();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (id >= 0 && id < listDataAdapter.getCount())
            openFragmentDetails(listDataAdapter.getItem((int) id));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateUI();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getCore().getFactoryNames().loadDataSearch(editTextSearch.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            getCore().getFactoryNames().loadDataSearch(editTextSearch.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void onSearchResult(boolean isSuccess) {
        if (isSuccess) {
            updateUI();
        } else {
            listDataAdapter.clear();
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundEmpty);
        }
    }

    private void updateUI() {
        listDataAdapter.clear();
        List<NameObject> listNames = getCore().getFactoryNames().getListSearch();
        if (listNames != null && listNames.size() != listDataAdapter.getCount() && listNames.size() != 0) {
            textWarning.setVisibility(View.GONE);
            listView.setBackgroundResource(R.color.colorBackgroundMain);
            listDataAdapter.addAll(listNames);
            listDataAdapter.notifyDataSetChanged();
        } else if (listNames != null && listNames.size() == 0) {
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundEmpty);
        } else if (listNames == null) {
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundEmpty);
        }
    }
}
