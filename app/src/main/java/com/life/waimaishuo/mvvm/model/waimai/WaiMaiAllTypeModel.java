package com.life.waimaishuo.mvvm.model.waimai;

import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.bean.api.respon.WaiMaiAllType;
import com.life.waimaishuo.bean.ui.LinkageGoodsTypeGroupedItemInfo;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WaiMaiAllTypeModel extends BaseModel {

    public List<BaseGroupedItem<LinkageGoodsTypeGroupedItemInfo>> groupedItemList = new ArrayList<>();

    public void requestLinkageGroupItems(RequestCallBack<Object> requestCallBack, int timeOutRequestTime) {
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
            public void onError(Throwable error) {
                groupedItemList.clear();
                LogUtil.e("requestLinkageGroupItems error:" + error.getMessage() + count);
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestLinkageGroupItems(requestCallBack, count); //再执行一次
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
