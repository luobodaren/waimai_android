package com.life.waimaishuo.mvvm.vm.waimai;

import android.content.Context;

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

    public WaimaiRecommendedFragment getRecommendedFragment(Context context) {
        WaimaiRecommendedFragment fragment = new WaimaiRecommendedFragment();
        fragment.setTitle(context.getResources().getStringArray(R.array.default_waimai_recommend_titles)[2]);
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

    public List<String> getScreenData() {
        List<String> screenData = new ArrayList<>();
        screenData.add("优惠活动");
        screenData.add("优惠活动");
        screenData.add("人均价格带");
        screenData.add("人均价格带");
        return screenData;
    }
}
