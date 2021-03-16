package com.life.waimaishuo.bean.api.request.bean;

/**
 * 获取用户红包 请求类
 */
public class GetUserCouponReqBean {

    /**
     * allPrice :
     * couponType : 0
     * delFlag : 0
     * pageNum : 0
     * pageSize : 0
     * shopId : 0
     * shopType : 0
     * state : 0
     * useScene : 0
     * userId : 0
     */

    private String allPrice;
    private int couponType;
    private int delFlag;
    private int pageNum;
    private int pageSize;
    private int shopId;
    private int shopType;
    private int state;
    private int useScene;
    private int userId;

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUseScene() {
        return useScene;
    }

    public void setUseScene(int useScene) {
        this.useScene = useScene;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
