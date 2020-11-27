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

    public Goods(String name, String details, String goodsImgUrl, String buyNum, String price) {
        this.name = name;
        this.details = details;
        this.goodsImgUrl = goodsImgUrl;
        this.buyNum = buyNum;
        this.price = price;
    }

    protected Goods(Parcel in) {
        name = in.readString();
        details = in.readString();
        goodsImgUrl = in.readString();
        buyNum = in.readString();
        price = in.readString();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(details);
        dest.writeString(goodsImgUrl);
        dest.writeString(buyNum);
        dest.writeString(price);
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

    @Override
    public int describeContents() {
        return 0;
    }

}
