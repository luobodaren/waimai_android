package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.respon.SecondKillTime;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.concurrent.TimeoutException;

public class WaimaiLimitedModel extends BaseModel {

    public SecondKillTime secondKillTime = new SecondKillTime();    //限时秒杀时间

    public void requestSecondKillTime(NotifyChangeRequestCallBack requestCallBack, WaiMaiReqData.WaiMaiSecondKillReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_MAIN_SECOND_KILL, GsonUtil.gsonString(reqData), false, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtil.d(data);
                if (!"".equals(data) && !"null".equals(data)) {
                    secondKillTime = GsonUtil.parserJsonToBean(data, SecondKillTime.class);
                    requestCallBack.onSuccess(secondKillTime);
                } else {
                    secondKillTime.setAllTimeNull();
                    requestCallBack.onSuccess(null);
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestSecondKillTime error:" + error.getMessage());
                secondKillTime.setAllTimeNull();
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    public void requestLimitedGoodsList(NotifyChangeRequestCallBack requestCallBack, WaiMaiReqData.WaiMaiSecondKillContentListReqData reqData, int timeOutRequestTime){

    }

}
