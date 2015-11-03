package com.innovation.tencent.botany_cultivate.domain;

/**
 * Created by Mr.Jadyn on 15/10/21.
 */
public class TodayWeather {

    private String weather;
    private String uv_index;//紫外线强度

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getUv_index() {
        return uv_index;
    }

    public void setUv_index(String uv_index) {
        this.uv_index = uv_index;
    }
}
