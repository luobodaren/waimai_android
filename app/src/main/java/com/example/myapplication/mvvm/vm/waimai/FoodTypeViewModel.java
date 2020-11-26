package com.example.myapplication.mvvm.vm.waimai;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.MyBaseRecyclerViewHolder;
import com.example.myapplication.adapter.mine.FoodTypeRecyclerViewHolder;
import com.example.myapplication.bean.ui.IconStrRecyclerViewItemData;
import com.example.myapplication.bean.ui.TypeCountRecyclerViewItemData;
import com.example.myapplication.databinding.ItemWaimaiRecyclerFoodTypeBinding;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

public class FoodTypeViewModel extends BaseRecyclerViewModel {


    private IconStrRecyclerViewItemData data;

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        if(mBaseViewHolder instanceof FoodTypeRecyclerViewHolder){
            FoodTypeRecyclerViewHolder viewHolder = (FoodTypeRecyclerViewHolder) mBaseViewHolder;
            ItemWaimaiRecyclerFoodTypeBinding binding = DataBindingUtil.bind(viewHolder.itemView);
            binding.setItem(data);
        }
//        else if(mBaseViewHolder instanceof FoodSubtypesRecyclerViewHolder){
//            FoodTypeRecyclerViewHolder viewHolder = (FoodTypeRecyclerViewHolder) mBaseViewHolder;
//            ItemWaimaiRecyclerFoodTypeBinding binding = DataBindingUtil.bind(viewHolder.itemView);
//            binding.setItem(data);
//        }
        else{
            LogUtil.e("ERROR:bindModel Fail");
        }
    }

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    public void setData(IconStrRecyclerViewItemData data) {
        this.data = data;
    }
}
