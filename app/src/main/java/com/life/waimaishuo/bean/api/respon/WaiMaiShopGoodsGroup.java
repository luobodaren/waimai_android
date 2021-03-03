package com.life.waimaishuo.bean.api.respon;

import com.google.gson.annotations.SerializedName;
import com.life.waimaishuo.bean.Goods;

import java.util.List;

public class WaiMaiShopGoodsGroup {

    @SerializedName(value = "groupName")
    String groupName;
    @SerializedName(value = "groupDesc")
    String groupDesc;
    @SerializedName(value = "groupIcon")
    String groupIcon;
    @SerializedName(value = "iconStatus")
    int iconStatus;
    @SerializedName(value = "goodsDeliveryListDtos")
    List<Goods> goodsDeliveryListDtos;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public int getIconStatus() {
        return iconStatus;
    }

    public void setIconStatus(int iconStatus) {
        this.iconStatus = iconStatus;
    }

    public List<Goods> getGoodsDeliveryListDtos() {
        return goodsDeliveryListDtos;
    }

    public void setGoodsDeliveryListDtos(List<Goods> goodsDeliveryListDtos) {
        this.goodsDeliveryListDtos = goodsDeliveryListDtos;
    }
}
