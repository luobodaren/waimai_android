package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineRiderRecruitModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineRiderRecruitViewModel extends BaseViewModel {

    private MineRiderRecruitModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineRiderRecruitModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }
}
