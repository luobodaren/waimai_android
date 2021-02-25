package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.BrandZoneModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class BrandZoneViewModel extends BaseViewModel {

    public BrandZoneModel mModel;

    public ObservableInt requestShopListObservable = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new BrandZoneModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<String> getPreferential() {
        return Constant.PREFERENTIAL_TABS;
    }


    public List<String> getScreenData() {
        List<String> screenData = new ArrayList<>();
        screenData.add("优惠活动");
        screenData.add("优惠活动");
//        screenData.add("商家服务");
//        screenData.add("商家服务");
//        screenData.add("品质");
//        screenData.add("品质");
        screenData.add("人均价格带");
        screenData.add("人均价格带");
        return screenData;
    }

    public List<IconStrData> getSubtypeTitles() {
        return null;
    }

    public List<Shop> getBrandShopList() {
        return mModel.getBrandShopList();
    }

    public void setBrandId(int brandId) {
        mModel.setBrandId(brandId);
    }

    public void setSortRules(SortTypeEnum sortTypeEnum) {
        mModel.setSortRules(sortTypeEnum);

    }

    public void setActivityType(String[] selectedPreferential) {
        mModel.setActivityType(selectedPreferential);
    }

    public void setScreenData(String valueOf, String valueOf1) {
        mModel.setScreenData(valueOf,valueOf1);
    }

    public void refreshListDate() {
        mModel.requestBrandShopData(new BaseModel.NotifyChangeRequestCallBack(requestShopListObservable),3);
    }
}
