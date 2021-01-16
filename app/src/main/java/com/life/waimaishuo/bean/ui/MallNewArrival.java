package com.life.waimaishuo.bean.ui;

import java.util.List;

public class MallNewArrival {

    String topBackgroundImgUrl;
    String seriesName;
    String seriesDescribe;
    List<TypeDescribeValue> goodsList;

    public MallNewArrival(String topBackgroundImgUrl, String seriesName, String seriesDescribe, List<TypeDescribeValue> goodsList) {
        this.topBackgroundImgUrl = topBackgroundImgUrl;
        this.seriesName = seriesName;
        this.seriesDescribe = seriesDescribe;
        this.goodsList = goodsList;
    }

    public String getTopBackgroundImgUrl() {
        return topBackgroundImgUrl;
    }

    public void setTopBackgroundImgUrl(String topBackgroundImgUrl) {
        this.topBackgroundImgUrl = topBackgroundImgUrl;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesDescribe() {
        return seriesDescribe;
    }

    public void setSeriesDescribe(String seriesDescribe) {
        this.seriesDescribe = seriesDescribe;
    }

    public List<TypeDescribeValue> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<TypeDescribeValue> goodsList) {
        this.goodsList = goodsList;
    }
}
