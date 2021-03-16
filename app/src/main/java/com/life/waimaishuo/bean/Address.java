package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    int userId;
    String consignee;

    String province;
    String city;
    String district;
    String detailedAddress;
    String lon;
    String lat;
    String postcode;    //邮编
    String phone;
    @SerializedName(value = "isDefault")    //是否是默认收货地址 0不是 1是
    int isDefaultAddress;
    @SerializedName(value = " isCanUsers")  //是否能用 1可用 2不可用
    int isEffective;

    public Address(String consignee, String phone, int isDefaultAddress, int isEffective) {
        this.consignee = consignee;
        this.phone = phone;
        this.isDefaultAddress = isDefaultAddress;
        this.isEffective = isEffective;
    }

    protected Address(Parcel in) {
        userId = in.readInt();
        consignee = in.readString();
        province = in.readString();
        city = in.readString();
        district = in.readString();
        detailedAddress = in.readString();
        lon = in.readString();
        lat = in.readString();
        postcode = in.readString();
        phone = in.readString();
        isDefaultAddress = in.readInt();
        isEffective = in.readInt();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsDefaultAddress() {
        return isDefaultAddress;
    }

    public void setIsDefaultAddress(int isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(consignee);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(detailedAddress);
        dest.writeString(lon);
        dest.writeString(lat);
        dest.writeString(postcode);
        dest.writeString(phone);
        dest.writeInt(isDefaultAddress);
        dest.writeInt(isEffective);
    }
}
