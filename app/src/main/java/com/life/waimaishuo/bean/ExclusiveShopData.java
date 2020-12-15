package com.life.waimaishuo.bean;

public class ExclusiveShopData {

    String shopIconStr;
    String shopName;
    String recent;

    public ExclusiveShopData(String shopIconStr, String shopName, String recent) {
        this.shopIconStr = shopIconStr;
        this.shopName = shopName;
        this.recent = recent;
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

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }
}
