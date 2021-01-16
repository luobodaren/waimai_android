package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallGoodsDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MallGoodsDetailViewModel extends BaseViewModel {

    public ObservableField<String> onGoToShopPageObservable = new ObservableField<>();

    private MallGoodsDetailModel mModel;

    private Goods mGoods;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallGoodsDetailModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void onGoToShopPageClick(View view){
        onGoToShopPageObservable.notifyChange();
    }

    public void setGoods(Goods goods) {
        this.mGoods = goods;
    }

    public Goods getGoods() {
        return mGoods;
    }
}
