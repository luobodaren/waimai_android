package com.example.myapplication.mvvm.vm.waimai;

import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.ShopDetailModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

public class ShopDetailViewModel extends BaseViewModel {

    ShopDetailModel model;

    @Override
    public BaseModel getModel() {
        model = new ShopDetailModel();
        return model;
    }

    @Override
    public void initData() {

    }
}
