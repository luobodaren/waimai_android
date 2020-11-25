package com.example.myapplication.mvvm.vm.mine;

import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.MyBaseRecyclerViewHolder;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.GoodLogisticsRecyclerModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

public class GoodLogisticsViewModel extends BaseRecyclerViewModel {

    GoodLogisticsRecyclerModel goodLogisticsRecyclerModel;

    GoodLogisticsRecyclerModel.Data data;

    @Override
    public void bindMode(MyBaseRecyclerViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {

    }

    @Override
    public BaseModel getModel() {
        goodLogisticsRecyclerModel = new GoodLogisticsRecyclerModel();
        return goodLogisticsRecyclerModel;
    }

    @Override
    public void initData() {

    }

    public void setData(GoodLogisticsRecyclerModel.Data data) {
        this.data = data;
    }
}
