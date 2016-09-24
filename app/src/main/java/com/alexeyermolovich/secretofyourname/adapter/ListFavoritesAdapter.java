package com.alexeyermolovich.secretofyourname.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.R;
import com.alexeyermolovich.secretofyourname.model.NameObject;

import java.util.List;

/**
 * Created by ermolovich on 24.9.16.
 */

public class ListFavoritesAdapter extends ArrayAdapter<NameObject> {

    public ListFavoritesAdapter(Context context, List<NameObject> objects) {
        super(context, R.layout.fragment_names_item, objects);
    }

    private static class ViewHolder {
        TextView textName;
        ImageView imageView;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_names_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView) convertView.findViewById(R.id.textName);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NameObject item = getItem(position);
        if (item != null) {
            viewHolder.textName.setText(item.getName());
            if (item.getSexName().equals(NameObject.MALE)) {
                viewHolder.imageView.setBackgroundResource(R.mipmap.ic_male);
            } else if (item.getSexName().equals(NameObject.FEMALE)) {
                viewHolder.imageView.setBackgroundResource(R.mipmap.ic_female);
            } else {
                viewHolder.imageView.setBackgroundResource(0);
            }
        }

        return convertView;
    }
}
