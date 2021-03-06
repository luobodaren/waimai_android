package com.life.waimaishuo.mvvm.view.fragment.order.waimai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.bean.Coupon;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentConfirmAnOrderWaimaiBinding;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mine.MineAddressManagerFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.widget.OrderDeliverFinishFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.widget.OrderInfoSettingTextFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.widget.OrderNoteFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.widget.OrderProcessFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.widget.OrderWaitDeliverFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.widget.OrderWaitPayFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiConfirmOrderViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

@Page(name = "????????????-??????", anim = CoreAnim.slide)
public class OrderConfirmFragment extends BaseFragment {

    private final static String ORDER_PAGE_TYPE_INT_KEY = "order_page_type";
    private final static String ORDER_SHOP_DATA_KEY = "order";

    int currentOrderType = -1; //?????????-1  1????????? 2????????? 3??????????????? 4??????????????? 5????????????
    public final static int ORDER_ACCESS_WAIMAI = 1;
    public final static int ORDER_ACCESS_ZIQU = 2;
    public final static int ORDER_ACCESS_WAIMAI_ONLY = 3;
    public final static int ORDER_ACCESS_ZIQU_ONLY = 4;
    //?????????????????????????????????
    public final static int ORDER_WAIT_FOR_PAY = 5;
    public final static int ORDER_WAIT_FOR_DELIVER = 6;
    public final static int ORDER_DELIVERING = 7;
    public final static int ORDER_DELIVERED = 8;
    public final static int ORDER_ZIQU_PAY_SUCCESS = 9;   //????????????????????????????????????

    FragmentConfirmAnOrderWaimaiBinding mBinding;
    WaiMaiConfirmOrderViewModel mViewModel;

    FragmentManager fm;
    FragmentTransaction ft;

