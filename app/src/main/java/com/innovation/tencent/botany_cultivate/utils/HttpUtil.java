package com.innovation.tencent.botany_cultivate.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mr.Jadyn on 15/8/27.
 */
public class HttpUtil {
    private static HttpUtils instance;

    private static HttpUtils getInstance() {
        if (instance == null) {
            instance = new HttpUtils();
            instance.configCurrentHttpCacheExpiry(1000 * 2);
            instance.configSoTimeout(5 * 1000);
        }
        return instance;
    }

    public static JSONObject sendPostRequest(String url, RequestParams params) {
        try {
            ResponseStream responseStream = getInstance().sendSync(HttpRequest.HttpMethod.POST, url, params);
            if (responseStream != null) {
                return new JSONObject(responseStream.readString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject sendGetRequest(String url) {
        try {
            ResponseStream responseStream = getInstance().sendSync(HttpRequest.HttpMethod.GET, url);
            if (responseStream != null) {
                return new JSONObject(responseStream.readString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 下载
     *
     * @param key 地址
     * @return
     * @throws IOException
     */
    public static InputStream downLoad(String key) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(key).openConnection();
        return conn.getInputStream();
    }
}
