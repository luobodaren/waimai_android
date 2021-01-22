package com.life.waimaishuo.bean;

public class Address {

    String address;

    String user_name;

    String phone;

    boolean isDefaultAddress;

    boolean isEffective;

    public Address(String address, String user_name, String phone, boolean isDefaultAddress, boolean isEffective) {
        this.address = address;
        this.user_name = user_name;
        this.phone = phone;
        this.isDefaultAddress = isDefaultAddress;
        this.isEffective = isEffective;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }
}
