package com.life.waimaishuo.bean.api.request.bean;

public class ShopEvaluateReqBean {

    int shopId;
    int pageNum;
    int pageSize;
    int haveIcon;   //是否有图 0表示不限制 1表示有图

    public ShopEvaluateReqBean(int shopId, int pageNum, int pageSize, int haveIcon) {
        this.shopId = shopId;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.haveIcon = haveIcon;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
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

    public int getHaveIcon() {
        return haveIcon;
    }

    public void setHaveIcon(int haveIcon) {
        this.haveIcon = haveIcon;
    }
}
