package com.life.waimaishuo.mvvm.vm.mall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainTypeFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallRecyclerRecommendFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

public class MallViewModel extends BaseViewModel {

    private MallModel mModel;

    public ObservableInt onAllTypeClickObservable = new ObservableInt();

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

    /**
     * 全部点击
     * @param view
     */
    public void onAllTypeClick(View view){
        onAllTypeClickObservable.notifyChange();
    }

    public String[] getGoodsTypeStrings() {
        return new String[]{"男装","女装","饰品","内衣","洗护","数码","全部"};
    }

    public BaseFragment getViewPagerFragment(String type) {
        if("全部".equals(type)){
            return new MallMainTypeFragment();
        }else{
            return new MallRecyclerRecommendFragment();
        }
    }

    
    public Drawable getTitleDrawable(Context context, String title) {   // FIXME: 2021/1/22 后续根据具体接口返回值修改
        Drawable drawable = null;
        switch (title){
            case "全部":
                drawable = context.getDrawable(R.mipmap.)
                break;
            case "女装":
                    break;
        }
        return drawable;
    }
}
