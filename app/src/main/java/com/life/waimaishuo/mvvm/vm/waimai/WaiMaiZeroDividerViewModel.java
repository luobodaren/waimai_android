package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.fragment.app.Fragment;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiZeroDividerModel;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaimaiRecommendedFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiZeroDividerViewModel extends BaseViewModel {

    private WaiMaiZeroDividerModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiZeroDividerModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    @Override
    public void init() {
        super.init();
    }

    public List<IconStrData> getSubtypeTitles() {
        return mModel.getSubTypeTitle();
    }

    public Fragment getRecommendedFragment() {
        WaimaiRecommendedFragment fragment = new WaimaiRecommendedFragment();
        return fragment;
    }

    public List<String> getPreferential() {
        List<String> cashBackList = new ArrayList<>();
        cashBackList.add("津贴优惠");
        cashBackList.add("会员领红包");
        cashBackList.add("满减优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        cashBackList.add("配送费优惠");
        return cashBackList;
    }
}
