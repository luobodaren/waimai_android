package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WaiMaiTypeModel extends BaseModel {

    public List<ImageUrlNameData> mFoodSubtypeList = new ArrayList<>();

    public ImageUrlNameData currentSubtypeImgUrl = new ImageUrlNameData("","");
    /**
     * 类别的子集
     *
     * @param requestCallBack
     */
    public void requestSubtype(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiSubTypeReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_TYPE_GET_SUBTYPES_BY_NAME, GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
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
                LogUtil.e("requestSubtype error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    public String getCurrentSubtypeImgUrl() {
        return currentSubtypeImgUrl.getImgUrl();
    }

    /**
     * 根据类型名称获取类型信息
     * @param requestCallBack
     * @param reqData
     */
    public void requestSubtypeImg(RequestCallBack<Object> requestCallBack, WaiMaiReqData.WaiMaiSubTypeReqData reqData){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_TYPE_GET_SUBTYPE_BY_NAME, GsonUtil.toJsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    currentSubtypeImgUrl = GsonUtil.parserJsonToBean(data, ImageUrlNameData.class);
                    currentSubtypeImgUrl.setImgUrl(HttpUtils.changeToHttps(currentSubtypeImgUrl.getImgUrl()));
                    requestCallBack.onSuccess(mFoodSubtypeList);
                } else {
                    currentSubtypeImgUrl.setImgUrl("");
                    currentSubtypeImgUrl.setName("");
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                currentSubtypeImgUrl.setImgUrl("");
                currentSubtypeImgUrl.setName("");
                LogUtil.e("requestSubtypeImg error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }
}
