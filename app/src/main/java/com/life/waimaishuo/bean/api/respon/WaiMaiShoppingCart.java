package com.life.waimaishuo.bean.api.respon;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.GoodsShoppingCart;

import java.util.List;

public class WaiMaiShoppingCart {
    /**
     * shopId :
     * shopName : 喜茶（深圳讯美科技GO店）
     * deliveryGoodsDto : [{"shopId":5229,"goodsId":403298,"versions":"2","goodsName":"满陇金桂","goodsPrimaryImg":null,"specs":"标准","attrs":"pla可解吸管、冰（推荐）、正常（推荐）、标准甜（推荐）、纯茶、","activityGoodsId":null,"isBargainGoods":0,"price":"16","originalPrice":null,"mealsFeeType":1,"buyCount":"1","allPrice":"16","isPitchOn":"1","describe":null,"updateIndex":null,"isMutualExclusion":null,"shopDistPrice":null,"boxPrice":"0","state":null},{"shopId":5229,"goodsId":403298,"versions":"2","goodsName":"满陇金桂","goodsPrimaryImg":null,"specs":"原创0糖零卡糖","attrs":"纸吸管-感略有影响、热、正常（推荐）、少少甜、纯茶、","activityGoodsId":null,"isBargainGoods":0,"price":"16","originalPrice":null,"mealsFeeType":1,"buyCount":"1","allPrice":"16","isPitchOn":"1","describe":null,"updateIndex":null,"isMutualExclusion":null,"shopDistPrice":null,"boxPrice":"0","state":null},{"shopId":5229,"goodsId":404584,"versions":"2","goodsName":"纯金凤茶王","goodsPrimaryImg":null,"specs":"标准","attrs":"纸吸管-感略有影响、冰（推荐）、正常（推荐）、标准甜（推荐）、纯茶、","activityGoodsId":null,"isBargainGoods":0,"price":"16","originalPrice":null,"mealsFeeType":1,"buyCount":"1","allPrice":"16","isPitchOn":"1","describe":null,"updateIndex":null,"isMutualExclusion":null,"shopDistPrice":null,"boxPrice":"0","state":null},{"shopId":5229,"goodsId":477021,"versions":"2","goodsName":"黑波波","goodsPrimaryImg":null,"specs":"标准","attrs":"分装（备注饮品）、","activityGoodsId":null,"isBargainGoods":0,"price":"2","originalPrice":null,"mealsFeeType":1,"buyCount":"1","allPrice":"2","isPitchOn":"1","describe":null,"updateIndex":null,"isMutualExclusion":null,"shopDistPrice":null,"boxPrice":"0","state":null}]
     * isUseCoupon : 2
     * couponId : null
     * activityCoupon : null
     * couponMoney : null
     * allMoney : 55
     * isBargainGoods : 0
     * isMutualExclusion : 1
     * distPrice : 5
     * bookFinishTime : 2021-03-04 17:17:56
     * distTeamName : null
     * cartDesc : 已优惠0元!
     * count : 4
     * boxPrice : 0
     * shopNature : 2
     * isPitchOn : 1
     */

    private int shopId;
    private String shopName;
    private String isUseCoupon;
    private Object couponId;
    private Object activityCoupon;
    private Object couponMoney;
    private String allMoney;
    private int isBargainGoods;
    private int isMutualExclusion;
    private String distPrice;
    private String bookFinishTime;
    private Object distTeamName;
    private String cartDesc;
    private int count;
    private String boxPrice;
    private int shopNature;
    private String isPitchOn;
    private List<GoodsShoppingCart> deliveryGoodsDto;

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

    public String getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(String isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public Object getCouponId() {
        return couponId;
    }

    public void setCouponId(Object couponId) {
        this.couponId = couponId;
    }

    public Object getActivityCoupon() {
        return activityCoupon;
    }

    public void setActivityCoupon(Object activityCoupon) {
        this.activityCoupon = activityCoupon;
    }

    public Object getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Object couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

    public int getIsBargainGoods() {
        return isBargainGoods;
    }

    public void setIsBargainGoods(int isBargainGoods) {
        this.isBargainGoods = isBargainGoods;
    }

    public int getIsMutualExclusion() {
        return isMutualExclusion;
    }

    public void setIsMutualExclusion(int isMutualExclusion) {
        this.isMutualExclusion = isMutualExclusion;
    }

    public String getDistPrice() {
        return distPrice;
    }

    public void setDistPrice(String distPrice) {
        this.distPrice = distPrice;
    }

    public String getBookFinishTime() {
        return bookFinishTime;
    }

    public void setBookFinishTime(String bookFinishTime) {
        this.bookFinishTime = bookFinishTime;
    }

    public Object getDistTeamName() {
        return distTeamName;
    }

    public void setDistTeamName(Object distTeamName) {
        this.distTeamName = distTeamName;
    }

    public String getCartDesc() {
        return cartDesc;
    }

    public void setCartDesc(String cartDesc) {
        this.cartDesc = cartDesc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(String boxPrice) {
        this.boxPrice = boxPrice;
    }

    public int getShopNature() {
        return shopNature;
    }

    public void setShopNature(int shopNature) {
        this.shopNature = shopNature;
    }

    public String getIsPitchOn() {
        return isPitchOn;
    }

    public void setIsPitchOn(String isPitchOn) {
        this.isPitchOn = isPitchOn;
    }

    public List<GoodsShoppingCart> getDeliveryGoodsDto() {
        return deliveryGoodsDto;
    }

    public void setDeliveryGoodsDto(List<GoodsShoppingCart> deliveryGoodsDto) {
        this.deliveryGoodsDto = deliveryGoodsDto;
    }


}
