package com.life.waimaishuo.mvvm.vm.order;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.EvaluateModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class EvaluateViewModel extends BaseViewModel {

    EvaluateModel mModel;

    public ObservableField<String> driveCommentContentObservable = new ObservableField<>();

    public ObservableField<String> waimaiCommentContentObservable = new ObservableField<>();

    public ObservableField<String> mallGoodsCommentContentObservable = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new EvaluateModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<String> getDriverCommentsType() {
        List<String> list = new ArrayList<>();
        list.add("送餐快");
        list.add("餐品完好");
        list.add("服务态度好");
        list.add("准时到达");
        list.add("着装整洁");
        list.add("X写评价");
        return list;
    }

    public List<String> getPackagingRankInfo() {
        List<String> list = new ArrayList<>();
        list.add("未评价");
        list.add("差劲");
        list.add("一般");
        list.add("好");
        list.add("赞");
        list.add("超赞");
        return list;
    }

    public List<String> getQualityRankInfo() {
        List<String> list = new ArrayList<>();
        list.add("未评价");
        list.add("差劲");
        list.add("一般");
        list.add("好");
        list.add("赞");
        list.add("超赞");
        return list;
    }

    public List<String> getRatingRankInfo() {
        List<String> list = new ArrayList<>();
        list.add("未评价");
        list.add("差劲");
        list.add("一般");
        list.add("好");
        list.add("赞");
        list.add("超赞");
        return list;
    }
}
