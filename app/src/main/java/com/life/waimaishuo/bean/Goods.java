package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class Goods extends BaseObservable implements Parcelable {

    @SerializedName(value = "id")
    private int id;
    @SerializedName(value = "shopId")
    private int shopId;
    @SerializedName(value = "goodsName")
    private String name;
    private String shopName;
    private String details;
    @SerializedName(value = "goodsPrimaryImg")
    private String goodsImgUrl;
    private String buyNum;

    private String price;

    private int time_send;

    private String price_deliver;
    private String price_avg_per_man;
    @SerializedName(value = "monSalesVolume")
    private int monSalesVolume;
    private String score;
    private String lowestPriceOf15Days;

    @SerializedName(value = "goodsTag")
    private int goodsTag;

    public Goods() {
    }

    public Goods(String name, int time_send, String goodsImgUrl, String price_deliver, int monSalesVolume, String score) {
        this.name = name;
        this.goodsImgUrl = goodsImgUrl;
        this.time_send = time_send;
        this.price_deliver = price_deliver;
        this.monSalesVolume = monSalesVolume;
        this.score = score;
    }

    public Goods(String name, String describe, String goodsImgUrl, String buyNum, String price) {
        this.name = name;
        this.details = describe;
        this.goodsImgUrl = goodsImgUrl;
        this.buyNum = buyNum;
        this.price = price;
    }


    protected Goods(Parcel in) {
        name = in.readString();
        shopName = in.readString();
        details = in.readString();
        goodsImgUrl = in.readString();
        buyNum = in.readString();
        price = in.readString();
        time_send = in.readInt();
        price_deliver = in.readString();
        price_avg_per_man = in.readString();
        monSalesVolume = in.readInt();
        score = in.readString();
        lowestPriceOf15Days = in.readString();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public int getTime_send() {
        return time_send;
    }

    public void setTime_send(int time_send) {
        this.time_send = time_send;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //更新所有字段
        notifyChange();
        //只更新本字段
//        notifyPropertyChanged(com.example.myapplication.BR.name);
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(String buyNum) {
        this.buyNum = buyNum;
    }

    public String getPrice_deliver() {
        return price_deliver;
    }

    public void setPrice_deliver(String price_deliver) {
        this.price_deliver = price_deliver;
    }

    public String getPrice_avg_per_man() {
        return price_avg_per_man;
    }

    public void setPrice_avg_per_man(String price_avg_per_man) {
        this.price_avg_per_man = price_avg_per_man;
    }

    public int getMonSalesVolume() {
        return monSalesVolume;
    }

    public void setMonSalesVolume(int monSalesVolume) {
        this.monSalesVolume = monSalesVolume;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLowestPriceOf15Days() {
        return lowestPriceOf15Days;
    }

    public void setLowestPriceOf15Days(String lowestPriceOf15Days) {
        this.lowestPriceOf15Days = lowestPriceOf15Days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shopName);
        dest.writeString(details);
        dest.writeString(goodsImgUrl);
        dest.writeString(buyNum);
        dest.writeString(price);
        dest.writeInt(time_send);
        dest.writeString(price_deliver);
        dest.writeString(price_avg_per_man);
        dest.writeInt(monSalesVolume);
        dest.writeString(score);
        dest.writeString(lowestPriceOf15Days);
    }
}
