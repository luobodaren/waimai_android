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

    FragmentApplyAfterSalesMallBinding mBinding;

    private final int maxNoteCharts = 50;

    ImageSelectGridAdapter imageSelectGridAdapter;
    private int maxSelectNum = 6;   //注意若要修改时 需要连同提示语一起修改
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected BaseViewModel setViewModel() {
        return null;
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
        mBinding.tvShopName.setText("欧舒丹甜蜜樱花沐浴啫喱/身体乳套装沐/欧舒丹甜蜜樱花沐浴啫喱/身体乳套装沐...");
        mBinding.tvGoodsInfo.setText("颜色分类：黄色");
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(requireContext().getString(R.string.chose_service_type));
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);
    }


}
