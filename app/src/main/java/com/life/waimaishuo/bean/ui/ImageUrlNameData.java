package com.life.waimaishuo.bean.ui;

import com.google.gson.annotations.SerializedName;

public class ImageUrlNameData {

    @SerializedName(value = "sort", alternate = {"Sort"})
    int sort;
    @SerializedName(value = "imgUrl",alternate = {"ImgUrl","url"})
    String url;
    @SerializedName(value = "decorationName")
    String name;
    @SerializedName(value = "decorationContentMap") //配合金刚区接口数据获取
    ImageUrlNameData imageUrlNameData;

    public ImageUrlNameData(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public ImageUrlNameData() { }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageUrlNameData getImageUrlNameData() {
        return imageUrlNameData;
    }

    public void setImageUrlNameData(ImageUrlNameData imageUrlNameData) {
        this.imageUrlNameData = imageUrlNameData;
    }
}
