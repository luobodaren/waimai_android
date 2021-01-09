package com.life.waimaishuo.mvvm.view.fragment.order;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Foods;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.databinding.FragmentOrderListBinding;
import com.life.waimaishuo.databinding.HeadOrderMallUnpayBinding;
import com.life.waimaishuo.databinding.HeadOrderWaimaiBinding;
import com.life.waimaishuo.enumtype.OrderPageEnum;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.enumtype.OrderTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseChildFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.waimai.ApplyAfterSalesFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.waimai.OrderConfirmFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.OrderListItemViewModel;
import com.life.waimaishuo.views.widget.dialog.SimpleConfirmCancelDialog;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

@Page(name = "订单页 ViewPager子页")
public class OrderBarItemFragment extends BaseChildFragment {

    private OrderListItemViewModel mViewModel;

    private OrderPageEnum mPageType;

    private XUIListPopup mListPopup;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new OrderListItemViewModel();  // FIXME: 2021/1/7 可考虑改成多个fragment 使用单个viewmodel
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    public void setPageType(OrderPageEnum pageType) {
        this.mPageType = pageType;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRecycler();
    }

    //viewType 布局类型
    private static final int mall = 1;
    private static final int waimai_un_pay = 2, waimai_un_deliver = 3, waimai_deliver = 4, waimai_finish = 5, waimai_after_sales = 6;

