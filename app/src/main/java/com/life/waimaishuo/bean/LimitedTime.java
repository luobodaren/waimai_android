package com.life.waimaishuo.bean;

import com.life.waimaishuo.enumtype.LimitedTimeStateEnum;

public class LimitedTime {

    String time;
    LimitedTimeStateEnum state;

    public LimitedTime(String time, LimitedTimeStateEnum state) {
        this.time = time;
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LimitedTimeStateEnum getState() {
        return state;
    }

    public void setState(LimitedTimeStateEnum state) {
        this.state = state;
    }
}
