package com.life.waimaishuo.mvvm.model.mine;

import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class MineAddressManagerModel extends BaseModel {

    public List<Address> addressList;

    /**
     * 请求收货地址 供其他Model类使用
     * @param httpCallback
     */
    public static void requestShoppingAddress(HttpUtils.HttpCallback httpCallback){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_GET_SHIPPING_ADDRESS,
                ApiConstant.DEFAULT_BASE_JSON_INFO, true, httpCallback);
    }

    /**
     * 请求收货地址
     */
    public void requestShoppingAddress(RequestCallBack<Object> requestCallBack){
        requestShoppingAddress(new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if(addressList == null){
                    addressList = new ArrayList<>();
                }
                addressList.clear();
                if(!"".equals(data)){
                    addressList = GsonUtil.parserJsonToArrayBeans(data,Address.class);
                    requestCallBack.onSuccess(addressList);
                }else{
                    requestCallBack.onFail(data);
                }
            }

            @Override
            public void onError(int type, Throwable error) {
                LogUtil.e(error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

}
