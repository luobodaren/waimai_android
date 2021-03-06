package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaimaiExclusiveViewModel extends BaseViewModel {

    WaimaiModel model;

    private List<Goods> mBreakFastList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        model = new WaimaiModel();
        return model;
    }

    @Override
    public void initData() {
        initExclusiveBreakfast();
    }

    @Override
    public void init() {
        super.init();


    }

    public List<Goods> getmBreakFastList() {
        return mBreakFastList;
    }

    private void initExclusiveBreakfast() {
        Goods goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("独家秘制黄焖鸡米饭(大学城店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("独家秘制黄焖鸡米饭(大学城店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("独家秘制黄焖鸡米饭(大学城店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("独家秘制黄焖鸡米饭(大学城店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("饭戒(西丽店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);

        goods = new Goods();
        goods.setName("独家秘制黄焖鸡米饭(大学城店)");
        goods.setPrice_deliver("20");
        goods.setTime_send(40);
        goods.setMonSalesVolume(1998);
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(goods);
    }


}
