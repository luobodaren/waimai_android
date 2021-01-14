package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainTypeFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallOtherTypeFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MallViewModel extends BaseViewModel {

    private MallModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    /**
     * 搜索栏点击
     * @param view
     */
    public void onSearchBtClick(View view){

    }

    public String[] getGoodsTypeStrings() {
        return new String[]{"全部","男装","女装","饰品","内衣","洗护"};
    }

    public BaseFragment getViewPagerFragment(String type) {
        if("全部".equals(type)){
            return new MallMainTypeFragment();
        }else{
            return new MallOtherTypeFragment();
        }
    }
}
