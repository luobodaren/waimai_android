package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.view.View;
import android.view.ViewGroup;

import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.config.CustomLinkagePrimaryMallShopClassificationAdapterConfig;
import com.life.waimaishuo.adapter.config.CustomLinkageSecondaryMallShopClassificationAdapterConfig;
import com.life.waimaishuo.bean.ui.LinkageMallShopClassificationGroupedItemInfo;
import com.life.waimaishuo.databinding.FragmentMallShopClassificationBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryMallShopClassificationItemClickListener;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.views.MyLinkageRecyclerView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import static java.lang.Integer.parseInt;

@Page(name = "商城-分类", anim = CoreAnim.slide)
public class MallShopClassificationFragment extends BaseFragment
        implements OnPrimaryItemClickListener,
        OnSecondaryMallShopClassificationItemClickListener {

    private FragmentMallShopClassificationBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallShopClassificationBinding) mViewDataBinding;
        mBinding.setViewModel((MallShopViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_shop_classification;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initLinkageRecycler();
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onPrimaryItemChange(int position) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item) {

    }

    @Override
    public void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item) {

    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item) {

    }

    private void initLinkageRecycler() {
        MyLinkageRecyclerView<LinkageMallShopClassificationGroupedItemInfo> linkage = mBinding.linkageClassificationType;
        linkage.init(((MallShopViewModel)baseViewModel).getClassificationData(),
                new CustomLinkagePrimaryMallShopClassificationAdapterConfig<>(this),
                new CustomLinkageSecondaryMallShopClassificationAdapterConfig<>(this));
        linkage.setGridMode(false);
    }

}
