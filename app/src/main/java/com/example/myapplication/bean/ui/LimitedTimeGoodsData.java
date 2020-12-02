package com.example.myapplication.bean.ui;

public class LimitedTimeGoodsData {

    String shopIconStr;
    String shopName;

    public LimitedTimeGoodsData(String shopIconStr, String shopName) {
        this.shopIconStr = shopIconStr;
        this.shopName = shopName;
    }

    public String getShopIconStr() {
        return shopIconStr;
    }

    public void setShopIconStr(String shopIconStr) {
        this.shopIconStr = shopIconStr;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


}
