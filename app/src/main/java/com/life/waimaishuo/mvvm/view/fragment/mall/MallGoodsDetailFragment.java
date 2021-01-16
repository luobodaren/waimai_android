package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.databinding.Observable;

import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallGoodsDetailBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallGoodsDetailViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "商城商品详情页", anim = CoreAnim.slide)
public class MallGoodsDetailFragment extends BaseFragment {

    private final static String KEY_GOODS_DATA = "goods";

    private FragmentMallGoodsDetailBinding mBinding;
    private MallGoodsDetailViewModel mViewModel;

    public static void openPageWithGoodsId(BaseFragment baseFragment, Goods goods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_GOODS_DATA, goods);
        baseFragment.openPage(MallGoodsDetailFragment.class, bundle);
    }

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MallGoodsDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallGoodsDetailBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_goods_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);

        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }
        mViewModel.setGoods(bundle.getParcelable(KEY_GOODS_DATA));
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initAppBarLayoutToolbar();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        MyDataBindingUtil.addCallBack(this, mViewModel.onGoToShopPageObservable,
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        // FIXME: 2021/1/16 传入正确的shop对象
                        MallShopFragment.openPageWithShop(MallGoodsDetailFragment.this,new Shop());
                    }
                });

        mBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int folding = 0;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {    // FIXME: 2020/12/25 存在问题：由于布局使用fitSystemWindow 布局会预留出状态栏空间，但activity又是沉浸式，所以会存在空白
                if (folding == 0) {
                    folding = mBinding.flFolding.getMeasuredHeight()
                            - ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).topMargin
                            - mBinding.appbarLayoutToolbar.getMeasuredHeight();    //可折叠的距离
                }
                float gradient = (float) -verticalOffset / (float) folding;//渐变值
                if ((-verticalOffset) < (folding / 2)) {            //折叠未超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);//状态栏白色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(false);
                    /*mBinding.layoutTitleShopDetail.etSearch.setFocusable(false);    // FIXME: 2020/12/25 无效
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(false);*/
                } else {                                          //折叠超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);//状态栏黑色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(true);
                    /*mBinding.layoutTitle.etSearch.setFocusable(true);
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(true);*/
                }
                LogUtil.d("渐变值：" + gradient);
                /*mBinding.layoutTitleShopDetail.etSearch.setAlpha(gradient);*/

                if ((-verticalOffset) == folding) {//完全折叠
                    LogUtil.d("完全折叠");
                }
            }
        });
    }

    private void initAppBarLayoutToolbar() {
        ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).setMargins(
                0, (int) (UIUtils.getInstance().getSystemBarHeight() / UIUtils.getInstance().getHorValue()), 0, 0);
    }

    private void setTitleBarFoldingStyle(boolean isFolding) {

    }

}
