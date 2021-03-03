package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.Observable;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.config.CustomLinkagePrimaryShopGoodsAdapterConfig;
import com.life.waimaishuo.adapter.config.CustomLinkageSecondaryShopGoodsAdapterConfig;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.databinding.FragmentWaimaiShopOrderDishesBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryShopGoodsItemClickListener;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopOrderDishesViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.views.MyLinkageRecyclerView;
import com.life.waimaishuo.views.widget.dialog.SpecificationDialog;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Page(name = "点餐", anim = CoreAnim.slide)
public class ShopOrderDishesFragment extends BaseFragment
        implements OnPrimaryItemClickListener,
        OnSecondaryShopGoodsItemClickListener {

    private FragmentWaimaiShopOrderDishesBinding mBinding;
    private ShopOrderDishesViewModel mViewModel;

    private SpecificationDialog specificationDialog;    //选规格Dialog

    private int shopId;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new ShopOrderDishesViewModel();
        }
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
        setRegisterEventBus(true);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initBanner();

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        mViewModel.requestShopGoodsGroupList(shopId);
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onPrimaryItemChange(int position) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        GoodsDetailFragment.openPage(this, shopId, item.info.getGoods());
    }

    @Override
    public void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        if (view.getId() == R.id.bt_chose_specification) {
            //请求商品规格信息
            mViewModel.requestGoodsSpecification();
        } else if (view.getId() == R.id.iv_add) {
            //开启加载dialog
            if(item.info.getGoods().getChoiceNumber() == 0){    //若没有数据 则加入购物车
                mViewModel.requestAddShoppingCart(String.valueOf(shopId), item.info.getGoods(),
                        "1","", "");
            }else{  //若有 则修改购物车加一
                mViewModel.requestChangeShoppingCart(String.valueOf(shopId), item.info.getGoods(),
                        String.valueOf(item.info.getGoods().getChoiceNumber() + 1),"", "");
            }
        } else if (view.getId() == R.id.iv_remove) {
            //开启加载dialog
            mViewModel.requestChangeShoppingCart(String.valueOf(shopId), item.info.getGoods(),
                    String.valueOf(item.info.getGoods().getChoiceNumber() - 1),"", "");
        }
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {

    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        super.MessageEvent(messageEvent);
        LogUtil.d(messageEvent.toString());
        if(messageEvent.getCode() == MessageCodeConstant.ADD_SHOPPING_CART_SUCCESS){

        }else if(messageEvent.getCode() == MessageCodeConstant.ADD_SHOPPING_CART_FALSE){

        }
    }


    private void initBanner() {
        mBinding.contentLayout.setVisibility(View.GONE);    //目前不需要展示
        /*BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getBannerSource(), R.layout.adapter_banner_image_item_shop_detail);
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(), "点击了轮播图：" + position, Toast.LENGTH_SHORT).show());
        mBinding.contentLayout.setAdapter(mAdapterHorizontal);
        mBinding.contentLayout.setItemSpace((int) UIUtils.getInstance().scalePx(20));*/
    }

    private void initLinkageRecycler() {
        if (mViewModel.getShopGoodsItems().size() <= 0) {  //构造空数据
            mViewModel.getShopGoodsItems().add(new BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>(true, "没有数据") {
            });
        }
        MyLinkageRecyclerView<LinkageShopGoodsGroupedItemInfo> linkage = mBinding.linkageOrderDishes;
        linkage.init(mViewModel.getShopGoodsItems(),
                new CustomLinkagePrimaryShopGoodsAdapterConfig<>(this, linkage),
                new CustomLinkageSecondaryShopGoodsAdapterConfig<>(this));
        linkage.setGridMode(false);
    }

    private void showChoseSpecificationDialog(Goods goods) {
        if (goods == null) {
            return;
        }
        if (specificationDialog == null) {
            specificationDialog = new SpecificationDialog(requireContext());
        }

        specificationDialog.setData(goods);

        if (!specificationDialog.isShowing()) {
            specificationDialog.show();
        }
    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.requestShopGoodsObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> initLinkageRecycler());
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.requestGoodsSpecificationObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> showChoseSpecificationDialog(mViewModel.getGoodsSpecification()));
            }
        });
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