    private OrderInfoSettingTextFragment infoSettingTextFragment;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new WaiMaiConfirmOrderViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentConfirmAnOrderWaimaiBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
        mBinding.layoutOrderNote.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_an_order_waimai;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);

        Bundle dataBundle = getArguments();
        if (dataBundle != null) {
            currentOrderType = dataBundle.getInt(ORDER_PAGE_TYPE_INT_KEY);
            mViewModel.setShop(dataBundle.getParcelable(ORDER_SHOP_DATA_KEY));
        } else {
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }
    }

    @Override
    protected void initViews() {
        super.initViews();

        if (fm == null) {
            fm = getChildFragmentManager();
        }

        mBinding.layoutTitle.tvTitle.setText(R.string.confirm_order);
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);

        setPageElementByOrderType();
        initOrderFoodsView();
        initData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();

        mBinding.layoutTitle.ivMenu.setOnClickListener(new View.OnClickListener() { // FIXME: 2021/1/12 ??????
            @Override
            public void onClick(View v) {
                currentOrderType++;
                if (currentOrderType > 9) {
                    currentOrderType = 1;
                }
                setPageElementByOrderType();
            }
        });

        mBinding.tvOrderAccessType.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (currentOrderType != 1) {
                    mBinding.tvOrderAccessType.setTextColor(getResources().getColor(R.color.text_normal));
                    mBinding.tvOrderAccessType2.setTextColor(getResources().getColor(R.color.white));
                    mBinding.tvOrderAccessType.setBackgroundResource(R.drawable.ic_bg_order_access_type_left);
                    mBinding.tvOrderAccessType2.setBackgroundResource(R.color.transparent);
                    currentOrderType = ORDER_ACCESS_WAIMAI;
                    refreshOrderAccessData();
                }
            }
        });

        mBinding.tvOrderAccessType2.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (currentOrderType != 2) {
                    mBinding.tvOrderAccessType.setTextColor(getResources().getColor(R.color.white));
                    mBinding.tvOrderAccessType2.setTextColor(getResources().getColor(R.color.text_normal));
                    mBinding.tvOrderAccessType.setBackgroundResource(R.color.transparent);
                    mBinding.tvOrderAccessType2.setBackgroundResource(R.drawable.ic_bg_order_access_type_right);
                    currentOrderType = ORDER_ACCESS_ZIQU;
                    refreshOrderAccessData();
                }
            }
        });

        mBinding.llRedPacket.setOnClickListener(v ->
                OrderSelectedRedPacketFragment.openPageForResult(OrderConfirmFragment.this,
                        Constant.REQUEST_CODE_CHOSE_RED_PACKET, mViewModel.getShop().getShopId()));

        mBinding.layoutOrderNote.clOrderNote.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            openPageForResult(OrderNoteFragment.class, bundle, Constant.REQUEST_CODE_ORDER_NOTE);
        });

        //???????????????????????????
        mBinding.layoutOrderTitle.tvShopLocation.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(mViewModel.getShippingAddress() != null){
                    openPageForResult(MineAddressManagerFragment.class,null,Constant.REQUEST_CODE_CHOSE_SHIPPING_ADDRESS);
                }
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_CHOSE_RED_PACKET) {
            if (resultCode == Constant.RESULT_CODE_SUCCESS) {
                Coupon coupon = data.getParcelableExtra(OrderSelectedRedPacketFragment.RESULT_KEY_RED_PACKET_ID);
                if (coupon != null) {
                    mViewModel.redPacketPriceValueObservable.set("-" + coupon.getPriceValue());
                }
            }
        }
        if (requestCode == Constant.REQUEST_CODE_ORDER_NOTE) {
            if (resultCode == Constant.RESULT_CODE_SUCCESS) {
                String note = data.getStringExtra(OrderNoteFragment.RESULT_KEY_NOTE);
                if (note != null) {
                    mViewModel.orderNoteObservable.set(note);
                }
            }
        }
        if(requestCode == Constant.REQUEST_CODE_CHOSE_SHIPPING_ADDRESS){
            if(resultCode == Constant.RESULT_CODE_SUCCESS){
                setShippingAddress(data != null ?
                        data.getParcelableExtra(MineAddressManagerFragment.RESULT_KEY_SELECT_ADDRESS) : null);
            }
        }

    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.onAccessTimeClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showPickUpTimeDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onPayTypeClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showPayTypeDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onChoseTablewareClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showChoseTablewareDialog();
            }
        });

        MyDataBindingUtil.addCallBack(this, mViewModel.onRequestShippingAddressObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(mViewModel.getShippingAddress().size() > 0){
                        for (Address address:mViewModel.getShippingAddress()) {
                            if(address.getIsDefaultAddress() == 1){
                                setShippingAddress(address);
                                return;
                            }
                        }
                        Address address = mViewModel.getShippingAddress().get(0);   //???????????????????????????????????????
                        setShippingAddress(address);
                    }else{
                        setShippingAddress(null);
                    }
                });
            }
        });
    }

    /**
     * ???????????????
     */
    private void initData() {   // FIXME: 2020/12/30 ??????????????? ??????????????????????????????????????????????????????????????????
        mBinding.tvShopName2.setText(mViewModel.getShop().getShop_name());
    }

    /**
     * ????????????????????????????????????????????????
     */
    private void setPageElementByOrderType() {
        //????????????
        mBinding.layoutTitle.tvTitle.setText(getPageName());

        if (ORDER_ACCESS_WAIMAI <= currentOrderType && currentOrderType <= ORDER_ACCESS_ZIQU_ONLY) {
            setViewVisibility(mBinding.flOrderTopInfo, true);
            setViewVisibility(mBinding.layoutOrderNote.llContent, true);
            setViewVisibility(mBinding.layoutMembers.clContent, true);
            setViewVisibility(mBinding.layoutConfirmOrder.llShoppingCart, true);
            setViewVisibility(mBinding.flOrderState, false);
            setViewVisibility(mBinding.layoutBottomOrderInfo.llContent, false);
            setViewVisibility(mBinding.layoutBottomOrderDeliverInfo.llContent, false);

            resetOrderTopInfoLayout();
            refreshOrderTitleView();
        } else {
            setViewVisibility(mBinding.flOrderTopInfo, false);
            setViewVisibility(mBinding.layoutOrderNote.llContent, false);
            setViewVisibility(mBinding.layoutMembers.clContent, false);
            setViewVisibility(mBinding.layoutConfirmOrder.llShoppingCart, false);
            setViewVisibility(mBinding.flOrderState, true);
            setViewVisibility(mBinding.layoutBottomOrderInfo.llContent, true);
            setViewVisibility(mBinding.layoutBottomOrderDeliverInfo.llContent, true);

            resetOrderStateLayout();
        }
    }

    /**
     * ?????????????????????
     */
    private void resetOrderTopInfoLayout() {
        if (infoSettingTextFragment == null) {
            infoSettingTextFragment = new OrderInfoSettingTextFragment();
            infoSettingTextFragment.baseViewModel = mViewModel;
            infoSettingTextFragment.setShopFoodAccessType(
                    infoSettingTextFragment.getAccessType(currentOrderType));
            ft = fm.beginTransaction();
            ft.replace(R.id.fl_order_info_set, infoSettingTextFragment).commit();
        }

        //???????????????????????????????????????
        //????????????????????????
        if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY || currentOrderType == ORDER_ACCESS_ZIQU_ONLY) {
            setViewVisibility(mBinding.tvOrderAccessType, false);
            setViewVisibility(mBinding.tvOrderAccessType2, false);
            mBinding.llOrderInfoTitle.setBackgroundResource(R.drawable.sr_bg_8dp_white);
        } else {
            setViewVisibility(mBinding.tvOrderAccessType, true);
            setViewVisibility(mBinding.tvOrderAccessType2, true);
            mBinding.llOrderInfoTitle.setBackgroundResource(R.drawable.sr_bg_bl_br_8dp_white);
        }
    }

    BaseFragment replaceFragment = null;

    /**
     * ??????????????? ?????????????????????????????????ViewModel??????Order???????????????
     * ???????????????????????????????????????????????????
     */
    private void resetOrderStateLayout() {
        if (currentOrderType == ORDER_WAIT_FOR_PAY) {
            replaceFragment = new OrderWaitPayFragment();
        } else if (currentOrderType == ORDER_WAIT_FOR_DELIVER || currentOrderType == ORDER_DELIVERING) {
            replaceFragment = new OrderWaitDeliverFragment();
        } else if (currentOrderType == ORDER_DELIVERED) {
            replaceFragment = new OrderDeliverFinishFragment();
        } else if (currentOrderType == ORDER_ZIQU_PAY_SUCCESS) {   //????????????????????????
            replaceFragment = new OrderProcessFragment();
        }
        if (replaceFragment != null) {
            replaceFragment.baseViewModel = mViewModel;
            ft = fm.beginTransaction();
            ft.replace(R.id.fl_order_state, replaceFragment).commit();
        } else {
            LogUtil.e("??????????????????");
        }
    }

    /**
     * ??????????????????????????? ??????????????????????????????????????????
     */
    private void refreshOrderAccessData() {
        refreshOrderInfoSetFrameLayout();
        refreshOrderTitleView();
    }

    /**
     * ?????????????????????????????? ?????????????????????????????????
     */
    private void refreshOrderInfoSetFrameLayout() {
        if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY || currentOrderType == ORDER_ACCESS_WAIMAI) {
            infoSettingTextFragment.resetViewByType(OrderInfoSettingTextFragment.ACCESS_WAIMAI);
        }
        if (currentOrderType == ORDER_ACCESS_ZIQU_ONLY || currentOrderType == ORDER_ACCESS_ZIQU) {
            infoSettingTextFragment.resetViewByType(OrderInfoSettingTextFragment.ACCESS_ZIQU);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    private void refreshOrderTitleView() {
        if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY || currentOrderType == ORDER_ACCESS_WAIMAI) {
            setViewVisibility(mBinding.layoutOrderTitle.clLocation, true);
            setViewVisibility(mBinding.layoutOrderNote.clTableware, true);
            resetOrderTitleInfo();
        }
        if (currentOrderType == ORDER_ACCESS_ZIQU_ONLY || currentOrderType == ORDER_ACCESS_ZIQU) {
            setViewVisibility(mBinding.layoutOrderTitle.clLocation, false);
            setViewVisibility(mBinding.layoutOrderNote.clTableware, false);
            resetOrderTitleInfo();
        }
    }

    /**
     * ?????? ????????? ???????????? ?????? ???????????????????????????
     */
    private void resetOrderTitleInfo() {
        mBinding.layoutOrderTitle.tvShopName.setText(mViewModel.getShop().getShop_name());
        if (currentOrderType == ORDER_ACCESS_WAIMAI || currentOrderType == ORDER_ACCESS_WAIMAI_ONLY) {
            //??????????????????
            mViewModel.requestShippingAddress();
        } else {
            mBinding.layoutOrderTitle.tvInfo.setText("??????220???????????????????????????????????????");
        }
    }

    private BottomSheet mPickUpTimeDialog;

    private void showPickUpTimeDialog() {
        if (mPickUpTimeDialog == null) {
            View contentView = initPickUpTimeView();
            mPickUpTimeDialog = new BottomSheet(requireContext());
            mPickUpTimeDialog.setContentView(
                    contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mPickUpTimeDialog.show();
    }

    private void closePickUpTimeDialog() {
        if (mPickUpTimeDialog != null && mPickUpTimeDialog.isShowing()) {
            mPickUpTimeDialog.cancel();
        }
    }

    private View initPickUpTimeView() {
        View view = View.inflate(getContext(), R.layout.layout_dialog_pick_up_time, null);
        initLeftRecycler(view.findViewById(R.id.rv_primary));
        initRightRecycler(view.findViewById(R.id.rv_secondary));
        view.findViewById(R.id.iv_close).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                closePickUpTimeDialog();
            }
        });
        return view;
    }

    private SelectedPositionRecyclerViewAdapter<String> leftAdapter;
    private SelectedPositionRecyclerViewAdapter<String> rightAdapter;

    private void initLeftRecycler(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        leftAdapter = new SelectedPositionRecyclerViewAdapter<String>(mViewModel.getLeftPickUpTimeData()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pick_up_time_left;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, String item) {
                if (selected) {
                    holder.setTextColor(R.id.tv, getResources().getColor(R.color.text_normal));
                    holder.setBackgroundColor(R.id.tv, getResources().getColor(R.color.white));
                } else {
                    holder.setTextColor(R.id.tv, getResources().getColor(R.color.text_pick_up_time_left_normal));
                    holder.setBackgroundColor(R.id.tv, getResources().getColor(R.color.transparent));
                }
                holder.setText(R.id.tv, item);
            }
        };
        recyclerView.setAdapter(leftAdapter);
        leftAdapter.setSelectedListener(new SelectedPositionRecyclerViewAdapter.OnSelectedListener<String>() {
            @Override
            public void onSelectChangeClick(BaseViewHolder holder, String item, boolean isCancel) {
                rightAdapter.setData(mViewModel.getRightPikUpTimeDataByIndex(leftAdapter.getData().indexOf(item)));
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRightRecycler(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rightAdapter = new SelectedPositionRecyclerViewAdapter<String>(mViewModel.getRightPikUpTimeDataByIndex(0)) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pick_up_time_right;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, String item) {
                holder.setText(R.id.tv, item);
            }
        };
        recyclerView.setAdapter(rightAdapter);
        rightAdapter.setSelectedListener(new SelectedPositionRecyclerViewAdapter.OnSelectedListener<String>() {
            @Override
            public void onSelectChangeClick(BaseViewHolder holder, String item, boolean isCancel) {
                mPickUpTimeDialog.dismiss();
                mViewModel.pickUpTimeObservable.set(item);
            }
        });
    }

    private BottomSheet mPayTypeDialog;

    private void showPayTypeDialog() {
        if (mPayTypeDialog == null) {
            View contentView = initPayTypeView();
            mPayTypeDialog = new BottomSheet(requireContext());
            mPayTypeDialog.setContentView(
                    contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        if (mPayTypeDialog.isShowing()) {
            mPayTypeDialog.cancel();
        } else {
            mPayTypeDialog.show();
        }
    }

    private View initPayTypeView() {
        View view = View.inflate(getContext(), R.layout.layout_dialog_pay_type, null);
        ((TextView) view.findViewById(R.id.tv_title)).setText(R.string.chose_pay_type);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_pay_type_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new SelectedPositionRecyclerViewAdapter<IconStrData>(mViewModel.getPayTypeList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pay_type;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setText(R.id.tv_pay_type_name, item.getIconType());
                holder.setImageResource(R.id.iv_left, item.getResImgId());
                holder.setVisible(R.id.divider_bottom, false);
                if (selected) {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_fill_red);
                } else {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_fill_gray);
                }
            }
        });

        ((SelectedPositionRecyclerViewAdapter<IconStrData>) recyclerView.getAdapter()).setSelectedListener((holder, item, isCancel) -> {
            LogUtil.d("???????????????" + item.getIconType());
            mPayTypeDialog.dismiss();
            infoSettingTextFragment.resetPayType(item);
        });
        return view;
    }

    private BottomSheet myChoseTableWareDialog;

    private void showChoseTablewareDialog() {
        if (myChoseTableWareDialog == null) {
            myChoseTableWareDialog = new BottomSheet.BottomListSheetBuilder(getActivity()) {
                @Override
                protected int getContentViewLayoutId() {
                    return R.layout.layout_dialog_chose_tableware;
                }
            }
                    .setTitle("??????????????????")
                    .addItem("????????????")
                    .addItem("1???")
                    .addItem("2???")
                    .setIsCenter(true)
                    .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                        dialog.dismiss();
                        mViewModel.tablewareObservable.set(tag);
                    })
                    .build();
            myChoseTableWareDialog.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myChoseTableWareDialog.dismiss();
                }
            });
        }
        if (!myChoseTableWareDialog.isShowing()) {
            myChoseTableWareDialog.show();
        }
    }

    private void initOrderFoodsView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        mBinding.recyclerGoodsList.setLayoutManager(linearLayoutManager);
        mBinding.recyclerGoodsList.setAdapter(getGoodsListAdapter());
    }

    private RecyclerView.Adapter getGoodsListAdapter() {
        return new BaseRecyclerAdapter<Object>() {
            private int divider = 1;
            private int goodsList = 2;
            private int preferential = 3;

            @Override
            protected View inflateView(ViewGroup parent, int layoutId) {
                if (layoutId == R.layout.layout_recycler) {
                    return LayoutInflater.from(parent.getContext()).inflate(layoutId, null, false);
                } else {
                    return super.inflateView(parent, layoutId);
                }
            }

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, Object item) {
                if (holder.getItemViewType() == goodsList) {
                    RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new MyBaseRecyclerAdapter(R.layout.item_recycler_waimai_order_food, mViewModel.getOrderFoods(), com.life.waimaishuo.BR.item));
                } else if (holder.getItemViewType() == preferential) {
                    RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new MyBaseRecyclerAdapter(R.layout.item_recycler_waimai_order_preferential, mViewModel.getPreferentialList(), com.life.waimaishuo.BR.item) {
                        @Override
                        protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, Object item) {
                            helper.setText(R.id.tv_tag, "?????????");
                        }
                    });
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {

                if (viewType == goodsList || viewType == preferential) {
                    return R.layout.layout_recycler;
                } else {
                    return R.layout.layout_divider;
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 1) {
                    return divider;
                } else if (position == 0) {
                    return goodsList;
                } else if (position == 2) {
                    return preferential;
                } else {
                    return divider;
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                bindData(holder, position, null);
            }
        };
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param baseFragment
     * @param order
     */
    public static void openPageWithOrder(BaseFragment baseFragment, Order order) {
        int orderPageType = -1;
        switch (OrderStateEnum.valueOf(order.getOrderState())) {
            case UN_PAY:
                orderPageType = ORDER_WAIT_FOR_PAY;
                break;
            case UN_DELIVER:
                orderPageType = ORDER_WAIT_FOR_DELIVER;
                break;
            case DELIVER:
                orderPageType = ORDER_DELIVERING;
                break;
            case FINISH:
                orderPageType = ORDER_DELIVERED;
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(ORDER_PAGE_TYPE_INT_KEY, orderPageType);
        bundle.putParcelable(ORDER_SHOP_DATA_KEY, order);
        baseFragment.openPage(OrderConfirmFragment.class, bundle);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * @param address
     */
    private void setShippingAddress(Address address){
        if(address == null){
            mBinding.layoutOrderTitle.tvShopLocation.setText("?????????????????????????????????...");
            mBinding.layoutOrderTitle.tvInfo.setText("");
            return;
        }
        mBinding.layoutOrderTitle.tvShopLocation.setText(   //????????????
                String.format("%s %s %s %s", address.getProvince(),
                        address.getCity(), address.getDistrict(),
                        address.getDetailedAddress()));
        mBinding.layoutOrderTitle.tvInfo.setText(   //????????????
                String.format("%s %s", address.getConsignee(), address.getPhone()));
    }

    /**
     * ????????????????????????????????????
     *
     * @param baseFragment
     * @param shop
     * @param deliverType
     */
    public static void openPageConfirmOrder(BaseFragment baseFragment, Shop shop, int deliverType) {
        Bundle bundle = new Bundle();
        bundle.putInt(ORDER_PAGE_TYPE_INT_KEY, deliverType);
        bundle.putParcelable(ORDER_SHOP_DATA_KEY, shop);
        baseFragment.openPage(OrderConfirmFragment.class, bundle);
    }

}
