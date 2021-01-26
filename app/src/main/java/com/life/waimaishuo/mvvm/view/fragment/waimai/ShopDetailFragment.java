package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tagAdapter.CashBackTagAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.MerchantsService;
import com.life.waimaishuo.bean.PreferentialActivity;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.ShoppingCartGood;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentWaimaiShopDetailBinding;
import com.life.waimaishuo.databinding.LayoutDialogShopMorePreferentialBinding;
import com.life.waimaishuo.databinding.LayoutDialogShoppingCartExpandBinding;
import com.life.waimaishuo.enumtype.ShopTabTypeEnum;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.waimai.OrderConfirmFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopDetailViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.widget.pop.MyCheckRoundTextInfoPop;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.Iterator;
import java.util.List;

@Page(name = "店铺详情页", anim = CoreAnim.slide)
public class ShopDetailFragment extends BaseFragment {

    private FragmentWaimaiShopDetailBinding mBinding;
    private ShopDetailViewModel mViewModel;

    private MyCheckRoundTextInfoPop mAddMemberInfoPopWindow;
    private Runnable mCancelPopRunnable;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new ShopDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentWaimaiShopDetailBinding) mViewDataBinding).setViewModel(mViewModel);
        mBinding = (FragmentWaimaiShopDetailBinding) mViewDataBinding;
        mBinding.layoutTitleShopDetail.setViewModel(mViewModel);
        mBinding.layoutMembers.setViewModel(mViewModel);
        mBinding.layoutShopDetails.setViewModel(mViewModel);
        mBinding.layoutShoppingCart.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_shop_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initAppBarLayoutToolbar();

        initHeadDetail();
        initMemberCard();

        initNavigationTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallback();

        /*mBinding.stickyNavigationLayout.setOnScrollChangeListener(moveRatio -> {
            if(moveRatio == 1){
                setStatusBarShowByType(HIDE_STATUS_BAR);
            } else {
                setStatusBarShowByType(SHOW_STATUS_BAR);
            }
        });*/
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //TODO: 2020/12/3 viewpager切换
                if (position == 0) {
                    mBinding.layoutShoppingCart.llShoppingCart.setVisibility(View.VISIBLE);
                } else {
                    mBinding.layoutShoppingCart.llShoppingCart.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int folding = 0;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {    // FIXME: 2020/12/25 存在问题：由于布局使用fitSystemWindow 布局会预留出状态栏空间，但activity又是沉浸式，所以会存在空白
                if (folding == 0) {
                    folding = mBinding.flFolding.getMeasuredHeight()
                            - ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).topMargin
                            - mBinding.appbarLayoutToolbar.getMeasuredHeight();    //可折叠的距离
                }
                float gradient = (float) -verticalOffset / (float) folding;//渐变值
                if ((-verticalOffset) < (folding / 2)) {            //折叠未超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);//状态栏白色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(false);
                    mBinding.layoutTitleShopDetail.etSearch.setFocusable(false);    // FIXME: 2020/12/25 无效
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(false);
                } else {                                          //折叠超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);//状态栏黑色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(true);
                    mBinding.layoutTitleShopDetail.etSearch.setFocusable(true);
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(true);
                }
                LogUtil.d("渐变值：" + gradient);
                mBinding.layoutTitleShopDetail.etSearch.setAlpha(gradient);

                if ((-verticalOffset) == folding) {//完全折叠
                    LogUtil.d("完全折叠");
                }
            }
        });
        mRootView.findViewById(R.id.iv_back).setOnClickListener((v) -> popToBack());
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    private void addCallback() {
        MyDataBindingUtil.addCallBack(this, mViewModel.onBackClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                popToBack();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onShareClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                shopShareDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onCollectClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onMembersCodeClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showMembersQrCodeDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onMorePreferentialClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showBottomPreferentialDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onCancelDialogClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                closePreferentialDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onShowShoppingCart, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                shopOrCloseShoppingCartDetailDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.goToSettleAccounts, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                OrderConfirmFragment.openPageConfirmOrder(ShopDetailFragment.this,null,OrderConfirmFragment.ORDER_ACCESS_WAIMAI); // FIXME: 2021/1/9 判断店铺订单类型传入对应值
            }
        });
    }

    private void initAppBarLayoutToolbar() {
        ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).setMargins(
                0, (int) (UIUtils.getInstance().getSystemBarHeight()/UIUtils.getInstance().getHorValue()), 0, 0);
    }

    private boolean isCollect = false;
    private boolean currentFoldStatus = false;

    private void setTitleBarFoldingStyle(boolean isFolding) {
        initTitleDrawable();

        if (currentFoldStatus != isFolding) {
            currentFoldStatus = isFolding;
            if (isFolding) {
                mBinding.layoutTitleShopDetail.ivBack.setImageDrawable(drawableBackDark);
                mBinding.layoutTitleShopDetail.ivSearch.setImageDrawable(drawableSearchDark);
                if (isCollect) {
                    mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollect);
                } else {
                    mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollectDark);
                }
                mBinding.layoutTitleShopDetail.ivShare.setImageDrawable(drawableShareDark);
            } else {
                mBinding.layoutTitleShopDetail.ivBack.setImageDrawable(drawableBack);
                mBinding.layoutTitleShopDetail.ivSearch.setImageDrawable(drawableSearch);
                if (isCollect) {
                    mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollect);
                } else {
                    mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollectLight);
                }
                mBinding.layoutTitleShopDetail.ivShare.setImageDrawable(drawableShare);
            }
        }
    }

    private Drawable drawableBack;
    private Drawable drawableBackDark;

    private Drawable drawableSearch;
    private Drawable drawableShareDark;

    private Drawable drawableShare;
    private Drawable drawableSearchDark;

    private Drawable drawableCollect;
    private Drawable drawableCollectLight;
    private Drawable drawableCollectDark;
    private boolean isInitTitleDrawable = false;

    private void initTitleDrawable() {
        if (!isInitTitleDrawable) {
            isInitTitleDrawable = true;
            drawableBack = getResources().getDrawable(R.drawable.ic_arrow_left_white);
            drawableBackDark = getResources().getDrawable(R.drawable.ic_arrow_left_black);
            drawableSearch = getResources().getDrawable(R.drawable.ic_search_white);
            drawableSearchDark = getResources().getDrawable(R.drawable.ic_search_black);
            drawableShare = getResources().getDrawable(R.drawable.ic_share);
            drawableShareDark = getResources().getDrawable(R.drawable.ic_share_black);
            drawableCollect = getResources().getDrawable(R.drawable.ic_collect_click);
            drawableCollectLight = getResources().getDrawable(R.drawable.ic_collect_unclick);
            drawableCollectDark = getResources().getDrawable(R.drawable.ic_collect_unclick_black);
        }
    }

    private void initHeadDetail() {
        Shop shop = mViewModel.getShopDetail();

        mBinding.layoutShopDetails.tvShopName.setText(shop.getShop_name());
        mBinding.layoutShopDetails.layoutScoreAndFans.setScore((int) Float.parseFloat(shop.getFavorable_rate()));
        mBinding.layoutShopDetails.layoutScoreAndFans.
                setFansStr(getString(R.string.number_of_fans, shop.getNumber_of_fans()));
        mBinding.layoutShopDetails.tvShopDescribe1.
                setText("月售" + shop.getSale_count_per_month() + "+ 配送约60分钟");
        mBinding.layoutShopDetails.tvMorePreferential.
                setText(getString(R.string.more_preferential, 4));
        mBinding.layoutShopDetails.tvShopAnnouncement.
                setText(getString(R.string.notice_content, shop.getNotice()));
        Glide.with(this).load(shop.getShop_head_portrait()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutShopDetails.ivShopIcon);
        initCashBackFlowTagLayout();
    }

    private void initCashBackFlowTagLayout() {
        CashBackTagAdapter tagAdapter = new CashBackTagAdapter(getContext());
        mBinding.layoutShopDetails.flowlayoutCashBack.setAdapter(tagAdapter);
        mBinding.layoutShopDetails.flowlayoutCashBack.
                setOnTagClickListener((parent, view, position) ->
                        Toast.makeText(getContext(),
                                "点击了：" + parent.getAdapter().getItem(position),
                                Toast.LENGTH_SHORT).show());

        tagAdapter.addTags(mViewModel.getCashBackData());
    }

    private void initMemberCard() {
        Shop shop = mViewModel.getShopDetail();
        mBinding.layoutMembers.tvShopMembersCardName.setText(shop.getMemberCard().getName());
        mBinding.layoutMembers.tvShopMembersCardDescribe.setText(shop.getMemberCard().getDescribe());
        Glide.with(this).load(shop.getShop_head_portrait()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutMembers.ivShopIcon);
    }

    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        int space = getResources().getDimensionPixelOffset(R.dimen.shop_detail_tabbar_item_space);
        List<ShopTabTypeEnum> shopTabTypes = mViewModel.getRecommendedTitle();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        Drawable indicatorDrawable = getResources().getDrawable(R.drawable.sr_widght_horizontal_bar);

        mBinding.tabSegment.setItemSpaceInScrollMode(space);
        mBinding.tabSegment.setIndicatorDrawable(indicatorDrawable);
        mBinding.tabSegment.setIndicatorWidthAdjustContent(false);
        mBinding.tabSegment.setHasIndicator(true);
        mBinding.tabSegment.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size));
        addTab(mBinding.tabSegment, adapter, shopTabTypes);
        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager, false);

        mBinding.viewPager.setOffscreenPageLimit(shopTabTypes.size() - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        List<ShopTabTypeEnum> titles) {
        Iterator<ShopTabTypeEnum> iterator = titles.iterator();
        while (iterator.hasNext()) {
            ShopTabTypeEnum shopTabType = iterator.next();
            String title = shopTabType.getName();
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_text_size_selected));
            adapter.addFragment(mViewModel.getTabBarFragment(shopTabType), title);
            tabSegment.addTab(tab);
        }
    }

    private Dialog mMembersQeCardDialog;
    /**
     * 显示会员二维码
     */
    private void showMembersQrCodeDialog() {
        if (mMembersQeCardDialog == null) {
            mMembersQeCardDialog = new Dialog(requireContext(), R.style.mySimpleNoTitleDialog);
            View view = View.inflate(requireContext(), R.layout.layout_dialog_members_qr_code, null);
            view.findViewById(R.id.bt_members_center).setOnClickListener(new BaseActivity.OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    mMembersQeCardDialog.dismiss();
                    showAddMemberInfo("加入会员成功", true);
                }
            });
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMembersQeCardDialog.setContentView(view, layoutParams);

            mMembersQeCardDialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams params = mMembersQeCardDialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_width));
            params.height = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelSize(R.dimen.members_qr_code_dialog_height));
            mMembersQeCardDialog.getWindow().setAttributes(params);
        }
        mMembersQeCardDialog.show();
    }

    private void showAddMemberInfo(String info, boolean successful) {
        // TODO: 2020/12/1 viewModel层读消息
        // TODO: 2020/12/1 改为在回调时调用
        if (mAddMemberInfoPopWindow == null) {
            mAddMemberInfoPopWindow = new MyCheckRoundTextInfoPop(getContext(), info, successful);
            mAddMemberInfoPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.removeCallbacks(mCancelPopRunnable);
                }
            });
        }

        if (mCancelPopRunnable == null) {
            mCancelPopRunnable = new Runnable() {
                @Override
                public void run() {
                    mAddMemberInfoPopWindow.dismiss();
                }
            };
        }

        mAddMemberInfoPopWindow.showAtCenter(mRootView);
        mHandler.postDelayed(mCancelPopRunnable, Constant.POP_WINDOW_SHOW_TIME);
    }

    private BottomSheet mPreferentialDialog;
    /**
     * 显示更多优惠dialog
     */
    private void showBottomPreferentialDialog() {
        if (mPreferentialDialog == null) {
            View contentView = initPreferentialContentView();
            mPreferentialDialog = new BottomSheet(requireContext());
            mPreferentialDialog.setContentView(contentView,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mPreferentialDialog.show();
    }

    private void closePreferentialDialog() {
        mPreferentialDialog.dismiss();
    }

    /**
     * 初始化更多优惠视图
     *
     * @return
     */
    private View initPreferentialContentView() {
        View view = View.inflate(requireContext(), R.layout.layout_dialog_shop_more_preferential, null);
        LayoutDialogShopMorePreferentialBinding binding = LayoutDialogShopMorePreferentialBinding.bind(view);
        binding.setViewModel(mViewModel);

        binding.tvShopName.setText(mViewModel.getShopDetail().getShop_name());

        binding.redPacketRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.redPacketRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
        binding.redPacketRecyclerView.setAdapter(new MyBaseRecyclerAdapter<RedPacket>(
                R.layout.item_recycler_waimai_dialog_red_packet, mViewModel.getPreferentialData().getRedPacketList(), BR.item) {
            String receiveStr = "";
            String receivedStr = "";

            @Override
            protected void initView(BaseViewHolder helper, RedPacket item) {
                super.initView(helper, item);
                if (receiveStr.equals("")) {
                    receiveStr = getResources().getString(R.string.receive);
                }
                if (receivedStr.equals("")) {
                    receivedStr = getResources().getString(R.string.received);
                }

                ((TextView) helper.getView(R.id.tv_price)).setText(
                        TextUtil.getAbsoluteSpannable("￥" + item.getPriceValue(),
                                (int) UIUtils.getInstance().scalePx(28),0,1));

                Button receiveBt = helper.getView(R.id.bt_receive);
                if (item.isGet()) {
                    receiveBt.setBackground(getResources().getDrawable(R.drawable.sr_stroke_1px_full_radius_red));
                    receiveBt.setText(receivedStr);
                    receiveBt.setTextColor(getResources().getColor(R.color.colorTheme));
                } else {
                    receiveBt.setBackground(getResources().getDrawable(R.drawable.sr_bg_full_corners_theme));
                    receiveBt.setText(receiveStr);
                    receiveBt.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        binding.preferentialRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.preferentialRecyclerView.addItemDecoration(getPreferentialRecyclerItemDecoration());
        binding.preferentialRecyclerView.setAdapter(new MyBaseRecyclerAdapter<PreferentialActivity>(
                R.layout.item_recycler_preferential_activity,
                mViewModel.getPreferentialData().getPreferentialActivityList(), BR.item) {
            @Override
            protected void initView(BaseViewHolder helper, PreferentialActivity item) {
                super.initView(helper, item);
                helper.setText(R.id.tv_tag, item.getName());
            }
        });

        binding.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.serviceRecyclerView.addItemDecoration(getPreferentialRecyclerItemDecoration());
        binding.serviceRecyclerView.setAdapter(new MyBaseRecyclerAdapter<MerchantsService>(
                R.layout.item_recycler_merchants_service,
                mViewModel.getPreferentialData().getMerchantsServiceList(), BR.item) {
            @Override
            protected void initView(BaseViewHolder helper, MerchantsService item) {
                super.initView(helper, item);
                helper.setText(R.id.tv_tag, item.getName());
            }
        });

        binding.tvNoticeContent.setText(getResources().getString(R.string.notice_content, mViewModel.getPreferentialData().getNotice()));

        return view;

    }

    private RecyclerView.ItemDecoration getPreferentialRecyclerItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.preferential_item_space));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.top = interval;
                }
            }
        };
    }

    BottomSheet mShoppingCartDialog;
    /**
     * 显示购物车详细信息
     */
    private void shopOrCloseShoppingCartDetailDialog() {
        LogUtil.d("显示购物车详情");
        if (mShoppingCartDialog == null) {
            View contentView = initShoppingCartDetailView();
            mShoppingCartDialog = new BottomSheet(requireContext());
            mShoppingCartDialog.setContentView(
                    contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        if(mShoppingCartDialog.isShowing()){
            mShoppingCartDialog.cancel();
        }else{
            mShoppingCartDialog.show();
        }
    }

    private View initShoppingCartDetailView() {
        View view = View.inflate(getContext(), R.layout.layout_dialog_shopping_cart_expand, null);
        LayoutDialogShoppingCartExpandBinding binding = LayoutDialogShoppingCartExpandBinding.bind(view);
        binding.setViewModel(mViewModel);

        // FIXME: 2020/12/25  购物车按钮添加viemodel

        binding.recyclerGoodsList.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.recyclerGoodsList.setAdapter(new MyBaseRecyclerAdapter<ShoppingCartGood>(
                R.layout.item_recycler_shopping_cart_goods, mViewModel.getShoppingCartData(), BR.item));
        binding.recyclerGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            int top_interval = (int) UIUtils.getInstance().scalePx(
                    getContext().getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = top_interval;
                if(parent.getChildAdapterPosition(view) == state.getItemCount()-1){   //最后一项
                    outRect.bottom = 4;
                }
            }
        });
        binding.layoutShoppingCart.tvPreferentialPrice.setVisibility(View.VISIBLE); // FIXME: 2020/12/25 暂时在这里处理
        binding.layoutShoppingCart.tvPreferentialPrice.setText(getString(R.string.has_been_reduced_to,"3.88"));

        return view;
    }

    BottomSheet shareDialog;
    private void shopShareDialog(){
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_QQ = 1;
        final int TAG_SHARE_WECHAT_MOMENT = 2;
        final int TAG_SHARE_QZONE = 3;
        final int TAG_SHARE_LOCAL = 4;
        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(getActivity()){
            @Override
            protected int getItemViewLayoutId() {
                return R.layout.item_share_dialog;
            }
        };
        shareDialog = builder
                .addItem(R.drawable.ic_share_wechat, "微信", TAG_SHARE_WECHAT_FRIEND, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.ic_share_qq, "QQ", TAG_SHARE_QQ, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.ic_share_wechat_moment, "朋友圈", TAG_SHARE_WECHAT_MOMENT, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.ic_share_qzone, "QQ空间", TAG_SHARE_QZONE, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    LogUtil.e("tag:" + tag + ", content:" + itemView.toString());
                }).build();
        shareDialog.getContentView().setBackgroundResource(R.drawable.sr_bg_tl_tr_8dp);
        ((TextView)shareDialog.getContentView().findViewById(R.id.bottom_sheet_close_button)).setText(R.string.cancel);
        shareDialog.show();
    }

}
