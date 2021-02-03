package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiRecommendedModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.List;

public class WaiMaiReccommendedViewModel extends BaseViewModel {

    private WaiMaiRecommendedModel mModel;

    public BaseObservable onRequestShopListObservable = new BaseObservable();

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

    public List getListData(String string) {
        return mModel.getListData(string);
    }

    public void refreshListData(String title) {
        if(title.equals(
                MyApplication.getMyApplication().getResources().getStringArray(
                        R.array.default_waimai_recommend_titles)[0])){
//            mModel.requestRecommendShopListData(title, new BaseModel.NotifyChangeRequestCallBack(onRequestShopListObservable),3);
        }else if(title.equals(
                MyApplication.getMyApplication().getResources().getStringArray(
                        R.array.default_waimai_recommend_titles)[1])){
//            mModel.requestFoundGoodsShopListData(new BaseModel.NotifyChangeRequestCallBack(onRequestShopListObservable),3);
        }else{
//            mModel.requestShopListData(title, new BaseModel.NotifyChangeRequestCallBack(onRequestShopListObservable),3);
        }
    }
}
