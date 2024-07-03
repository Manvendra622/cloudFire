package com.example.cloudfire;

import org.json.JSONArray;
import org.json.JSONObject;

public class dataList {
    long dt;
    JSONObject main;
    JSONArray weather;

    public dataList(long dt, JSONObject main,JSONArray weather){
        this.dt=dt;
        this.weather=weather;
        this.main=main;

    }

    public JSONArray getWeather() {
        return weather;
    }

    public void setWeather(JSONArray weather) {
        this.weather = weather;
    }

    public JSONObject getMain() {
        return main;
    }

    public void setMain(JSONObject main) {
        this.main = main;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getDt() {
        return dt;
    }
}
