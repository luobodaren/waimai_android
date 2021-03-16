package com.life.waimaishuo.bean.api.request.bean;

public class AddShippingAddress {

    /**
     * city :
     * consignee :
     * createTime :
     * delFlag : 0
     * detailedAddress :
     * district :
     * id : 0
     * isCanUsers : 0
     * isDefault : 0
     * lat :
     * lon :
     * phone :
     * postcode :
     * province :
     * updateTime :
     * userId : 0
     */

    private String city;
    private String consignee;
    private String createTime;
    private int delFlag;
    private String detailedAddress;
    private String district;
    private int id;
    private int isCanUsers;
    private int isDefault;
    private String lat;
    private String lon;
    private String phone;
    private String postcode;
    private String province;
    private String updateTime;
    private int userId;

    public AddShippingAddress(String province, String city, String district, String detailedAddress, String postcode, String lat, String lon, String consignee, String phone, int userId, int isDefault) {
        this.city = city;
        this.consignee = consignee;
        this.detailedAddress = detailedAddress;
        this.district = district;
        this.isDefault = isDefault;
        this.lat = lat;
        this.lon = lon;
        this.phone = phone;
        this.postcode = postcode;
        this.province = province;
        this.userId = userId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsCanUsers() {
        return isCanUsers;
    }

    public void setIsCanUsers(int isCanUsers) {
        this.isCanUsers = isCanUsers;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
