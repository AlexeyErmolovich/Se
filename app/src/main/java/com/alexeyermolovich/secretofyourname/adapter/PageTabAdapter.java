package com.alexeyermolovich.secretofyourname.adapter;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ermolovich on 23.9.16.
 */

public class PageTabAdapter extends FragmentPagerAdapter {

    private final String TAG = this.getClass().getName();

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitleList = new ArrayList<>();

    private ViewPager viewPager;

    public PageTabAdapter(FragmentManager fm, ViewPager viewPager) {
        super(fm);
        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        if (position < getCount()) {
            android.app.FragmentManager manager = ((android.app.Fragment) object).getFragmentManager();
            if (manager != null) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((android.app.Fragment) object);
                trans.commit();
            }
        }
    }

    public void addFrag(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public void clearPager() {
        for (int i = 0; i < fragmentList.size(); i++) {
            try {
                Object object = this.instantiateItem(viewPager, i); // makes warnings at first run, don't care of it man
                if (object != null)
                    destroyItem(viewPager, i, object);
            } catch (Exception e) {
                Log.w(TAG, e.getLocalizedMessage());
            }
        }
        fragmentList.clear();
        fragmentTitleList.clear();
    }
}
