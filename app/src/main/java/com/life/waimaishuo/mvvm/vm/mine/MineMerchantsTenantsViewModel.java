package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineMerchantsTenantsModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mine.MinePlatformIntroduceFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MineMerchantsTenantsViewModel extends BaseViewModel {

    private MineMerchantsTenantsModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineMerchantsTenantsModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public String[] getTabTitleStrings() {
        String[] strings = {"平台介绍", "开店流程", "开店要求", "开店帮助"};
        return strings;
    }

    public BaseFragment getViewPagerFragment(String title) {
        return new MinePlatformIntroduceFragment();
        /*switch (title){
            case "平台介绍":
                return new MinePlatformIntroduceFragment();
        }
        return null;*/
    }
}
