package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineCreateShopModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineCreateShopViewModel extends BaseViewModel {

    private MineCreateShopModel mModel;


    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineCreateShopModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

}
