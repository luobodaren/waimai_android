package com.example.myapplication.mvvm.vm;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.MyBaseRecyclerViewHolder;
import com.example.myapplication.mvvm.model.BaseModel;

public abstract class BaseRecyclerViewModel extends BaseViewModel {

    /**
     * 数据展示
     * @param mBaseViewHolder
     * @param baseModel
     */
    public abstract void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter);

}
