package com.life.waimaishuo.mvvm.view.fragment.order.waimai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.FragmentConfirmAnOrderBinding;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
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

@Page(name = "确认订单", anim = CoreAnim.slide)
public class OrderConfirmFragment extends BaseFragment {

    private final static String ORDER_PAGE_TYPE_INT_KEY = "order_page_type";
    private final static String ORDER_DATA_KEY = "order";
    int currentOrderType = -1; //初始值-1  1：外卖 2：自取 3：仅有外卖 4：仅有自取 5：均没有

    public final static int ORDER_ACCESS_WAIMAI = 1;
    public final static int ORDER_ACCESS_ZIQU = 2;
    public final static int ORDER_ACCESS_WAIMAI_ONLY = 3;
    public final static int ORDER_ACCESS_ZIQU_ONLY = 4;
    //订单进行中到结束的状态
    public final static int ORDER_WAIT_FOR_PAY = 5;
    public final static int ORDER_WAIT_FOR_DELIVER = 6;
    public final static int ORDER_DELIVERING = 7;
    public final static int ORDER_DELIVERED = 8;
    public final static int ORDER_ZIQU_PAY_SUCCESS = 9;   //店内自取且支付成功的状态

    private static int REQUEST_CODE_CHOSE_RED_PACKET = 1000;
    private static int REQUEST_CODE_ORDER_NOTE = 1001;

    FragmentConfirmAnOrderBinding mBinding;
    WaiMaiConfirmOrderViewModel mViewModel;

    FragmentManager fm;
    FragmentTransaction ft;

    String selectedRedPacketPrice = "";

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
        mBinding = (FragmentConfirmAnOrderBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
        mBinding.layoutOrderNote.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_an_order;
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
        if(dataBundle != null){
            currentOrderType =dataBundle.getInt(ORDER_PAGE_TYPE_INT_KEY);
            mViewModel.setCurrentAccessType(currentOrderType);
            mViewModel.setOrder(dataBundle.getParcelable(ORDER_DATA_KEY));
        }else{
            throw new Error("没有传入bundle 无法确定页面类型");
        }
    }

    @Override
    protected void initViews() {
        super.initViews();

        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);

        initOrderInfoFrameLayout();

        initOrderFoodsView();

        initData();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        addCallBack();

