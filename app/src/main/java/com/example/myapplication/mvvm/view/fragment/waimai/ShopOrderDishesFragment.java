package com.example.myapplication.mvvm.view.fragment.waimai;

import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CustomLinkagePrimaryShopGoodsAdapterConfig;
import com.example.myapplication.adapter.CustomLinkageSecondaryShopGoodsAdapterConfig;
import com.example.myapplication.bean.LinkageGroupedItemWaimaiType;
import com.example.myapplication.databinding.FragmentShopOrderDishesBinding;
import com.example.myapplication.listener.OnPrimaryItemClickListener;
import com.example.myapplication.listener.OnSecondaryItemClickListener;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.ShopOrderDishesViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "点餐",anim = CoreAnim.slide)
public class ShopOrderDishesFragment extends com.example.myapplication.mvvm.view.fragment.BaseFragment
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
        setFitStatusBarHeight(true);
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
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
                new CustomLinkageSecondaryShopGoodsAdapterConfig(this));
        linkage.setGridMode(false);
    }

}
