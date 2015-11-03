package com.innovation.tencent.botany_cultivate.domain;

/**
 * Created by Mr.Jadyn on 15/10/21.
 */
public class Weather {
    private CurrentWeather sk;
    private TodayWeather today;

    public Weather() {
        if (sk == null) {
            sk = new CurrentWeather();
        }
        if (today == null) {
            today = new TodayWeather();
        }
    }

    public CurrentWeather getSk() {

        return sk;
    }

    public void setSk(CurrentWeather sk) {
        this.sk = sk;
    }

    public TodayWeather getToday() {

        return today;
    }

    public void setToday(TodayWeather today) {
        this.today = today;
    }
}
