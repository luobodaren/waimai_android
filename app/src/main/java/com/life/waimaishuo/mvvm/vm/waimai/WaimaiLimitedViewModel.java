package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.LimitedGoods;
import com.life.waimaishuo.bean.LimitedTime;
import com.life.waimaishuo.enumtype.LimitedTimeStateEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiLimitedModel;
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

    public List<LimitedTime> getLimitedTimeData(int pageType) { // FIXME: 2021/1/19 根据类型获取数据
        List<LimitedTime> limitedTimes = new ArrayList<>();
        limitedTimes.add(new LimitedTime("11:00", LimitedTimeStateEnum.STARTING));
        limitedTimes.add(new LimitedTime("12:00", LimitedTimeStateEnum.ROBBING));
        limitedTimes.add(new LimitedTime("13:00", LimitedTimeStateEnum.NO_START));
        limitedTimes.add(new LimitedTime("14:00", LimitedTimeStateEnum.NO_START));
        limitedTimes.add(new LimitedTime("15:00", LimitedTimeStateEnum.NO_START));
        return limitedTimes;
    }

    public List getLimitedGoodsList(int pageType) { // FIXME: 2021/1/19 根据类型获取数据
        Goods goods = new Goods("烧烤","这是一堆烧烤",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1608280639060&di=3a0b9c974ec98fc1789244936e6f4f02&imgtype=0&src=http%3A%2F%2Fci.xiaohongshu.com%2F8ec567ec-674d-45b5-9cd1-d14be8d2e042%40r_1280w_1280h.jpg",
                "2","550.00");

        List<LimitedGoods> limitedFoods = new ArrayList<>();
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.NO_START, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.STARTING, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.SALE_OUT, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.STARTING, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.SALE_OUT, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.STARTING, "长安老牌牛肉店"));
        return limitedFoods;
    }
}
