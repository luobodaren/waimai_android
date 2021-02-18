package com.life.waimaishuo.mvvm.model.waimai;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiRecommendReqData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WaiMaiRecommendedModel extends BaseModel {

    public List<Shop> shopList  = new ArrayList<>();
    public List<Shop> shopGoodsList = new ArrayList<>();//发现好物

    /**
     * 获取推荐列表
     * @return
     */
    public List<Shop> getShopList(){
        return shopList;
    }

    /**
     * 获取商品列表
     * @return
     */
    public List<Shop> getShopGoodsList() {
        return shopGoodsList;
    }

    // TODO: 2021/2/5 增加对应分页的接口、逻辑
    /**
     * 获取发现好物列表
     * @param requestCallBack
     * @param reqData
     * @param timeOutRequestTime
     */
    public void requestGoodsListData(RequestCallBack<Object> requestCallBack, WaiMaiRecommendReqData reqData, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_GOODS_LIST, GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    String total = GsonUtil.getNoteJsonString(data,"total");
                    shopGoodsList = GsonUtil.parserJsonToArrayBeans(data,"list", Shop.class);
                    for (Shop shop:shopGoodsList) {
                        shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShop_head_portrait()));
                        for (Goods goods:shop.getGoodsInfoList()) {
                            goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                        }
                    }
                    requestCallBack.onSuccess(total);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                if(shopGoodsList != null){
                    shopGoodsList.clear();
                }
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestGoodsListData(requestCallBack, reqData, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    /**
     * 推荐店铺
     * @param requestCallBack
     * @param reqData
     * @param timeOutRequestTime
     */
    public void requestShopListData(RequestCallBack<Object> requestCallBack, WaiMaiRecommendReqData reqData, int timeOutRequestTime) {
        LogUtil.d("requestShopListData" + GsonUtil.toJsonString(reqData));
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SHIOP_LIST, GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    String total = GsonUtil.getNoteJsonString(data,"total");
                    shopList = GsonUtil.parserJsonToArrayBeans(data,"list", Shop.class);
                    for (Shop shop:shopList) {
                        shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShop_head_portrait()));
                    }
                    requestCallBack.onSuccess(total);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                if(shopList != null){
                    shopList.clear();
                }
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestShopListData(requestCallBack, reqData, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

}
