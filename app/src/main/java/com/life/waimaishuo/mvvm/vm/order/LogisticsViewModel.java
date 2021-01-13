package com.life.waimaishuo.mvvm.vm.order;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.ui.LogisticsData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.LogisticsModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class LogisticsViewModel extends BaseViewModel {

    LogisticsModel mModel;

    public ObservableField<String> logisticsCompanyName = new ObservableField<>();
    public ObservableField<String> logisticsCompanyPhone = new ObservableField<>();
    public ObservableField<String> logisticsOrderCode = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new LogisticsModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        logisticsCompanyName.set("圆通物流");
        logisticsCompanyPhone.set("官方电话 95554>");
        logisticsOrderCode.set("圆通速递 YT576836561134");
    }

    /**
     * 注意 在设置数据的时候 需要按照顺序 从后往前设置
     * @return
     */
    public List<LogisticsData> getLogisticsData() {
        List<LogisticsData> logisticsData = new ArrayList<>();
        logisticsData.add(new LogisticsData("2020-08-19 14:16","已签收","签收人：高小姐，电话：14677839277"));
        logisticsData.add(new LogisticsData("2020-08-19 14:16","运输中","到达【太原市小店区北营街道中转站】"));
        logisticsData.add(new LogisticsData("2020-08-19 14:16","离开【太原市小店区北营街道中转站】"));
        logisticsData.add(new LogisticsData("2020-08-19 14:16","离开【太原市小店区北营街道中转站】"));

        return logisticsData;
    }
}
