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

@Page(name = "订单头部信息—配送完成")
public class OrderDeliverFinishFragment extends BaseFragment {

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
        Drawable reminderDrawable = getResources().getDrawable(R.drawable.ic_one_more_order);
        reminderDrawable.setBounds(0,0, drawableSize, drawableSize);
        Drawable connectedMerchantsDrawable = getResources().getDrawable(R.drawable.ic_message_connect_meachants);
        connectedMerchantsDrawable.setBounds(0,0, drawableSize, drawableSize);
        mBinding.tvLeft.setTextColor(getResources().getColor(R.color.colorTheme));
        mBinding.tvLeft.setCompoundDrawables(null,reminderDrawable,null,null);
        mBinding.tvRight.setCompoundDrawables(null,connectedMerchantsDrawable,null,null);

        Glide.with(this)
                .load(mViewModel.getShop().getShop_head_portrait())
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(mBinding.ivShopIcon);

        initData();
    }

    private void initData() {
        mBinding.tvTitle.setText(R.string.order_delivered);
        mBinding.tvTitleInfo.setText(R.string.thank_words_order_finish);
        mBinding.tvInfo.setText("准时达：订单已准时送达，感谢您使用准时达服务");
        mBinding.tvLeft.setText(R.string.one_mode_order);
        mBinding.tvRight.setText(R.string.connect_merchants);
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
