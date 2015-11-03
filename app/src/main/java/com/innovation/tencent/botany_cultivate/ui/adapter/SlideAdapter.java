package com.innovation.tencent.botany_cultivate.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.utils.ImageLoader;

import java.util.List;

/**
 * Created by Mr.Jadyn on 15/10/20.
 */
public class SlideAdapter extends PagerAdapter {
    private String[] urls;
    private List<View> mList;
    private ImageLoader imageLoader;

    public SlideAdapter(List<View> list, Context context, String[] urls) {
        mList = list;
        imageLoader = ImageLoader.getInstance(context);
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mList.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_slide_item);
        imageLoader.loadImage(urls[position], imageView);
        container.removeView(view);
        container.addView(view);
        return mList.get(position);
    }
}
