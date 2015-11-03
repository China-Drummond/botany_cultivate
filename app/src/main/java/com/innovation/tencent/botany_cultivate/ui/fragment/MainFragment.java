package com.innovation.tencent.botany_cultivate.ui.fragment;


import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.base.BaseFragment;
import com.innovation.tencent.botany_cultivate.constant.API;
import com.innovation.tencent.botany_cultivate.domain.TodaySeed;
import com.innovation.tencent.botany_cultivate.domain.Weather;
import com.innovation.tencent.botany_cultivate.handler.SlideHandler;
import com.innovation.tencent.botany_cultivate.net.NetTask;
import com.innovation.tencent.botany_cultivate.ui.adapter.SlideAdapter;
import com.innovation.tencent.botany_cultivate.ui.pulltorefresh.PullToRefreshBase;
import com.innovation.tencent.botany_cultivate.ui.pulltorefresh.PullToRefreshScrollView;
import com.innovation.tencent.botany_cultivate.utils.HttpUtil;
import com.innovation.tencent.botany_cultivate.utils.ImageLoader;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFragment extends BaseFragment {
    private RadioGroup rg_slide_main;
    private RadioButton rb_dot1_main, rb_dot2_main, rb_dot3_main;
    private ViewPager vp_slide_main;
    private View slideItem;
    private View baseView;
    private TextView tv_city_main, tv_temp_main, tv_humidity_main, tv_uvindex_main, tv_wind_main, tv_weather_main;
    private ImageView iv_todayseed1_main, iv_todayseed2_main, iv_todayseed3_main;
    private TextView tv_todayseed_main, tv_todayseed1_main, tv_todayseed2_main, tv_todayseed3_main;
    private LinearLayout ll_todayseed_main, ll_todayseed1_main, ll_todayseed2_main, ll_todayseed3_main;
    private PullToRefreshScrollView ptr_main;
    private ImageLoader imageLoader;
    private LayoutInflater inflater;
    private String[] urls;
    private List<TodaySeed> todaySeeds;
    private List<View> slideItems;
    private static final int IMAGE_NUM = 3;
    private SlideAdapter adapter;
    private SlideHandler slideHandler;
    private Weather weather;

    @Override
    protected int getRooyView() {
        return R.layout.fragment_main;
    }


    @Override
    protected void setListener() {
        ptr_main.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initWeather("成都");

            }
        });
        vp_slide_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (vp_slide_main.getCurrentItem()) {
                    case 0:
                        rb_dot1_main.setChecked(true);
                        break;
                    case 1:
                        rb_dot2_main.setChecked(true);
                        break;
                    case 2:
                        rb_dot3_main.setChecked(true);
                        break;

                }
                slideHandler.sendMessage(Message.obtain(slideHandler, SlideHandler.PAGE_TAG, vp_slide_main.getCurrentItem(), 0));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        slideHandler.sendEmptyMessage(SlideHandler.STOP);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        slideHandler.sendEmptyMessageDelayed(SlideHandler.ALERT_PAGE, SlideHandler.DELAY);
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    protected void init() {
        weather = new Weather();
        urls = new String[]{
                "http://img0.imgtn.bdimg.com/it/u=510878081,2337058705&fm=21&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=1659272368,2722888983&fm=21&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=1347763669,3428842308&fm=21&gp=0.jpg"
        };
        slideItems = new ArrayList<View>();
        todaySeeds = new ArrayList<TodaySeed>();

    }


    @Override
    protected void initData(View view) {
        baseView = view;
        ptr_main = (PullToRefreshScrollView) view.findViewById(R.id.ptr_main);

        vp_slide_main = (ViewPager) view.findViewById(R.id.vp_slide_main);
        rg_slide_main = (RadioGroup) view.findViewById(R.id.rg_slide_main);
        rb_dot1_main = (RadioButton) view.findViewById(R.id.rb_dot1_main);
        rb_dot2_main = (RadioButton) view.findViewById(R.id.rb_dot2_main);
        rb_dot3_main = (RadioButton) view.findViewById(R.id.rb_dot3_main);

        tv_city_main = (TextView) view.findViewById(R.id.tv_city_main);
        tv_humidity_main = (TextView) view.findViewById(R.id.tv_humidity_main);
        tv_temp_main = (TextView) view.findViewById(R.id.tv_temp_main);
        tv_uvindex_main = (TextView) view.findViewById(R.id.tv_uvindex_main);
        tv_wind_main = (TextView) view.findViewById(R.id.tv_wind_main);
        tv_weather_main = (TextView) view.findViewById(R.id.tv_weather_main);

        ll_todayseed_main = (LinearLayout) view.findViewById(R.id.ll_todayseed_main);
        ll_todayseed1_main = (LinearLayout) view.findViewById(R.id.ll_todayseed1_main);
        ll_todayseed2_main = (LinearLayout) view.findViewById(R.id.ll_todayseed2_main);
        ll_todayseed3_main = (LinearLayout) view.findViewById(R.id.ll_todayseed3_main);
        iv_todayseed1_main = (ImageView) view.findViewById(R.id.iv_todayseed1_main);
        iv_todayseed2_main = (ImageView) view.findViewById(R.id.iv_todayseed2_main);
        iv_todayseed3_main = (ImageView) view.findViewById(R.id.iv_todayseed3_main);
        tv_todayseed_main = (TextView) view.findViewById(R.id.tv_todayseed_main);
        tv_todayseed1_main = (TextView) view.findViewById(R.id.tv_todayseed1_main);
        tv_todayseed2_main = (TextView) view.findViewById(R.id.tv_todayseed2_main);
        tv_todayseed3_main = (TextView) view.findViewById(R.id.tv_todayseed3_main);

        inflater = LayoutInflater.from(view.getContext());
        imageLoader = ImageLoader.getInstance(view.getContext());
        slideHandler = new SlideHandler(view.getContext(), vp_slide_main);
        initSlide(view);
        initWeather("成都");
        initTodaySeed();
    }

    private void initSlide(View view) {
        rb_dot1_main.setChecked(true);
        slideItems.clear();
        for (int i = 0; i < IMAGE_NUM; i++) {
            slideItem = inflater.inflate(R.layout.slide_item, null);
            slideItems.add(slideItem);
        }
        adapter = new SlideAdapter(slideItems, view.getContext(), urls);
        vp_slide_main.setAdapter(adapter);
    }

    private void initWeather(final String cityName) {
        tv_city_main.setText(cityName);
        new NetTask(baseView) {
            @Override
            protected JSONObject onLoad() {
                return HttpUtil.sendGetRequest(API.URL_WEATHER + cityName);
            }

            @Override
            protected void onSuccess(JSONObject jsonObject) throws Exception {
                weather = JSON.parseObject(jsonObject.toString(), Weather.class);
                String temp = weather.getSk().getTemp() + "C";
                String uv_index = weather.getToday().getUv_index();
                String w = weather.getToday().getWeather();
                String humidity = weather.getSk().getHumidity();
                String wind = weather.getSk().getWind_direction() + weather.getSk().getWind_strength();
                tv_temp_main.setText(temp);
                tv_uvindex_main.setText(uv_index);
                tv_weather_main.setText(w);
                tv_humidity_main.setText(humidity);
                tv_wind_main.setText(wind);
                ptr_main.onRefreshComplete();
            }

            @Override
            protected void onError(int errorCode, String errorStr) {
                super.onError(errorCode, errorStr);
                System.out.println("----->" + errorStr + errorCode);
            }

            @Override
            protected void onFail() {
                super.onFail();
            }
        }.execute();
    }


    private void initTodaySeed() {
        ll_todayseed1_main.setVisibility(View.INVISIBLE);
        ll_todayseed2_main.setVisibility(View.INVISIBLE);
        ll_todayseed3_main.setVisibility(View.INVISIBLE);
        new NetTask(baseView) {

            @Override
            protected JSONObject onLoad() {
                RequestParams params = new RequestParams();
                params.addBodyParameter("cityname", "成都");
                return HttpUtil.sendPostRequest(API.URL_TODAYSEED, params);
            }

            @Override
            protected void onSuccess(JSONObject jsonObject) throws Exception {
                todaySeeds = JSON.parseArray(jsonObject.getString("seed_array"), TodaySeed.class);
                System.out.println("------>" + todaySeeds.size());
                switch (todaySeeds.size()) {
                    case 0:
                        tv_todayseed_main.setVisibility(View.GONE);
                        ll_todayseed_main.setVisibility(View.GONE);
                        break;
                    case 1:
                        tv_todayseed1_main.setText(todaySeeds.get(0).getName());
                        imageLoader.loadImage(todaySeeds.get(0).getImage(), iv_todayseed1_main);
                        ll_todayseed1_main.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        tv_todayseed1_main.setText(todaySeeds.get(0).getName());
                        imageLoader.loadImage(todaySeeds.get(0).getImage(), iv_todayseed1_main);
                        tv_todayseed2_main.setText(todaySeeds.get(1).getName());
                        imageLoader.loadImage(todaySeeds.get(1).getImage(), iv_todayseed2_main);
                        ll_todayseed1_main.setVisibility(View.VISIBLE);
                        ll_todayseed2_main.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tv_todayseed1_main.setText(todaySeeds.get(0).getName());
                        imageLoader.loadImage(todaySeeds.get(0).getImage(), iv_todayseed1_main);
                        tv_todayseed2_main.setText(todaySeeds.get(1).getName());
                        imageLoader.loadImage(todaySeeds.get(1).getImage(), iv_todayseed2_main);
                        tv_todayseed3_main.setText(todaySeeds.get(2).getName());
                        imageLoader.loadImage(todaySeeds.get(2).getImage(), iv_todayseed3_main);
                        ll_todayseed1_main.setVisibility(View.VISIBLE);
                        ll_todayseed2_main.setVisibility(View.VISIBLE);
                        ll_todayseed3_main.setVisibility(View.VISIBLE);
                        break;
                    default:
                        Collections.shuffle(todaySeeds);
                        tv_todayseed1_main.setText(todaySeeds.get(0).getName());
                        imageLoader.loadImage(todaySeeds.get(0).getImage(), iv_todayseed1_main);
                        tv_todayseed2_main.setText(todaySeeds.get(1).getName());
                        imageLoader.loadImage(todaySeeds.get(1).getImage(), iv_todayseed2_main);
                        tv_todayseed3_main.setText(todaySeeds.get(2).getName());
                        imageLoader.loadImage(todaySeeds.get(2).getImage(), iv_todayseed3_main);
                        ll_todayseed1_main.setVisibility(View.VISIBLE);
                        ll_todayseed2_main.setVisibility(View.VISIBLE);
                        ll_todayseed3_main.setVisibility(View.VISIBLE);
                        break;
                }
                ptr_main.onRefreshComplete();
            }

            @Override
            protected void onError(int errorCode, String errorStr) {
                super.onError(errorCode, errorStr);
                System.out.println("----->" + errorStr + errorCode);
            }

            @Override
            protected void onFail() {
                super.onFail();
            }
        }.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        slideHandler.sendEmptyMessage(SlideHandler.BREAK);
    }

    @Override
    public void onStop() {
        super.onStop();
        slideHandler.sendEmptyMessage(SlideHandler.STOP);
    }
}
