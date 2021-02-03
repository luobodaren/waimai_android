package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class WaiMaiRecommendedModel extends BaseModel {

    public Map<String ,List<Shop>> shopListMap = new HashMap<>();
    public List<Shop> shopList  = new ArrayList<>();

    /**
     * 获取推荐列表
     * @param title
     * @return
     */
    public List<Shop> getListData(String title){
        List<Shop> shopList = shopListMap.get(title);
        if(shopList == null){
            shopList =  new ArrayList<>();
        }
        return shopList;
    }

    public void requestRecommendShopListData(String title, RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_GOODS_LIST, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    shopList = GsonUtil.parserJsonToArrayBeans(data,"sysDecorations", Shop.class);
                    shopListMap.put(title,shopList);
                    requestCallBack.onSuccess(shopList);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                shopList = shopListMap.get(title);
                if(shopList != null){
                    shopList.clear();
                }
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestRecommendShopListData(title, requestCallBack, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }

    public void requestShopListData(String title, RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_GOODS_LIST, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    shopList = GsonUtil.parserJsonToArrayBeans(data,"sysDecorations", Shop.class);
                    shopListMap.put(title, shopList);
                    requestCallBack.onSuccess(shopList);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                shopList = shopListMap.get(title);
                if(shopList != null){
                    shopList.clear();
                }
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestShopListData(title, requestCallBack, count);
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
