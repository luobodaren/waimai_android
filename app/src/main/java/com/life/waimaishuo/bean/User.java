package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    /**
     * allowance :
     * appletOpenId :
     * couponNumber : 0
     * createId : 0
     * createTime :
     * delFlag : 0
     * description :
     * dyOpenId :
     * headPortrait :
     * id : 0
     * isVip : 0
     * lostLoginTime :
     * lostPayTime :
     * nickName :
     * orderNumber : 0
     * orderPrice :
     * packetNumber : 0
     * phoneNumber :
     * qqOpenId :
     * qqUnionId :
     * registerStatus : 0
     * registrationId :
     * state : 0
     * updateId : 0
     * updateTime :
     * userPwd :
     * vipNumber :
     * vipOverTime :
     * vipStartTime :
     * wbUId :
     * wxOpenId :
     * wxUnionId :
     * zfbUnionId :
     * zfbUserId :
     */

    private String allowance;
    private String appletOpenId;
    private int couponNumber;
    private int createId;
    private String createTime;
    private int delFlag;
    private String description;
    private String dyOpenId;
    private String headPortrait;
    private int id;
    private int isVip;
    private String lostLoginTime;
    private String lostPayTime;
    private String nickName;
    private int orderNumber;
    private String orderPrice;
    private int packetNumber;
    private String phoneNumber;
    private String qqOpenId;
    private String qqUnionId;
    private int registerStatus;
    private String registrationId;
    private int state;
    private int updateId;
    private String updateTime;
    private String userPwd;
    private String vipNumber;
    private String vipOverTime;
    private String vipStartTime;
    private String wbUId;
    private String wxOpenId;
    private String wxUnionId;
    private String zfbUnionId;
    private String zfbUserId;

    protected User(Parcel in) {
        allowance = in.readString();
        appletOpenId = in.readString();
        couponNumber = in.readInt();
        createId = in.readInt();
        createTime = in.readString();
        delFlag = in.readInt();
        description = in.readString();
        dyOpenId = in.readString();
        headPortrait = in.readString();
        id = in.readInt();
        isVip = in.readInt();
        lostLoginTime = in.readString();
        lostPayTime = in.readString();
        nickName = in.readString();
        orderNumber = in.readInt();
        orderPrice = in.readString();
        packetNumber = in.readInt();
        phoneNumber = in.readString();
        qqOpenId = in.readString();
        qqUnionId = in.readString();
        registerStatus = in.readInt();
        registrationId = in.readString();
        state = in.readInt();
        updateId = in.readInt();
        updateTime = in.readString();
        userPwd = in.readString();
        vipNumber = in.readString();
        vipOverTime = in.readString();
        vipStartTime = in.readString();
        wbUId = in.readString();
        wxOpenId = in.readString();
        wxUnionId = in.readString();
        zfbUnionId = in.readString();
        zfbUserId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAllowance() {
        return allowance;
    }

    public void setAllowance(String allowance) {
        this.allowance = allowance;
    }

    public String getAppletOpenId() {
        return appletOpenId;
    }

    public void setAppletOpenId(String appletOpenId) {
        this.appletOpenId = appletOpenId;
    }

    public int getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(int couponNumber) {
        this.couponNumber = couponNumber;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDyOpenId() {
        return dyOpenId;
    }

    public void setDyOpenId(String dyOpenId) {
        this.dyOpenId = dyOpenId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getLostLoginTime() {
        return lostLoginTime;
    }

    public void setLostLoginTime(String lostLoginTime) {
        this.lostLoginTime = lostLoginTime;
    }

    public String getLostPayTime() {
        return lostPayTime;
    }

    public void setLostPayTime(String lostPayTime) {
        this.lostPayTime = lostPayTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(int packetNumber) {
        this.packetNumber = packetNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getQqUnionId() {
        return qqUnionId;
    }

    public void setQqUnionId(String qqUnionId) {
        this.qqUnionId = qqUnionId;
    }

    public int getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(int registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getVipNumber() {
        return vipNumber;
    }

    public void setVipNumber(String vipNumber) {
        this.vipNumber = vipNumber;
    }

    public String getVipOverTime() {
        return vipOverTime;
    }

    public void setVipOverTime(String vipOverTime) {
        this.vipOverTime = vipOverTime;
    }

    public String getVipStartTime() {
        return vipStartTime;
    }

    public void setVipStartTime(String vipStartTime) {
        this.vipStartTime = vipStartTime;
    }

    public String getWbUId() {
        return wbUId;
    }

    public void setWbUId(String wbUId) {
        this.wbUId = wbUId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getZfbUnionId() {
        return zfbUnionId;
    }

    public void setZfbUnionId(String zfbUnionId) {
        this.zfbUnionId = zfbUnionId;
    }

    public String getZfbUserId() {
        return zfbUserId;
    }

    public void setZfbUserId(String zfbUserId) {
        this.zfbUserId = zfbUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(allowance);
        dest.writeString(appletOpenId);
        dest.writeInt(couponNumber);
        dest.writeInt(createId);
        dest.writeString(createTime);
        dest.writeInt(delFlag);
        dest.writeString(description);
        dest.writeString(dyOpenId);
        dest.writeString(headPortrait);
        dest.writeInt(id);
        dest.writeInt(isVip);
        dest.writeString(lostLoginTime);
        dest.writeString(lostPayTime);
        dest.writeString(nickName);
        dest.writeInt(orderNumber);
        dest.writeString(orderPrice);
        dest.writeInt(packetNumber);
        dest.writeString(phoneNumber);
        dest.writeString(qqOpenId);
        dest.writeString(qqUnionId);
        dest.writeInt(registerStatus);
        dest.writeString(registrationId);
        dest.writeInt(state);
        dest.writeInt(updateId);
        dest.writeString(updateTime);
        dest.writeString(userPwd);
        dest.writeString(vipNumber);
        dest.writeString(vipOverTime);
        dest.writeString(vipStartTime);
        dest.writeString(wbUId);
        dest.writeString(wxOpenId);
        dest.writeString(wxUnionId);
        dest.writeString(zfbUnionId);
        dest.writeString(zfbUserId);
    }

    @Override
    public String toString() {
        return "User{" +
                "allowance='" + allowance + '\'' +
                ", appletOpenId='" + appletOpenId + '\'' +
                ", couponNumber=" + couponNumber +
                ", createId=" + createId +
                ", createTime='" + createTime + '\'' +
                ", delFlag=" + delFlag +
                ", description='" + description + '\'' +
                ", dyOpenId='" + dyOpenId + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", id=" + id +
                ", isVip=" + isVip +
                ", lostLoginTime='" + lostLoginTime + '\'' +
                ", lostPayTime='" + lostPayTime + '\'' +
                ", nickName='" + nickName + '\'' +
                ", orderNumber=" + orderNumber +
                ", orderPrice='" + orderPrice + '\'' +
                ", packetNumber=" + packetNumber +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qqOpenId='" + qqOpenId + '\'' +
                ", qqUnionId='" + qqUnionId + '\'' +
                ", registerStatus=" + registerStatus +
                ", registrationId='" + registrationId + '\'' +
                ", state=" + state +
                ", updateId=" + updateId +
                ", updateTime='" + updateTime + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", vipNumber='" + vipNumber + '\'' +
                ", vipOverTime='" + vipOverTime + '\'' +
                ", vipStartTime='" + vipStartTime + '\'' +
                ", wbUId='" + wbUId + '\'' +
                ", wxOpenId='" + wxOpenId + '\'' +
                ", wxUnionId='" + wxUnionId + '\'' +
                ", zfbUnionId='" + zfbUnionId + '\'' +
                ", zfbUserId='" + zfbUserId + '\'' +
                '}';
    }
}
