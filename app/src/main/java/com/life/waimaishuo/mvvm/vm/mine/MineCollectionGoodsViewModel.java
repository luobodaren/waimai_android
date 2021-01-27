package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineCollectionGoodsModel;
import com.life.waimaishuo.mvvm.model.mine.MineCollectionShopModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineCollectionGoodsViewModel extends BaseViewModel {

    private MineCollectionGoodsModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineCollectionGoodsModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    /**
     * 有效的商品
     * @return
     */
    public List<Goods> getCollectionGoodsData() {
        List<Goods> goodsList = new ArrayList<>();
        Goods goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setScore("4.8");
        goods.setPrice("20");
        goods.setTime_send(40);
        goods.setCount_per_month(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        goodsList.add(goods);
        return goodsList;
    }

    /**
     * 失效的商品
     * @return
     */
    public List<Goods> getCollectionNoneffectiveGoodsData() {
        List<Goods> goodsList = new ArrayList<>();
        Goods goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setScore("4.8");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setCount_per_month(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        goodsList.add(goods);
        return goodsList;
    }
}
