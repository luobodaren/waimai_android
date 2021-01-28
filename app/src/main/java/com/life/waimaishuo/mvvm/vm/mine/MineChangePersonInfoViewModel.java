package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineChangePersonInfoModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineChangePersonInfoViewModel extends BaseViewModel {

    private MineChangePersonInfoModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineChangePersonInfoModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
