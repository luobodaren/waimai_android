package com.life.waimaishuo.bean.api.request.bean;

public class ShopCommendGoodsReqBean {

    int shopId;
    int brandId;
    int pageNum;
    int pageSize;

    public ShopCommendGoodsReqBean(int shopId, int brandId, int pageNum, int pageSize) {
        this.shopId = shopId;
        this.brandId = brandId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
