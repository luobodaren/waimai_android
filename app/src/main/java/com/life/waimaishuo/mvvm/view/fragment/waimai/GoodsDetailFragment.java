package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.GoodsShoppingCart;
import com.life.waimaishuo.bean.Message;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.respon.WaiMaiShoppingCart;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.bean.event.ShoppingCartOptionEvent;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.databinding.FragmentWaimaiGoodsDetailBinding;
import com.life.waimaishuo.databinding.ItemRecyclerShoppingCartGoodsBinding;
import com.life.waimaishuo.databinding.LayoutDialogShoppingCartExpandBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.order.waimai.OrderConfirmFragment;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiGoodsDetailViewModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.widget.dialog.SpecificationDialog;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

@Page(name = "商品详情", anim = CoreAnim.slide)
public class GoodsDetailFragment extends BaseFragment {

    private final static String BUNDLE_KEY_SHOP = "bundle_key_shop_id";
    private final static String BUNDLE_KEY_GOODS = "bundle_key_goods";

    private FragmentWaimaiGoodsDetailBinding mBinding;
    private WaiMaiGoodsDetailViewModel mViewModel;

    private SpecificationDialog specificationDialog;

    private Shop shop;  //保存店铺传进来的Goods信息，有部分数据需要从这里获取
    private Goods goods;    //保存店铺传进来的Goods信息，有部分数据需要从这里获取

    private BottomSheet mShoppingCartDialog;//购物车dialog
    private MyBaseRecyclerAdapter<GoodsShoppingCart> recyclerGoodsListAdapter; //购物车商品recycler Adapter

    private WaiMaiShoppingCart waiMaiShoppingCart;  //外卖购物车信息

