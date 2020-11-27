package com.example.myapplication.mvvm.vm.mine;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.mine.GoodsLogisticsRecyclerViewHolder;
import com.example.myapplication.bean.ui.IconStrRecyclerViewItemData;
import com.example.myapplication.databinding.ItemMineRecyclerGoodLogisticsBinding;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.mine.GoodLogisticsRecyclerModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;

public class GoodLogisticsViewModel extends BaseRecyclerViewModel {

    GoodLogisticsRecyclerModel goodLogisticsRecyclerModel;

    IconStrRecyclerViewItemData data = new IconStrRecyclerViewItemData(0,"");

    @Override
    public void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter) {
        GoodsLogisticsRecyclerViewHolder viewHolder;
        if(mBaseViewHolder instanceof GoodsLogisticsRecyclerViewHolder){
            viewHolder = (GoodsLogisticsRecyclerViewHolder)mBaseViewHolder;
            ItemMineRecyclerGoodLogisticsBinding itemMineRecyclerGoodLogisticsBinding = DataBindingUtil.bind(viewHolder.itemView);
            itemMineRecyclerGoodLogisticsBinding.setItem(data);
        }else{
            LogUtil.e("ERROR:bindModel Fail");
        }
    }

    @Override
    public BaseModel getModel() {
        goodLogisticsRecyclerModel = new GoodLogisticsRecyclerModel();
        return goodLogisticsRecyclerModel;
    }

    @Override
    public void initData() {

    }

    public void setData(IconStrRecyclerViewItemData data) {
        this.data.setData(data);
    }

}
