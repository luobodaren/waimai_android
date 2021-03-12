package com.life.waimaishuo.mvvm.model.mine;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.api.request.MineReqData;
import com.life.waimaishuo.bean.api.request.bean.AddShippingAddress;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

public class MineAddShippingAddressModel extends BaseModel {

    /**
     * 新增地址
     */
    public void requestAddShippingAddress(RequestCallBack<Object> requestCallBack, AddShippingAddress addShippingAddress){
        MineReqData.AddShippingAddress reqData = new MineReqData.AddShippingAddress(addShippingAddress);
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_ADD_SHIPPING_ADDRESS,
                GsonUtil.gsonString(reqData), true, new HttpUtils.HttpCallback() {
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

}
