package com.life.waimaishuo.mvvm.model.waimai;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineAddressManagerModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiConfirmOrderModel extends BaseModel {

    public List<Address> addressList;   //初始值为null(为了在执行点击事件时，可判断是否请求了地址

    public void requestShoppingAddress(RequestCallBack<Object> requestCallBack){
        MineAddressManagerModel.requestShoppingAddress(new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if(addressList == null){
                    addressList = new ArrayList<>();
                }
                addressList.clear();
                if(!"".equals(data)){
                    addressList = GsonUtil.parserJsonToArrayBeans(data,Address.class);
                    requestCallBack.onSuccess(data);
                }else{
                    requestCallBack.onFail(data);
                }
            }

            @Override
            public void onError(int type, Throwable error) {
                addressList.clear();
                requestCallBack.onFail(error.getMessage());
            }
        });

    }

}
