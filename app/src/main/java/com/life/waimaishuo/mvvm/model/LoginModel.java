package com.life.waimaishuo.mvvm.model;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.User;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.util.net.HttpUtils;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.api.request.BaseReqData;
import com.life.waimaishuo.bean.api.request.bean.GetVerificationReqBean;
import com.life.waimaishuo.bean.api.request.bean.LoginWithVerificcationReqBean;
import com.life.waimaishuo.constant.ApiConstant;

import org.greenrobot.eventbus.EventBus;

public class LoginModel extends BaseModel {

    public void requestVerification(String currentRequestVerificationPhone, RequestCallBack<Object> requestCallBack) {
        LogUtil.d("currentRequestVerificationPhone:" + currentRequestVerificationPhone);
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
                            LogUtil.d("token : " + data);
                            //去除前后引号
                            if(data.startsWith("\""))
                                data = data.substring(1);
                            if(data.endsWith("\""))
                                data = data.substring(0,data.length()-1);
                            Global.setToken(data);
                            //接着请求个人信息
                            requestUserInfo(requestCallBack, phoneNumber);
                        }else{
                            requestCallBack.onFail("");
                        }
                    }

                    @Override
                    public void onError(int type, Throwable error) {
                        requestCallBack.onFail(error.getMessage());
                    }
                });
    }

    public void requestUserInfo(RequestCallBack<Object> requestCallBack, String phone){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_GET_USER_INFO,
                GsonUtil.gsonString(new BaseReqData<>(phone)),
                false, new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtil.d(data);
                        if(!"".equals(data)){
                            Global.setUserInfoAndSave(GsonUtil.parserJsonToBean(data, User.class));
                            Global.getUser().setHeadPortrait(HttpUtils.changeToHttps(Global.getUser().getHeadPortrait()));
                            //发送EventBus事件，通知登录成功
                            EventBus.getDefault().post(new MessageEvent(MessageCodeConstant.LOGIN_AND_GET_USERINFO_SUCCESS,null));
                            requestCallBack.onSuccess(data);
                        }else{
                            requestCallBack.onFail("");
                        }
                    }

                    @Override
                    public void onError(int type, Throwable error) {
                        requestCallBack.onFail(error.getMessage());
                    }
                });
    }
}
