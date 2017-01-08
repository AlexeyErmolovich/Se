package com.alexeyermolovich.secretofyourname.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.adapter.ListAllExpandableAdapter;
import com.alexeyermolovich.secretofyourname.factory.FactoryNames;
import com.alexeyermolovich.secretofyourname.model.NameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ermolovich on 23.9.16.
 */

public class FragmentTabAll extends FragmentTabAbstract
        implements FactoryNames.OnGetNameListListener,
        ExpandableListView.OnChildClickListener {

    private ExpandableListView listView;
    private ListAllExpandableAdapter allExpandableAdapter;
    private TextView textWarning;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allExpandableAdapter = new ListAllExpandableAdapter(getCore().getApplicationContext(), new ArrayList<String>(), new HashMap<String, List<NameObject>>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_all, container, false);

        this.listView = (ExpandableListView) view.findViewById(R.id.listViewAll);
        listView.setAdapter(allExpandableAdapter);
        listView.setOnChildClickListener(this);

        this.textWarning = (TextView) view.findViewById(R.id.textWarning);

        getCore().getFactoryNames().setOnGetNameListListener(this);

        updateUI();

        return view;
    }

    private void updateUI() {
        allExpandableAdapter.getListGroup().clear();
        allExpandableAdapter.getListChild().clear();
        List<NameObject> listNames = getCore().getFactoryNames().getListNames();
        if (listNames != null && listNames.size() != allExpandableAdapter.getAllCount()) {
            textWarning.setVisibility(View.GONE);
            listView.setBackgroundResource(R.color.colorBackgroundMain);

            for (NameObject nameObject : listNames) {
                List<String> listGroup = allExpandableAdapter.getListGroup();
                String groupName = nameObject.getName().substring(0, 1).toUpperCase();
                if (!listGroup.contains(groupName)) {
                    listGroup.add(groupName);
                }
                Map<String, List<NameObject>> listChild = allExpandableAdapter.getListChild();
                if (!listChild.containsKey(groupName)) {
                    listChild.put(groupName, new ArrayList<NameObject>());
                }
                List<NameObject> nameObjects = listChild.get(groupName);
                nameObjects.add(nameObject);
            }
            allExpandableAdapter.notifyDataSetChanged();
        } else if (listNames == null) {
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundEmpty);
        }
    }

    @Override
    public void onGetNames(boolean isSuccess) {
        if (isSuccess) {
            updateUI();
        } else {
            allExpandableAdapter.getListGroup().clear();
            allExpandableAdapter.getListChild().clear();
            textWarning.setVisibility(View.VISIBLE);
            listView.setBackgroundResource(R.color.colorBackgroundEmpty);
        }
    }

    @Override
    public void onGetCountNames(int count) {

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        openFragmentDetails((NameObject) allExpandableAdapter.getChild(groupPosition, childPosition));
        return true;
    }
}
