package com.life.waimaishuo.mvvm.model.mine;

import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.List;

public class MineAddressManagerModel extends BaseModel {

    public List<Address> addressList;

    /**
     * 请求地址
     * @param httpCallback
     */
    public static void requestShoppingAddress(HttpUtils.HttpCallback httpCallback){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_GET_SHIPPING_ADDRESS,
                ApiConstant.DEFAULT_BASE_JSON_INFO, true, httpCallback);
    }

}
