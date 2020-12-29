package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.CustomLinkagePrimaryShopGoodsAdapterConfig;
import com.life.waimaishuo.adapter.CustomLinkageSecondaryShopGoodsAdapterConfig;
import com.life.waimaishuo.bean.LinkageGroupedItemShopGoods;
import com.life.waimaishuo.databinding.FragmentWaimaiShopOrderDishesBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryShopGoodsItemClickListener;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopOrderDishesViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;

@Page(name = "点餐",anim = CoreAnim.slide)
public class ShopOrderDishesFragment extends BaseFragment
        implements OnPrimaryItemClickListener,
        OnSecondaryShopGoodsItemClickListener {

    private FragmentWaimaiShopOrderDishesBinding mBinding;
    ShopOrderDishesViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new ShopOrderDishesViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiShopOrderDishesBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_shop_order_dishes;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initBanner();
        initLinkageRecycler();
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGroupedItemShopGoods.ItemInfo> item) {
        openPage(WaiMaiGoodsDetailFragment.class,new Bundle()); // FIXME: 2020/12/28 后续需要传入商品id
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageGroupedItemShopGoods.ItemInfo> item) {

    }

    private void initBanner(){
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getBannerSource(),R.layout.adapter_banner_image_item_shop_detail);
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        mBinding.contentLayout.setAdapter(mAdapterHorizontal);
        mBinding.contentLayout.setItemSpace((int) UIUtils.getInstance(getContext()).scalePx(20));
    }

    private void initLinkageRecycler() {
        LinkageRecyclerView<LinkageGroupedItemShopGoods.ItemInfo> linkage = mBinding.linkageWaimaiType;
        linkage.init(mViewModel.getShopGoodsItems(),
                new CustomLinkagePrimaryShopGoodsAdapterConfig<>(this,linkage),
                new CustomLinkageSecondaryShopGoodsAdapterConfig<>(this,linkage,mViewModel));
        linkage.setGridMode(false);
    }

}
