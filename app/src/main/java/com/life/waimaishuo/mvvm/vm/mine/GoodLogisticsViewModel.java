package com.life.waimaishuo.mvvm.vm.mine;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.ItemMineRecyclerGoodLogisticsBinding;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.GoodLogisticsRecyclerModel;
import com.life.waimaishuo.mvvm.vm.BaseRecyclerViewModel;

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
