package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.LimitedTime;
import com.life.waimaishuo.enumtype.LimitedTimeStateEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiLimitedModel;
import com.life.waimaishuo.mvvm.vm.BaseRecyclerViewModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaimaiLimitedViewModel extends BaseViewModel {

    private WaimaiLimitedModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaimaiLimitedModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    @Override
    public void init() {
        super.init();
    }

    public List<LimitedTime> getLimitedTimeData() {
        List<LimitedTime> limitedTimes = new ArrayList<>();
        limitedTimes.add(new LimitedTime("11:00", LimitedTimeStateEnum.STARTING));
        limitedTimes.add(new LimitedTime("12:00", LimitedTimeStateEnum.ROBBING));
        limitedTimes.add(new LimitedTime("13:00", LimitedTimeStateEnum.NO_START));
        limitedTimes.add(new LimitedTime("14:00", LimitedTimeStateEnum.NO_START));
        limitedTimes.add(new LimitedTime("15:00", LimitedTimeStateEnum.NO_START));
        return limitedTimes;
    }
}
