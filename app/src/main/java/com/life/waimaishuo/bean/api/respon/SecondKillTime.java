package com.life.waimaishuo.bean.api.respon;

import com.google.gson.annotations.SerializedName;

public class SecondKillTime {

    @SerializedName(value = "startTime")
    String startTime;   //开始时间
    @SerializedName(value = "overTime")
    String overTime;    //结束时间
    @SerializedName(value = "nextStartTime")
    String nextStartTime;   //下次开始时间
    @SerializedName(value = "nextOverTime")
    String nextOverTime;    //下次结束时间

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getNextStartTime() {
        return nextStartTime;
    }

    public void setNextStartTime(String nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    public String getNextOverTime() {
        return nextOverTime;
    }

    public void setNextOverTime(String nextOverTime) {
        this.nextOverTime = nextOverTime;
    }

    public void setAllTimeDefault(){
        startTime = "";
        overTime = "";
        nextStartTime = "";
        nextOverTime = "";
    }

    public void setAllTime(String time){
        startTime = time;
        overTime = time;
        nextStartTime = time;
        nextOverTime = time;
    }
}
