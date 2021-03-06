package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.CashBackTagAdapter;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.GoodsShoppingCart;
import com.life.waimaishuo.bean.api.respon.WaiMaiShoppingCart;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.databinding.FragmentWaimaiShopDetailBinding;
import com.life.waimaishuo.databinding.ItemRecyclerShoppingCartGoodsBinding;
import com.life.waimaishuo.databinding.LayoutDialogShopMorePreferentialBinding;
import com.life.waimaishuo.databinding.LayoutDialogShoppingCartExpandBinding;
import com.life.waimaishuo.enumtype.ShopTabTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.waimai.OrderConfirmFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopDetailViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.widget.pop.MyCheckRoundTextInfoPop;
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
import java.util.concurrent.CountDownLatch;

import static java.lang.Integer.parseInt;

@Page(name = "???????????????", anim = CoreAnim.slide)
public class ShopDetailFragment extends BaseFragment {

    private final static String BUNDLE_KEY_SHOP_ID = "key_shop_id";

    private FragmentWaimaiShopDetailBinding mBinding;
    private ShopDetailViewModel mViewModel;

    private MyCheckRoundTextInfoPop mAddMemberInfoPopWindow;
    private Runnable mCancelPopRunnable;

    private int shopId;

    private CashBackTagAdapter shopCashBackTagAdapter;

    private Dialog mMembersQeCardDialog;    //?????????dialog

    private BottomSheet mShoppingCartDialog;//?????????dialog
    private MyBaseRecyclerAdapter<GoodsShoppingCart> recyclerGoodsListAdapter; //???????????????recycler Adapter

