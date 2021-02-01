package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.LogUtil;
import com.life.base.utils.net.HttpUtils;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class WaimaiModel extends BaseModel {

    public void getBannerItemList(RequestCallBack<String> requestCallBack) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SLIDESHOW, "{ \"reqData\":\"\"}", false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String json) {
                requestCallBack.onSuccess(json);
                LogUtil.d(json);
            }

            @Override
            public void onError(String error) {
                requestCallBack.onFail(error);
                LogUtil.d(error);
            }
        });
    }
}
