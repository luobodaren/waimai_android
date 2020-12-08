package com.example.myapplication.mvvm.view.fragment.waimai;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CustomLinkagePrimaryAdapterConfig;
import com.example.myapplication.adapter.ElemeSecondaryAdapterConfig;
import com.example.myapplication.bean.ElemeGroupedItem;
import com.example.myapplication.databinding.FragmentWaimaiAllTypeBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaiMaiTypeViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

@Page(name = "全部分类", anim = CoreAnim.slide)
public class WaimaiTypeFragment extends BaseFragment implements
        CustomLinkagePrimaryAdapterConfig.OnPrimaryItemClickListener,
        ElemeSecondaryAdapterConfig.OnSecondaryItemClickListener{

    private FragmentWaimaiAllTypeBinding mBinding;
    private WaiMaiTypeViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaiMaiTypeViewModel();
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
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
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
                                     BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
//        SnackbarUtils.Short(view, item.info.getTitle()).show();
    }

    @Override
    public void onGoodAdd(View view, BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
//        SnackbarUtils.Short(view, "添加：" + item.info.getTitle()).show();
    }

    private void initLinkageRecycler(){
        LinkageRecyclerView<ElemeGroupedItem.ItemInfo> linkage = mBinding.linkageWaimaiType;
        FrameLayout rightTopCustomView = linkage.findViewById(R.id.right_top_custom);
        initRightTopCustomView(rightTopCustomView);
        linkage.init(mViewModel.getElemeGroupItems(),
                new CustomLinkagePrimaryAdapterConfig<>(this,linkage),
                new ElemeSecondaryAdapterConfig(this));
        linkage.setGridMode(true);

    }

    private void initRightTopCustomView(FrameLayout frameLayout) {
        RadiusImageView imageView = new RadiusImageView(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackground(getResources().getDrawable(R.drawable.sr_bg_card_view_white));
        imageView.setCornerRadius(24);
        frameLayout.addView(imageView);
        Glide.with(this)
                .load("https://img.pic88.com/16067341360375.jpg")
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(imageView);
    }
}
