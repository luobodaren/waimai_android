package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.os.Bundle;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.databinding.FragmentMallShopImpressionBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "店铺印象", anim = CoreAnim.slide)
public class MallShopImpressionFragment extends BaseFragment {

    private final static String KEY_SHOP_DATA = "shop_data";

    private FragmentMallShopImpressionBinding mBinding;

    private Shop shop;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallShopImpressionBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_shop_impression;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        shop = bundle.getParcelable(KEY_SHOP_DATA);

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
    }

    private void initTitle(){
        setViewVisibility(mBinding.layoutTitle.ivShare,false);
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    public static void openPageWithShopData(BaseFragment baseFragment, Shop shop){
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SHOP_DATA,shop);
        baseFragment.openPage(MallShopImpressionFragment.class,bundle);
    }

}
