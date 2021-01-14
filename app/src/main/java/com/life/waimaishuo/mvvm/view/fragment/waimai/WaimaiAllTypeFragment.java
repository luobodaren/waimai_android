package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.CustomLinkagePrimaryGoodsTypeAdapterConfig;
import com.life.waimaishuo.adapter.CustomLinkageSecondaryGoodsTypeAdapterConfig;
import com.life.waimaishuo.bean.LinkageGroupedItemWaimaiType;
import com.life.waimaishuo.databinding.FragmentWaimaiAllTypeBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryItemClickListener;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiAllTypeViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

@Page(name = "全部分类", anim = CoreAnim.slide)
public class WaimaiAllTypeFragment extends BaseFragment implements
        OnPrimaryItemClickListener,
        OnSecondaryItemClickListener {

    private FragmentWaimaiAllTypeBinding mBinding;
    private WaiMaiAllTypeViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaiMaiAllTypeViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiAllTypeBinding) mViewDataBinding;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_all_type;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initLinkageRecycler();
        findViewById(R.id.iv_left_action).setOnClickListener(v -> {
            popToBack();
        });
    }

    @Override
    protected void onLifecycleResume() {
        changeStatusBarMode();
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
//        SnackbarUtils.Short(view, title).show();
    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view,
                                     BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo> item) {
//        SnackbarUtils.Short(view, item.info.getGoodsTypeStrings()).show();
        // FIXME: 2020/12/15 bundle传入频道与子类型
        Bundle bundle = new Bundle();
        bundle.putString(WaimaiTypeFragment.BUNDLE_FOOD_TYPE_STR_KEY,item.info.getContent());
        openPage(WaimaiTypeFragment.class,bundle);
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder,
                                     BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo> item) {
        if(item.isHeader){
            openPage(WaimaiTypeFragment.class);
        }
    }


    private void initLinkageRecycler(){
        LinkageRecyclerView<LinkageGroupedItemWaimaiType.ItemInfo> linkage = mBinding.linkageWaimaiType;
        FrameLayout rightTopCustomView = linkage.findViewById(R.id.right_top_custom);
        rightTopCustomView.setVisibility(View.VISIBLE);
        initRightTopCustomView(rightTopCustomView);
        linkage.init(mViewModel.getElemeGroupItems(),
                new CustomLinkagePrimaryGoodsTypeAdapterConfig<>(this,linkage),
                new CustomLinkageSecondaryGoodsTypeAdapterConfig(this));
        linkage.setGridMode(true);

    }

    private void initRightTopCustomView(FrameLayout frameLayout) {
        RadiusImageView imageView = new RadiusImageView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackground(getResources().getDrawable(R.drawable.sr_bg_8dp_white));
        imageView.setCornerRadius(24);
        frameLayout.addView(imageView);
        Glide.with(this)
                .load(mViewModel.getAdvertisingUrl())
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(imageView);
    }
}