        mBinding.tvOrderAccessType.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if (currentOrderType != 1) {
                    mBinding.tvOrderAccessType.setTextColor(getResources().getColor(R.color.text_normal));
                    mBinding.tvOrderAccessType2.setTextColor(getResources().getColor(R.color.white));
                    mBinding.tvOrderAccessType.setBackgroundResource(R.drawable.ic_bg_order_access_type_left);
                    mBinding.tvOrderAccessType2.setBackgroundResource(R.color.transparent);
                    currentOrderType = ORDER_ACCESS_WAIMAI;
                    mViewModel.setCurrentAccessType(currentOrderType);
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
                    mViewModel.setCurrentAccessType(currentOrderType);
                    refreshOrderAccessData();
                }
            }
        });

        mBinding.llRedPacket.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            openPageForResult(OrderSelectedRedPacketFragment.class,bundle,REQUEST_CODE_CHOSE_RED_PACKET);
        });
        mBinding.layoutOrderNote.clOrderNote.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            openPageForResult(OrderNoteFragment.class,bundle,REQUEST_CODE_ORDER_NOTE);
        });
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
        MyDataBindingUtil.addCallBack(this,mViewModel.onChoseTablewareClickObservable, new Observable.OnPropertyChangedCallback(){
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showChoseTablewareDialog();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {   // FIXME: 2020/12/30 初始化数据 部分数据可又跳转进来的地地方传入，如店铺名等
        selectedRedPacketPrice = getString(R.string.please_select_red_packet);

        resetOrderTitleInfo();

        mBinding.tvShopName2.setText("嘉禾一品粥 (国展店)");
    }

    /**
     * 初始化配送信息碎片（自取或配送）
     */
    private void initOrderInfoFrameLayout() {
        if (fm == null) {
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
        }

        if(ORDER_ACCESS_WAIMAI <= currentOrderType && currentOrderType <= ORDER_ACCESS_ZIQU_ONLY){
            mBinding.layoutBottomOrderInfo.llContent.setVisibility(View.GONE);
            mBinding.layoutBottomOrderDeliverInfo.llContent.setVisibility(View.GONE);

            infoSettingTextFragment = new OrderInfoSettingTextFragment();
            infoSettingTextFragment.baseViewModel = mViewModel;
            ft.add(R.id.fl_order_info_set, infoSettingTextFragment).commit();
            if (currentOrderType == ORDER_ACCESS_WAIMAI || currentOrderType == ORDER_ACCESS_ZIQU) { //可以选择配送方式
                //do nothing
            } else {  //只有一种配送方式
                mBinding.tvOrderAccessType.setVisibility(View.GONE);
                mBinding.tvOrderAccessType2.setVisibility(View.GONE);
                mBinding.llOrderInfoTitle.setBackgroundResource(R.drawable.sr_bg_8dp_white);

                if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY) {
                } else if (currentOrderType == ORDER_ACCESS_ZIQU_ONLY) {
                    refreshOrderTitleView();
                    mBinding.layoutOrderNote.clTableware.setVisibility(View.GONE);
                }
            }
        }else{  //隐藏无关界面
            mBinding.flOrderTopInfo.setVisibility(View.GONE);
            mBinding.layoutOrderNote.llContent.setVisibility(View.GONE);
            mBinding.layoutMembers.clContent.setVisibility(View.GONE);
            mBinding.layoutConfirmOrder.llShoppingCart.setVisibility(View.GONE);
            BaseFragment replaceFragment = null;
            if(currentOrderType == ORDER_WAIT_FOR_PAY){
                replaceFragment = new OrderWaitPayFragment();
            } else if (currentOrderType == ORDER_WAIT_FOR_DELIVER || currentOrderType == ORDER_DELIVERING){
                replaceFragment = new OrderWaitDeliverFragment();
            } else if (currentOrderType == ORDER_DELIVERED){
                replaceFragment = new OrderDeliverFinishFragment();
            } else if (currentOrderType == ORDER_ZIQU_PAY_SUCCESS) {   //店内自取支付成功
                replaceFragment = new OrderProcessFragment();
            }
            if(replaceFragment != null){
                replaceFragment.baseViewModel = mViewModel;
                ft.replace(R.id.fl_order_state, replaceFragment).commit();
            }else{
                LogUtil.e("布局加载出错");
            }
        }
    }

    /**
     * 刷新 店铺名 店铺未知 距离 或用户名电话的方法
     */
    private void resetOrderTitleInfo() {
        mBinding.layoutOrderTitle.tvShopName.setText("嘉禾一品粥 (国展店)");
        if(currentOrderType == ORDER_ACCESS_WAIMAI || currentOrderType == ORDER_ACCESS_WAIMAI_ONLY){
            mBinding.layoutOrderTitle.tvShopLocation.setText("太原南站（小店南东风雪铁龙2幢3单元301");
            mBinding.layoutOrderTitle.tvInfo.setText("张三洗  28347345211");
        }else{
            mBinding.layoutOrderTitle.tvInfo.setText("距您220米，请确认下单门店是否正确");
        }
    }

    /**
     * 切换配送方法时调用
     */
    private void refreshOrderAccessData() {
        refreshOrderInfoSetFrameLayout();
        refreshOrderTitleView();
    }

    private void refreshOrderTitleView(){
        if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY || currentOrderType == ORDER_ACCESS_WAIMAI) {
            if(mBinding.layoutOrderTitle.clLocation.getVisibility() != View.VISIBLE){
                mBinding.layoutOrderTitle.clLocation.setVisibility(View.VISIBLE);
            }
            if(mBinding.layoutOrderNote.clTableware.getVisibility() != View.VISIBLE){
                mBinding.layoutOrderNote.clTableware.setVisibility(View.VISIBLE);
            }
            resetOrderTitleInfo();
        }
        if (currentOrderType == ORDER_ACCESS_ZIQU_ONLY || currentOrderType == ORDER_ACCESS_ZIQU) {
            mBinding.layoutOrderTitle.clLocation.setVisibility(View.GONE);
            mBinding.layoutOrderNote.clTableware.setVisibility(View.GONE);
            resetOrderTitleInfo();
        }
    }


    /**
     * 切换外卖配送与自取时 碎片显示内容跟着切换
     */
    private void refreshOrderInfoSetFrameLayout() {
        if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY || currentOrderType == ORDER_ACCESS_WAIMAI) {
            infoSettingTextFragment.resetViewByType(OrderInfoSettingTextFragment.ACCESS_WAIMAI);
        }
        if (currentOrderType == ORDER_ACCESS_ZIQU_ONLY || currentOrderType == ORDER_ACCESS_ZIQU) {
            infoSettingTextFragment.resetViewByType(OrderInfoSettingTextFragment.ACCESS_ZIQU);
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
    private void closePickUpTimeDialog(){
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

    private SelectedPositionRecylerViewAdapter<String> leftAdapter;
    private SelectedPositionRecylerViewAdapter<String> rightAdapter;
    private void initLeftRecycler(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        leftAdapter = new SelectedPositionRecylerViewAdapter<String>(mViewModel.getLeftPickUpTimeData()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pick_up_time_left;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, String item) {
                if(selected){
                    holder.setTextColor(R.id.tv,getResources().getColor(R.color.text_normal));
                    holder.setBackgroundColor(R.id.tv,getResources().getColor(R.color.white));
                }else{
                    holder.setTextColor(R.id.tv,getResources().getColor(R.color.text_pick_up_time_left_normal));
                    holder.setBackgroundColor(R.id.tv,getResources().getColor(R.color.transparent));
                }
                holder.setText(R.id.tv,item);
            }
        };
        recyclerView.setAdapter(leftAdapter);
        leftAdapter.setSelectedListener(new SelectedPositionRecylerViewAdapter.OnSelectedListener<String>() {
            @Override
            public void onSelectedClick(BaseViewHolder holder, String item) {
                rightAdapter.setData(mViewModel.getRightPikUpTimeDataByIndex(leftAdapter.getData().indexOf(item)));
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRightRecycler(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        rightAdapter = new SelectedPositionRecylerViewAdapter<String>(mViewModel.getRightPikUpTimeDataByIndex(0)) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pick_up_time_right;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, String item) {
                holder.setText(R.id.tv,item);
            }
        };
        recyclerView.setAdapter(rightAdapter);
        rightAdapter.setSelectedListener(new SelectedPositionRecylerViewAdapter.OnSelectedListener<String>() {
            @Override
            public void onSelectedClick(BaseViewHolder holder, String item) {
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
        ((TextView)view.findViewById(R.id.tv_title)).setText(R.string.chose_pay_type);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_pay_type_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new SelectedPositionRecylerViewAdapter<IconStrData>(mViewModel.getPayTypeList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pay_type;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setText(R.id.tv_pay_type_name, item.getIconType());
                holder.setImageResource(R.id.iv_left, item.getResImgId());
                if (selected) {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_fill_red);
                } else {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_fill_gray);
                }
            }
        });

        ((SelectedPositionRecylerViewAdapter<IconStrData>) recyclerView.getAdapter()).setSelectedListener((holder, item) -> {
            LogUtil.d("支付方式：" + item.getIconType());
            mPayTypeDialog.dismiss();
            infoSettingTextFragment.resetPayType(item);
        });
        return view;
    }

    private BottomSheet myChoseTableWareDialog;
    private void showChoseTablewareDialog(){
        if(myChoseTableWareDialog == null){
            myChoseTableWareDialog = new BottomSheet.BottomListSheetBuilder(getActivity()){
                @Override
                protected int getContentViewLayoutId() {
                    return R.layout.layout_dialog_chose_tableware;
                }
            }
                    .setTitle("选择餐具备份")
                    .addItem("无需餐具")
                    .addItem("1份")
                    .addItem("2份")
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
        if(!myChoseTableWareDialog.isShowing()){
            myChoseTableWareDialog.show();
        }
    }


    private void initOrderFoodsView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false){
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
                if(layoutId == R.layout.layout_recycler){
                    return LayoutInflater.from(parent.getContext()).inflate(layoutId, null, false);
                }else{
                    return super.inflateView(parent,layoutId);
                }
            }

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, Object item) {
                if(holder.getItemViewType() == goodsList){
                    RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(new MyBaseRecyclerAdapter(R.layout.item_recycler_order_food,mViewModel.getOrderFoods(), com.life.waimaishuo.BR.item));
                }else if(holder.getItemViewType() == preferential){
                    RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(new MyBaseRecyclerAdapter(R.layout.item_recycler_order_preferential,mViewModel.getPreferentialList(), com.life.waimaishuo.BR.item){
                        @Override
                        protected void initView(BaseViewHolder helper, Object item) {
                            super.initView(helper, item);
                            helper.setText(R.id.tv_tag,"配送费");
                        }
                    });
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {

                if(viewType == goodsList || viewType == preferential){
                    return R.layout.layout_recycler;
                } else{
                    return R.layout.layout_divider;
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(position == 1){
                    return divider;
                }else if(position == 0) {
                    return goodsList;
                }else if(position == 2){
                    return preferential;
                }else{
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOSE_RED_PACKET){
            if(resultCode == RESULT_CODE_SUCCESS){
                RedPacket redPacket = data.getParcelableExtra(OrderSelectedRedPacketFragment.RESULT_KEY_RED_PACKET_ID);
                if(redPacket != null){
                    mViewModel.redPacketPriceValueObservable.set("-" + redPacket.getPriceValue());
                }
            }
        }
        if(requestCode == REQUEST_CODE_ORDER_NOTE){
            if(resultCode == RESULT_CODE_SUCCESS){
                String note = data.getStringExtra(OrderNoteFragment.RESULT_KEY_NOTE);
                if(note != null){
                    mViewModel.orderNoteObservable.set(note);
                }
            }
        }

    }

    /**
     * 外卖订单点击进入此订单详情页
     * @param baseFragment
     * @param order
     */
    public static void openPageWithOrder(BaseFragment baseFragment, Order order){
        int orderPageType = -1;
        switch (OrderStateEnum.valueOf(order.getOrderState())){
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
        bundle.putInt(ORDER_PAGE_TYPE_INT_KEY,orderPageType);
        bundle.putParcelable(ORDER_DATA_KEY,order);
        baseFragment.openPage(OrderConfirmFragment.class,bundle);

    }

    /**
     * 根据样式打开页面确认订单
     * @param baseFragment
     * @param order
     * @param deliverType
     */
    public static void openPageConfirmOrder(BaseFragment baseFragment, Order order, int deliverType){
        Bundle bundle = new Bundle();
        bundle.putInt(ORDER_PAGE_TYPE_INT_KEY,deliverType);
        bundle.putParcelable(ORDER_DATA_KEY,order);
        baseFragment.openPage(OrderConfirmFragment.class,bundle);
    }

}