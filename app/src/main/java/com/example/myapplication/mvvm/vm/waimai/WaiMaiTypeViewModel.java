package com.example.myapplication.mvvm.vm.waimai;

import com.example.myapplication.R;
import com.example.myapplication.bean.Shop;
import com.example.myapplication.bean.ui.IconStrData;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.waimai.WaimaiModel;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.waimai.RecommendedFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiTypeViewModel extends BaseViewModel {

    private WaimaiModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaimaiModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        initFoodRecyclerData();
    }

    public List<IconStrData> getSubtypeTitles() {
        return mFoodSubtypeList;
    }

    private List<IconStrData> mFoodSubtypeList = new ArrayList<>();
    private void initFoodRecyclerData(){
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

    public BaseFragment getRecommendedFragment() {
        /*if(recommendedFragment == null){
            recommendedFragment = new RecommendedFragment();
            recommendedFragment.setData(mShopList);
        }
        return recommendedFragment;*/

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
