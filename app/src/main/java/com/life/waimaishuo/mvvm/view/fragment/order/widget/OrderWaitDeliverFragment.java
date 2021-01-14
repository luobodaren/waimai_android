package com.life.waimaishuo.mvvm.view.fragment.order.widget;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.LayoutOrderStateInfoTopBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiConfirmOrderViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "订单头部信息—等待配送、配送中")
public class OrderWaitDeliverFragment extends BaseFragment {

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

        int drawableSize = (int) UIUtils.getInstance().scalePx(44);
        Drawable reminderDrawable = getResources().getDrawable(R.drawable.ic_reminder);
        reminderDrawable.setBounds(0,0, drawableSize, drawableSize);
        Drawable connectedMerchantsDrawable = getResources().getDrawable(R.drawable.ic_message_connect_meachants);
        connectedMerchantsDrawable.setBounds(0,0, drawableSize, drawableSize);
        mBinding.tvLeft.setCompoundDrawables(null,reminderDrawable,null,null);
        mBinding.tvRight.setCompoundDrawables(null,connectedMerchantsDrawable,null,null);

        Glide.with(this)
                .load(mViewModel.getOrderInfo().getShopIconUrl())
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(mBinding.ivShopIcon);

        initData();

    }

    private void initData() {
        mBinding.tvTitle.setText(requireContext().getString(R.string.merchants_are_stocking_up));
        mBinding.tvTitleInfo.setText("预计12：35送达");
        mBinding.tvInfo.setText("准时达服务：12:45前未送达，可获得赔付");
        mBinding.tvLeft.setText(requireContext().getString(R.string.reminder));
        mBinding.tvRight.setText(requireContext().getString(R.string.connect_merchants));
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
