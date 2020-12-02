package com.example.myapplication.mvvm.vm.mine;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.bean.ui.IconStrData;
import com.example.myapplication.databinding.ItemMineRecyclerGoodLogisticsBinding;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.GoodLogisticsRecyclerModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

public class GoodLogisticsViewModel extends BaseRecyclerViewModel {

    GoodLogisticsRecyclerModel goodLogisticsRecyclerModel;

    IconStrData data = new IconStrData(0,"");

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        ItemMineRecyclerGoodLogisticsBinding itemMineRecyclerGoodLogisticsBinding = DataBindingUtil.bind(mBaseViewHolder.itemView);
        itemMineRecyclerGoodLogisticsBinding.setItem(data);
    }

    @Override
    public BaseModel getModel() {
        goodLogisticsRecyclerModel = new GoodLogisticsRecyclerModel();
        return goodLogisticsRecyclerModel;
    }

    @Override
    public void initData() {

    }

    public void setData(IconStrData data) {
        this.data.setData(data);
    }

}
