package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopReportMerchantsModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class ShopReportMerchantsViewModel extends BaseViewModel {

    ShopReportMerchantsModel mModel;

    public ObservableField<String> reportContentOvservabler = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new ShopReportMerchantsModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

}
