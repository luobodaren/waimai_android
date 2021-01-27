package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineSettingModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineSettingViewModel extends BaseViewModel {

    private MineSettingModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineSettingModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
