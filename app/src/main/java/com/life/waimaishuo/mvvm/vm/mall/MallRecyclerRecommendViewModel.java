package com.life.waimaishuo.mvvm.vm.mall;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallRecommendModel;
import com.life.waimaishuo.mvvm.model.mall.MallRecyclerRecommendModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallRecyclerRecommendViewModel extends BaseViewModel {

    MallRecyclerRecommendModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallRecyclerRecommendModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<Goods> getRecommendGoodsList() {
        List<Goods> goods = new ArrayList<>();
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        goods.add(new Goods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640","600","18.0"));
        return goods;
    }

}
