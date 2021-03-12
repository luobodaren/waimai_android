package com.life.waimaishuo.bean.api.request;

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

}
