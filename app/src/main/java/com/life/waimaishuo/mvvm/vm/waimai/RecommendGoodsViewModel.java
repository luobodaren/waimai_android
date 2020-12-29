package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.Foods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.RecommendGoodsModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecommendGoodsViewModel extends BaseViewModel {

    RecommendGoodsModel mModel;

    @Override
    public BaseModel getModel() {
        mModel = new RecommendGoodsModel();
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<Foods> getGoodsList() {
        List<Foods> foods = new ArrayList<>();
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        foods.add(new Foods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        return foods;
    }
}
