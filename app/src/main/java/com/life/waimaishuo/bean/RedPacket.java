package com.life.waimaishuo.bean;

public class RedPacket {

    String name;
    String priceValue;
    String introduce;
    boolean isGet;

    public RedPacket(String name, String priceValue, String introduce, boolean isGet) {
        this.name = name;
        this.priceValue = priceValue;
        this.introduce = introduce;
        this.isGet = isGet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean get) {
        isGet = get;
    }
}
