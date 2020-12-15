package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.view.View;
import android.view.ViewGroup;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.CustomLinkagePrimaryShopGoodsAdapterConfig;
import com.life.waimaishuo.adapter.CustomLinkageSecondaryShopGoodsAdapterConfig;
import com.life.waimaishuo.bean.LinkageGroupedItemWaimaiType;
import com.life.waimaishuo.databinding.FragmentShopOrderDishesBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryItemClickListener;
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
        OnSecondaryItemClickListener {

    private FragmentShopOrderDishesBinding mBinding;
    ShopOrderDishesViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new ShopOrderDishesViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentShopOrderDishesBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_order_dishes;
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
        initLinkageRecycler();
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo> item) {

    }

    private void initLinkageRecycler() {
        LinkageRecyclerView<LinkageGroupedItemWaimaiType.ItemInfo> linkage = mBinding.linkageWaimaiType;
        linkage.init(mViewModel.getShopGoodsItems(),
                new CustomLinkagePrimaryShopGoodsAdapterConfig<>(this,linkage),
                new CustomLinkageSecondaryShopGoodsAdapterConfig<>(this,linkage));
        linkage.setGridMode(false);
    }

}
