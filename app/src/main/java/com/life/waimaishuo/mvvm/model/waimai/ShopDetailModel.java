package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Coupon;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.CommonReqData;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.request.bean.AddUserCollectReqBean;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailModel extends BaseModel {

    public Shop shop = new Shop();
    public List<String> couponStringList = new ArrayList<>();

    public boolean isJoinShopFans = false;

    public boolean isCollectShop = false;

    /**
     * 刷新优惠介绍列表
     */
    private void refreshCouponStringList(){
        if(shop != null && shop.getCouponList() != null){
            for(Coupon coupon : shop.getCouponList()){
                couponStringList.add(coupon.getIntroduceByType());
            }
        }
    }

    public void requestShopInfo(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_SHOPINFO, GsonUtil.toJsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    shop = GsonUtil.parserJsonToBean(data,Shop.class);
                    shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShop_head_portrait()));
                    refreshCouponStringList();

                    requestCallBack.onSuccess(shop);
                } else {
                    shop = new Shop();
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                shop = new Shop();
                LogUtil.e("requestShopInfo error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 判断是否成为会员
     * @param requestCallBack
     * @param reqData
     */
    public void requestIsJoinShopFans(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_IS_JOIN_SHOP_FANS,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if("".equals(data)){
                    isJoinShopFans = false;
                }else{
                    isJoinShopFans = true;
                }
                requestCallBack.onSuccess(data);
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestIsJoinShopFans error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 加入店铺会员
     */
    public void joinInShopFans(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_JOIN_SHOP_FANS,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                isJoinShopFans = true;
                requestCallBack.onSuccess(data);
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestIsJoinShopFans error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 是否收藏店铺
     */
    public void requestIsCollectShop(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_IS_COLLECT_SHOP,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if("".equals(data) || "false".equals(data)){
                    isCollectShop = false;
                }else{
                    isCollectShop = true;
                }
                requestCallBack.onSuccess(data);
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestIsCollectShop error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 收藏或取消收藏 店铺
     * @param requestCallBack
     * @param shopId
     */
    public void doOrCancelCollectShop(RequestCallBack<Object> requestCallBack, int shopId){
        CommonReqData.AddUserCollectReqData reqData = new CommonReqData.AddUserCollectReqData(new AddUserCollectReqBean(shopId,0,1));
        if(isCollectShop){//收藏则取消收藏
            HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_CANCEL_COLLECT_SHOP_OR_GOOD,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
                @Override
                public void onSuccess(String data) {
                    if(!"".equals(data) && !"false".equals(data)){
                        isCollectShop = false;
                    }
                    requestCallBack.onSuccess(data);
                }

                @Override
                public void onError(int errorType, Throwable error) {
                    LogUtil.e("requestCancelCollectShop error:" + error.getMessage());
                    requestCallBack.onFail(error.getMessage());
                }
            });
        }else{  //没收藏 则收藏
            HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_COLLECT_SHOP_OR_GOOD,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
                @Override
                public void onSuccess(String data) {
                    if(!"".equals(data) && !"false".equals(data)){
                        isCollectShop = true;
                    }
                    requestCallBack.onSuccess(data);
                }

                @Override
                public void onError(int errorType, Throwable error) {
                    LogUtil.e("requestCollectShop error:" + error.getMessage());
                    requestCallBack.onFail(error.getMessage());
                }
            });
        }

    }

    public List<String> getCouponStringList() {
        return couponStringList;
    }

}
