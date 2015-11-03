package com.innovation.tencent.botany_cultivate.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.base.BaseFragment;
import com.innovation.tencent.botany_cultivate.ui.adapter.ShopAdapter;

import java.util.ArrayList;

public class ShopFragment extends BaseFragment {

    private ViewPager vp_shop_shop;
    private PagerAdapter vp_adapter_shop;
    private TextView tv_virtual_shop;
    private TextView tv_reality_shop;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    @Override
    protected int getRooyView() {
        return R.layout.fragment_shop;
    }

    @Override
    protected void setListener() {

        vp_shop_shop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_virtual_shop.setBackgroundColor(0x80FFFFFF);
                        tv_reality_shop.setBackgroundColor(0x66CC66);
                        break;
                    case 1:
                        tv_virtual_shop.setBackgroundColor(0x66CC66);
                        tv_reality_shop.setBackgroundColor(0x80FFFFFF);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_virtual_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_shop_shop.setCurrentItem(0);
            }
        });

        tv_reality_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_shop_shop.setCurrentItem(1);
            }
        });
    }

    @Override
    protected void init() {
        fragments = new ArrayList<Fragment>();
        fragments.add(0,new ShopMainFragment());
        fragments.add(1,new ShopMainFragment());
        fm = getChildFragmentManager();
        vp_adapter_shop = new ShopAdapter(fm,fragments);
    }

    @Override
    protected void initData(View view) {
        tv_virtual_shop = (TextView) view.findViewById(R.id.tv_virtual_shop);
        tv_reality_shop = (TextView) view.findViewById(R.id.tv_reality_shop);

        vp_shop_shop = (ViewPager) view.findViewById(R.id.vp_shop_shop);
        vp_shop_shop.setAdapter(vp_adapter_shop);
        vp_shop_shop.setCurrentItem(0);
    }

}
