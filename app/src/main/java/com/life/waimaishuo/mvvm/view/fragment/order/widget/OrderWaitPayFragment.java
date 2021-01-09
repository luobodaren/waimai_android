package com.life.waimaishuo.mvvm.view.fragment.order.widget;

import android.graphics.drawable.Drawable;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import com.bumptech.glide.Glide;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.LayoutOrderStateInfoTopBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiConfirmOrderViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "订单头部信息—等待支付")
public class OrderWaitPayFragment extends BaseFragment {

    LayoutOrderStateInfoTopBinding mBinding;
    WaiMaiConfirmOrderViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (LayoutOrderStateInfoTopBinding)mViewDataBinding;
        mViewModel = (WaiMaiConfirmOrderViewModel) baseViewModel;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_order_state_info_top;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null ;
    }

    @Override
    protected void initViews() {
        super.initViews();

        int drawableSize = (int) UIUtils.getInstance(getContext()).scalePx(44);
        Drawable goPayDrawable = getResources().getDrawable(R.drawable.ic_go_pay);
        goPayDrawable.setBounds(0,0, drawableSize, drawableSize);
        Drawable cancelOrderDrawable = getResources().getDrawable(R.drawable.ic_cancel_order);
        cancelOrderDrawable.setBounds(0,0, drawableSize, drawableSize);
        mBinding.tvLeft.setCompoundDrawables(null,cancelOrderDrawable,null,null);
        mBinding.tvRight.setCompoundDrawables(null,goPayDrawable,null,null);

        Glide.with(this)
                .load(mViewModel.getOrderInfo().getShopIconUrl())
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(mBinding.ivShopIcon);

        initData();

    }

    private void initData() {
        mBinding.tvTitle.setText(requireContext().getString(R.string.wait_for_pay));
        mBinding.tvTitleInfo.setText(requireContext().getString(R.string.pay_ASAP));
        mBinding.tvInfo.setText(requireContext().getString(R.string.waimaishuo_deliver));
        mBinding.tvLeft.setText(requireContext().getString(R.string.cancel_order));
        mBinding.tvRight.setText(requireContext().getString(R.string.go_pay));
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mBinding.tvLeft.setOnClickListener(v -> {

        });
        mBinding.tvRight.setOnClickListener(v -> {

        });
    }

}
