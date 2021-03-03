package com.life.waimaishuo.bean.api.request.bean;

import com.google.gson.annotations.SerializedName;

public class SecondKillReqBean {

    @SerializedName(value = "startTime")
    private String startTime;
    @SerializedName(value = "endTime")
    private String endTime;
    @SerializedName(value = "pageNum")
    private int pageNum;
    @SerializedName(value = "pageSize")
    private int pageSize;
    @SerializedName(value = "userLonAndLat")
    private String userLonAndLat;
    @SerializedName(value = "marketingType")
    private int marketingType;  //营销类型 1商城瓷片区 2商城更多好店 3商城限时秒杀 4外卖专属早餐 5外卖限时秒杀 6外卖0元配送

    public SecondKillReqBean(String startTime, String endTime, int pageNum, int pageSize, String userLonAndLat, int marketingType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.userLonAndLat = userLonAndLat;
        this.marketingType = marketingType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserLonAndLat() {
        return userLonAndLat;
    }

    public void setUserLonAndLat(String userLonAndLat) {
        this.userLonAndLat = userLonAndLat;
    }

    public int getMarketingType() {
        return marketingType;
    }

    public void setMarketingType(int marketingType) {
        this.marketingType = marketingType;
    }

    @Override
    public String toString() {
        return "SecondKillReqBean{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", userLonAndLat='" + userLonAndLat + '\'' +
                ", marketingType=" + marketingType +
                '}';
    }
}
