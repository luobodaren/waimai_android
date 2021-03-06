package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.api.request.bean.SearchReqBean;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class BrandZoneModel extends BaseModel {

    private List<Shop> brandShopList = new ArrayList<>();
    private WaiMaiReqData.WaiMaiSearchReqData reqData;

    {
        reqData = new WaiMaiReqData.WaiMaiSearchReqData(
                new SearchReqBean(Global.LocationProvince,Global.LocationCity,Global.LocationDistrict,
                        Global.userLonAndLat,1,10,0));
    }

    public List<Shop> getBrandShopList() {
        return brandShopList;
    }

    public void setBrandId(int brandId) {
        reqData.reqData.setBrandId(brandId);
    }

    public void setSortRules(SortTypeEnum sortTypeEnum) {
        int sortRules = 5;
        switch (sortTypeEnum){
            case SCORE:
                sortRules = 5;
                break;
            case STARTING_SHIPPING_PRICE_LOWEST:
                sortRules = 1;
                break;
            case DELIVERY_FASTEST:
                sortRules = 2;
                break;
            case DELIVERY_PRICE_LOWEST:
                sortRules = 3;
                break;
            case PER_CAPITA_PRICE_LOWEST:
                sortRules = 6;
                break;
            case PER_CAPITA_PRICE_HIGHEST:
                sortRules = 7;
                break;
            case DISTANCE:
                sortRules = 4;
                break;
            case SALES:
                sortRules = 0;
                break;
        }
        reqData.reqData.setSortRules(sortRules);
    }

    public void setActivityType(String[] activityType) {
        //????????????????????????
        List<String> activityTypeList = new ArrayList<>();
        if (activityType.length > 0) {
            LogUtil.d(activityType[0]);
            for (String s : activityType) {
                if (Constant.PREFERENTIAL_TABS.contains(s)) {
                    activityTypeList.add(
                            Constant.PREFERENTIAL_TABS_INDEX.get(Constant.PREFERENTIAL_TABS.indexOf(s)));
                }
            }
        }
        reqData.reqData.setActivityType(activityTypeList.toArray(activityType));
    }

    public void setScreenData(String valueOf, String valueOf1) {
        reqData.reqData.setMinAvgPrice(valueOf);
        reqData.reqData.setMaxAvgPrice(valueOf1);
    }

    public void requestBrandShopData(RequestCallBack<Object> requestCallBack) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_SEARCH, GsonUtil.gsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    brandShopList = GsonUtil.parserJsonToArrayBeans(data, "list", Shop.class);
                    if (brandShopList != null) {
                        for (Shop shop : brandShopList) {
                            shop.setShop_head_portrait(HttpUtils.changeToHttps(shop.getShop_head_portrait()));
                            for (Goods goods : shop.getGoodsInfoList()) {
                                goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                            }
                        }
                    }
                    requestCallBack.onSuccess(brandShopList);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                brandShopList.clear();
                LogUtil.e("requestLinkageGroupItems error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

}
