package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiBusinessQualificationModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class WaiMaiBusinessQualificationViewModel extends BaseViewModel {

    WaiMaiBusinessQualificationModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiBusinessQualificationModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

}
