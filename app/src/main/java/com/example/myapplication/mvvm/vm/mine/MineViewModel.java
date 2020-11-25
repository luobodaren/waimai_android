package com.example.myapplication.mvvm.vm.mine;

import com.example.myapplication.R;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.GoodLogisticsRecyclerModel;
import com.example.myapplication.mvvm.model.mine.MineModel;
import com.example.myapplication.mvvm.model.mine.TopDataRecyclerModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineViewModel extends BaseViewModel {

    private String name;
    private String detail;
    private int headIconId = R.drawable.ic_waimai_brand;

    private List<TopDataRecyclerModel.Data> mTopDataList = new ArrayList<>();
    private List<GoodLogisticsRecyclerModel.Data> mGoodLogisticsData = new ArrayList<>();

    MineModel myModel;

    @Override
    public BaseModel getModel() {
        myModel = new MineModel();
        return myModel;
    }

    @Override
    public void initData() {
        initTopRecyclerData();
        initGoodLogistics();
    }

    public List<TopDataRecyclerModel.Data> getmTopDataList() {
        return mTopDataList;
    }

    public List<GoodLogisticsRecyclerModel.Data> getGoodLogisticsdata() {
        return mGoodLogisticsData;
    }

    public List<BaseRecyclerViewModel> getTopRecyclerViewModelList() {
        List<BaseRecyclerViewModel> list = new ArrayList<>();
        for(int i = 0; i< mTopDataList.size(); i++){
            TopRecyclerViewModel topRecyclerViewModel = new TopRecyclerViewModel();
            topRecyclerViewModel.setData(mTopDataList.get(i));
            list.add(topRecyclerViewModel);
        }
        return list;
    }

    public List<BaseRecyclerViewModel> getGoodLogisticsRecyclerViewModelList() {
        List<BaseRecyclerViewModel> list = new ArrayList<>();
        for(int i = 0; i< mGoodLogisticsData.size(); i++){
            GoodLogisticsViewModel goodLogisticsViewModel = new GoodLogisticsViewModel();
            goodLogisticsViewModel.setData(mGoodLogisticsData.get(i));
            list.add(goodLogisticsViewModel);
        }
        return list;
    }

    private void initTopRecyclerData(){
        mTopDataList.add(new TopDataRecyclerModel.Data("商品收藏","20"));
        mTopDataList.add(new TopDataRecyclerModel.Data("关注店铺","12"));
        mTopDataList.add(new TopDataRecyclerModel.Data("浏览记录","70"));
        mTopDataList.add(new TopDataRecyclerModel.Data("红包卡卷","30"));
    }

    private void initGoodLogistics(){
        mGoodLogisticsData.add(new GoodLogisticsRecyclerModel.Data(R.drawable.ic_wait_payment,"待付款"));
        mGoodLogisticsData.add(new GoodLogisticsRecyclerModel.Data(R.drawable.ic_wait_delivery,"待配送"));
        mGoodLogisticsData.add(new GoodLogisticsRecyclerModel.Data(R.drawable.ic_in_deliver,"配送中"));
        mGoodLogisticsData.add(new GoodLogisticsRecyclerModel.Data(R.drawable.ic_wait_common,"待评价"));
        mGoodLogisticsData.add(new GoodLogisticsRecyclerModel.Data(R.drawable.ic_after_sales,"售后/退款"));
    }

}
