package com.alexeyermolovich.secretofyourname.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexeyermolovich.secretofyourname.R;

/**
 * Created by ermolovich on 24.9.16.
 */

public class SectionView extends LinearLayout {

    private View view;
    private TextView textTitle;
    private TextView textData;

    public SectionView(Context context) {
        super(context);
        init(context);
    }

    public SectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.section_view, this);
        this.textTitle = (TextView) view.findViewById(R.id.textTitle);
        this.textData = (TextView) view.findViewById(R.id.textData);
    }

    public void setTextData(String textData){
        this.textData.setText(textData);
    }

    public void setTextTitle(String textTitle){
        this.textTitle.setText(textTitle);
    }

    public void setTextAndData(String title, String data){
        setTextTitle(title);
        setTextData(data);
    }
}
