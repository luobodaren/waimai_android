package com.life.waimaishuo.bean;

public class PreferentialActivity {

    String name;
    String introduce;
    String preferentialPrice;

    public PreferentialActivity(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }

    public PreferentialActivity(String name, String introduce, String preferentialPrice) {
        this.name = name;
        this.introduce = introduce;
        this.preferentialPrice = preferentialPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }
}
