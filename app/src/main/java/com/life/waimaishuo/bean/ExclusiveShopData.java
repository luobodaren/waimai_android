package com.life.waimaishuo.bean;

public class ExclusiveShopData {

    String shopIconStr;
    String goodsIconStr;
    String shopName;
    String recent;

    public ExclusiveShopData(String shopIconStr, String goodsIconStr, String shopName, String recent) {
        this.shopIconStr = shopIconStr;
        this.goodsIconStr = goodsIconStr;
        this.shopName = shopName;
        this.recent = recent;
    }

    public String getShopIconStr() {
        return shopIconStr;
    }

    public String getGoodsIconStr() {
        return goodsIconStr;
    }

    public String getShopName() {
        return shopName;
    }

    public String getRecent() {
        return recent;
    }
}
