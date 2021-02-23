package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiReqData;
import com.life.waimaishuo.bean.api.request.bean.SubTypeNameReqData;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiTypeModel;
import com.life.waimaishuo.mvvm.model.waimai.WaimaiModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaimaiRecommendedFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiTypeViewModel extends BaseViewModel {

    private WaiMaiTypeModel mModel;

    public ObservableInt subtypeObservableInt = new ObservableInt();

    public int[] bgImgId =
            {
                    R.mipmap.png_bg_food_type_health_food, R.mipmap.png_bg_food_type_drink,
                    R.mipmap.png_bg_food_type_mark, R.mipmap.png_bg_food_type_fruit,
                    R.mipmap.png_bg_food_type_flowers, R.mipmap.png_bg_food_type_hamburg,
                    R.mipmap.png_bg_food_type_japanese, R.mipmap.png_bg_food_type_self_help,
                    R.mipmap.png_bg_food_type_city
            };

    @Override
    public BaseModel getModel() {
        if (mModel == null) {
            mModel = new WaiMaiTypeModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public BaseFragment getRecommendedFragment() {
        /*if(recommendedFragment == null){
            recommendedFragment = new WaimaiRecommendedFragment();
            recommendedFragment.setData(mShopList);
        }
        return recommendedFragment;*/

        WaimaiRecommendedFragment fragment = new WaimaiRecommendedFragment();
        return fragment;
    }

    public List<String> getPreferential() {
        return Constant.PREFERENTIAL_TABS;
    }

    public List<ImageUrlNameData> getSubtypeTitles() {
        return mModel.mFoodSubtypeList;
    }

    public void refreshSubTypeTitles(String typeName) {
        mModel.requestSubtype(new BaseModel.NotifyChangeRequestCallBack(subtypeObservableInt),
                new WaiMaiReqData.WaiMaiSubTypeReqData(new SubTypeNameReqData(1, typeName)),
                3);
    }

    public int getTopBgImgId(int i) {
        return bgImgId[i];
    }
}
