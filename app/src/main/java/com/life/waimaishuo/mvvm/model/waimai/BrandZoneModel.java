package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.request.bean.SearchReqData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import static com.life.waimaishuo.enumtype.SortTypeEnum.SCORE;
import static com.life.waimaishuo.enumtype.SortTypeEnum.STARTING_SHIPPING_PRICE_LOWEST;

public class BrandZoneModel extends BaseModel {

    private List<Shop> brandShopList = new ArrayList<>();
    private WaiMaiReqData.WaiMaiSearchReqData reqData;

    {
        reqData = new WaiMaiReqData.WaiMaiSearchReqData(
                new SearchReqData(Global.LocationProvince,Global.LocationCity,Global.LocationDistrict,
                        Global.userLonAndLat,1,10,0));
    }

    public List<Shop> getBrandShopList() {
        return brandShopList;
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
        //解析优惠活动标签
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

    public void requestBrandShopData(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_ALL_TYPE, ApiConstant.DEFAULT_BASE_JSON_INFO, false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                groupedItemList.clear();
                if (!"".equals(data) && !"null".equals(data)) {
                    List<WaiMaiAllType> waiMaiAllTypeList = GsonUtil.parserJsonToArrayBeans(data, WaiMaiAllType.class);
                    BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo> item;
                    LinkageGoodsTypeGroupedItemInfo itemInfo;
                    if (waiMaiAllTypeList != null) {
                        for (WaiMaiAllType waiMaiAllType : waiMaiAllTypeList) {
                            item = new BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>(true, waiMaiAllType.getTypeName()) {};
                            item.info = new LinkageGoodsTypeGroupedItemInfo("","","",HttpUtils.changeToHttps(waiMaiAllType.getTypeIon()),"");
                            groupedItemList.add(item);
                            for (WaiMaiAllType.SubType subType : waiMaiAllType.getSubTypeList()) {
                                itemInfo = new LinkageGoodsTypeGroupedItemInfo(
                                        subType.getTypeName(),
                                        waiMaiAllType.getTypeName(),
                                        "", HttpUtils.changeToHttps(subType.getTypeIon()));
                                item = new BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>(itemInfo) {
                                };
                                groupedItemList.add(item);
                            }
                        }
                    }

                    requestCallBack.onSuccess(groupedItemList);
                } else {
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                groupedItemList.clear();
                LogUtil.e("requestLinkageGroupItems error:" + error.getMessage() + count);
                if(errorType == HttpUtils.HttpCallback.ERROR_TYPE_REQUEST){
                    if (error instanceof TimeoutException) {
                        if (count >= 0) {
                            requestLinkageGroupItems(requestCallBack, count); //再执行一次
                        } else {
                            requestCallBack.onFail(error.getMessage());
                        }
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }
}
