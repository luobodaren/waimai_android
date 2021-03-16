package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.Coupon;
import com.life.waimaishuo.bean.api.request.bean.GetUserCouponReqBean;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineRedPacketModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderRedPacketModel extends BaseModel {

    public List<Coupon> couponList;

    /**
     * 获取红包或优惠卷列表
     * @param shopId
     * @param pageSize
     * @param pageNum
     */
    public void requestCoupon(RequestCallBack<Object> requestCallBack, int shopId, int pageSize, int pageNum) {
        GetUserCouponReqBean getUserCouponReqBean = new GetUserCouponReqBean();
        getUserCouponReqBean.setShopType(1);
        getUserCouponReqBean.setUseScene(1);
        getUserCouponReqBean.setUserId(Global.getUser().getId());
        getUserCouponReqBean.setShopId(shopId);
        getUserCouponReqBean.setPageSize(pageSize);
        getUserCouponReqBean.setPageNum(pageNum);
        MineRedPacketModel.requestCoupon(getUserCouponReqBean, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if(couponList != null){
                    couponList.clear();
                }
                if(!"".equals(data)){
                    couponList = GsonUtil.parserJsonToArrayBeans(data, Coupon.class);
                    requestCallBack.onSuccess(data);
                }else{
                    requestCallBack.onFail(data);
                }
            }

            @Override
            public void onError(int type, Throwable error) {
                if(couponList != null){
                    couponList.clear();
                }
                requestCallBack.onFail(error.getMessage());
            }
        });
    }
}
