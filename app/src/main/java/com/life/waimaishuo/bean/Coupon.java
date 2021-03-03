package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * 优惠券
 */
public class Coupon implements Parcelable {

    int id;
    @SerializedName(value = "couponName")
    String name;
    @SerializedName(value = "couponType")
    String couponType;  //优惠劵类型( 1红包 2无门槛券 3满减活动 4折扣券(商品活动) 5津贴 6满减  7门店拼单购物)
    @SerializedName(value = "typeTag")
    String typeTag;     //类型标签
    @SerializedName(value = "getStatus")
    int getStatus;  //判断用户是否领取 0未领取 1领取
    @SerializedName(value = "shopId")
    int shopId; //门店ID （0为平台
    @SerializedName(value = "usedAmount")
    String priceValue;  //用卷金额（减多少）
    @SerializedName(value = "discount")
    String discount;    //折扣数
    @SerializedName(value = "withAmount")
    String withAmount;  //满多少金额

    @SerializedName(value = "validType")
    int validType;  //时效类型(0无过期 1绝对时效（领取后XXX-XXX时间段有效） 2相对时效（领取后N天有效）)
    @SerializedName(value = "validDays")
    int validDays;  //自领取之日起有效天数
    @SerializedName(value = "validStartTime")
    String validStartTime;  //使用开始时间
    @SerializedName(value = "validEndTime")
    String validEndTime;    //使用结束时间

    String pricePreferential;
    String introduce;
    String useLimited;

    String remainingCount;
    String allCount;

    String couponUrl;

    public Coupon(int id, String couponUrl, String name, String priceValue, String pricePreferential, String introduce, String useLimited, String remainingCount, String allCount) {
        this.id = id;
        this.couponUrl = couponUrl;
        this.name = name;
        this.priceValue = priceValue;
        this.pricePreferential = pricePreferential;
        this.introduce = introduce;
        this.useLimited = useLimited;
        this.remainingCount = remainingCount;
        this.allCount = allCount;
    }

    protected Coupon(Parcel in) {
        id = in.readInt();
        name = in.readString();
        couponType = in.readString();
        typeTag = in.readString();
        getStatus = in.readInt();
        shopId = in.readInt();
        priceValue = in.readString();
        discount = in.readString();
        withAmount = in.readString();
        validType = in.readInt();
        validDays = in.readInt();
        validStartTime = in.readString();
        validEndTime = in.readString();
        pricePreferential = in.readString();
        introduce = in.readString();
        useLimited = in.readString();
        remainingCount = in.readString();
        allCount = in.readString();
        couponUrl = in.readString();
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

    public String getPricePreferential() {
        return pricePreferential;
    }

    public void setPricePreferential(String pricePreferential) {
        this.pricePreferential = pricePreferential;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

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

    public String getUseLimited() {
        return useLimited;
    }

    public void setUseLimited(String useLimited) {
        this.useLimited = useLimited;
    }

    public String getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(String remainingCount) {
        this.remainingCount = remainingCount;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }

    public int getGetStatus() {
        return getStatus;
    }

    public void setGetStatus(int getStatus) {
        this.getStatus = getStatus;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getWithAmount() {
        return withAmount;
    }

    public void setWithAmount(String withAmount) {
        this.withAmount = withAmount;
    }

    public int getValidType() {
        return validType;
    }

    public void setValidType(int validType) {
        this.validType = validType;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    public String getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(String validStartTime) {
        this.validStartTime = validStartTime;
    }

    public String getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(String validEndTime) {
        this.validEndTime = validEndTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(couponType);
        dest.writeString(typeTag);
        dest.writeInt(getStatus);
        dest.writeInt(shopId);
        dest.writeString(priceValue);
        dest.writeString(discount);
        dest.writeString(withAmount);
        dest.writeInt(validType);
        dest.writeInt(validDays);
        dest.writeString(validStartTime);
        dest.writeString(validEndTime);
        dest.writeString(pricePreferential);
        dest.writeString(introduce);
        dest.writeString(useLimited);
        dest.writeString(remainingCount);
        dest.writeString(allCount);
        dest.writeString(couponUrl);
    }

    /**
     * 通过优惠劵类型匹配 获取对应的介绍字符串
     * @return
     */
    public String getIntroduceByType(){
        String introduce = "";
        switch (couponType){
            case "3":
            case "6":
                introduce = String.format("%1$s减%2$s",withAmount,priceValue);
                break;
            case "4":
                introduce = String.format("折扣商品%s折起",discount);
                break;
        }
        return introduce;
    }
}