    public static void openPage(BaseFragment baseFragment, Shop shop, Goods goods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY_SHOP, shop);
        bundle.putParcelable(BUNDLE_KEY_GOODS, goods);
        baseFragment.openPage(GoodsDetailFragment.class, bundle);
    }

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new WaiMaiGoodsDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiGoodsDetailBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
        mBinding.layoutShoppingCart.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_goods_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        shop = bundle.getParcelable(BUNDLE_KEY_SHOP);
        goods = bundle.getParcelable(BUNDLE_KEY_GOODS);

        setRegisterEventBus(true);
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        //初始化布局中的title
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);

        return null;
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        //请求购物车数据
        EventBus.getDefault().post(new MessageEvent(MessageCodeConstant.SHOPPING_CART_REQUEST_DATA, null));
        mViewModel.requestGoodsDetail(goods.getId());
    }

    @Override
    protected void initViews() {
        super.initViews();
        //initBanner();
        initNavigationTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBak();

        mBinding.layoutGoodsOptionChose.btChoseSpecification.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                showChoseSpecificationDialog(mViewModel.getGoodsData());
            }
        });

        mBinding.layoutGoodsOptionAddShoppingCart.ivAdd.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                // TODO: 2021/3/4 打开加载dialog
                int buyCount = mViewModel.getGoodsData().getChoiceNumber() + 1;
                if (buyCount > 0) {
                    mViewModel.getGoodsData().setWantBuyCount(String.valueOf(mViewModel.getGoodsData().getChoiceNumber() + 1));
                    handleAddOrReduceGoodsCount();
                }
            }
        });
        mBinding.layoutGoodsOptionAddShoppingCart.ivRemove.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (mViewModel.getGoodsData().getIsChooseSpecs() == 0) {
                    Toast.makeText(requireContext(), "删除商品请在购物车中删除！", Toast.LENGTH_SHORT).show();
                } else {
                    int buyCount = mViewModel.getGoodsData().getChoiceNumber() - 1;
                    if (buyCount >= 0) {
                        mViewModel.getGoodsData().setWantBuyCount(String.valueOf(buyCount));
                        handleAddOrReduceGoodsCount();
                    }
                }
            }
        });
    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        super.MessageEvent(messageEvent);
        switch (messageEvent.getCode()) {
            case MessageCodeConstant.SHOPPING_CART_ADD_SUCCESS:
            case MessageCodeConstant.SHOPPING_CART_CHANGE_SUCCESS:
                if (messageEvent instanceof ShoppingCartOptionEvent) {
                    ShoppingCartOptionEvent shoppingCartOptionEvent = (ShoppingCartOptionEvent) messageEvent;
                    if (shoppingCartOptionEvent.goodsId == mViewModel.getGoodsData().getId()) {
                        mViewModel.getGoodsData().setChoiceNumber(shoppingCartOptionEvent.buyCount);
                        mBinding.layoutGoodsOptionAddShoppingCart.tvCount.setText(String.valueOf(shoppingCartOptionEvent.buyCount));
                    }
                }
                setGoodsOptionLayoutState();
                break;
            case MessageCodeConstant.SHOPPING_CART_DEL_LIST_SUCCESS:
                mViewModel.getGoodsData().setChoiceNumber(0);
                waiMaiShoppingCart = null;
                setShoppingCartData(null, null);
                setGoodsOptionLayoutState();
                break;
            case MessageCodeConstant.SHOPPING_CART_DATA_UPDATE:
                waiMaiShoppingCart = (WaiMaiShoppingCart) messageEvent.getMessage();
                setShoppingCartData(waiMaiShoppingCart, null);
                if (mViewModel.getGoodsData() != null) {
                    for (GoodsShoppingCart goodsShoppingCart : waiMaiShoppingCart.getDeliveryGoodsDto()) {
                        if (goodsShoppingCart.getShopId() == shop.getShopId()
                                && goodsShoppingCart.getGoodsId() == mViewModel.getGoodsData().getId()) {
                            mViewModel.getGoodsData().setChoiceNumber(Integer.valueOf(goodsShoppingCart.getBuyCount()));
                            mBinding.layoutGoodsOptionAddShoppingCart.tvCount.setText(String.valueOf(goodsShoppingCart.getBuyCount()));
                            break;
                        }
                    }
                    setGoodsOptionLayoutState();
                }
                break;
        }
    }

    private void addCallBak() {
        MyDataBindingUtil.addCallBack(this, mViewModel.onShowShoppingCart, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                shopOrCloseShoppingCartDetailDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.goToSettleAccounts, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                OrderConfirmFragment.openPageConfirmOrder(GoodsDetailFragment.this, OrderConfirmFragment.ORDER_ACCESS_WAIMAI); // FIXME: 2021/1/9 判断店铺订单类型传入对应值
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onGetGoodsDetailObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.getGoodsData() != null) {
                        refreshData();
                    }
                    mBinding.stickyNavigationLayout.setNeedResetCanScrollDistance(true);
                });
            }
        });
    }

    private void refreshData() {
        mBinding.setGoods(mViewModel.getGoodsData());

        if (mViewModel.getGoodsData().getDiscount() != null && !"".equals(mViewModel.getGoodsData().getDiscount())) {
            mBinding.layoutPreferential.tvDiscount.setText(getString(R.string.discount, mViewModel.getGoodsData().getDiscount()));
        } else {
            mBinding.layoutPreferential.llContent.setVisibility(View.GONE);
        }

        //由店铺传入的goods信息获取价格
        String price = String.format("￥%s", goods.getSpecialPrice());
        mBinding.tvCurrentPrice.setText(TextUtil.getAbsoluteSpannable(price, (int) UIUtils.getInstance().scalePx(16), 0, 1));

        String price_two = String.format("￥%s", goods.getLinePrice());
        mBinding.tvPrePrice.setText(TextUtil.getAbsoluteSpannable(price_two, (int) UIUtils.getInstance().scalePx(12), 0, 1));
        TextUtil.setStrikeThrough(mBinding.tvPrePrice);

        if (mViewModel.getGoodsData().getDetails() == null || "".equals(mViewModel.getGoodsData().getDetails())) {
            BaseFragment.setViewVisibility(mBinding.tvIntroduce, false);
        } else {
            mBinding.tvIntroduce.setText(mViewModel.getGoodsData().getDetails());
            BaseFragment.setViewVisibility(mBinding.tvIntroduce, true);
        }
        setGoodsOptionLayoutState();
    }

    /**
     * 设置商品操作区域的显示情况
     */
    private void setGoodsOptionLayoutState() {
        if (mViewModel.getGoodsData().getIsChooseSpecs() == 0
                && mViewModel.getGoodsData().getChoiceNumber() == 0) {    //需要选择规格，且购物车无数据时 -》显示选规格按钮
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionAddShoppingCart.llContent, false);
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionChose.llContent, true);
        } else {
            mBinding.layoutGoodsOptionAddShoppingCart.tvCount.setText(String.valueOf(mViewModel.getGoodsData().getChoiceNumber()));
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionAddShoppingCart.llContent, true);
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionChose.llContent, false);
        }
    }

    /*private void initBanner() {
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getGoodsPictures(),R.layout.adapter_banner_image_item_brand_story){
            @Override
            public void bindData(@NonNull RecyclerViewHolder holder, int position, String imgUrl) {
                ((ImageView)holder.findViewById(R.id.iv_item)).setScaleType(ImageView.ScaleType.CENTER_CROP);
                super.bindData(holder, position, imgUrl);
            }
        };
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        mBinding.bannerLayout.setAdapter(mAdapterHorizontal);
    }*/

    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        int space = getResources().getDimensionPixelOffset(R.dimen.goods_detail_tabbar_item_space);
        List<String> tabTitle = mViewModel.getTabTitle();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.stickyView.setItemSpaceInScrollMode(space);
        mBinding.stickyView.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widget_horizontal_bar));
        mBinding.stickyView.setIndicatorWidthAdjustContent(false);
        mBinding.stickyView.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.goods_detail_tabbar_item_text_size));
        addTab(mBinding.stickyView, adapter, tabTitle);
        mBinding.stickyView.setupWithViewPager(mBinding.adaptiveSizeView, false);

        mBinding.adaptiveSizeView.setOffscreenPageLimit(tabTitle.size() - 1);
        mBinding.adaptiveSizeView.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        List<String> titles) {
        Iterator<String> iterator = titles.iterator();
        while (iterator.hasNext()) {
            String title = iterator.next();
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_text_size_selected));
            adapter.addFragment(mViewModel.getTabBarFragment(title,shop,goods), title);
            tabSegment.addTab(tab);
        }
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

    /**
     * 显示购物车详细信息
     */
    private void shopOrCloseShoppingCartDetailDialog() {
        LogUtil.d("显示购物车详情");
        if (mShoppingCartDialog == null) {
            mShoppingCartDialog = new BottomSheet(requireContext());
            View contentView = initShoppingCartDetailView();
            mShoppingCartDialog.setContentView(
                    contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        if (mShoppingCartDialog.isShowing()) {
            mShoppingCartDialog.cancel();
        } else {
            mShoppingCartDialog.show();
        }
    }

    private View initShoppingCartDetailView() {
        View view = View.inflate(getContext(), R.layout.layout_dialog_shopping_cart_expand, null);
        LayoutDialogShoppingCartExpandBinding binding = LayoutDialogShoppingCartExpandBinding.bind(view);

        //设置店铺名称
        binding.tvShopName.setText(shop.getShop_name());

        binding.tvEmptyShoppingCart.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MessageCodeConstant.SHOPPING_CART_DEL_LIST, null));
            }
        });

        binding.recyclerGoodsList.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerGoodsListAdapter = new MyBaseRecyclerAdapter<GoodsShoppingCart>(
                R.layout.item_recycler_shopping_cart_goods, new ArrayList<>(), com.life.waimaishuo.BR.item) {
            int priceSymbolSize = (int) UIUtils.getInstance().scalePx(16);

            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, GoodsShoppingCart item) {
                super.initView(viewDataBinding, helper, item);
                ItemRecyclerShoppingCartGoodsBinding binding1 = (ItemRecyclerShoppingCartGoodsBinding) viewDataBinding;

                binding1.tvGoodsDiscountPrice.setText(
                        TextUtil.getAbsoluteSpannable("￥" + item.getAllPrice(), priceSymbolSize,
                                0, 1));

                Goods goods = new Goods();
                goods.setChoiceNumber(Integer.valueOf(item.getBuyCount()));
                binding1.layoutGoodsOptionAddShoppingCart.setGoods(goods);

                binding1.layoutGoodsOptionAddShoppingCart.ivAdd.setOnClickListener(new BaseActivity.OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        handleAddOrReduceGoodsCount(true, item);
                    }
                });
                binding1.layoutGoodsOptionAddShoppingCart.ivRemove.setOnClickListener(new BaseActivity.OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        handleAddOrReduceGoodsCount(false, item);
                    }
                });
            }

            private void handleAddOrReduceGoodsCount(boolean isAdd, GoodsShoppingCart goodsShoppingCart) {
                //加入购物车
                if (isAdd) {
                    EventBus.getDefault().post(new MessageEvent(
                            MessageCodeConstant.SHOPPING_CART_ADD_WITH_SHOPPING_DATA, goodsShoppingCart));
                } else {
                    EventBus.getDefault().post(new MessageEvent(
                            MessageCodeConstant.SHOPPING_CART_REDUCE_WITH_SHOPPING_DATA, goodsShoppingCart));
                }
            }
        };
        binding.recyclerGoodsList.setAdapter(recyclerGoodsListAdapter);
        binding.recyclerGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            int top_interval = (int) UIUtils.getInstance().scalePx(
                    getContext().getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = top_interval;
                if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {   //最后一项
                    outRect.bottom = 4;
                }
            }
        });
        setShoppingCartData(waiMaiShoppingCart, view);
        return view;
    }

    /**
     * 设置购物车数据
     *
     * @param shoppingCartDialogContentView 购物车dialog的contentView
     */
    private void setShoppingCartData(WaiMaiShoppingCart waiMaiShoppingCart, View shoppingCartDialogContentView) {
        int goodsCount = 0;
        String allPrice = "0";
        String distPrice = "0";
        String cartDesc = "";
        List<GoodsShoppingCart> list = new ArrayList<>();
        if (waiMaiShoppingCart != null) {
            goodsCount = waiMaiShoppingCart.getCount();
            allPrice = waiMaiShoppingCart.getAllMoney();
            if (allPrice == null || "".equals(allPrice))
                allPrice = "0";
            distPrice = waiMaiShoppingCart.getDistPrice();
            if (distPrice == null || "".equals(distPrice))
                distPrice = "0";
            cartDesc = waiMaiShoppingCart.getCartDesc();
            if (cartDesc == null || "".equals(cartDesc))
                cartDesc = "";
            list = waiMaiShoppingCart.getDeliveryGoodsDto();
            if (list == null)
                list = new ArrayList<>();
        }
        //设置商品数量角标
        if (goodsCount > 0) {
            mBinding.layoutShoppingCart.tvGoodsCount.setText(
                    String.valueOf(goodsCount));
            setViewVisibility(mBinding.layoutShoppingCart.tvGoodsCount, true);
        } else {
            setViewVisibility(mBinding.layoutShoppingCart.tvGoodsCount, false);
        }
        //设置总价
        mBinding.layoutShoppingCart.tvSumPrice.setText(String.format("￥%s", allPrice));
        //设置配送费
        mBinding.layoutShoppingCart.tvDistPrice.setText(getString(R.string.dist_price, distPrice));

        /*---------- 下面为更新dialog内数据 ----------*/
        View view = shoppingCartDialogContentView;
        if (view == null) {
            if (mShoppingCartDialog == null)
                return;
            view = mShoppingCartDialog.getContentView();
        }
        if (view == null) {
            return;
        }

        LayoutDialogShoppingCartExpandBinding binding = DataBindingUtil.bind(view);
        if (binding == null)
            return;

        //设置优惠价格
        if (!"".equals(cartDesc)) {
            setViewVisibility(binding.layoutShoppingCart.tvPreferentialPrice, true);
            binding.layoutShoppingCart.tvPreferentialPrice.setText(cartDesc);
        } else {
            setViewVisibility(binding.layoutShoppingCart.tvPreferentialPrice, false);
        }
        //设置商品数量角标
        if (goodsCount > 0) {
            binding.layoutShoppingCart.tvGoodsCount.setText(
                    String.valueOf(goodsCount));
            setViewVisibility(binding.layoutShoppingCart.tvGoodsCount, true);
        } else {
            setViewVisibility(binding.layoutShoppingCart.tvGoodsCount, false);
        }
        //设置总价
        binding.layoutShoppingCart.tvSumPrice.setText(String.format("￥%s", allPrice));
        //设置配送费
        binding.layoutShoppingCart.tvDistPrice.setText(getString(R.string.dist_price, distPrice));
        //刷新商品
        if (recyclerGoodsListAdapter != null) {
            recyclerGoodsListAdapter.setNewData(list);
            recyclerGoodsListAdapter.notifyDataSetChanged();
        }
    }

    private void handleAddOrReduceGoodsCount() {
        if (goods.getIsChooseSpecs() == 1) {  //若无需选择规格
            EventBus.getDefault().post(new MessageEvent(MessageCodeConstant.SHOPPING_CART_ADD_WITH_GOODS, mViewModel.getGoodsData()));
        } else {
            showChoseSpecificationDialog(mViewModel.getGoodsData());
        }
    }

}
