package com.innovation.tencent.botany_cultivate.ui.adapter;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.*;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.ui.view.FlowerItem;

import java.util.List;

/**
 * Created by Drummond on 2015/10/27.
 */
public class FlowerAdapter extends ArrayAdapter<FlowerItem> {

    private int resourceId;
    private int m_count;

    public FlowerAdapter(Context context,int textViewResourceId,List<FlowerItem> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
        m_count = objects.size() / 2 + objects.size() % 2;
    }

    @Override
    public int getCount(){
        return m_count;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        FlowerItem flowerItem_left = getItem(position * 2);
        FlowerItem flowerItem_right;

        if (position * 2 + 1 != m_count * 2 - 1){
            flowerItem_right = getItem(position * 2 + 1);
        }else flowerItem_right = null;

        final View view;
        ViewHolder viewHolder;

        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();

            //left information
            viewHolder.flowerName_left = (TextView) view.findViewById(R.id.tv_leftflower_shopitem);
            viewHolder.price_left = (TextView) view.findViewById(R.id.tv_leftprice_shopitem);
            viewHolder.area_left = (TextView) view.findViewById(R.id.tv_leftarea_shopitem);
            viewHolder.flowerImage_left = (ImageView) view.findViewById(R.id.iv_leftimage_shopitem);
            viewHolder.accountImage_left = (ImageView) view.findViewById(R.id.iv_leftcircle_shopitem);
            viewHolder.item_left = (RelativeLayout) view.findViewById(R.id.rl_left_detail);

            //right information
            viewHolder.flowerName_right = (TextView) view.findViewById(R.id.tv_rightflower_shopitem);
            viewHolder.price_right = (TextView) view.findViewById(R.id.tv_rightprice_shopitem);
            viewHolder.area_right = (TextView) view.findViewById(R.id.tv_rightarea_shopitem);
            viewHolder.flowerImage_right = (ImageView) view.findViewById(R.id.iv_rightimage_shopitem);
            viewHolder.accountImage_right = (ImageView) view.findViewById(R.id.iv_rightcircle_shopitem);
            viewHolder.item_right = (RelativeLayout) view.findViewById(R.id.rl_right_detail);

            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.flowerName_left.setText(flowerItem_left.getName());
        viewHolder.price_left.setText("$" + String.valueOf(flowerItem_left.getPrice()));
        viewHolder.area_left.setText(flowerItem_left.getArea());
        viewHolder.flowerImage_left.setImageResource(flowerItem_left.getFlowerImageID());
        viewHolder.accountImage_left.setImageResource(flowerItem_left.getAccountImageID());
        viewHolder.item_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"click left",Toast.LENGTH_SHORT).show();
                Log.d("test_left","click");
            }
        });

        if (flowerItem_right != null){
            viewHolder.flowerName_right.setText(flowerItem_right.getName());
            viewHolder.price_right.setText("$" + String.valueOf(flowerItem_right.getPrice()));
            viewHolder.area_right.setText(flowerItem_right.getArea());
            viewHolder.flowerImage_right.setImageResource(flowerItem_right.getFlowerImageID());
            viewHolder.accountImage_right.setImageResource(flowerItem_right.getAccountImageID());
            viewHolder.item_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(),"click right",Toast.LENGTH_SHORT).show();
                    Log.d("test_right","click");
                }
            });
//            viewHolder.item_right.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP){
//                        view_background.findViewById(R.id.rl_right_detail).setBackgroundColor(0x80FFFFFF);
//                    }
//
//                    if (event.getAction() == MotionEvent.ACTION_DOWN){
//                        view_background.findViewById(R.id.rl_right_detail).setBackgroundColor(0xFFFFFF);
//                        Log.d("test_right","click");
//                    }
//
//                    return false;
//                }
//            });
        }

        return view;
    }

    class ViewHolder{
        TextView flowerName_left;
        TextView flowerName_right;

        TextView price_left;
        TextView price_right;

        TextView area_left;
        TextView area_right;

        ImageView flowerImage_left;
        ImageView flowerImage_right;

        ImageView accountImage_left;
        ImageView accountImage_right;

        RelativeLayout item_left;
        RelativeLayout item_right;
    }
}
