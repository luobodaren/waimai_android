package com.life.waimaishuo.mvvm.view.fragment.mine;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentBusinessCooperationBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineBusinessCooperationViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "商务合作", anim = CoreAnim.slide)
public class MineBusinessCooperationFragment extends BaseFragment {

    private FragmentBusinessCooperationBinding mBinding;
    private MineBusinessCooperationViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineBusinessCooperationViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentBusinessCooperationBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_business_cooperation;
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
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);

        //mBinding.layoutTitle.tvRight.setText(R.string.record_of_apply);
    }

}
