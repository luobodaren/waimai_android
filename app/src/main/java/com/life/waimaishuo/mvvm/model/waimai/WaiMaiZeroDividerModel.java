package com.life.waimaishuo.mvvm.model.waimai;

import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.mvvm.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiZeroDividerModel extends BaseModel {

    private List<IconStrData> mFoodSubtypeList = new ArrayList<>();

    public List<IconStrData> getSubTypeTitle() {
        return mFoodSubtypeList;
    }
}
