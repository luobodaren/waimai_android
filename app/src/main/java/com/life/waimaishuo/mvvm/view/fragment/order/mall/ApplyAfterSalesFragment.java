package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageSelectGridAdapter;
import com.life.waimaishuo.databinding.FragmentApplyAfterSalesMallBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.ApplyAfterSalesViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Page(name = "申请售后-商城", anim = CoreAnim.slide)
public class ApplyAfterSalesFragment extends BaseFragment {

    public final static String KEY_ORDER = "order_key";

    private FragmentApplyAfterSalesMallBinding mBinding;
    private ApplyAfterSalesViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ApplyAfterSalesViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentApplyAfterSalesMallBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_after_sales_mall;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);

        if(getArguments() != null){
            mViewModel.setOrder(getArguments().getParcelable(KEY_ORDER));
        }
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        init();

        initTitle();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mBinding.tvWantRefund.setOnClickListener(v -> {
            if (getArguments() != null) {
                RefundFragment.openPageRefundReturn(this, getArguments().getParcelable(KEY_ORDER), false);
            }
            LogUtil.e("bundle == null");
        });
        mBinding.tvRefundReturnGoods.setOnClickListener(v -> {
            if (getArguments() != null) {
                RefundFragment.openPageRefundReturn(this, getArguments().getParcelable(KEY_ORDER), true);
            }
            LogUtil.e("bundle == null");
        });
    }

    private void init() {
        mBinding.tvGoodsInfo.setText("颜色分类：黄色");
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(requireContext().getString(R.string.chose_service_type));
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);
    }


}
