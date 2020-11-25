package com.example.myapplication.mvvm.vm.mine;

import com.example.myapplication.R;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.MineModel;
import com.example.myapplication.mvvm.model.mine.TopDataRecyclerModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.mine.TopRecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineViewModel extends BaseViewModel {

    String name;
    String detail;
    int headIconId = R.drawable.ic_waimai_brand;

    List<TopDataRecyclerModel.Data> dataList = new ArrayList<>();

    MineModel myModel;

    @Override
    public BaseModel getModel() {
        myModel = new MineModel();
        return myModel;
    }

    @Override
    public void initData() {
        initTopRecyclerData();
    }

    public List<TopDataRecyclerModel.Data> getDataList() {
        return dataList;
    }

    public List<BaseRecyclerViewModel> getTopRecyclerViewModelList() {
        List<BaseRecyclerViewModel> list = new ArrayList<>();
        for(int i = 0; i< dataList.size();i++){
            TopRecyclerViewModel topRecyclerViewModel = new TopRecyclerViewModel();
            topRecyclerViewModel.setData(dataList.get(i));
            list.add(topRecyclerViewModel);
        }
        return list;
    }

    private void initTopRecyclerData(){
        dataList.add(new TopDataRecyclerModel.Data("商品收藏","20"));
        dataList.add(new TopDataRecyclerModel.Data("关注店铺","12"));
        dataList.add(new TopDataRecyclerModel.Data("浏览记录","70"));
        dataList.add(new TopDataRecyclerModel.Data("红包卡卷","30"));
    }
}
