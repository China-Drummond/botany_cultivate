package com.innovation.tencent.botany_cultivate.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by Mr.Jadyn on 15/10/13.
 */
public class MyApp extends Application {
    // 获取到主线程的上下文
    private static MyApp mContext = null;
    // 获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    // 获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    // 获取到主线程
    private static Thread mMainThead = null;
    // 获取到主线程的id
    private static int mMainTheadId;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mMainThreadHandler = new Handler();
        mMainThreadLooper = getMainLooper();
        mMainThead = Thread.currentThread();
        // android.os.Process.myUid() 获取到用户id
        // android.os.Process.myPid()获取到进程id
        // android.os.Process.myTid()获取到调用线程的id
        mMainTheadId = android.os.Process.myTid();


    }

    public static MyApp getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }

}
