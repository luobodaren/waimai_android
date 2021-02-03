package com.life.waimaishuo.bean.api;

import com.google.gson.annotations.SerializedName;

public class SimpleString {

    @SerializedName(value = "id")
    int id;
    @SerializedName(value = "typeName")
    String name;

    public SimpleString() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
