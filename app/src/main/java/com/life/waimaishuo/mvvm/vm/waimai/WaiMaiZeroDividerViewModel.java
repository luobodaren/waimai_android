package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.fragment.app.Fragment;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiZeroDividerModel;
import com.life.waimaishuo.mvvm.view.fragment.waimai.RecommendedFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiZeroDividerViewModel extends BaseViewModel {

    private WaiMaiZeroDividerModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiZeroDividerModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        initFoodRecyclerData();
    }

    @Override
    public void init() {
        super.init();
    }

    public List<IconStrData> getSubtypeTitles() {
        return mFoodSubtypeList;
    }

    private List<IconStrData> mFoodSubtypeList = new ArrayList<>();
    private void initFoodRecyclerData(){    // FIXME: 2020/12/18 这里信息要与主页 全部外卖类同步
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"健康美食"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"甜品饮品"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"超时便利"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"蔬菜水果"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"送药上门"));

        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"汉堡蔬菜"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"日韩料理"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"同城零售"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"快速自取"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"沙拉轻食"));

        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"下午茶"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"小吃馆"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"地方菜"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"化妆品"));
        mFoodSubtypeList.add(new IconStrData(R.mipmap.ic_food_all_subtype,"全部分类"));
    }

    public Fragment getRecommendedFragment() {
        List<Shop> list = new ArrayList<>();
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));
        list.add(new Shop("嘉禾一品粥(国展店)"));

        RecommendedFragment fragment = new RecommendedFragment();
        fragment.setData(list);
        return fragment;
    }

    public List<String> getPreferential() {
        List<String> cashBackList = new ArrayList<>();
        cashBackList.add("津贴优惠");
        cashBackList.add("会员领红包");
        cashBackList.add("满减优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        return cashBackList;
    }
}
