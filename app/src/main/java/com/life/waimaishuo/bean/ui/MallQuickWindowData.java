package com.life.waimaishuo.bean.ui;

public class MallQuickWindowData {

    String leftImgUrl;
    String leftCurrentPrice;
    String leftPrePrice;

    String rightImgUrl;
    String rightCurrentPrice;
    String rightPrePrice;

    public MallQuickWindowData(String leftImgUrl, String leftCurrentPrice, String leftPrePrice, String rightImgUrl, String rightCurrentPrice, String rightPrePrice) {
        this.leftImgUrl = leftImgUrl;
        this.leftCurrentPrice = leftCurrentPrice;
        this.leftPrePrice = leftPrePrice;
        this.rightImgUrl = rightImgUrl;
        this.rightCurrentPrice = rightCurrentPrice;
        this.rightPrePrice = rightPrePrice;
    }

    public String getLeftImgUrl() {
        return leftImgUrl;
    }

    public void setLeftImgUrl(String leftImgUrl) {
        this.leftImgUrl = leftImgUrl;
    }

    public String getLeftCurrentPrice() {
        return leftCurrentPrice;
    }

    public void setLeftCurrentPrice(String leftCurrentPrice) {
        this.leftCurrentPrice = leftCurrentPrice;
    }

    public String getLeftPrePrice() {
        return leftPrePrice;
    }

    public void setLeftPrePrice(String leftPrePrice) {
        this.leftPrePrice = leftPrePrice;
    }

    public String getRightImgUrl() {
        return rightImgUrl;
    }

    public void setRightImgUrl(String rightImgUrl) {
        this.rightImgUrl = rightImgUrl;
    }

    public String getRightCurrentPrice() {
        return rightCurrentPrice;
    }

    public void setRightCurrentPrice(String rightCurrentPrice) {
        this.rightCurrentPrice = rightCurrentPrice;
    }

    public String getRightPrePrice() {
        return rightPrePrice;
    }

    public void setRightPrePrice(String rightPrePrice) {
        this.rightPrePrice = rightPrePrice;
    }
}