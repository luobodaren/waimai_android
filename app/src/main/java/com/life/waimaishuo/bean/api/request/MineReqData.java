package com.life.waimaishuo.bean.api.request;

import com.life.waimaishuo.bean.api.request.bean.GetUserCouponReqBean;

/**
 * 我的模块
 */
public class MineReqData{

    /**
     * 新增收货地址
     */
    public static class AddShippingAddress extends BaseReqData<com.life.waimaishuo.bean.api.request.bean.AddShippingAddress>{
        public AddShippingAddress(com.life.waimaishuo.bean.api.request.bean.AddShippingAddress reqData) {
            super(reqData);
        }
    }

    /**
     * 获取用户优惠劵或红包列表
     */
    public static class GetCouponList extends BaseReqData<GetUserCouponReqBean>{
        public GetCouponList(GetUserCouponReqBean reqData) {
            super(reqData);
        }
    }

}
