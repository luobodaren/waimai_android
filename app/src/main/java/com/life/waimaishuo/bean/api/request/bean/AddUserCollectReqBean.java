package com.life.waimaishuo.bean.api.request.bean;

public class AddUserCollectReqBean {

    public int shopId;      //店铺Id
    public int goodsId;     //商品Id
    public int collectType;     //收藏类型  1店铺 2商品

    /**
     * 仅换入一个类型对应Id
     * @param shopId
     * @param goodsId
     * @param collectType
     */
    public AddUserCollectReqBean(int shopId, int goodsId, int collectType) {
        this.shopId = shopId;
        this.goodsId = goodsId;
        this.collectType = collectType;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCollectType() {
        return collectType;
    }

    public void setCollectType(int collectType) {
        this.collectType = collectType;
    }
}
