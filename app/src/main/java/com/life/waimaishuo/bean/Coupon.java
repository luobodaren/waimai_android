package com.life.waimaishuo.bean;

/**
 * 优惠券
 */
public class Coupon {

    int id;
    String name;
    String couponUrl;
    String priceValue;
    String pricePreferential;
    String introduce;
    String useLimited;

    String remainingCount;
    String allCount;

    public Coupon(int id, String couponUrl, String name, String priceValue, String pricePreferential, String introduce, String useLimited, String remainingCount, String allCount) {
        this.id = id;
        this.couponUrl = couponUrl;
        this.name = name;
        this.priceValue = priceValue;
        this.pricePreferential = pricePreferential;
        this.introduce = introduce;
        this.useLimited = useLimited;
        this.remainingCount = remainingCount;
        this.allCount = allCount;
    }

    public String getPricePreferential() {
        return pricePreferential;
    }

    public void setPricePreferential(String pricePreferential) {
        this.pricePreferential = pricePreferential;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
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

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getUseLimited() {
        return useLimited;
    }

    public void setUseLimited(String useLimited) {
        this.useLimited = useLimited;
    }

    public String getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(String remainingCount) {
        this.remainingCount = remainingCount;
    }
}
