package com.life.waimaishuo.mvvm.vm.mall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.ObservableInt;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainTypeFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallRecyclerRecommendFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallViewModel extends BaseViewModel {

    private MallModel mModel;

    public ObservableInt goToLocation = new ObservableInt();

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
     *  点击定位
     * @param view
     */
    public void onLocatLayoutClick(View view){
        goToLocation.notifyChange();
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

    int drawableSize;
    Drawable drawable = null;
    public Drawable getTitleDrawable(Context context, String title) {   // FIXME: 2021/1/22 后续根据具体接口返回值修改
        if(drawable == null){
            if(drawableSize == 0){
                drawableSize = (int) UIUtils.getInstance().scalePx(75);
            }
            drawable = context.getResources().getDrawable(R.mipmap.text_ic_mall_tab);
            drawable.setBounds(0,0,drawableSize,drawableSize);
        }
        return drawable;
    }

    public List<ImageUrlNameData> getTypeTabData() {
        List<ImageUrlNameData> list = new ArrayList<>();
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        return list;
    }
}
