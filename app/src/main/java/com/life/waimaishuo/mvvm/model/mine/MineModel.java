package com.life.waimaishuo.mvvm.model.mine;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.User;
import com.life.waimaishuo.bean.api.request.BaseReqData;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import org.greenrobot.eventbus.EventBus;

public class MineModel extends BaseModel {

    public void requestUserInfo(RequestCallBack<Object> requestCallBack){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_GET_USER_INFO,
                GsonUtil.gsonString(new BaseReqData<>("13715714099")),
                false, new HttpUtils.HttpCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtil.d(data);
                        if(!"".equals(data)){
                            Global.setUserInfoAndSave(GsonUtil.parserJsonToBean(data, User.class));
                            Global.getUser().setHeadPortrait(HttpUtils.changeToHttps(Global.getUser().getHeadPortrait()));
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
