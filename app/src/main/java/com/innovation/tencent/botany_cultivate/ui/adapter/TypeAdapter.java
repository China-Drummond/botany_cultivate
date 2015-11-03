package com.innovation.tencent.botany_cultivate.ui.adapter;

import android.content.Context;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.innovation.tencent.botany_cultivate.R;

import java.util.List;

/**
 * Created by Drummond on 2015/10/27.
 */
public class TypeAdapter extends ArrayAdapter<String> {

    private int resourceId;

    public TypeAdapter(Context context,int textViewResourceId,List<String> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        String string = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView tv_type_typeitem = (TextView) view.findViewById(R.id.tv_type_typeitem);
        tv_type_typeitem.setText(string);
        return view;
    }

}
