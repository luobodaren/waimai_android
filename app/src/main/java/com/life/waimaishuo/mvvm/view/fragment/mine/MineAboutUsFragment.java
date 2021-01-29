package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.view.View;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentMineAboutUsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "关于我们", anim = CoreAnim.slide)
public class MineAboutUsFragment extends BaseFragment {

    private FragmentMineAboutUsBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineAboutUsBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_about_us;
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

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);
    }
}
