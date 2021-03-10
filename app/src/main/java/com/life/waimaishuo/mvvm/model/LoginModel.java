package com.life.waimaishuo.mvvm.model;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.api.request.BaseReqData;
import com.life.waimaishuo.bean.api.request.bean.GetVerificationReqBean;
import com.life.waimaishuo.bean.api.request.bean.LoginWithVerificcationReqBean;
import com.life.waimaishuo.constant.ApiConstant;

public class LoginModel extends BaseModel {

    public void requestVerification(String currentRequestVerificationPhone, RequestCallBack<Object> requestCallBack) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_GET_VERIFICATION,
                GsonUtil.gsonString(new BaseReqData<>(new GetVerificationReqBean(currentRequestVerificationPhone, 1))),
                false, new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String data) {
                        requestCallBack.onSuccess(data);
                    }

                    @Override
                    public void onError(int type, Throwable error) {
                        requestCallBack.onFail(error.getMessage());
                    }
                });
    }

    /**
     * 手机号 验证码登录
     * @param phoneNumber
     * @param verification
     * @param requestCallBack
     */
    public void loginByPhone(String phoneNumber, String verification, RequestCallBack<Object> requestCallBack) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_LOGIN,
                GsonUtil.gsonString(new BaseReqData<>(new LoginWithVerificcationReqBean(verification,phoneNumber))),
                false, new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtil.d(data);
                        if(!"".equals(data)){
                            Global.setPhoneLoginSuccessData(phoneNumber,data);
                            requestCallBack.onSuccess(data);
                        }else{
                            requestCallBack.onFail("");
                        }
                        requestCallBack.onSuccess(data);
                    }

                    @Override
                    public void onError(int type, Throwable error) {
                        requestCallBack.onFail(error.getMessage());
                    }
                });
    }
}
