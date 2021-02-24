package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiZeroDividerModel extends BaseModel {

    private List<IconStrData> mFoodSubtypeList = new ArrayList<>();

    public List<IconStrData> getSubTypeTitle() {
        return mFoodSubtypeList;
    }

    /**
     * 获取顶部筛选的recyclerView内容 目前不需要实现
     */
    /*public void requestFoodSubTypeList(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiSubTypeReqData reqData, int timeOutRequestTime){
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant., GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    mFoodSubtypeList = GsonUtil.parserJsonToArrayBeans(data, ImageUrlNameData.class);
                    String url;
                    for (ImageUrlNameData imageUrlNameData : mFoodSubtypeList) {
                        url = HttpUtils.changeToHttps(imageUrlNameData.getImgUrl());
                        imageUrlNameData.setImgUrl(url);
                    }
                    requestCallBack.onSuccess(mFoodSubtypeList);
                } else {
                    mFoodSubtypeList.clear();
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(Throwable error) {
                mFoodSubtypeList.clear();
                LogUtil.e("requestSubtype error:" + error.getMessage() + count);
                if (error instanceof TimeoutException) {
                    if (count >= 0) {
                        requestSubtype(requestCallBack, reqData, count);
                    } else {
                        requestCallBack.onFail(error.getMessage());
                    }
                } else {
                    requestCallBack.onFail(error.getMessage());
                }
            }
        });
    }*/
}
