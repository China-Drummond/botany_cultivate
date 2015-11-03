package com.innovation.tencent.botany_cultivate.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.base.BaseActivity;
import com.innovation.tencent.botany_cultivate.base.BaseFragment;
import com.innovation.tencent.botany_cultivate.ui.fragment.GardenFragment;
import com.innovation.tencent.botany_cultivate.ui.fragment.IndividualFragment;
import com.innovation.tencent.botany_cultivate.ui.fragment.MainFragment;
import com.innovation.tencent.botany_cultivate.ui.fragment.ShopFragment;
import com.innovation.tencent.botany_cultivate.ui.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private RadioButton rb_home_main, rb_garden_main, rb_shop_main, rb_individual_main;
    private RadioGroup rg_main;
    private MyViewPager vp_main;
    private TextView tv_title_main;
    private String[] titles;
    private List<BaseFragment> fragments;
    private FragmentManager fm;
    private MyPagerAdapter adapter;

    @Override
    protected void init() {
        fragments = new ArrayList<BaseFragment>();
        fragments.add(0, new MainFragment());
        fragments.add(1, new GardenFragment());
        fragments.add(2, new ShopFragment());
        fragments.add(3, new IndividualFragment());
        fm=getSupportFragmentManager();
    }

    @Override
    protected int getRootView() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListener() {

        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (vp_main.getCurrentItem()) {
                    case 0:
                        rb_home_main.setChecked(true);
                        break;
                    case 1:
                        rb_garden_main.setChecked(true);
                        break;
                    case 2:
                        rb_shop_main.setChecked(true);
                        break;
                    case 3:
                        rb_individual_main.setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageSelected(int position) {
                alterTitle(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = -1;
                switch (checkedId) {
                    case R.id.rb_home_main:
                        position = 0;
                        break;

                    case R.id.rb_garden_main:
                        position = 1;
                        break;

                    case R.id.rb_shop_main:
                        position = 2;
                        break;

                    case R.id.rb_individual_main:
                        position = 3;
                        break;

                    default:
                        break;
                }
                vp_main.setCurrentItem(position, false);
            }
        });
    }

    @Override
    protected void initData() {
        rb_home_main = (RadioButton) findViewById(R.id.rb_home_main);
        rb_garden_main = (RadioButton) findViewById(R.id.rb_garden_main);
        rb_shop_main = (RadioButton) findViewById(R.id.rb_shop_main);
        rb_individual_main = (RadioButton) findViewById(R.id.rb_individual_main);
        vp_main = (MyViewPager) findViewById(R.id.vp_main);
        tv_title_main = (TextView) findViewById(R.id.tv_title_main);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        adapter = new MyPagerAdapter(fm);
        vp_main.setOffscreenPageLimit(4);//让viewpager缓存4个页面
        vp_main.setAdapter(adapter);
    }

    private void alterTitle(int position){
        switch (position){
            case 0:
                tv_title_main.setText("首页");
                break;
            case 1:
                tv_title_main.setText("我的花园");
                break;
            case 2:
                tv_title_main.setText("市场");
                break;
            case 3:
                tv_title_main.setText("个人中心");
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
