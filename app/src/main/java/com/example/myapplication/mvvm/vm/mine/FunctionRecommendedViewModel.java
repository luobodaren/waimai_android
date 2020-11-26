package com.example.myapplication.mvvm.vm.mine;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.MyBaseRecyclerViewHolder;
import com.example.myapplication.adapter.mine.FunctionRecommendedViewHolder;
import com.example.myapplication.bean.ui.IconStrRecyclerViewItemData;
import com.example.myapplication.databinding.ItemMineRecyclerMoreRecommendedBinding;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.FunctionRecommendedModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

public class FunctionRecommendedViewModel extends BaseRecyclerViewModel {

    FunctionRecommendedModel model;

    IconStrRecyclerViewItemData data;

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        FunctionRecommendedViewHolder viewHolder;
        if(mBaseViewHolder instanceof FunctionRecommendedViewHolder){
            viewHolder = (FunctionRecommendedViewHolder)mBaseViewHolder;
            ItemMineRecyclerMoreRecommendedBinding itemMineRecyclerMoreRecommendedBinding = DataBindingUtil.bind(viewHolder.itemView);
            itemMineRecyclerMoreRecommendedBinding.setItem(data);
        }else{
            LogUtil.e("ERROR:bindModel Fail");
        }
    }

    @Override
    public BaseModel getModel() {
        model = new FunctionRecommendedModel();
        return model;
    }

    @Override
    public void initData() {

    }

    public void setData(IconStrRecyclerViewItemData data){
        this.data = data;
    }

}
