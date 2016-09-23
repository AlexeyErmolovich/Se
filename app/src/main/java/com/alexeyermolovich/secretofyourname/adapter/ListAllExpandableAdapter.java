package com.alexeyermolovich.secretofyourname.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.model.NameObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ermolovich on 23.9.16.
 */

public class ListAllExpandableAdapter extends BaseExpandableListAdapter {

    private final String TAG = this.getClass().getName();

    private Context context;

    private List<String> listGroup;

    private Map<String, List<NameObject>> listChild;

    public ListAllExpandableAdapter(Context context, List<String> listGroup, Map<String, List<NameObject>> listChild) {
        this.context = context;
        this.listGroup = listGroup;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_names_title_item, null);
        }

        String nameGroup = (String) getGroup(groupPosition);
        TextView textTitle = (TextView) convertView.findViewById(R.id.textTitle);
        textTitle.setText(nameGroup);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_names_item, null);
        }

        NameObject child = (NameObject) getChild(groupPosition, childPosition);
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        View bottomDivider = convertView.findViewById(R.id.bottomDivider);
        View bottomDividerItem = convertView.findViewById(R.id.bottomDividerItem);

        if (child != null) {
            textName.setText(child.getName());
            if (child.getSex().equals(NameObject.MALE)) {
                imageView.setBackgroundResource(R.mipmap.ic_male);
            } else if (child.getSex().equals(NameObject.FEMALE)) {
                imageView.setBackgroundResource(R.mipmap.ic_female);
            } else {
                imageView.setBackgroundResource(0);
            }
            if (getChildrenCount(groupPosition) == childPosition + 1) {
                bottomDivider.setVisibility(View.VISIBLE);
                bottomDividerItem.setVisibility(View.GONE);
            } else {
                bottomDivider.setVisibility(View.GONE);
                bottomDividerItem.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<String> getListGroup() {
        return listGroup;
    }

    public Map<String, List<NameObject>> getListChild() {
        return listChild;
    }
}
