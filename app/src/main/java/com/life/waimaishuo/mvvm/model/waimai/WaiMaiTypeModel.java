package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WaiMaiTypeModel extends BaseModel {

    public List<ImageUrlNameData> mFoodSubtypeList = new ArrayList<>();

    /**
     * 类别的子集
     *
     * @param requestCallBack
     * @param timeOutRequestTime
     */
    public void requestSubtype(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiSubTypeReqData reqData, int timeOutRequestTime) {
        timeOutRequestTime--;
        int count = timeOutRequestTime;
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_TYPE_GET_SUBTYPE_BY_NAME, GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
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
            public void onError(int errorType, Throwable error) {
                mFoodSubtypeList.clear();
                LogUtil.e("requestSubtype error:" + error.getMessage() + count);
                if(errorType == HttpUtils.HttpCallback.ERROR_TYPE_REQUEST){
                    if (error instanceof TimeoutException) {
                        if (count >= 0) {
                            requestSubtype(requestCallBack, reqData, count);
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
