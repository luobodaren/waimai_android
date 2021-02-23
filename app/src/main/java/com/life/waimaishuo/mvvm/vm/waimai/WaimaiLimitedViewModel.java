package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.base.utils.LogUtil;
import com.life.base.utils.TimeUtil;
import com.life.waimaishuo.Global;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.LimitedGoods;
import com.life.waimaishuo.bean.LimitedTime;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.request.bean.SecondKillReqData;
import com.life.waimaishuo.bean.api.respon.SecondKillTime;
import com.life.waimaishuo.enumtype.LimitedTimeStateEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiLimitedModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WaimaiLimitedViewModel extends BaseViewModel {

    private WaimaiLimitedModel mModel;

    public ObservableInt requestLimitedTimeObservable = new ObservableInt();
    public ObservableInt requestLimitedContentObservable = new ObservableInt();

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
        if(mModel.secondKillTime != null){
            addLimitedTime(limitedTimes,mModel.secondKillTime.getStartTime(),TimeUtil.getCurrentDay());
            addLimitedTime(limitedTimes,mModel.secondKillTime.getNextStartTime(),TimeUtil.getCurrentDay());
        }
        return limitedTimes;
    }

    public SecondKillTime getSecondKillTime(){
        return mModel.secondKillTime;
    }

    /**
     * 对比时间 并添加进list
     * @param limitedTimes
     * @param time_one
     * @param time_two
     */
    private void addLimitedTime(List<LimitedTime> limitedTimes, String time_one, String time_two){
        if(time_one == null || "".equals(time_one)){
            return;
        }
        int result = TimeUtil.compare_date(time_one, time_two);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtil.getDateByFormat(time_one,TimeUtil.dateFormatYMDHM));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if(minute.length() == 1){
            minute = "0" + minute;
        }
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + minute;
        if(result != 0){
            if(result == 1){
                limitedTimes.add(new LimitedTime(time, LimitedTimeStateEnum.STARTING));
            }else{
                limitedTimes.add(new LimitedTime(time, LimitedTimeStateEnum.NO_START));
            }
        }else{
            LogUtil.e("抢购时间对比出错");
            limitedTimes.add(new LimitedTime(time, LimitedTimeStateEnum.NO_START));
        }
    }

    public void requestLimitedTimeData() {
        mModel.requestSecondKillTime(new BaseModel.NotifyChangeRequestCallBack(requestLimitedTimeObservable),new WaiMaiReqData.WaiMaiSecondKillReqData(5),3);
    }


    public void requestLimitedGoodsList(String startTime, String overTime,int pageNumber,int pageSize) {
        mModel.requestLimitedGoodsList(new BaseModel.NotifyChangeRequestCallBack(requestLimitedContentObservable),
                new WaiMaiReqData.WaiMaiSecondKillContentListReqData(
                        new SecondKillReqData(startTime,overTime,pageNumber,pageSize, Global.userLonAndLat,5)),3);
    }

    public void requestLimitedShopList() {
    }

    public List getLimitedGoodsList(int pageType) { // FIXME: 2021/1/19 根据类型获取数据
        Goods goods = new Goods("烧烤","这是一堆烧烤",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1608280639060&di=3a0b9c974ec98fc1789244936e6f4f02&imgtype=0&src=http%3A%2F%2Fci.xiaohongshu.com%2F8ec567ec-674d-45b5-9cd1-d14be8d2e042%40r_1280w_1280h.jpg",
                2,"550.00");

        List<LimitedGoods> limitedFoods = new ArrayList<>();
        limitedFoods.add(new LimitedGoods(goods,"50","500","500",LimitedTimeStateEnum.NO_START, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","150","500",LimitedTimeStateEnum.STARTING, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","0","500",LimitedTimeStateEnum.SALE_OUT, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","0","500",LimitedTimeStateEnum.STARTING, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","0","500",LimitedTimeStateEnum.SALE_OUT, "长安老牌牛肉店"));
        limitedFoods.add(new LimitedGoods(goods,"50","400","500",LimitedTimeStateEnum.STARTING, "长安老牌牛肉店"));
        return limitedFoods;
    }

}
