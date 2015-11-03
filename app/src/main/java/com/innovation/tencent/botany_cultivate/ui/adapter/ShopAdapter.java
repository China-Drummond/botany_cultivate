package com.innovation.tencent.botany_cultivate.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Drummond on 2015/11/1.
 */
public class ShopAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public ShopAdapter(FragmentManager fm){
        super(fm);
    }

    public ShopAdapter(FragmentManager fm,ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
