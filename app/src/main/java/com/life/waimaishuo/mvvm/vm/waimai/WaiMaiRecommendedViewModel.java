package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiRecommendedModel;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaimaiRecommendedFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiRecommendedViewModel extends BaseViewModel {

    private WaiMaiRecommendedModel mModel;

    public ObservableField<String> onRequestListObservable = new ObservableField<>();//保存List总数

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiRecommendedModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<Shop> getListData(int queryType) {
        if(queryType == WaimaiRecommendedFragment.QUERY_TYPE_SHOP){
            return mModel.getShopList();
        }else if(queryType == WaimaiRecommendedFragment.QUERY_TYPE_GOODS){
            return mModel.getShopGoodsList();
        }else if(queryType == WaimaiRecommendedFragment.QUERY_TYPE_ZERO_DELIVER){
            return mModel.getZeroDeliverShopList();
        } else{
            return new ArrayList<>();
        }
    }

    public void refreshListData(WaiMaiReqData.WaiMaiRecommendReqData waiMaiReqData) {
        BaseModel.RequestCallBack<Object> requestCallBack = new BaseModel.RequestCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                onRequestListObservable.set((String) result);
                onRequestListObservable.notifyChange();
            }

            @Override
            public void onFail(String error) {
                onRequestListObservable.set("-1");
                onRequestListObservable.notifyChange();
            }
        };
        if(waiMaiReqData.reqData.getQueryType() == WaimaiRecommendedFragment.QUERY_TYPE_GOODS ){
            mModel.requestGoodsListData(requestCallBack, waiMaiReqData);
        }else if(waiMaiReqData.reqData.getQueryType() == WaimaiRecommendedFragment.QUERY_TYPE_SHOP){
            mModel.requestShopListData(requestCallBack, waiMaiReqData);
        }else if(waiMaiReqData.reqData.getQueryType() == WaimaiRecommendedFragment.QUERY_TYPE_ZERO_DELIVER){    //0元配送
            mModel.requestZeroDeliverListData(requestCallBack,waiMaiReqData);
        }
    }
}
