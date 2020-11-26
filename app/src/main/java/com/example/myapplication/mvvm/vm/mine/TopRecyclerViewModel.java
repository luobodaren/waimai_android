package com.example.myapplication.mvvm.vm.mine;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.MyBaseRecyclerViewHolder;
import com.example.myapplication.adapter.mine.TopDataRecyclerViewHolder;
import com.example.myapplication.bean.ui.TypeCountRecyclerViewItemData;
import com.example.myapplication.databinding.VerticalDataShowItemBinding;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public class TopRecyclerViewModel extends BaseRecyclerViewModel {

    TypeCountRecyclerViewItemData data;

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        TopDataRecyclerViewHolder viewHolder;
        if(mBaseViewHolder instanceof TopDataRecyclerViewHolder){
            viewHolder = (TopDataRecyclerViewHolder)mBaseViewHolder;
            VerticalDataShowItemBinding verticalDataShowItemBinding = DataBindingUtil.bind(viewHolder.itemView);
            verticalDataShowItemBinding.setItem(data);
            if(adapter.getData().size() == adapter.getParentPosition(mBaseViewHolder)){
                viewHolder.rightInterval.setVisibility(View.VISIBLE);
            }
        }else{
            LogUtil.e("ERROR:bindModel Fail");
        }
    }

    // FIXME: 2020/11/25 getModel 改掉 initModel
    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    public void setData(TypeCountRecyclerViewItemData data) {
        this.data = data;
    }

}
