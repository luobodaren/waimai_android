package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 红包
 */
public class RedPacket implements Parcelable {

    int id;
    String name;
    String priceValue;
    String introduce;
    String userLimited;
    String useTimeLimited;
    String orderLimited;
    Shop shop;
    boolean isGet;
    boolean isEffective;

    public RedPacket(String name, String priceValue, String introduce, boolean isGet) {
        this.name = name;
        this.priceValue = priceValue;
        this.introduce = introduce;
        this.isGet = isGet;
    }

    public RedPacket(int id, String name, String priceValue, String introduce, String userLimited, String useTimeLimited, boolean isGet, String orderLimited, boolean isEffective) {
        this.id = id;
        this.name = name;
        this.priceValue = priceValue;
        this.introduce = introduce;
        this.userLimited = userLimited;
        this.useTimeLimited = useTimeLimited;
        this.isGet = isGet;
        this.orderLimited = orderLimited;
        this.isEffective = isEffective;
    }

    protected RedPacket(Parcel in) {
        id = in.readInt();
        name = in.readString();
        priceValue = in.readString();
        introduce = in.readString();
        userLimited = in.readString();
        useTimeLimited = in.readString();
        orderLimited = in.readString();
        shop = in.readParcelable(Shop.class.getClassLoader());
        isGet = in.readByte() != 0;
        isEffective = in.readByte() != 0;
    }

    public static final Creator<RedPacket> CREATOR = new Creator<RedPacket>() {
        @Override
        public RedPacket createFromParcel(Parcel in) {
            return new RedPacket(in);
        }

        @Override
        public RedPacket[] newArray(int size) {
            return new RedPacket[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean get) {
        isGet = get;
    }

    public String getUserLimited() {
        return userLimited;
    }

    public void setUserLimited(String userLimited) {
        this.userLimited = userLimited;
    }

    public String getUseTimeLimited() {
        return useTimeLimited;
    }

    public void setUseTimeLimited(String useTimeLimited) {
        this.useTimeLimited = useTimeLimited;
    }

    public String getOrderLimited() {
        return orderLimited;
    }

    public void setOrderLimited(String orderLimited) {
        this.orderLimited = orderLimited;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(priceValue);
        dest.writeString(introduce);
        dest.writeString(userLimited);
        dest.writeString(useTimeLimited);
        dest.writeString(orderLimited);
        dest.writeParcelable(shop, flags);
        dest.writeByte((byte) (isGet ? 1 : 0));
        dest.writeByte((byte) (isEffective ? 1 : 0));
    }
}
