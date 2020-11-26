package com.example.myapplication.mvvm.vm.waimai;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.bean.ui.IconStrRecyclerViewItemData;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaimaiViewModel extends BaseViewModel {

    String mLocation;

    private List<IconStrRecyclerViewItemData> mFoodTypeList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {
        initFoodRecyclerData();
    }

    public void onSearchBtClick(View view,String searchStr) {

    }

    public List<IconStrRecyclerViewItemData> getMyFoodDataList() {
        return mFoodTypeList;
    }

    private void initFoodRecyclerData(){
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_health,"健康美食"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_drink,"甜品饮品"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_mark,"超时便利"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_fruit,"蔬菜水果"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_medicine,"送药上门"));

        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_hamburg,"汉堡蔬菜"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_japanese,"日韩料理"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_my_city,"同城零售"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_self_help,"快速自取"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_sala,"沙拉轻食"));

        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_tea,"下午茶"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_tea_shop,"小吃馆"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_local,"地方菜"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_cosmetics,"化妆品"));
        mFoodTypeList.add(new IconStrRecyclerViewItemData(R.mipmap.ic_food_all_type,"全部分类"));
    }

    public List<BaseRecyclerViewModel> getFoodRecyclerViewModelList() {
        List<BaseRecyclerViewModel> list = new ArrayList<>();
        int size = mFoodTypeList.size();
        for(int i = 0; i< size; i++){
             FoodTypeViewModel foodTypeViewModel = new FoodTypeViewModel();
            foodTypeViewModel.setData(mFoodTypeList.get(i));
            list.add(foodTypeViewModel);
        }
        return list;
    }
}
