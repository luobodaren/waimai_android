package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineViewModel extends BaseViewModel {

    MineModel myModel;

    @Override
    public BaseModel getModel() {
        myModel = new MineModel();
        return myModel;
    }

    @Override
    public void initData() {

    }

    public String getTopDataValue(int position){
        switch (position){
            case 0:
                return "10";
            case 1:
                return "20";
            case 2:
                return "30";
            case 3:
                return "25";
        }
        return "";
    }

}
