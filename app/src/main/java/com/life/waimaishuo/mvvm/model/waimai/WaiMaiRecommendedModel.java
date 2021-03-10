package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WaiMaiRecommendedModel extends BaseModel {

    public List<Shop> shopList  = new ArrayList<>();
    public List<Shop> shopGoodsList = new ArrayList<>();//发现好物
    public List<Shop> zeroDeliverShopList = new ArrayList<>();//零元配送

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

    /**
     * 获取零元配送
     * @return
     */
    public List<Shop> getZeroDeliverShopList() {
        return zeroDeliverShopList;
    }

    // TODO: 2021/2/5 增加对应分页的接口、逻辑
    /**
     * 获取发现好物列表
     * @param requestCallBack
     * @param reqData
     */
    public void requestGoodsListData(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiRecommendReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_GOODS_LIST, GsonUtil.gsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtil.d("requestGoodsListData data:" + data);
                if (!"".equals(data)) {
                    Integer total = GsonUtil.getIntNoteJsonString(data,"total");
                    String listData = GsonUtil.getStringNoteJsonString(data,"list");
                    if((total != null && total > 0) || (!"null".equals(listData) && !"".equals(listData))){
                        shopGoodsList = GsonUtil.parserJsonToArrayBeans(listData, Shop.class);
                        for (Shop shop:shopGoodsList) {
                            shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShop_head_portrait()));
                            for (Goods goods:shop.getGoodsInfoList()) {
                                goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                            }
                        }
                    }else{
                        shopGoodsList.clear();
                    }
                    requestCallBack.onSuccess(String.valueOf(total));
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                if(shopGoodsList != null){
                    shopGoodsList.clear();
                }

                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 推荐店铺
     * @param requestCallBack
     * @param reqData
     */
    public void requestShopListData(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiRecommendReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SHIOP_LIST, GsonUtil.gsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    Integer total = GsonUtil.getIntNoteJsonString(data,"total");
                    String listData = GsonUtil.getStringNoteJsonString(data,"list");
                    LogUtil.d("listData" + listData);
                    if((total != null && total > 0) || (!"null".equals(listData) && !"".equals(listData))){
                        shopList = GsonUtil.parserJsonToArrayBeans(listData, Shop.class);
                        for (Shop shop:shopList) {
                            shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShopImage()));
                        }
                    }else{
                        shopList.clear();
                    }
                    requestCallBack.onSuccess(String.valueOf(total));
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                if(shopList != null){
                    shopList.clear();
                }
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 0元配送
     * @param requestCallBack
     * @param reqData
     */
    public void requestZeroDeliverListData(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiRecommendReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_ZERO_DELIVER, GsonUtil.gsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    Integer total = GsonUtil.getIntNoteJsonString(data,"total");
                    String listData = GsonUtil.getStringNoteJsonString(data,"list");
                    LogUtil.d("listData" + listData);
                    if((total != null && total > 0) || (!"null".equals(listData) && !"".equals(listData))){
                        zeroDeliverShopList = GsonUtil.parserJsonToArrayBeans(listData, Shop.class);
                        for (Shop shop:zeroDeliverShopList) {
                            shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShop_head_portrait()));
                        }
                    }else{
                        zeroDeliverShopList.clear();
                    }
                    requestCallBack.onSuccess(String.valueOf(total));
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                if(zeroDeliverShopList != null){
                    zeroDeliverShopList.clear();
                }
                if(errorType == HttpUtils.HttpCallback.ERROR_TYPE_CODE){
                    if(Integer.valueOf(error.getMessage()) == 1){   //接口返回的code=1 表示没有商家参加
                        requestCallBack.onSuccess(null);
                    }
                }else{
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

}