    private CountDownLatch countDownLatch;

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

        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }

        shopId = bundle.getInt(BUNDLE_KEY_SHOP_ID);

        setRegisterEventBus(true);
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

        initTitleDrawable();

        initAppBarLayoutToolbar();

        initCashBackFlowTagLayout();
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
                //TODO: 2020/12/3 viewpager??????
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
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (folding == 0) {
                    folding = mBinding.flFolding.getMeasuredHeight()
                            - ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).topMargin
                            - mBinding.appbarLayoutToolbar.getMeasuredHeight();    //??????????????????
                }
                float gradient = (float) -verticalOffset / (float) folding;//?????????
                if ((-verticalOffset) < (folding / 2)) {            //?????????????????????
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);//?????????????????????
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(false);
                    mBinding.layoutTitleShopDetail.etSearch.setFocusable(false);    // FIXME: 2020/12/25 ?????? ?????????????????? ????????????????????????
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(false);
                } else {                                          //??????????????????
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);//?????????????????????
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(true);
                    mBinding.layoutTitleShopDetail.etSearch.setFocusable(true);
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(true);
                }
                mBinding.layoutTitleShopDetail.etSearch.setAlpha(gradient);

                if ((-verticalOffset) == folding) {//????????????
                    LogUtil.d("????????????");
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

    @Override
    protected void firstRequestData() {
        super.firstRequestData();
        countDownLatch = new CountDownLatch(2);
        Thread thread = new Thread(() -> {  //?????????????????? ???????????? ????????????????????????????????????????????????????????????????????????
            mViewModel.requestShopInfo(shopId);
            mViewModel.requestShoppingCart(shopId);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch = null;
            mHandler.post(this::initNavigationTab);
        });
        thread.start();

        mViewModel.requestIsJoinShopFans(shopId);
        mViewModel.requestIsCollectShop(shopId);
    }

    @Override
    public void MessageEvent(MessageEvent messageEvent) {
        super.MessageEvent(messageEvent);
        switch (messageEvent.getCode()) {
            case MessageCodeConstant.SHOPPING_CART_ADD_SUCCESS:
            case MessageCodeConstant.SHOPPING_CART_CHANGE_SUCCESS:
            case MessageCodeConstant.SHOPPING_CART_REQUEST_DATA:
                //?????????????????????
                mViewModel.requestShoppingCart(shopId);
                break;
            case MessageCodeConstant.SHOPPING_CART_DEL_LIST:
                mViewModel.requestDelShoppingCartList(shopId);
                break;
            case MessageCodeConstant.SHOPPING_CART_DEL_LIST_SUCCESS:
                setShoppingCartData(mViewModel.getWaiMaiShoppingCart(), null);
                break;
        }
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
                mViewModel.doOrCancelCollectShop(shopId);
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onMembersCodeClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (mViewModel.isJoinShopFans()) {    //?????????
                    showMembersQrCodeDialog();
                } else {  //????????????
                    //??????????????????
                    mViewModel.joinShopFans(shopId);
                }
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
                int pageType = -1;
                int shopNature = mViewModel.getShopDetail().getShop_nature();
                if (shopNature == 1) {
                    pageType = OrderConfirmFragment.ORDER_ACCESS_WAIMAI;
                } else if (shopNature == 2) {
                    pageType = OrderConfirmFragment.ORDER_ACCESS_WAIMAI_ONLY;
                } else if (shopNature == 3) {
                    pageType = OrderConfirmFragment.ORDER_ACCESS_ZIQU_ONLY;
                }
                //????????????????????????
                OrderConfirmFragment.openPageConfirmOrder(ShopDetailFragment.this, mViewModel.getShopDetail(), pageType); // FIXME: 2021/1/9 ???????????????????????????????????????
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestShopInfoObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (countDownLatch != null) {
                    if (countDownLatch.getCount() > 0) {
                        countDownLatch.countDown();
                    } else {
                        countDownLatch = null;
                    }
                }
                mHandler.post(() -> refreshShopInfo());
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestIsJoinShopFansObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.isJoinShopFans()) {
                        mBinding.layoutMembers.btSignIn.setText(R.string.tv_show_qr_code);
                    } else {
                        mBinding.layoutMembers.btSignIn.setText(R.string.tv_sign_in_members);
                    }
                    setViewVisibility(mBinding.layoutMembers.btSignIn, true);
                });
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestJoinShopFansObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.onRequestIsJoinShopFansObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_SUCCESS) {
                        Toast.makeText(requireContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                        mBinding.layoutMembers.btSignIn.setText(R.string.tv_show_qr_code);
                    } else {
                        Toast.makeText(requireContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestIsCollectShopObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> refreshCollectIv(currentFoldStatus, mViewModel.isCollectShop()));
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestCollectOrCancelShopObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (mViewModel.onRequestCollectOrCancelShopObservable.get() == BaseModel.NotifyChangeRequestCallBack.REQUEST_SUCCESS) {
                        if (mViewModel.isCollectShop()) {
                            Toast.makeText(requireContext(), "???????????????", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                        }
                        refreshCollectIv(currentFoldStatus, mViewModel.isCollectShop());
                    }
                });
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestShoppingCartObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if (countDownLatch != null) {
                        if (countDownLatch.getCount() > 0) {
                            countDownLatch.countDown();
                        } else {
                            countDownLatch = null;
                        }
                    }
                    EventBus.getDefault().post(new MessageEvent(        //?????????????????????????????????
                            MessageCodeConstant.SHOPPING_CART_DATA_UPDATE, mViewModel.getWaiMaiShoppingCart()));

                    setShoppingCartData(mViewModel.getWaiMaiShoppingCart(), null);
                });
            }
        });
    }

    private void initAppBarLayoutToolbar() {
        ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).setMargins(
                0, (int) (UIUtils.getInstance().getSystemBarHeight() / UIUtils.getInstance().getHorValue()), 0, 0);
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

    /**
     * ?????????title????????? ????????????
     */
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

    private void initCashBackFlowTagLayout() {
        shopCashBackTagAdapter = new CashBackTagAdapter(getContext());
        mBinding.layoutShopDetails.flowlayoutCashBack.setAdapter(shopCashBackTagAdapter);
        mBinding.layoutShopDetails.flowlayoutCashBack.
                setOnTagClickListener((parent, view, position) ->
                        Toast.makeText(getContext(),
                                "????????????" + parent.getAdapter().getItem(position),
                                Toast.LENGTH_SHORT).show());

        shopCashBackTagAdapter.addTags(mViewModel.getShopDetail().getCouponList());
    }

    /**
     * ????????????????????????
     */
    private void initNavigationTab() {
        if (mBinding.viewPager.getAdapter() == null) {
            int space = getResources().getDimensionPixelOffset(R.dimen.shop_detail_tabbar_item_space);
            List<ShopTabTypeEnum> shopTabTypes = mViewModel.getRecommendedTitle();
            FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

            mBinding.tabSegment.setItemSpaceInScrollMode(space);
            mBinding.tabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widget_horizontal_bar));
            mBinding.tabSegment.setIndicatorWidthAdjustContent(false);
            mBinding.tabSegment.setHasIndicator(true);
            mBinding.tabSegment.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size));
            addTab(mBinding.tabSegment, adapter, shopTabTypes);
            mBinding.tabSegment.setupWithViewPager(mBinding.viewPager, false);

            mBinding.viewPager.setOffscreenPageLimit(shopTabTypes.size() - 1);
            mBinding.viewPager.setAdapter(adapter);
        }
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        List<ShopTabTypeEnum> titles) {
        Iterator<ShopTabTypeEnum> iterator = titles.iterator();
        while (iterator.hasNext()) {
            ShopTabTypeEnum shopTabType = iterator.next();
            String title = shopTabType.getName();
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize((int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_text_size_selected)));
            adapter.addFragment(mViewModel.getTabBarFragment(shopTabType, shopId), title);
            tabSegment.addTab(tab);
        }
    }

    private boolean currentFoldStatus = false;

    /**
     * ?????????????????? ????????????
     *
     * @param isFolding
     */
    private void setTitleBarFoldingStyle(boolean isFolding) {
        if (currentFoldStatus != isFolding) {
            currentFoldStatus = isFolding;
            refreshCollectIv(isFolding, mViewModel.isCollectShop());
            if (isFolding) {
                mBinding.layoutTitleShopDetail.ivBack.setImageDrawable(drawableBackDark);
                mBinding.layoutTitleShopDetail.ivSearch.setImageDrawable(drawableSearchDark);
                mBinding.layoutTitleShopDetail.ivShare.setImageDrawable(drawableShareDark);
            } else {
                mBinding.layoutTitleShopDetail.ivBack.setImageDrawable(drawableBack);
                mBinding.layoutTitleShopDetail.ivSearch.setImageDrawable(drawableSearch);
                mBinding.layoutTitleShopDetail.ivShare.setImageDrawable(drawableShare);
            }
        }
    }

    /**
     * ????????????????????????IV?????????
     */
    private void refreshCollectIv(boolean isFolding, boolean isCollectShop) {
        if (isFolding) {
            if (isCollectShop) {
                mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollect);
            } else {
                mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollectDark);
            }
        } else {
            if (isCollectShop) {
                mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollect);
            } else {
                mBinding.layoutTitleShopDetail.ivCollect.setImageDrawable(drawableCollectLight);
            }
        }
    }

    /**
     * ?????????????????????
     */
    private void showMembersQrCodeDialog() {
        if (mMembersQeCardDialog == null) {
            mMembersQeCardDialog = new Dialog(requireContext(), R.style.mySimpleNoTitleDialog);
            View view = View.inflate(requireContext(), R.layout.layout_dialog_members_qr_code, null);
            view.findViewById(R.id.bt_members_center).setOnClickListener(new BaseActivity.OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    mMembersQeCardDialog.dismiss();
                    showAddMemberInfo("??????????????????", true);
                }
            });

            //??????????????????icon
            int drawableSize = (int) UIUtils.getInstance().scalePx(38);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_members_sign_white);
            drawable.setBounds(0, 0, drawableSize, drawableSize);
            ((TextView) view.findViewById(R.id.tv_members_center)).setCompoundDrawables(drawable, null, null, null);

            //????????????
            ((TextView)view.findViewById(R.id.tv_qr_code)).setText(mViewModel.getQrCode());
            //???????????????
            //Bitmap qrBitmap = Utils.createQRCodeBitmap(mViewModel.getQrCode(), 800, 800,"UTF-8","H", "1", Color.BLACK, Color.WHITE);
            Bitmap qrBitmap = Utils.createBarcodeBitmap(mViewModel.getQrCode(),
                    (int) UIUtils.getInstance().scalePx(382), (int) UIUtils.getInstance().scalePx(226),
                    Color.BLACK, Color.WHITE);
            ((ImageView) view.findViewById(R.id.iv_qr_code)).setImageBitmap(qrBitmap);

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
        // TODO: 2020/12/1 viewModel????????????
        // TODO: 2020/12/1 ????????????????????????
        if (mAddMemberInfoPopWindow == null) {
            mAddMemberInfoPopWindow = new MyCheckRoundTextInfoPop(getContext(), info, successful);
            mAddMemberInfoPopWindow.setOnDismissListener(() -> mHandler.removeCallbacks(mCancelPopRunnable));
        }

        if (mCancelPopRunnable == null) {
            mCancelPopRunnable = () -> mAddMemberInfoPopWindow.dismiss();
        }

        mAddMemberInfoPopWindow.showAtCenter(mRootView);
        mHandler.postDelayed(mCancelPopRunnable, Constant.POP_WINDOW_SHOW_TIME);
    }

    private BottomSheet mPreferentialDialog;

    /**
     * ??????????????????dialog
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
     * ???????????????????????????
     *
     * @return
     */
    private View initPreferentialContentView() {
        View view = View.inflate(requireContext(), R.layout.layout_dialog_shop_more_preferential, null);
        LayoutDialogShopMorePreferentialBinding binding = LayoutDialogShopMorePreferentialBinding.bind(view);
        binding.setViewModel(mViewModel);

        binding.tvShopName.setText(mViewModel.getShopDetail().getShop_name());

        binding.tvRedPackage.setVisibility(View.GONE);
        binding.redPacketRecyclerView.setVisibility(View.GONE);
        /*binding.redPacketRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.redPacketRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
        binding.redPacketRecyclerView.setAdapter(new MyBaseRecyclerAdapter<RedPacket>(
                R.layout.item_recycler_waimai_dialog_red_packet, mViewModel.getPreferentialData().getRedPacketList(), com.life.waimaishuo.BR.item) {
            String receiveStr = "";
            String receivedStr = "";

            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, RedPacket item) {
                if (receiveStr.equals("")) {
                    receiveStr = getResources().getString(R.string.receive);
                }
                if (receivedStr.equals("")) {
                    receivedStr = getResources().getString(R.string.received);
                }

                ((TextView) helper.getView(R.id.tv_price)).setText(
                        TextUtil.getAbsoluteSpannable("???" + item.getPriceValue(),
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
        });*/


        binding.preferentialRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.preferentialRecyclerView.addItemDecoration(getPreferentialRecyclerItemDecoration());
        binding.preferentialRecyclerView.setAdapter(new MyBaseRecyclerAdapter<String>(
                R.layout.item_recycler_preferential_activity,
                mViewModel.getShopDetail().getCouponList(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_tag, item);
            }
        });

        binding.tvService.setVisibility(View.GONE);
        binding.serviceRecyclerView.setVisibility(View.GONE);
        /*binding.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        binding.serviceRecyclerView.addItemDecoration(getPreferentialRecyclerItemDecoration());
        binding.serviceRecyclerView.setAdapter(new MyBaseRecyclerAdapter<MerchantsService>(
                R.layout.item_recycler_merchants_service,
                mViewModel.getPreferentialData().getMerchantsServiceList(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, MerchantsService item) {
                helper.setText(R.id.tv_tag, item.getName());
            }
        });*/

        binding.tvNoticeContent.setText(mViewModel.getShopDetail().getNotice());

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

    /**
     * ???????????????????????????
     */
    private void shopOrCloseShoppingCartDetailDialog() {
        LogUtil.d("?????????????????????");
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

        //??????????????????
        binding.tvShopName.setText(mViewModel.getShopDetail().getShop_name());

        binding.tvEmptyShoppingCart.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mViewModel.requestDelShoppingCartList(shopId);
            }
        });
        // FIXME: 2020/12/25  ?????????????????????viemodel
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
                        TextUtil.getAbsoluteSpannable("???" + item.getAllPrice(), priceSymbolSize,
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
                //???????????????
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
                if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {   //????????????
                    outRect.bottom = 4;
                }
            }
        });
        setShoppingCartData(mViewModel.getWaiMaiShoppingCart(), view);
        return view;
    }

    /**
     * ?????????????????????
     *
     * @param shoppingCartDialogContentView ?????????dialog???contentView
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
        //????????????????????????
        if (goodsCount > 0) {
            mBinding.layoutShoppingCart.tvGoodsCount.setText(
                    String.valueOf(goodsCount));
            setViewVisibility(mBinding.layoutShoppingCart.tvGoodsCount, true);
        } else {
            setViewVisibility(mBinding.layoutShoppingCart.tvGoodsCount, false);
        }
        //????????????
        mBinding.layoutShoppingCart.tvSumPrice.setText(String.format("???%s", allPrice));
        //???????????????
        mBinding.layoutShoppingCart.tvDistPrice.setText(getString(R.string.dist_price, distPrice));

        /*---------- ???????????????dialog????????? ----------*/
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

        //??????????????????
        if (!"".equals(cartDesc)) {
            setViewVisibility(binding.layoutShoppingCart.tvPreferentialPrice, true);
            binding.layoutShoppingCart.tvPreferentialPrice.setText(cartDesc);
        } else {
            setViewVisibility(binding.layoutShoppingCart.tvPreferentialPrice, false);
        }
        //????????????????????????
        if (goodsCount > 0) {
            binding.layoutShoppingCart.tvGoodsCount.setText(
                    String.valueOf(goodsCount));
            setViewVisibility(binding.layoutShoppingCart.tvGoodsCount, true);
        } else {
            setViewVisibility(binding.layoutShoppingCart.tvGoodsCount, false);
        }
        //????????????
        binding.layoutShoppingCart.tvSumPrice.setText(String.format("???%s", allPrice));
        //???????????????
        binding.layoutShoppingCart.tvDistPrice.setText(getString(R.string.dist_price, distPrice));
        //????????????
        if (recyclerGoodsListAdapter != null) {
            recyclerGoodsListAdapter.setNewData(list);
            recyclerGoodsListAdapter.notifyDataSetChanged();
        }
    }

    BottomSheet shareDialog;

    private void shopShareDialog() {
        final int TAG_SHARE_WECHAT_FRIEND = 0;
        final int TAG_SHARE_QQ = 1;
        final int TAG_SHARE_WECHAT_MOMENT = 2;
        final int TAG_SHARE_QZONE = 3;
        final int TAG_SHARE_LOCAL = 4;
        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(getActivity()) {
            @Override
            protected int getItemViewLayoutId() {
                return R.layout.item_share_dialog;
            }
        };
        shareDialog = builder
                .addItem(R.drawable.ic_share_wechat, "??????", TAG_SHARE_WECHAT_FRIEND, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.ic_share_qq, "QQ", TAG_SHARE_QQ, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.ic_share_wechat_moment, "?????????", TAG_SHARE_WECHAT_MOMENT, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.drawable.ic_share_qzone, "QQ??????", TAG_SHARE_QZONE, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    LogUtil.e("tag:" + tag + ", content:" + itemView.toString());
                }).build();
        shareDialog.getContentView().setBackgroundResource(R.drawable.sr_bg_tl_tr_8dp);
        ((TextView) shareDialog.getContentView().findViewById(R.id.bottom_sheet_close_button)).setText(R.string.cancel);
        shareDialog.show();
    }

    private void refreshShopInfo() {
        //Head??????
        Glide.with(requireContext())
                .load(mViewModel.getShopDetail().getShop_sign())
                .centerCrop()
                .placeholder(R.mipmap.png_bg_shop_top)
                .into(mBinding.imgBgTop);
        mBinding.layoutShopDetails.setShop(mViewModel.getShopDetail());
        String notice = mViewModel.getShopDetail().getNotice();
        if (null == notice || "".equals(notice)) {
            setViewVisibility(mBinding.layoutShopDetails.tvShopAnnouncement, false);
        } else {
            setViewVisibility(mBinding.layoutShopDetails.tvShopAnnouncement, true);
        }

        List<String> stringList = mViewModel.getShopDetail().getCouponList();
        if (stringList == null || stringList.size() == 0) {
            setViewVisibility(mBinding.layoutShopDetails.flowlayoutCashBack, false);
        } else {
            setViewVisibility(mBinding.layoutShopDetails.flowlayoutCashBack, true);
            shopCashBackTagAdapter.setData(stringList);
        }
        Glide.with(this).load(mViewModel.getShopDetail().getShop_head_portrait()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutShopDetails.ivShopIcon);

        //????????????
        mBinding.layoutMembers.setShop(mViewModel.getShopDetail());
        Glide.with(this).load(mViewModel.getShopDetail().getShop_head_portrait()).
                placeholder(R.drawable.ic_waimai_brand).centerCrop().
                into(mBinding.layoutMembers.ivShopIcon);
    }

    public static void openPage(BaseFragment baseFragment, int shopId) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_SHOP_ID, shopId);
        baseFragment.openPage(ShopDetailFragment.class, bundle);
    }


}
