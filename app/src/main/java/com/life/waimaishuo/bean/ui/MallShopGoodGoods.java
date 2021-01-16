package com.life.waimaishuo.bean.ui;

import java.util.List;

public class MallShopGoodGoods {

    String goodsName;
    String interestedCount;
    List<String> goodsSignList;
    List<String> goodsImglist;

    public MallShopGoodGoods(String goodsName, String interestedCount, List<String> goodsSignList, List<String> goodsImglist) {
        this.goodsName = goodsName;
        this.interestedCount = interestedCount;
        this.goodsSignList = goodsSignList;
        this.goodsImglist = goodsImglist;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getInterestedCount() {
        return interestedCount;
    }

    public void setInterestedCount(String interestedCount) {
        this.interestedCount = interestedCount;
    }

    public List<String> getGoodsSignList() {
        return goodsSignList;
    }

    public void setGoodsSignList(List<String> goodsSignList) {
        this.goodsSignList = goodsSignList;
    }

    public List<String> getGoodsImglist() {
        return goodsImglist;
    }

    public void setGoodsImglist(List<String> goodsImglist) {
        this.goodsImglist = goodsImglist;
    }
}
