package com.life.waimaishuo.bean;

import com.google.gson.annotations.SerializedName;

public class SearchTag {

    @SerializedName(value = "id")
    private long Id;
    /**
     * 搜索标签名称
     */
    @SerializedName(value = "seachTagName")
    private String name;

    /**
     * 创建时间
     */
    @SerializedName(value = "createTime")
    private String createTime;

    @SerializedName(value = "isRecommend")
    private int isRecommend;

    public SearchTag() {
    }

    public SearchTag(long id, String name, String createTime) {
        Id = id;
        this.name = name;
        this.createTime = createTime;
    }

    public long getId() {
        return Id;
    }

    public SearchTag setId(long id) {
        Id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SearchTag setName(String name) {
        this.name = name;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int isRecommend() {
        return isRecommend;
    }

    public void setRecommend(int recommend) {
        isRecommend = recommend;
    }

    @Override
    public String toString() {
        return "SearchTag{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isRecommend=" + isRecommend +
                '}';
    }
}
