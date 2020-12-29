package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiConfirmModel;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaiMaiConfirmOrderFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.List;

public class WaiMaiConfirmOrderViewModel extends BaseViewModel {

    WaiMaiConfirmModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiConfirmModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List getOrderInfo(int currentSelectedAccessType) {
        if(currentSelectedAccessType == WaiMaiConfirmOrderFragment.ACCESS_WAIMAI){

        }
    }
}
