package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.Observable;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.config.CustomLinkagePrimaryShopGoodsAdapterConfig;
import com.life.waimaishuo.adapter.config.CustomLinkageSecondaryShopGoodsAdapterConfig;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.GoodsShoppingCart;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.respon.WaiMaiShoppingCart;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.bean.event.ShoppingCartOptionEvent;
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

@Page(name = "点餐", anim = CoreAnim.slide)
public class ShopOrderDishesFragment extends BaseFragment
        implements OnPrimaryItemClickListener,
        OnSecondaryShopGoodsItemClickListener {

    private FragmentWaimaiShopOrderDishesBinding mBinding;
    private ShopOrderDishesViewModel mViewModel;

    private MyLinkageRecyclerView<LinkageShopGoodsGroupedItemInfo> linkage; //双列表Recycler

    private SpecificationDialog specificationDialog;    //选规格Dialog

    private Shop shop;

    public WaiMaiShoppingCart waiMaiShoppingCart;   //初始的购物车数据，用于获取到商品数据时，设置以购买数量

    public Goods tempGoods; //保存一个商品信息，用于在请求商品规格后，进行展示

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

        mViewModel.requestShopGoodsGroupList(shop.getShopId());
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onPrimaryItemChange(int position) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        GoodsDetailFragment.openPage(this, shop, item.info.getGoods());
    }

    @Override
    public void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        tempGoods = item.info.getGoods();
        if (view.getId() == R.id.bt_chose_specification || view.getId() == R.id.iv_add) {
            // TODO: 2021/3/4 打开加载dialog
            int buyCount = tempGoods.getChoiceNumber() + 1;
            if (buyCount > 0) {
                tempGoods.setWantBuyCount(String.valueOf(tempGoods.getChoiceNumber() + 1));
                handleAddOrReduceGoodsCount();
            }
        } else if (view.getId() == R.id.iv_remove) {
            if (tempGoods.getIsChooseSpecs() == 0) {
                Toast.makeText(requireContext(), "删除商品请在购物车中删除！", Toast.LENGTH_SHORT).show();
            } else {
                int buyCount = tempGoods.getChoiceNumber() - 1;
                if (buyCount >= 0) {
                    tempGoods.setWantBuyCount(String.valueOf(buyCount));
                    handleAddOrReduceGoodsCount();
                }
            }
        }
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {

    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        super.MessageEvent(messageEvent);
        LogUtil.d(messageEvent.toString());
        if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_ADD_SUCCESS
                || messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_CHANGE_SUCCESS) {
            if (messageEvent instanceof ShoppingCartOptionEvent) {
                ShoppingCartOptionEvent shoppingCartOptionEvent = (ShoppingCartOptionEvent) messageEvent;
                for (BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> linkageShopGoodsGroupedItem : mViewModel.getShopGoodsItems()) {
                    if (linkageShopGoodsGroupedItem.isHeader) {
                        continue;
                    }
                    //linkage最后一项为BaseGroupedItem<DefaultGroupedItem$ItemInfo> 需要添加判断
                    if (linkageShopGoodsGroupedItem.info instanceof LinkageShopGoodsGroupedItemInfo) {
                        if (shoppingCartOptionEvent.goodsId == linkageShopGoodsGroupedItem.info.getGoods().getId()) {
                            linkageShopGoodsGroupedItem.info.getGoods().setChoiceNumber(shoppingCartOptionEvent.buyCount);
                            if(linkageShopGoodsGroupedItem.info.getUiUpdateObservable() != null)
                                linkageShopGoodsGroupedItem.info.getUiUpdateObservable().notifyChange();
                            break;
                        }
                    }
                }
            }
        } else if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_ADD_FALSE) {
            Toast.makeText(requireContext(), "加入购物车失败", Toast.LENGTH_SHORT).show();
        } else if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_CHANGE_FALSE) {
            Toast.makeText(requireContext(), "修改商品失败", Toast.LENGTH_SHORT).show();
        } else if(messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_ADD_WITH_GOODS){
            tempGoods = (Goods) messageEvent.getMessage();  //需要设置好wantBuy
            handleAddOrReduceGoodsCount();
        } else if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_ADD_WITH_GOODS_DIRECT) { //这里直接执行加入购物车操作 而不是点击事件触发的加入购物车
            Goods buyGoods = (Goods) messageEvent.getMessage();
            mViewModel.requestAddShoppingCart(String.valueOf(shop.getShopId()), buyGoods);
        } else if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_ADD_WITH_SHOPPING_DATA) {
            GoodsShoppingCart goodsShoppingCart = (GoodsShoppingCart) messageEvent.getMessage();
            Goods buyGoods = new Goods(goodsShoppingCart);
            buyGoods.setWantBuyCount(String.valueOf(buyGoods.getChoiceNumber() + 1));
            mViewModel.requestChangeShoppingCart(String.valueOf(shop.getShopId()), buyGoods);
        } else if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_REDUCE_WITH_SHOPPING_DATA) {
            GoodsShoppingCart goodsShoppingCart = (GoodsShoppingCart) messageEvent.getMessage();
            Goods buyGoods = new Goods(goodsShoppingCart);
            buyGoods.setWantBuyCount(String.valueOf(buyGoods.getChoiceNumber() - 1));
            mViewModel.requestChangeShoppingCart(String.valueOf(shop.getShopId()), buyGoods);
        } else if (messageEvent.getCode() == MessageCodeConstant.SHOPPING_CART_DEL_LIST_SUCCESS) {
            if (linkage != null && linkage.getSecondaryAdapter() != null) {
                for (BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> linkageShopGoodsGroupedItem : mViewModel.getShopGoodsItems()) {
                    if (!linkageShopGoodsGroupedItem.isHeader) {
                        //linkage最后一项为BaseGroupedItem<DefaultGroupedItem$ItemInfo> 需要添加判断
                        if (linkageShopGoodsGroupedItem.info instanceof LinkageShopGoodsGroupedItemInfo) {
                            linkageShopGoodsGroupedItem.info.getGoods().setChoiceNumber(0);
                        }
                    }
                }
                linkage.getSecondaryAdapter().notifyDataSetChanged();
            }
        }
    }

    /**
     * 点击事件触发的加入购物车操作
     */
    private void handleAddOrReduceGoodsCount() {
        if (tempGoods.getSpecificationList() != null || tempGoods.getAttributeList() != null) {
            if (tempGoods.getIsChooseSpecs() == 1) {  //若无需选择规格
                if (tempGoods.getSpecSelected() == null || "".equals(tempGoods.getSpecSelected())) {  //判断是否以设置选择的规格
                    //设置选择的规格
                    if (tempGoods.getSpecificationList() != null && tempGoods.getSpecificationList().size() > 0) {
                        tempGoods.setSpecSelected(tempGoods.getSpecificationList().get(0).getName());
                    } else {
                        tempGoods.setSpecSelected(getString(R.string.waimai_goods_default_spec_str));
                    }
                }
                if(tempGoods.getAttrs() == null){   //没有设置属性则设置为空字符串
                    tempGoods.setAttrs("");
                }
                if (Integer.valueOf(tempGoods.getWantBuyCount()) == 1 && tempGoods.getChoiceNumber() == 0) {  //由0 -> 1加入购物车
                    mViewModel.requestAddShoppingCart(String.valueOf(shop.getShopId()), tempGoods);
                } else {  //否则 修改购物车
                    mViewModel.requestChangeShoppingCart(String.valueOf(shop.getShopId()), tempGoods);
                }
            } else {
                showChoseSpecificationDialog(tempGoods);
            }
        } else {
            //请求商品规格信息 完成后打开规格Dialog
            mViewModel.requestGoodsSpecification(tempGoods);
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
        linkage = mBinding.linkageOrderDishes;
        linkage.setScrollSmoothly(false);
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
        MyDataBindingUtil.addCallBack(this, mViewModel.requestGoodsSpecificationObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    //判断是加入购物车 还是删除 还是修改
                    handleAddOrReduceGoodsCount();
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.requestShopGoodsObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    //设置购买数量
                    for (BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item : mViewModel.getShopGoodsItems()) {
                        if (item.isHeader)
                            continue;
                        for (GoodsShoppingCart goodsShoppingCart : waiMaiShoppingCart.getDeliveryGoodsDto()) {
                            if (goodsShoppingCart.getGoodsId() == item.info.getGoods().getId()) {
                                item.info.getGoods().setChoiceNumber(Integer.valueOf(goodsShoppingCart.getBuyCount()));
                            }
                        }
                    }
                    initLinkageRecycler();
                });
            }
        });
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
