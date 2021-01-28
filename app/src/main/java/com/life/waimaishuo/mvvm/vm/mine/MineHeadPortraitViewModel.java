package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineHeadPortraitModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineHeadPortraitViewModel extends BaseViewModel {

    private MineHeadPortraitModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineHeadPortraitModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
