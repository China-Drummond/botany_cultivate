package com.innovation.tencent.botany_cultivate.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovation.tencent.botany_cultivate.R;

/**
 * Created by Drummond on 2015/10/19.
 */
public class MyDialog extends Dialog{

    //定义回调事件，用于dialog的点击事件
    public interface OnMyDialogListener{
        public void back(EditText editText);
    }

    private int dialogType;
    private String m_title = null;
    private String m_inform = null;
    private String m_edit = null;
    private int m_image = 0;
    private OnMyDialogListener m_myDialogListener;
    public EditText m_editText;

    public MyDialog(Context context,String title,String inform,int image,OnMyDialogListener myDialogListener){
        super(context);
        this.dialogType = 1;
        this.m_title = title;
        this.m_inform = inform;
        this.m_image = image;
        this.m_myDialogListener = myDialogListener;
    }

    public MyDialog(Context context,String title,String inform,OnMyDialogListener myDialogListener){
        super(context);
        this.dialogType = 2;
        this.m_title = title;
        this.m_inform = inform;
        this.m_myDialogListener = myDialogListener;
    }

    public MyDialog(Context context,String title,String inform,String edit,OnMyDialogListener myDialogListener){
        super(context);
        this.dialogType = 3;
        this.m_title = title;
        this.m_inform = inform;
        this.m_myDialogListener = myDialogListener;
    }

    public MyDialog(Context context,String title,OnMyDialogListener myDialogListener){
        super(context);
        this.dialogType = 4;
        this.m_title = title;
        this.m_myDialogListener = myDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        switch (dialogType){
            case 1:
                setContentView(R.layout.mydialog_image);
                setTitle(m_title);
                ImageView iv_image_image = (ImageView) findViewById(R.id.iv_image_image);
                iv_image_image.setImageResource(m_image);
                TextView tv_inform_image = (TextView) findViewById(R.id.tv_inform_image);
                tv_inform_image.setText(m_inform);
                Button btn_ok_image = (Button) findViewById(R.id.btn_ok_image);
                btn_ok_image.setOnClickListener(clickListener);
                break;
            case 2:
                setContentView(R.layout.mydialog_inform);
                setTitle(m_title);
                TextView tv_inform_inform = (TextView) findViewById(R.id.tv_inform_inform);
                tv_inform_inform.setText(m_inform);
                Button btn_ok_inform = (Button) findViewById(R.id.btn_ok_inform);
                btn_ok_inform.setOnClickListener(clickListener);
                break;
            case 3:
                setContentView(R.layout.mydialog_edit);
                setTitle(m_title);
                TextView tv_inform_edit = (TextView) findViewById(R.id.tv_inform_edit);
                tv_inform_edit.setText(m_inform);
                Button btn_ok_edit = (Button) findViewById(R.id.btn_ok_edit);
                btn_ok_edit.setOnClickListener(clickListener);
                m_editText = (EditText) findViewById(R.id.et_edit_edit);
                break;
            case 4:
                setContentView(R.layout.mydialog_progress);
                setTitle(m_title);
                Button btn_ok_progress = (Button) findViewById(R.id.btn_ok_progress);
                btn_ok_progress.setOnClickListener(clickListener);
                break;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            m_myDialogListener.back(m_editText);
            MyDialog.this.dismiss();
        }
    };

}
