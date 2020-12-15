package com.life.waimaishuo.mvvm.vm;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.mvvm.model.BaseModel;

public abstract class BaseRecyclerViewModel extends BaseViewModel {

    /**
     * 数据展示
     * @param mBaseViewHolder
     * @param baseModel
     */
    public abstract void bindModel(BaseViewHolder mBaseViewHolder, BaseModel baseModel, MyBaseRecyclerAdapter adapter);

}
