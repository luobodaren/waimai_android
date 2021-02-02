package com.life.waimaishuo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExclusiveShopData {

    @SerializedName(value = "shopImage")
    private String shopIconStr;
    @SerializedName(value = "shopName")
    private String shopName;
    @SerializedName(value = "monSalesVolume")
    int recent;

    @SerializedName(value = "goodsInfo")
    List<Goods> goodsList;

    public ExclusiveShopData(String shopIconStr, String shopName, int recent) {
        this.shopIconStr = shopIconStr;
        this.shopName = shopName;
        this.recent = recent;
    }

    public String getShopIconStr() {
        return shopIconStr;
    }


    public String getShopName() {
        return shopName;
    }

    public int getRecent() {
        return recent;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }
}
