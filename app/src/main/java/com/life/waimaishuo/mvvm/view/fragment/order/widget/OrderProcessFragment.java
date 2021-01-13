package com.life.waimaishuo.mvvm.view.fragment.order.widget;

import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.LayoutOrderProcessBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiConfirmOrderViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "订单头部信息—已完成")
public class OrderProcessFragment extends BaseFragment {

    LayoutOrderProcessBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (LayoutOrderProcessBinding)mViewDataBinding;
        mBinding.setViewModel((WaiMaiConfirmOrderViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_order_process;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null ;
    }

    @Override
    protected void initViews() {
        super.initViews();


    }

    private void initReservedPhone() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();

    }

}
