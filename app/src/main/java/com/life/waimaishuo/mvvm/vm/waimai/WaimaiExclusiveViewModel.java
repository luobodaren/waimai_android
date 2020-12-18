package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.Foods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaimaiExclusiveViewModel extends BaseViewModel {

    WaimaiModel model;

    private List<Foods> mBreakFastList = new ArrayList<>();

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

    public List<Foods> getmBreakFastList() {
        return mBreakFastList;
    }

    private void initExclusiveBreakfast() {
        Foods foods = new Foods();
        foods.setName("饭戒(西丽店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("独家秘制黄焖鸡米饭(大学城店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("饭戒(西丽店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("独家秘制黄焖鸡米饭(大学城店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("饭戒(西丽店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("独家秘制黄焖鸡米饭(大学城店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("饭戒(西丽店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("独家秘制黄焖鸡米饭(大学城店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("饭戒(西丽店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);

        foods = new Foods();
        foods.setName("独家秘制黄焖鸡米饭(大学城店)");
        foods.setScore("4.8");
        foods.setPrice_deliver("20");
        foods.setTime_send(40);
        foods.setCount_per_month(1998);
        foods.setFoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        mBreakFastList.add(foods);
    }


}
