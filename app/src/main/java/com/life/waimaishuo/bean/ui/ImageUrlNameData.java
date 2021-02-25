package com.life.waimaishuo.bean.ui;

import com.google.gson.annotations.SerializedName;

public class ImageUrlNameData {

    @SerializedName(value = "sort", alternate = {"Sort"})
    int sort;
    @SerializedName(value = "imgUrl",alternate = {"ImgUrl","img","typeIcon"})
    String imgUrl;
    @SerializedName(value = "url")  //一般为跳转链接
    String url;
    @SerializedName(value = "decorationName",alternate = {"typeName"})
    String name;
    @SerializedName(value = "desc")
    String describe;
    @SerializedName(value = "decorationContentMap") //配合金刚区接口数据获取 (包括其他大部分接口
    ImageUrlNameData imageUrlNameData;

    @SerializedName(value = "brandId")
    int brandId; //配合活动专区-品牌专区 获取品牌Id

    public ImageUrlNameData(String imgUrl, String name) {
        this.imgUrl = imgUrl;
        this.name = name;
    }

    public ImageUrlNameData() { }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ImageUrlNameData getImageUrlNameData() {
        return imageUrlNameData;
    }

    public void setImageUrlNameData(ImageUrlNameData imageUrlNameData) {
        this.imageUrlNameData = imageUrlNameData;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
}
