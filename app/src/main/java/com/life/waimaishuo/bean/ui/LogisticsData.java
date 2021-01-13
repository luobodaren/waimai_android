package com.life.waimaishuo.bean.ui;

public class LogisticsData {

    private String time;
    private String state;
    private String state_describe;

    public LogisticsData(String time, String state, String state_describe) {
        this.time = time;
        this.state = state;
        this.state_describe = state_describe;
    }

    public LogisticsData(String time, String state_describe) {
        this.time = time;
        this.state_describe = state_describe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_describe() {
        return state_describe;
    }

    public void setState_describe(String state_describe) {
        this.state_describe = state_describe;
    }
}
