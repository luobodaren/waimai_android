package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.bean.ui.MallGoods;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.databinding.FragmentOrderDetailBinding;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.LogisticsFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.MallOrderDetailViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "订单详情-商城")
public class OrderDetailFragment extends BaseFragment {

    private final static String ORDER_PAGE_TYPE_INT_KEY = "order_page_type";
    private final static String ORDER_DATA_KEY = "order";
    private static int mPageState = -1;
    public final static int ORDER_CANCEL = 1;   //已取消
    public final static int ORDER_WAIT_FOR_PAY = 2;
    public final static int ORDER_WAIT_FOR_DELIVER = 3;
    public final static int ORDER_DELIVERING = 4;
    public final static int ORDER_DELIVERED = 5;
    private FragmentOrderDetailBinding mBinding;
    private MallOrderDetailViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MallOrderDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentOrderDetailBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);

        Bundle dataBundle = getArguments();
        if(dataBundle != null){
            mPageState = dataBundle.getInt(ORDER_PAGE_TYPE_INT_KEY);
            mViewModel.setOrder(dataBundle.getParcelable(ORDER_DATA_KEY));
        }else{
            throw new Error("没有传入bundle 无法确定页面类型");
        }
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initOrderShopsGoods();
        setPageElementByOrderType();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.clDeliver.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ORDER_DATA_KEY,mViewModel.getOrder());
            openPage(LogisticsFragment.class,bundle);
        });
    }

    private void initTitle(){

        // FIXME: 2021/1/13 删除点击监听
        mBinding.layoutTitle.ivMenu.setOnClickListener(v -> {
            mPageState++;
            if(mPageState > ORDER_DELIVERED){
                mPageState = 1;
            }
            setPageElementByOrderType();
        });
        setViewVisibility(mBinding.layoutTitle.ivShare,false);

        mBinding.layoutTitle.tvTitle.setText(getString(R.string.order_detail));
    }

    private void resetTitle(){
        switch (mPageState) {
            case ORDER_CANCEL:
                mBinding.layoutTitle.tvTitle.setText(getString(R.string.cancel_order));
                break;
            case ORDER_WAIT_FOR_PAY:
                mBinding.layoutTitle.tvTitle.setText(getString(R.string.wait_for_pay));
                break;
            case ORDER_WAIT_FOR_DELIVER:
                mBinding.layoutTitle.tvTitle.setText(getString(R.string.waiting_for_merchant_delivery));
                break;
            case ORDER_DELIVERING:
                // FIXME: 2021/1/13 根据情况判断配送方式
                mBinding.layoutTitle.tvTitle.setText(getString(R.string.expressage_delivering));
                break;
            case ORDER_DELIVERED:
                mBinding.layoutTitle.tvTitle.setText(getString(R.string.complete_transaction));
                break;
        }
    }

    /**
     * 初始化商品展示布局
     */
    private void initOrderShopsGoods() {
        initGoodsInfoRecycler(mBinding.recyclerGoodsInfo, mViewModel.getMallOrder().getMallGoodsList());
        initOrderInfoRecycler(mBinding.recyclerOrderInfo, mViewModel.getMallOrder().getShopOrderInfo());

    }

    private void initGoodsInfoRecycler(RecyclerView recyclerView, List<MallGoods> mallGoodsList) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(
                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_order_detail_shops_goods_info, mallGoodsList, com.life.waimaishuo.BR.item));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance(requireContext()).scalePx(24);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
    }

    private void initOrderInfoRecycler(RecyclerView recyclerView, List<TypeDescribeValue> typeDescribeValueList) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(
                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_confirm_order_info, typeDescribeValueList, com.life.waimaishuo.BR.item) {
                    @Override
                    protected void initView(BaseViewHolder helper, Object item) {
                        super.initView(helper, item);
                        if (helper.getAdapterPosition() == 1) {
                            helper.setTextColor(R.id.tv_type_value, getResources().getColor(R.color.text_normal));
                        }
                    }
                });
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance(requireContext()).scalePx(24);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
    }

    private void setPageElementByOrderType() {

        resetTitle();

        switch (mPageState) {
            case ORDER_CANCEL:
            case ORDER_WAIT_FOR_PAY:
                setViewVisibility(mBinding.clDeliver, false);
                break;
            case ORDER_WAIT_FOR_DELIVER:
            case ORDER_DELIVERING:
            case ORDER_DELIVERED:
                setViewVisibility(mBinding.clDeliver, true);
                resetClDeliver();
                break;
        }

        resetBottomOrderInfo();
    }

    private void resetClDeliver(){
        if(mPageState == ORDER_WAIT_FOR_DELIVER){
            mBinding.ivDeliver.setImageResource(R.drawable.ic_car);
            mBinding.tvDeliverState.setText(getString(R.string.wait_for_deliver));
        }else if(mPageState == ORDER_DELIVERING){
            mBinding.ivDeliver.setImageResource(R.drawable.ic_deliver_man);
            mBinding.tvDeliverState.setText(getString(R.string.delivering));
        }else{  //配送完成
            mBinding.ivDeliver.setImageResource(R.drawable.ic_car);
            mBinding.tvDeliverState.setText(getString(R.string.sign_for_goods));
        }
    }

    boolean hasChangeButtonStyle = false;
    boolean hasInitBottomOrderRecycler = false;
    private MyBaseRecyclerAdapter bottomOrderRecyclerAdapter;
    private void resetBottomOrderInfo() {
        if (hasChangeButtonStyle) {   //恢复默认样式
            hasChangeButtonStyle = false;
            mBinding.layoutBottomOrderInfo.btThree.setBackgroundResource(R.drawable.sr_stroke_1px_10radius_gray);
            mBinding.layoutBottomOrderInfo.btThree.setTextColor(getResources().getColor(R.color.text_normal));
        }

        if (!hasInitBottomOrderRecycler) {
            hasInitBottomOrderRecycler = true;
            bottomOrderRecyclerAdapter =
                    new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_order_info, mViewModel.getMallOrder().getOrderInfo(), com.life.waimaishuo.BR.item);
            mBinding.layoutBottomOrderInfo.recyclerOrderInfo.setLayoutManager(
                    new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            mBinding.layoutBottomOrderInfo.recyclerOrderInfo.setAdapter(bottomOrderRecyclerAdapter);
        }

        bottomOrderRecyclerAdapter.notifyDataSetChanged();

        switch (mPageState) {
            case ORDER_CANCEL:
                setViewVisibility(mBinding.layoutBottomOrderInfo.ivLeft, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btOne, false);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btTwo, false);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btThree, true);

                mBinding.layoutBottomOrderInfo.btThree.setText(getString(R.string.deleted_order));
                mBinding.layoutBottomOrderInfo.btThree.setOnClickListener(v -> deleteOrder());
                break;
            case ORDER_WAIT_FOR_PAY:
                setViewVisibility(mBinding.layoutBottomOrderInfo.ivLeft, false);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btOne, false);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btTwo, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btThree, true);

                mBinding.layoutBottomOrderInfo.btTwo.setText(getString(R.string.driver_info));
                mBinding.layoutBottomOrderInfo.btTwo.setOnClickListener(v -> showDriverInfo());
                mBinding.layoutBottomOrderInfo.btThree.setText(getString(R.string.pay));
                mBinding.layoutBottomOrderInfo.btThree.setOnClickListener(v -> pay());

                hasChangeButtonStyle = true;//设置标志 修改了样式
                mBinding.layoutBottomOrderInfo.btThree.setBackgroundResource(R.drawable.sr_stroke_1px_10radius_red);
                mBinding.layoutBottomOrderInfo.btThree.setTextColor(getResources().getColor(R.color.colorTheme));
                break;
            case ORDER_WAIT_FOR_DELIVER:
                setViewVisibility(mBinding.layoutBottomOrderInfo.ivLeft, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btOne, false);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btTwo, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btThree, true);

                mBinding.layoutBottomOrderInfo.btTwo.setText(getString(R.string.remind_shipment));
                mBinding.layoutBottomOrderInfo.btTwo.setOnClickListener(v -> remindShipment());
                mBinding.layoutBottomOrderInfo.btThree.setText(getString(R.string.view_logistics));
                mBinding.layoutBottomOrderInfo.btThree.setOnClickListener(v -> viewLogistics());
                break;
            case ORDER_DELIVERING:
                setViewVisibility(mBinding.layoutBottomOrderInfo.ivLeft, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btOne, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btTwo, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btThree, true);

                mBinding.layoutBottomOrderInfo.btOne.setText(getString(R.string.lengthen_receipt_time));
                mBinding.layoutBottomOrderInfo.btOne.setOnClickListener(v -> lengthenReceiptTime());
                mBinding.layoutBottomOrderInfo.btTwo.setText(getString(R.string.driver_info));
                mBinding.layoutBottomOrderInfo.btTwo.setOnClickListener(v -> showDriverInfo());
                mBinding.layoutBottomOrderInfo.btThree.setText(getString(R.string.receipt_confirm));
                mBinding.layoutBottomOrderInfo.btThree.setOnClickListener(v -> receiptConfirm());
                break;
            case ORDER_DELIVERED:
                setViewVisibility(mBinding.layoutBottomOrderInfo.ivLeft, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btOne, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btTwo, true);
                setViewVisibility(mBinding.layoutBottomOrderInfo.btThree, true);

                mBinding.layoutBottomOrderInfo.btOne.setText(getString(R.string.deleted_order));
                mBinding.layoutBottomOrderInfo.btOne.setOnClickListener(v -> deleteOrder());
                mBinding.layoutBottomOrderInfo.btTwo.setText(getString(R.string.evaluation));
                mBinding.layoutBottomOrderInfo.btTwo.setOnClickListener(v -> evaluation());
                mBinding.layoutBottomOrderInfo.btThree.setText(getString(R.string.add_to_shopping_cart));
                mBinding.layoutBottomOrderInfo.btThree.setOnClickListener(v -> addShoppingCart());
                break;
        }
    }

    /**
     * 骑手信息
     */
    private void showDriverInfo() {

    }

    /**
     * 付款
     */
    private void pay() {

    }

    /**
     * 删除订单
     */
    private void deleteOrder() {

    }

    /**
     * 提醒发货
     */
    private void remindShipment() {

    }

    /**
     * 查看物流
     */
    private void viewLogistics() {

    }

    /**
     * 延长收货
     */
    private void lengthenReceiptTime() {

    }

    /**
     * 确认签收
     */
    private void receiptConfirm() {

    }

    /**
     * 评价
     */
    private void evaluation() {

    }

    /**
     * 加入购物车
     */
    private void addShoppingCart() {

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
        baseFragment.openPage(OrderDetailFragment.class,bundle);

    }

}
