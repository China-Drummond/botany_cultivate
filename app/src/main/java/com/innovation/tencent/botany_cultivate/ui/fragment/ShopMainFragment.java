package com.innovation.tencent.botany_cultivate.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.base.BaseFragment;
import com.innovation.tencent.botany_cultivate.ui.adapter.TypeAdapter;
import com.innovation.tencent.botany_cultivate.ui.view.FlowerItem;

import java.util.ArrayList;
import java.util.List;

public class ShopMainFragment extends BaseFragment {

    private ListView lv_type_shop;
    private List<String> type_item_list;
    private TypeAdapter typeAdapter;

    @Override
    protected int getRooyView() {
        return R.layout.fragment_shop_main;
    }

    @Override
    protected void setListener() {

        lv_type_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = type_item_list.get(position);
                List<FlowerItem> flowerList = new ArrayList<FlowerItem>();
                flowerList.add(new FlowerItem(name,50,"成都市",R.mipmap.ic_launcher,R.mipmap.ic_launcher));
                flowerList.add(new FlowerItem(name, 50, "成都市", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                flowerList.add(new FlowerItem(name, 50, "成都市", R.mipmap.ic_launcher, R.mipmap.ic_launcher));
                flowerList.add(new FlowerItem(name, 50, "成都市", R.mipmap.ic_launcher, R.mipmap.ic_launcher));

                ShopDetailFragment shopDetailFragment = new ShopDetailFragment();
                ArrayList list = new ArrayList();
                list.add(flowerList);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", list);
                shopDetailFragment.setArguments(bundle);

                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fl_detail_shop, shopDetailFragment);
                transaction.commit();

                //shopDetailFragment.setFlowerList(flowerList);
            }
        });
    }

    @Override
    protected void init() {

        type_item_list = new ArrayList<String>();

        initTypeItem();

        ShopDetailFragment shopDetailFragment = new ShopDetailFragment();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl_detail_shop, shopDetailFragment);
        transaction.commit();
    }

    @Override
    protected void initData(View view) {

        lv_type_shop = (ListView) view.findViewById(R.id.lv_type_shop);
        typeAdapter = new TypeAdapter(view.getContext(),R.layout.frament_shop_typeitem,type_item_list);
        lv_type_shop.setAdapter(typeAdapter);
    }

    private void initTypeItem(){

        type_item_list.add("玫瑰");
        type_item_list.add("百合");
        type_item_list.add("兰花");
        type_item_list.add("梅花");
        type_item_list.add("紫罗兰");
    }

}
