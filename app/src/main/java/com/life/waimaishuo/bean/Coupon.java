package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 优惠券
 */
public class Coupon implements Parcelable {

    /**
     * id : 2828
     * shopId : 14914
     * shopName : 星巴克咖啡（国家工程实验室大楼店）
     * shopHeadPortrait : http://cjwm-pic.oss-cn-beijing.aliyuncs.com/wms270c2710-7b64-49d0-deb0-6b275be48257.jpg
     * couponId : 2967
     * couponName : 满60减20
     * couponPhoto : null
     * used : 1
     * couponType : 3
     * withSpecial : 0
     * supportTenantId : null
     * withAmount : 60
     * usedAmount : 20
     * discount : null
     * securities : 0
     * takeCount : 0
     * useScene : 0
     * usedCount : 0
     * startTime : 2021-02-09 23:59:11
     * endTime : 2022-01-01
     * validType : 1
     * validStartTime : 2021-02-09 00:00:00
     * validEndTime : 2022-01-01 00:00:00
     * validDays : null
     * status : 1
     * shareRules : 0
     * pastDays : 292
     * isCanUser : null
     * goodsTypeId : null
     * goodsTypeNames : null
     */

    private int id;
    private int shopId;
    private String shopName;
    private String shopHeadPortrait;
    private int couponId;
    private String couponName;
    private Object couponPhoto;
    private int used;
    private int couponType;
    private int withSpecial;
    private Object supportTenantId;
    private String withAmount;
    private String usedAmount;
    private Object discount;
    private int securities;
    private int takeCount;
    private int useScene;
    private int usedCount;
    private String startTime;
    private String endTime;
    private int validType;
    private String validStartTime;
    private String validEndTime;
    private Object validDays;
    private int status;
    private String shareRules;
    private int pastDays;
    private Object isCanUser;
    private Object goodsTypeId;
    private Object goodsTypeNames;

    //时效类型(0无过期 1绝对时效（领取后XXX-XXX时间段有效） 2相对时效（领取后N天有效）)//自领取之日起有效天数//使用开始时间 //使用结束时间
//类型标签//判断用户是否领取 0未领取 1领取//门店ID （0为平台//用卷金额（减多少）//折扣数//满多少金额
    //优惠劵类型( 1红包 2无门槛券 3满减活动 4折扣券(商品活动) 5津贴 6满减  7门店拼单购物)


    protected Coupon(Parcel in) {
        id = in.readInt();
        shopId = in.readInt();
        shopName = in.readString();
        shopHeadPortrait = in.readString();
        couponId = in.readInt();
        couponName = in.readString();
        used = in.readInt();
        couponType = in.readInt();
        withSpecial = in.readInt();
        withAmount = in.readString();
        usedAmount = in.readString();
        securities = in.readInt();
        takeCount = in.readInt();
        useScene = in.readInt();
        usedCount = in.readInt();
        startTime = in.readString();
        endTime = in.readString();
        validType = in.readInt();
        validStartTime = in.readString();
        validEndTime = in.readString();
        status = in.readInt();
        shareRules = in.readString();
        pastDays = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(shopId);
        dest.writeString(shopName);
        dest.writeString(shopHeadPortrait);
        dest.writeInt(couponId);
        dest.writeString(couponName);
        dest.writeInt(used);
        dest.writeInt(couponType);
        dest.writeInt(withSpecial);
        dest.writeString(withAmount);
        dest.writeString(usedAmount);
        dest.writeInt(securities);
        dest.writeInt(takeCount);
        dest.writeInt(useScene);
        dest.writeInt(usedCount);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(validType);
        dest.writeString(validStartTime);
        dest.writeString(validEndTime);
        dest.writeInt(status);
        dest.writeString(shareRules);
        dest.writeInt(pastDays);
    }

    @Override
    public int describeContents() {
        return 0;
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

    /**
     * 通过优惠劵类型匹配 获取对应的介绍字符串
     * @return
     */
    public String getIntroduceByType(){
        String introduce = "";
        switch (couponType){
            case 3:
            case 6:
                introduce = String.format("%1$s减%2$s",withAmount,usedAmount);
                break;
            case 4:
                introduce = String.format("折扣商品%s折起",discount);
                break;
        }
        return introduce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopHeadPortrait() {
        return shopHeadPortrait;
    }

    public void setShopHeadPortrait(String shopHeadPortrait) {
        this.shopHeadPortrait = shopHeadPortrait;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Object getCouponPhoto() {
        return couponPhoto;
    }

    public void setCouponPhoto(Object couponPhoto) {
        this.couponPhoto = couponPhoto;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getWithSpecial() {
        return withSpecial;
    }

    public void setWithSpecial(int withSpecial) {
        this.withSpecial = withSpecial;
    }

    public Object getSupportTenantId() {
        return supportTenantId;
    }

    public void setSupportTenantId(Object supportTenantId) {
        this.supportTenantId = supportTenantId;
    }

    public String getWithAmount() {
        return withAmount;
    }

    public void setWithAmount(String withAmount) {
        this.withAmount = withAmount;
    }

    public String getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(String usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public int getSecurities() {
        return securities;
    }

    public void setSecurities(int securities) {
        this.securities = securities;
    }

    public int getTakeCount() {
        return takeCount;
    }

    public void setTakeCount(int takeCount) {
        this.takeCount = takeCount;
    }

    public int getUseScene() {
        return useScene;
    }

    public void setUseScene(int useScene) {
        this.useScene = useScene;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getValidType() {
        return validType;
    }

    public void setValidType(int validType) {
        this.validType = validType;
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

    public Object getValidDays() {
        return validDays;
    }

    public void setValidDays(Object validDays) {
        this.validDays = validDays;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShareRules() {
        return shareRules;
    }

    public void setShareRules(String shareRules) {
        this.shareRules = shareRules;
    }

    public int getPastDays() {
        return pastDays;
    }

    public void setPastDays(int pastDays) {
        this.pastDays = pastDays;
    }

    public Object getIsCanUser() {
        return isCanUser;
    }

    public void setIsCanUser(Object isCanUser) {
        this.isCanUser = isCanUser;
    }

    public Object getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Object goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public Object getGoodsTypeNames() {
        return goodsTypeNames;
    }

    public void setGoodsTypeNames(Object goodsTypeNames) {
        this.goodsTypeNames = goodsTypeNames;
    }
}
