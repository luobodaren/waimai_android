package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.RecommendGoodsModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecommendGoodsViewModel extends BaseViewModel {

    RecommendGoodsModel mModel;

    public ObservableInt onGetCommendGoodsObservable = new ObservableInt();

    @Override
    public BaseModel getModel() {
        mModel = new RecommendGoodsModel();
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<Goods> getGoodsList() {
        return mModel.commendGoods;
    }

    public void requestCommendGoods(int shopId, int brandId, int pageNum, int pageSize) {
        mModel.requestCommendGoods(new BaseModel.NotifyChangeRequestCallBack(onGetCommendGoodsObservable), shopId, brandId, pageNum, pageSize);
    }
}
