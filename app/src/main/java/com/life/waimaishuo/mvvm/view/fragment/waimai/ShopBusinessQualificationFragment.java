package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.view.View;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.LayoutBusinessQualificationBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiBusinessQualificationViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "营业资格")
public class ShopBusinessQualificationFragment extends BaseFragment {

    LayoutBusinessQualificationBinding mBinding;
    WaiMaiBusinessQualificationViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiBusinessQualificationViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (LayoutBusinessQualificationBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_business_qualification;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
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
        initTitleBarView();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitleBarView() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivBack.setOnClickListener((v) -> popToBack());
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);
    }
}
