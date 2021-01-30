package com.life.waimaishuo.bean;

public class MemberPackage {

    String name;
    String value;
    String preferential;

    public MemberPackage(String name, String value, String preferential) {
        this.name = name;
        this.value = value;
        this.preferential = preferential;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPreferential() {
        return preferential;
    }

    public void setPreferential(String preferential) {
        this.preferential = preferential;
    }
}
