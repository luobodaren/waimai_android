package com.life.waimaishuo.bean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.life.waimaishuo.R;

public class Foods extends BaseObservable implements Parcelable {

    private String name;
    private String shopName;
    private String details;
    private String foodsImgUrl;
    private String buyNum;

    private String price;

    private int time_send;

    private String price_deliver;
    private String price_avg_per_man;
    private int count_per_month;
    private String score;

    public Foods() {
    }

    public Foods(String name, int time_send, String foodsImgUrl, String price_deliver, int count_per_month, String score) {
        this.name = name;
        this.foodsImgUrl = foodsImgUrl;
        this.time_send = time_send;
        this.price_deliver = price_deliver;
        this.count_per_month = count_per_month;
        this.score = score;
    }

    public Foods(String name, String describe, String foodsImgUrl, String buyNum, String price) {
        this.name = name;
        this.details = describe;
        this.foodsImgUrl = foodsImgUrl;
        this.buyNum = buyNum;
        this.price = price;
    }

    protected Foods(Parcel in) {
        name = in.readString();
        details = in.readString();
        foodsImgUrl = in.readString();
        buyNum = in.readString();
        price = in.readString();
        time_send = in.readInt();
        price_deliver = in.readString();
        price_avg_per_man = in.readString();
        count_per_month = in.readInt();
        score = in.readString();
    }

    public static final Creator<Foods> CREATOR = new Creator<Foods>() {
        @Override
        public Foods createFromParcel(Parcel in) {
            return new Foods(in);
        }

        @Override
        public Foods[] newArray(int size) {
            return new Foods[size];
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

    public String getFoodsImgUrl() {
        return foodsImgUrl;
    }

    public void setFoodsImgUrl(String foodsImgUrl) {
        this.foodsImgUrl = foodsImgUrl;
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

    public int getCount_per_month() {
        return count_per_month;
    }

    public void setCount_per_month(int count_per_month) {
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
        dest.writeString(foodsImgUrl);
        dest.writeString(buyNum);
        dest.writeString(price);
        dest.writeInt(time_send);
        dest.writeString(price_deliver);
        dest.writeString(price_avg_per_man);
        dest.writeInt(count_per_month);
        dest.writeString(score);
    }

}
