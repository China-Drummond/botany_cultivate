package com.innovation.tencent.botany_cultivate.domain;

/**
 * Created by Mr.Jadyn on 15/10/21.
 */
public class CurrentWeather {
    private String temp;//当前温度
    private String wind_strength;
    private String wind_direction;
    private String humidity;

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_strength() {
        return wind_strength;
    }

    public void setWind_strength(String wind_strength) {
        this.wind_strength = wind_strength;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }



}
