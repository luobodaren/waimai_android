package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiRecommendReqData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiRecommendedModel;
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
        if(queryType == 0){
            return mModel.getShopList();
        }else if(queryType == 1){
            return mModel.getShopGoodsList();
        }else{
            return new ArrayList<>();
        }
    }

    public void refreshListData(WaiMaiRecommendReqData waiMaiRecommendReqData) {
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
        if(waiMaiRecommendReqData.reqData.getQueryType() == 1 ){
            mModel.requestGoodsListData(requestCallBack, waiMaiRecommendReqData, 3);
        }else if(waiMaiRecommendReqData.reqData.getQueryType() == 0){
            mModel.requestShopListData(requestCallBack, waiMaiRecommendReqData,3);
        }
    }
}
