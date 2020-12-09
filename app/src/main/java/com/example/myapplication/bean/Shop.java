package com.example.myapplication.bean;

public class Shop {

    String shopName;

    String shopIcon;

    float score;

    String number_of_fans;  //粉丝数

    String announcement;    //公告

    String sale_count_per_month;

    MemberCard memberCard;

    public Shop() { }

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getNumber_of_fans() {
        return number_of_fans;
    }

    public void setNumber_of_fans(String number_of_fans) {
        this.number_of_fans = number_of_fans;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getSale_count_per_month() {
        return sale_count_per_month;
    }

    public void setSale_count_per_month(String sale_count_per_month) {
        this.sale_count_per_month = sale_count_per_month;
    }

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }
}
