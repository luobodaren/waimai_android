package com.example.myapplication.bean;

public class Shop {

    String shopName;

    String shopIcon;

    String score;

    String number_of_fans;  //粉丝数

    String announcement;    //公告

    String sale_count_per_month;

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
