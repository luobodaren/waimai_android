package com.life.waimaishuo.bean;

public class ApplyRecord {

    String imgUrl;
    String name;

    String state;
    String applyTime;

    public ApplyRecord(String imgUrl, String name, String state, String applyTime) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.state = state;
        this.applyTime = applyTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
