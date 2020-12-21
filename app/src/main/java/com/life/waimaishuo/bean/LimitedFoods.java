package com.life.waimaishuo.bean;

import com.life.waimaishuo.enumtype.LimitedTimeStateEnum;

public class LimitedFoods {

    Foods foods;

    String limitedPrice;

    LimitedTimeStateEnum limitedTimeStateEnum;

    String remainingCount;

    String goodsTotalCount;

    String shopName;

    public LimitedFoods(Foods foods, String limitedPrice, String remainingCount, String goodsTotalCount, LimitedTimeStateEnum limitedTimeStateEnum, String shopName) {
        this.foods = foods;
        this.limitedPrice = limitedPrice;
        this.remainingCount = remainingCount;
        this.goodsTotalCount = goodsTotalCount;
        this.limitedTimeStateEnum = limitedTimeStateEnum;
        this.shopName = shopName;
    }

    public Foods getFoods() {
        return foods;
    }

    public void setFoods(Foods foods) {
        this.foods = foods;
    }

    public String getLimitedPrice() {
        return limitedPrice;
    }

    public void setLimitedPrice(String limitedPrice) {
        this.limitedPrice = limitedPrice;
    }

    public String getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(String remainingCount) {
        this.remainingCount = remainingCount;
    }

    public String getGoodsTotalCount() {
        return goodsTotalCount;
    }

    public void setGoodsTotalCount(String goodsTotalCount) {
        this.goodsTotalCount = goodsTotalCount;
    }

    public LimitedTimeStateEnum getLimitedTimeStateEnum() {
        return limitedTimeStateEnum;
    }

    public void setLimitedTimeStateEnum(LimitedTimeStateEnum limitedTimeStateEnum) {
        this.limitedTimeStateEnum = limitedTimeStateEnum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
