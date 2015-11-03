package com.innovation.tencent.botany_cultivate.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.base.BaseFragment;
import com.innovation.tencent.botany_cultivate.ui.adapter.FlowerAdapter;
import com.innovation.tencent.botany_cultivate.ui.view.FlowerItem;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailFragment extends BaseFragment {
    private ListView lv_flower_shop;
    private List<FlowerItem> flower_item_list;
    private FlowerAdapter flowerAdapter;
    private View m_view;
    private int state = 0;

    public void setFlowerList(List<FlowerItem> flowerList){
        flower_item_list = flowerList;
        state = 1;
    }

    @Override
    protected int getRooyView() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void init() {
        if (state == 0){
            flower_item_list = new ArrayList<FlowerItem>();
            initFlowerItem();
        }
    }

    @Override
    protected void initData(View view) {
        m_view = view;
        lv_flower_shop = (ListView) view.findViewById(R.id.lv_flower_shop);
        flowerAdapter = new FlowerAdapter(view.getContext(),R.layout.fragment_shop_detailitem,flower_item_list);
        lv_flower_shop.setAdapter(flowerAdapter);
    }

    private void initFlowerItem(){

        FlowerItem flowerItem1 = new FlowerItem("玫瑰",50,"成都市",R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        flower_item_list.add(flowerItem1);

        FlowerItem flowerItem2 = new FlowerItem("百合",50,"成都市",R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        flower_item_list.add(flowerItem2);

        FlowerItem flowerItem3 = new FlowerItem("茉莉",50,"成都市",R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        flower_item_list.add(flowerItem3);
    }
}
