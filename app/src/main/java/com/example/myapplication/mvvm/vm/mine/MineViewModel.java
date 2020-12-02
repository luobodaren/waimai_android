package com.example.myapplication.mvvm.vm.mine;

import com.example.myapplication.R;
import com.example.myapplication.bean.ui.IconStrData;
import com.example.myapplication.bean.ui.TypeCountData;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.MineModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineViewModel extends BaseViewModel {

    private String name;
    private String detail;
    private int headIconId = R.drawable.ic_waimai_brand;

    private List<TypeCountData> mTopDataList = new ArrayList<>();
    private List<IconStrData> mGoodLogisticsData = new ArrayList<>();
    private List<IconStrData> mMoreRecommendData = new ArrayList<>();

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
        initMoreRecommene();
    }

    public List<TypeCountData> getmTopDataList() {
        return mTopDataList;
    }

    public List<IconStrData> getGoodLogisticsdata() {
        return mGoodLogisticsData;
    }

    public List<IconStrData> getMoreRecommenedData() {
        return mMoreRecommendData;
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
        mTopDataList.add(new TypeCountData("商品收藏","20"));
        mTopDataList.add(new TypeCountData("关注店铺","12"));
        mTopDataList.add(new TypeCountData("浏览记录","70"));
        mTopDataList.add(new TypeCountData("红包卡卷","30"));
    }

    private void initGoodLogistics(){
        mGoodLogisticsData.add(new IconStrData(R.drawable.ic_wait_payment,"待付款"));
        mGoodLogisticsData.add(new IconStrData(R.drawable.ic_wait_delivery,"待配送"));
        mGoodLogisticsData.add(new IconStrData(R.drawable.ic_in_deliver,"配送中"));
        mGoodLogisticsData.add(new IconStrData(R.drawable.ic_wait_common,"待评价"));
        mGoodLogisticsData.add(new IconStrData(R.drawable.ic_after_sales,"售后/退款"));
    }

    private void initMoreRecommene(){
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_adress,"地址管理"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_award,"推荐有奖"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_collect,"我的收藏"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_tenants,"商家入驻"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_cooperation,"商务合作"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_service,"我的客服"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_rider,"骑手招募"));
        mMoreRecommendData.add(new IconStrData(R.drawable.ic_recomended_help,"帮助中心"));
    }

}
