package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentFillingLogisticsOrderBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "退货退款——填写物流单号")
public class FillingReturnLogisticsFragment extends BaseFragment {

    FragmentFillingLogisticsOrderBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentFillingLogisticsOrderBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_filling_logistics_order;
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
        mBinding.layoutTitle.tvTitle.setText(R.string.refund_return);
    }
}
