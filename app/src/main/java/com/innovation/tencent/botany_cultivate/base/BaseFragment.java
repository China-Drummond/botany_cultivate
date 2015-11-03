package com.innovation.tencent.botany_cultivate.base;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Mr.Jadyn on 15/10/13.
 */
public abstract class BaseFragment extends Fragment {
    protected BaseActivity myActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myActivity= (BaseActivity) getActivity();
        init();
        View view=inflater.inflate(getRooyView(),container,false);
        initData(view);
        setListener();

        return view;

    }

    /**
     * 设置布局
     * @return
     */
    protected abstract int getRooyView();
    /**
     * 设置监听
     */
    protected abstract void setListener();
    /**
     * 加载UI前的预初始化
     */
    protected abstract void init();
    /**
     * 请求数据，设置UI
     */
    protected abstract void initData(View view);

    @Override
    public void onResume() {
        super.onResume();

    }
}
