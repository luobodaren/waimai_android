package com.example.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Goods extends BaseObservable implements Parcelable {

    private String name;
    private String details;
    private String goodsImgUrl;
    private String buyNum;

    private String price;

    private String price_deliver;
    private String price_avg_per_man;
    private String count_per_month;
    private String score;

    public Goods() {
    }


    protected Goods(Parcel in) {
        name = in.readString();
        details = in.readString();
        goodsImgUrl = in.readString();
        buyNum = in.readString();
        price = in.readString();
        price_deliver = in.readString();
        price_avg_per_man = in.readString();
        count_per_month = in.readString();
        score = in.readString();
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

    public String getCount_per_month() {
        return count_per_month;
    }

    public void setCount_per_month(String count_per_month) {
        this.count_per_month = count_per_month;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(details);
        dest.writeString(goodsImgUrl);
        dest.writeString(buyNum);
        dest.writeString(price);
        dest.writeString(price_deliver);
        dest.writeString(price_avg_per_man);
        dest.writeString(count_per_month);
        dest.writeString(score);
    }
}
