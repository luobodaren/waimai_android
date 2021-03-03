package com.life.waimaishuo.bean.api.request.bean;

public class ShoppingCartOption {

    private String attrs;       //属性
    private String boxPrice;
    private String buyCount;
    private String goodsId;
    private String goodsName;
    //private String goodsPrimaryImg;
    private String isBargainGoods;
    private String isPitchOn;
    private String price;
    private String shopId;
    private String specs;
    private String versions;

    public ShoppingCartOption(String attrs, String boxPrice, String buyCount, String goodsId, String goodsName, String isBargainGoods, String isPitchOn, String price, String shopId, String specs, String versions) {
        this.attrs = attrs;
        this.boxPrice = boxPrice;
        this.buyCount = buyCount;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.isBargainGoods = isBargainGoods;
        this.isPitchOn = isPitchOn;
        this.price = price;
        this.shopId = shopId;
        this.specs = specs;
        this.versions = versions;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(String boxPrice) {
        this.boxPrice = boxPrice;
    }

    public String getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(String buyCount) {
        this.buyCount = buyCount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getIsBargainGoods() {
        return isBargainGoods;
    }

    public void setIsBargainGoods(String isBargainGoods) {
        this.isBargainGoods = isBargainGoods;
    }

    public String getIsPitchOn() {
        return isPitchOn;
    }

    public void setIsPitchOn(String isPitchOn) {
        this.isPitchOn = isPitchOn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }
}
