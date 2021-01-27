package com.life.waimaishuo.mvvm.view.fragment.mine;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentSettingBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineSettingViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "设置", anim = CoreAnim.slide)
public class MineSettingFragment extends BaseFragment {

    private FragmentSettingBinding mBinding;
    private MineSettingViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new MineSettingViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentSettingBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
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

        initTitle();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

}
