package com.example.myapplication.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Goods extends BaseObservable {

    @Bindable
    public String name;

    private String details;

    private float price;

    public Goods(String name, String details, float price) {
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //更新所有字段
        notifyChange();
        //只更新本字段
//        notifyPropertyChanged(com.example.myapplication.BR.name);
    }

    @Bindable
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
