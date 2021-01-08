package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.life.waimaishuo.enumtype.OrderStateEnum;

import java.util.Iterator;
import java.util.List;

public class Order implements Parcelable {

    String shopName;
    String shopIconUrl;
    String orderCreateTime;
    int orderType;
    int orderState;
    List<Foods> foodsList;

    String string_1; //可用于存储自定义信息


    public Order(String shopName, String shopIconUrl, String orderCreateTime, int orderState, List<Foods> foodsList, int orderType) {
        this.shopName = shopName;
        this.shopIconUrl = shopIconUrl;
        this.orderCreateTime = orderCreateTime;
        this.orderState = orderState;
        this.foodsList = foodsList;
        this.orderType = orderType;
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

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getString_1() {
        return string_1;
    }

    public void setString_1(String string_1) {
        this.string_1 = string_1;
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

    public double getOrderPrice(){
        Foods foods;
        double prices = 0f;
        Iterator<Foods> iterator = foodsList.iterator();
        if(iterator.hasNext()){
            foods = iterator.next();
            prices += Double.valueOf(foods.getPrice());
        }
        return prices;
    }

}
