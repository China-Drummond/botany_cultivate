package com.innovation.tencent.botany_cultivate.net;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.innovation.tencent.botany_cultivate.R;
import com.innovation.tencent.botany_cultivate.base.BaseActivity;
import com.innovation.tencent.botany_cultivate.ui.dialog.MyDialog;
import com.innovation.tencent.botany_cultivate.ui.pulltorefresh.PullToRefreshScrollView;
import com.innovation.tencent.botany_cultivate.utils.NetWorkUtil;
import com.innovation.tencent.botany_cultivate.utils.ThreadPoolUtil;
import com.innovation.tencent.botany_cultivate.utils.UIUtil;

import org.json.JSONObject;

/**
 * Created by Mr.Jadyn on 15/10/15.
 */
public abstract class NetTask {
    private JSONObject jsonObject;
    private Context context;
    private View baseView;
    private MyDialog myProcessDialog, myErrorDialog;
    private PullToRefreshScrollView ptr_scroll;
    public NetTask(View view) {
        this.context = view.getContext();
        this.baseView=view;
        myProcessDialog = new MyDialog(context, "请稍等", new MyDialog.OnMyDialogListener() {
            @Override
            public void back(EditText editText) {

            }
        });
        ptr_scroll= (PullToRefreshScrollView) view.findViewById(R.id.ptr_main);
        myErrorDialog = new MyDialog(context, "网络问题", "网络不给力啊", new MyDialog.OnMyDialogListener() {
            @Override
            public void back(EditText editText) {

            }
        });

    }


    public void execute() {

        if (NetWorkUtil.getInstance(context).isConnectNet()) {
            //showProgress
            onStart();
            myProcessDialog.show();
            TaskRunnable mTask = new TaskRunnable();
            ThreadPoolUtil.getLongPool().execute(mTask);
        } else {
            myErrorDialog.show();
            ptr_scroll.onRefreshComplete();
        }
    }

    /**
     * 异步任务执行前的预处理
     */
    protected void onStart() {

    }

    /**
     * 加载数据
     *
     * @return
     */
    protected abstract JSONObject onLoad();

    /**
     * 请求数据成功后的处理
     *
     * @param jsonObject
     * @throws Exception
     */
    protected abstract void onSuccess(JSONObject jsonObject) throws Exception;

    /**
     * 返回错误时的处理逻辑
     *
     * @param errorCode
     * @param errorStr
     */
    protected void onError(int errorCode, String errorStr) {
        switch (errorCode) {

        }
    }

    /**
     * 请求失败的处理逻辑
     */
    protected void onFail() {

    }

    /**
     * 完成后的处理逻辑
     */
    protected void onFinish() {

    }

    private class TaskRunnable implements Runnable {

        @Override
        public void run() {
            jsonObject = onLoad();
            UITaskRunnable uiTaskRunnable = new UITaskRunnable();
            UIUtil.runInMainThread(uiTaskRunnable);
        }
    }

    private class UITaskRunnable implements Runnable {

        @Override
        public void run() {

            if (jsonObject == null) {
                onFail();
                onFinish();
                myProcessDialog.hide();
                myErrorDialog.show();
                ptr_scroll.onRefreshComplete();
            }
            try {

                if (jsonObject.getInt("resultcode") == 200) {

                    onSuccess(jsonObject.getJSONObject("result"));
                } else {
                    int errorCode = jsonObject.getInt("resultcode");
                    String errorStr = jsonObject.getString("reason");
                    onError(errorCode, errorStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //出错了，请稍后重试
            } finally {
                onFinish();
                myProcessDialog.hide();
            }
        }
    }
}
