package com.innovation.tencent.botany_cultivate.handler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Created by Mr.Jadyn on 15/10/21.
 */
public  class SlideHandler extends Handler {
    public static final int ALERT_PAGE = 0;
    public static final int STOP = 1;
    public static final int PAGE_TAG = 2;
    public static final int BREAK=3;
    public static final int DELAY = 5000;
    private Context context;
    private int currentItem = 0;
    private ViewPager vp;

    public SlideHandler(Context context, ViewPager vp) {
        this.context = context;
        this.vp = vp;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (context == null) {
            return;
        }
        if (hasMessages(ALERT_PAGE)) {
            removeMessages(ALERT_PAGE);
        }
        switch (msg.what) {
            case ALERT_PAGE:
                currentItem=(currentItem+1)%3;
                vp.setCurrentItem(currentItem);
                sendEmptyMessageDelayed(ALERT_PAGE, DELAY);
                break;
            case STOP:
                break;
            case BREAK:
                sendEmptyMessageDelayed(ALERT_PAGE, DELAY);
                break;
            case PAGE_TAG:
                currentItem = msg.arg1;
                break;
            default:
                break;
        }
    }
}