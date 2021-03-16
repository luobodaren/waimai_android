package com.life.waimaishuo.mvvm.model.mine;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.api.request.MineReqData;
import com.life.waimaishuo.bean.api.request.bean.GetUserCouponReqBean;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

public class MineRedPacketModel extends BaseModel {

    public static void requestCoupon(GetUserCouponReqBean getUserCouponReqBean, HttpUtils.HttpCallback httpCallback){
        MineReqData.GetCouponList reqData = new MineReqData.GetCouponList(getUserCouponReqBean);
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GET_USER_COUPON, GsonUtil.gsonString(reqData), true,httpCallback);
    }

    /*public void requestCoupon() {
        GetUserCouponReqBean getUserCouponReqBean = new GetUserCouponReqBean();
        MineRedPacketModel.requestCoupon(, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onError(int type, Throwable error) {

            }
        });
    }*/

}
