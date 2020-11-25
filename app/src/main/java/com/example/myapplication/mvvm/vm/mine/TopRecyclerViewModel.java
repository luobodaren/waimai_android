package com.example.myapplication.mvvm.vm.mine;

import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.adapter.RecyclerViewHolder.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerViewHolder.MyBaseRecyclerViewHolder;
import com.example.myapplication.adapter.mine.TopDataRecyclerAdapter;
import com.example.myapplication.adapter.mine.TopDataRecyclerViewHolder;
import com.example.myapplication.databinding.VerticalDataShowItemBinding;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.TopDataRecyclerModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

import java.util.List;

public class TopRecyclerViewModel extends BaseRecyclerViewModel {

    private static List<TopDataRecyclerModel.Data> datas;

    TopDataRecyclerModel.Data data;

    @Override
    public void bindMode(MyBaseRecyclerViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        TopDataRecyclerViewHolder viewHolder = (TopDataRecyclerViewHolder)mBaseViewHolder;
        VerticalDataShowItemBinding verticalDataShowItemBinding = DataBindingUtil.bind(viewHolder.itemView);
        verticalDataShowItemBinding.setItem(data);
        if(adapter.getData().size() == adapter.getParentPosition(mBaseViewHolder)){
            viewHolder.rightInterval.setVisibility(View.VISIBLE);
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

    public void setData(TopDataRecyclerModel.Data data) {
        this.data = data;
    }
}
