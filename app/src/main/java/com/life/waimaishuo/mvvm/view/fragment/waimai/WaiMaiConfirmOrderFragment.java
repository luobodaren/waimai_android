package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
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
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.FragmentConfirmAnOrderBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
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
public class WaiMaiConfirmOrderFragment extends BaseFragment {

    int currentOrderType = -1; //初始值-1  1：外卖 2：自取 3：仅有外卖 4：仅有自取 5：均没有

    public static int ORDER_ACCESS_WAIMAI = 1;
    public static int ORDER_ACCESS_ZIQU = 2;
    public static int ORDER_ACCESS_WAIMAI_ONLY = 3;
    public static int ORDER_ACCESS_ZIQU_ONLY = 4;
    public static int ORDER_ZIQU_PAY_SUCCESS = 5;   //店内自取且支付成功的状态

    FragmentConfirmAnOrderBinding mBinding;
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
        mBinding = (FragmentConfirmAnOrderBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
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
    }

    @Override
    protected void initViews() {
        super.initViews();
        currentOrderType = 1;  // FIXME: 2020/12/29 后续更具实际情况修改
        mViewModel.setCurrentAccessType(currentOrderType);

        mBinding.layoutTitle.tvTitle.setText(getPageName());

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
    }

    private void addCallBack() {
        MyDataBindingUtil.addCallBack(this, mViewModel.onAccessTimeClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showPickUpTimeDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onPayTypeClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showPayTypeDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onTimePick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                closePickUpTimeDialog();
                infoSettingTextFragment.setPickUpTime(pickedTime);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {   // FIXME: 2020/12/30 初始化数据 部分数据可又跳转进来的地地方传入，如店铺名等
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
        if (currentOrderType != ORDER_ZIQU_PAY_SUCCESS) { //除开支付成功且店内自取
            infoSettingTextFragment = new OrderInfoSettingTextFragment();
            infoSettingTextFragment.baseViewModel = mViewModel;
            ft.add(R.id.fl_order_info_set, infoSettingTextFragment).commit();
            if (currentOrderType == ORDER_ACCESS_WAIMAI || currentOrderType == ORDER_ACCESS_ZIQU) { //可以选择配送方式
                //do nothing
            } else {  //只有一种配送方式
                mBinding.tvOrderAccessType.setVisibility(View.GONE);
                mBinding.tvOrderAccessType2.setVisibility(View.GONE);
                mBinding.llOrderInfoTitle.setBackgroundResource(R.drawable.sr_bg_8dp_white);
            }

            if (currentOrderType == ORDER_ACCESS_WAIMAI_ONLY) {

            } else if (currentOrderType == ORDER_ACCESS_ZIQU_ONLY) {
                mBinding.layoutOrderNote.clTableware.setVisibility(View.GONE);
            }
        } else {  //  店内自取支付成功
            //另个一fragment
            ft.add(infoSettingTextFragment, null).commit();
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
    private String pickedTime = "";
    private void initLeftRecycler(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        leftAdapter = new SelectedPositionRecylerViewAdapter<String>(mViewModel.getLeftPickUpTimeData()) {
            @Override
            public int getLayoutId() {
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
            public int getLayoutId() {
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
                pickedTime = item;
                mViewModel.onTimePick.notifyChange();
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
            public int getLayoutId() {
                return R.layout.item_recycler_pay_type;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setText(R.id.tv_pay_type_name, item.getIconType());
                holder.setImageResource(R.id.iv_left, item.getResImgId());
                if (selected) {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_red);
                } else {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_gray);
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


    private void initOrderFoodsView() {

        mBinding.recyclerGoodsList.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerGoodsList.setAdapter(new BaseRecyclerAdapter<Object>() {
            private int recycler = 1;
            private int divider = 2;

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, Object item) {
                holder.getAdapterPosition()
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                   /* return R.layout.item_recycler_order_food;
                }else if(viewType == preferentialList){
                    return R.layout.layout_divider;
                }else if(viewType == redPackage){
                    return ;
                }else if(viewType == preferentialInstructions){
                    return ;*/

                if(viewType == recycler){
                    return R.layout.layout_recycler;
                }else{
                    return R.layout.layout_divider;
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(position == 1){
                    return divider;
                }else if(position == 3) {
                    return divider;
                }else if(position == 5){
                    return divider;
                }else {
                    return recycler;
                }
            }

            @Override
            public int getItemCount() {
                return 6;
            }
        });
        mBinding.recyclerGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            int top_interval =(int) UIUtils.getInstance(getContext()).scalePx(33);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = top_interval;

            }
        });
    }

}
