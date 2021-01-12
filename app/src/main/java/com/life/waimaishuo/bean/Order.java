package com.life.waimaishuo.bean;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.enumtype.OrderStateEnum;

import java.util.Iterator;
import java.util.List;

public class Order implements Parcelable {

    String shopName;
    String shopIconUrl;
    String orderCreateTime;
    int orderType;
    int orderState;
    List<Goods> goodsList;

    String string_1; //可用于存储自定义信息


    public Order(String shopName, String shopIconUrl, String orderCreateTime, int orderState, List<Goods> goodsList, int orderType) {
        this.shopName = shopName;
        this.shopIconUrl = shopIconUrl;
        this.orderCreateTime = orderCreateTime;
        this.orderState = orderState;
        this.goodsList = goodsList;
        this.orderType = orderType;
    }

    protected Order(Parcel in) {
        shopName = in.readString();
        shopIconUrl = in.readString();
        orderCreateTime = in.readString();
        orderState = in.readInt();
        goodsList = in.createTypedArrayList(Goods.CREATOR);
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

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
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
        dest.writeTypedList(goodsList);
    }

    /**
     * 获得简要的订单商品信息 带格式
     *
     * @param isWithPrice
     * @return
     */
    public String getFoodsSimpleInfo(Context context, boolean isWithPrice) {
        String foodsInfo = "";
        if (goodsList.size() > 1) {
            foodsInfo = goodsList.get(0).getName() + " " +
                    String.format(context.getString(R.string.goods_number), String.valueOf(goodsList.size()));
        } else if (goodsList.size() == 1) {
            foodsInfo = goodsList.get(0).getName();
        } else {
            LogUtil.d("订单商品信息出错，没有商品信息");
        }
        if (isWithPrice) {
            foodsInfo = foodsInfo + " " + getOrderPrice(context);
        }
        return foodsInfo;
    }

    /**
     * 获得订单价格 带格式
     */
    public String getOrderPrice(Context context) {
        return context.getString(R.string.goods_price, String.valueOf(getOrderPrice()));
    }

    public double getOrderPrice(){
        Goods goods;
        double prices = 0f;
        Iterator<Goods> iterator = goodsList.iterator();
        if(iterator.hasNext()){
            goods = iterator.next();
            prices += Double.valueOf(goods.getPrice());
        }
        return prices;
    }

    public Goods getFirstFoods(){
        if(goodsList != null && goodsList.size() >= 1){
            return goodsList.get(0);
        }else {
            return null;
        }
    }

}
