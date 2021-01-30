package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineBusinessCooperationModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineBusinessCooperationViewModel extends BaseViewModel {

    private MineBusinessCooperationModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineBusinessCooperationModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
