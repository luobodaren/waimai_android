package com.life.waimaishuo.mvvm.vm.mall;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallGoodsDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MallGoodsDetailViewModel extends BaseViewModel {

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

    public void setGoods(Goods goods) {
        this.mGoods = goods;
    }

    public Goods getGoods() {
        return mGoods;
    }
}