    private void initRecycler() {
        RecyclerView recyclerView = ((FragmentOrderListBinding) mViewDataBinding).recyclerOrderList;

        BaseRecyclerAdapter<Order> baseRecyclerAdapter
                = new BaseRecyclerAdapter<Order>(mViewModel.getOrderData(mPageType)) {

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, Order item) {
                int viewType = holder.getItemViewType();

                holder.getView(R.id.go_order_detail_page_layout).setOnClickListener(v -> {
                    if(item.getOrderType() == OrderTypeEnum.WAI_MAI.getCode()){ //外卖
                        OrderConfirmFragment.openPageWithOrder(OrderBarItemFragment.this,item);
                    }else if(item.getOrderType() == OrderTypeEnum.MALL.getCode()){  //商城

                    }else{
                        LogUtil.e("订单类型匹配出错 orderType:" + item.getOrderType());
                    }
                });

                if (viewType == mall) {
                    MyBaseRecyclerAdapter adapter1 = new MyBaseRecyclerAdapter<Foods>(R.layout.item_good_info, item.getFoodsList(), BR.goods);

                    View headView = View.inflate(MyApplication.getMyApplication(), R.layout.head_order_mall_unpay, null);
                    ((HeadOrderMallUnpayBinding) DataBindingUtil.bind(headView)).setOrder(item);
                    adapter1.addHeaderView(headView);

                    View footView;
                    if (item.getOrderState() == OrderStateEnum.UN_PAY.getCode()) {
                        footView = View.inflate(MyApplication.getMyApplication(), R.layout.foot_order_mall_unpay, null);
                        adapter1.addFooterView(footView);
                    } else if (item.getOrderState() == OrderStateEnum.UN_DELIVER.getCode()) {
                        footView = View.inflate(MyApplication.getMyApplication(), R.layout.foot_order_mall_un_deliver, null);
                        adapter1.addFooterView(footView);
                    } else if (item.getOrderState() == OrderStateEnum.DELIVER.getCode()) {
                        footView = View.inflate(MyApplication.getMyApplication(), R.layout.foot_order_mall_deliver, null);
                        footView.findViewById(R.id.bt_receipt_confirm).setOnClickListener(v -> onReceiptButtonClick(item));
                        footView.findViewById(R.id.bt_driver_info).setOnClickListener(v -> onDriverInfoButtonClick(item));
                        footView.findViewById(R.id.bt_lengthen_receipt_time).setOnClickListener(v -> onLengthenReceiptButtonClick(item));
                        adapter1.addFooterView(footView);
                    } else if (item.getOrderState() == OrderStateEnum.FINISH.getCode()) {
                        footView = View.inflate(MyApplication.getMyApplication(), R.layout.foot_order_mall_finish, null);
                        footView.findViewById(R.id.bt_comment).setOnClickListener(v -> onMallCommentButtonClick(item));
                        footView.findViewById(R.id.iv_menu).setOnClickListener(v -> onIvMenuClick(v, item));
                        adapter1.addFooterView(footView);
                    } else if (item.getOrderState() == OrderStateEnum.AFTER_SALES.getCode()) {
                        footView = View.inflate(MyApplication.getMyApplication(), R.layout.foot_order_mall_after_sale, null);
                        adapter1.addFooterView(footView);
                    } else if (item.getOrderState() == OrderStateEnum.CLOSE.getCode()) {
                        footView = View.inflate(MyApplication.getMyApplication(), R.layout.foot_order_mall_close, null);
                        adapter1.addFooterView(footView);
                    }
                    ((RecyclerView) holder.getView(R.id.recycler_goods_list)).setLayoutManager(
                            new LinearLayoutManager(MyApplication.getMyApplication(),
                                    LinearLayoutManager.VERTICAL, false));
                    ((RecyclerView) holder.getView(R.id.recycler_goods_list)).setAdapter(adapter1);
                } else {    //外卖的布局
                    if (viewType == waimai_un_pay || viewType == waimai_finish
                            || viewType == waimai_after_sales) {
                        item.setString_1(item.getOrderCreateTime());//处理头布局显示的info

                        holder.text(R.id.tv_foods_info, getFoodsSimpleInfo(item, false));
                        holder.text(R.id.tv_foods_price, getOrderPrice(item));

                        if (viewType == waimai_un_pay) {
                            holder.click(R.id.bt_cancel_order, v -> onCancelOrderButtonClick(item));
                        } else if (viewType == waimai_finish) {
                            holder.click(R.id.bt_comment, v -> onWaimaiCommentButtonClick(item));
                            holder.click(R.id.iv_menu,v -> onIvMenuClick(v,item));
                        }

                    } else if (viewType == waimai_un_deliver || viewType == waimai_deliver) {
                        item.setString_1(getFoodsSimpleInfo(item, true));//处理头布局显示的info

                        holder.click(R.id.bt_connect_driver, v -> onConnectedDriverButtonClick(item));
                        //处理地图信息
                    }

                    //所有类型布局的头设置信息
                    HeadOrderWaimaiBinding headBinding = DataBindingUtil.bind(holder.itemView.findViewById(R.id.layout_head));
                    headBinding.setOrder(item);
                }
            }

            /**
             * 所以布局最外层id为 go_order_detail_page_layout，用于设置点击事件 进入订单详情页。
             * @param viewType
             * @return
             */
            @Override
            protected int getItemLayoutId(int viewType) {
                switch (viewType) {
                    case mall:
                        return R.layout.item_recycler_order_mall_list;
                    case waimai_un_pay:
                        return R.layout.item_recycler_order_unpay_waimai;
                    case waimai_un_deliver:
                    case waimai_deliver:
                        return R.layout.item_recycler_order_deliver_waimai; //若有不同布局 记得修改bindData方法内逻辑
                    case waimai_finish:
                        return R.layout.item_recycler_order_finish_waimai;
                    case waimai_after_sales:
                        return R.layout.item_recycler_order_after_sale_waimai;
                }
                return -1;
            }

            @Override
            public int getItemViewType(int position) {
                // TODO: 2021/1/7 添加逻辑 判断是外卖还是商场
                //若是商场，则用一个布局文件 完成内容切换
                int orderType = getData().get(position).getOrderType();
                if (orderType == OrderTypeEnum.WAI_MAI.getCode()) {   //外卖类型布局
                    OrderStateEnum orderStateEnum =
                            OrderStateEnum.valueOf(getData().get(position).getOrderState());
                    if (orderStateEnum != null) {
                        switch (orderStateEnum) {
                            case UN_PAY:
                                return waimai_un_pay;
                            case UN_DELIVER:
                                return waimai_un_deliver;
                            case DELIVER:
                                return waimai_deliver;
                            case FINISH:
                                return waimai_finish;
                            case AFTER_SALES:
                                return waimai_after_sales;
                        }
                    } else {
                        LogUtil.e("布局类型匹配失败");
                    }
                } else if (orderType == OrderTypeEnum.MALL.getCode()) {  //商城类型布局
                    return mall;
                }

                return super.getItemViewType(position);
            }
        };
        // TODO: 2020/11/27 添加viewType 根据不同的type加载不同的布局  目前只支持外卖订单
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setAdapter(baseRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int space = (int) UIUtils.getInstance(requireContext()).scalePx(
                    getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = space;
            }
        });
    }

    /**
     * 获得简要的订单商品信息
     *
     * @param order
     * @param isWithPrice
     * @return
     */
    private String getFoodsSimpleInfo(Order order, boolean isWithPrice) {
        String foodsInfo = "";
        if (order.getFoodsList().size() > 1) {
            foodsInfo = order.getFoodsList().get(0).getName() + " " +
                    getString(R.string.foods_number, String.valueOf(order.getFoodsList().size()));
        } else if (order.getFoodsList().size() == 1) {
            foodsInfo = order.getFoodsList().get(0).getName();
        } else {
            LogUtil.d("订单商品信息出错，没有商品信息");
        }
        if (isWithPrice) {
            foodsInfo = foodsInfo + " " + getOrderPrice(order);
        }
        return foodsInfo;
    }

    /**
     * 获得订单价格 带格式
     *
     * @param order
     * @return
     */
    private String getOrderPrice(Order order) {
        return getString(R.string.goods_price, String.valueOf(order.getOrderPrice()));
    }

    /**
     * 显示取消订单确认dialog
     *
     * @param order
     */
    private void onCancelOrderButtonClick(Order order) {
        new SimpleConfirmCancelDialog.Builder(requireContext())
                .setContentString(getString(R.string.sure_cancel_order), R.color.text_normal)
                .initLeftBt(getString(R.string.confirm), R.color.text_tip, false)
                .initRightBt(getString(R.string.cancel), R.color.text_normal, true)
                .setOnButtonClickListener((view, position) -> {

                })
                .build().show();
    }

    /**
     * 显示联系骑手dialog
     *
     * @param order
     */
    private void onConnectedDriverButtonClick(Order order) {
        new SimpleConfirmCancelDialog.Builder(requireContext())
                .setContentString("13513153551", R.color.text_normal) // FIXME: 2021/1/7 写入骑手电话
                .initLeftBt(getString(R.string.cancel), R.color.text_tip, false)
                .initRightBt(getString(R.string.dial), R.color.text_normal, true)
                .setOnButtonClickListener((view, position) -> {

                })
                .build().show();
    }

    /**
     * 显示确认签收dialog
     *
     * @param order
     */
    private void onReceiptButtonClick(Order order) {
        new SimpleConfirmCancelDialog.Builder(requireContext())
                .setContentString(getString(R.string.sure_receipt_confirm), R.color.text_normal)
                .initLeftBt(getString(R.string.cancel), R.color.text_normal, true)
                .initRightBt(getString(R.string.confirm), R.color.text_normal, true)
                .setOnButtonClickListener((view, position) -> {

                })
                .build().show();
    }

    /**
     * 显示骑手信息dialog
     *
     * @param order
     */
    private void onDriverInfoButtonClick(Order order) {
        new SimpleConfirmCancelDialog.Builder(requireContext())
                .setContentString("王小二\n400-333-222", R.color.text_normal) // FIXME: 2021/1/7 写入骑手信息
                .initLeftBt(getString(R.string.cancel), R.color.text_normal, true)
                .initRightBt(getString(R.string.dial), R.color.text_normal, true)
                .setOnButtonClickListener((view, position) -> {

                })
                .build().show();
    }

    /**
     * 显示延长签收dialog
     *
     * @param order
     */
    private void onLengthenReceiptButtonClick(Order order) {
        new SimpleConfirmCancelDialog.Builder(requireContext())
                .setContentString(getString(R.string.lengthen_sure_receipt), R.color.text_normal)
                .initLeftBt(getString(R.string.cancel), R.color.text_normal, true)
                .initRightBt(getString(R.string.confirm), R.color.text_normal, true)
                .setOnButtonClickListener((view, position) -> {

                })
                .build().show();
    }

    /**
     * 商城订单评论按钮点击
     */
    private void onMallCommentButtonClick(Order order) {
        Bundle bundle = new Bundle();
        openPage(EvaluateMallFragment.class, bundle);
    }

    /**
     * 外卖订单评论按钮点击
     */
    private void onWaimaiCommentButtonClick(Order order) {
        Bundle bundle = new Bundle();
        openPage(EvaluateWaiMaiFragment.class, bundle);
    }

    /**
     * 菜单图标点击
     */
    private void onIvMenuClick(View v,Order order) {
        initListPopupIfNeed();
        setListPopupItemClickListener((parent, view, position, id) -> {
            mListPopup.dismiss();
            Bundle bundle = new Bundle();
            bundle.putParcelable("order",order);    // TODO: 2021/1/8 改为fragment内的静态方法
            if(order.getOrderType() == OrderTypeEnum.WAI_MAI.getCode()){
                openPage(ApplyAfterSalesFragment.class,bundle);
            }else if(order.getOrderType() == OrderTypeEnum.MALL.getCode()){
                openPage(com.life.waimaishuo.mvvm.view.fragment.order.mall.ApplyAfterSalesFragment.class,bundle);
            }
        });
        mListPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(XUIPopup.DIRECTION_BOTTOM);
        mListPopup.show(v);
    }

    /**
     * 初始化
     */
    private void initListPopupIfNeed() {
        if (mListPopup == null) {
            int margin = 0;
            String[] listItems = new String[]{
                    "申请售后"
            };
            XUISimpleAdapter adapter = XUISimpleAdapter.create(getContext(), listItems);
            mListPopup = new XUIListPopup(getContext(), adapter);
            mListPopup.create((int) UIUtils.getInstance(requireContext()).scalePx(152),
                    (int) UIUtils.getInstance(
                            requireContext()).scalePx(200), null);
            FrameLayout.LayoutParams lp =
                    (FrameLayout.LayoutParams)mListPopup.getListView().getLayoutParams();
            lp.setMargins(0,margin,0,margin);
            mListPopup.getListView().setLayoutParams(lp);
        }
    }

    private void setListPopupItemClickListener(AdapterView.OnItemClickListener onItemClickListener){
        mListPopup.getListView().setOnItemClickListener(onItemClickListener);
    }

}
