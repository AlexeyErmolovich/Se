package com.alexeyermolovich.secretofyourname;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alexeyermolovich.secretofyourname.adapter.PageTabAdapter;
import com.alexeyermolovich.secretofyourname.fragment.FragmentTabAll;
import com.alexeyermolovich.secretofyourname.fragment.FragmentTabFavorites;
import com.alexeyermolovich.secretofyourname.fragment.FragmentTabSearch;

public class MainActivity extends AppCompatActivity {

    private Core core;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PageTabAdapter pageTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
    }

    @Override
    protected void onDestroy() {
        pageTabAdapter.clearPager();
        super.onDestroy();
    }

    private void initUi() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager();

        this.tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
    }

    private void setupViewPager() {
        pageTabAdapter = new PageTabAdapter(getSupportFragmentManager(), viewPager);

        FragmentTabSearch fragmebtTabSearch = new FragmentTabSearch();
        FragmentTabAll fragmentTabAll = new FragmentTabAll();
        FragmentTabFavorites fragmentTabFavorites = new FragmentTabFavorites();

        pageTabAdapter.addFrag(fragmebtTabSearch, getString(R.string.tab_name_search));
        pageTabAdapter.addFrag(fragmentTabAll, getString(R.string.tab_all));
        pageTabAdapter.addFrag(fragmentTabFavorites, getString(R.string.tab_favorite));

        viewPager.setAdapter(pageTabAdapter);

    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
