package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.life.waimaishuo.enumtype.OrderStateEnum;

import java.util.List;

public class Order implements Parcelable {

    String shopName;
    String shopIconUrl;
    String orderCreateTime;
    int orderState;

    List<Foods> foodsList;

    public Order(String shopName, String shopIconUrl, String orderCreateTime, int orderState, List<Foods> foodsList) {
        this.shopName = shopName;
        this.shopIconUrl = shopIconUrl;
        this.orderCreateTime = orderCreateTime;
        this.orderState = orderState;
        this.foodsList = foodsList;
    }

    protected Order(Parcel in) {
        shopName = in.readString();
        shopIconUrl = in.readString();
        orderCreateTime = in.readString();
        orderState = in.readInt();
        foodsList = in.createTypedArrayList(Foods.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIconUrl() {
        return shopIconUrl;
    }

    public void setShopIconUrl(String shopIconUrl) {
        this.shopIconUrl = shopIconUrl;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public int getOrderState() {
        return orderState;
    }

    public String getOrderStateString(){
        return OrderStateEnum.getState(orderState);
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public List<Foods> getFoodsList() {
        return foodsList;
    }

    public void setFoodsList(List<Foods> foodsList) {
        this.foodsList = foodsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopName);
        dest.writeString(shopIconUrl);
        dest.writeString(orderCreateTime);
        dest.writeInt(orderState);
        dest.writeTypedList(foodsList);
    }

}
