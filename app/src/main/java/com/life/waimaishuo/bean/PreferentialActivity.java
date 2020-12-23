package com.life.waimaishuo.bean;

public class PreferentialActivity {

    String name;
    String introduce;

    public PreferentialActivity(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
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
}
