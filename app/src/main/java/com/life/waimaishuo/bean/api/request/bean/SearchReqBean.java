package com.life.waimaishuo.bean.api.request.bean;

import java.util.Arrays;

public class SearchReqBean {

    /*必要属性*/
    String province;    //省
    String city;        //城市
    String district;    //区
    String userLonAndLat;//经纬度 "113.94736,22.544748" 先经度后纬度
    int pageNum;        //页数
    int pageSize;       //页显示数
    int queryType;      //查询类型  0推荐商家 1发现好物 2 0元配送
    /*非必要属性*/
    int activity;       //活动类型
    int recommendType;  //推荐分类
    String oneType;     //一级分类
    String twoType;     //二级分类
    int brandId;        //品牌Id
    int sortRules;      //排序选择 0销量降序 1起送价升序 2配送最快 3配送费最低 4距离最近 5综合排序(店铺评分) 6人均从低到高 7人均从高到底
    String shopCategory;//多个类型使用‘-’隔开 “美食-菜品”
    String[] activityType;  //1满减活动,2会员活动,3津贴优惠,4配送优惠,5拼单购物,6优惠券
    String maxAvgPrice;     //人均最高价
    String minAvgPrice;     //人均最低价
    String[] shopIds;       //参加活动的商家的id集合(例:[“1”,”2”,”3”])
    String queryStr;    //用户要搜索的字段  可为空

    String startTime;   //秒杀抢购开始 结束时间
    String endTime;

    /**
     * 必要属性 初始化
     * @param province
     * @param city
     * @param district
     * @param userLonAndLat
     * @param pageNum
     * @param pageSize
     * @param queryType
     */
    public SearchReqBean(String province, String city, String district, String userLonAndLat, int pageNum, int pageSize, int queryType) {
        this.province = province;
        this.city = city;
        this.district = district;
        this.userLonAndLat = userLonAndLat;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.queryType = queryType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUserLonAndLat() {
        return userLonAndLat;
    }

    public void setUserLonAndLat(String userLonAndLat) {
        this.userLonAndLat = userLonAndLat;
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

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(int recommendType) {
        this.recommendType = recommendType;
    }

    public String getOneType() {
        return oneType;
    }

    public void setOneType(String oneType) {
        this.oneType = oneType;
    }

    public String getTwoType() {
        return twoType;
    }

    public void setTwoType(String twoType) {
        this.twoType = twoType;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getSortRules() {
        return sortRules;
    }

    public void setSortRules(int sortRules) {
        this.sortRules = sortRules;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String[] getActivityType() {
        return activityType;
    }

    public void setActivityType(String[] activityType) {
        this.activityType = activityType;
    }

    public String getMaxAvgPrice() {
        return maxAvgPrice;
    }

    public void setMaxAvgPrice(String maxAvgPrice) {
        this.maxAvgPrice = maxAvgPrice;
    }

    public String getMinAvgPrice() {
        return minAvgPrice;
    }

    public void setMinAvgPrice(String minAvgPrice) {
        this.minAvgPrice = minAvgPrice;
    }

    public String[] getShopIds() {
        return shopIds;
    }

    public void setShopIds(String[] shopIds) {
        this.shopIds = shopIds;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SearchReqBean{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", userLonAndLat='" + userLonAndLat + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", queryType=" + queryType +
                ", activity=" + activity +
                ", recommendType=" + recommendType +
                ", oneType='" + oneType + '\'' +
                ", twoType='" + twoType + '\'' +
                ", brandId=" + brandId +
                ", sortRules=" + sortRules +
                ", shopCategory='" + shopCategory + '\'' +
                ", activityType=" + Arrays.toString(activityType) +
                ", maxAvgPrice='" + maxAvgPrice + '\'' +
                ", minAvgPrice='" + minAvgPrice + '\'' +
                ", shopIds=" + Arrays.toString(shopIds) +
                ", queryStr='" + queryStr + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
