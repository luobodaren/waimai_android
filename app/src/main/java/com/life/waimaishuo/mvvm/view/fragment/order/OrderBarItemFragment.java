package com.life.waimaishuo.mvvm.view.fragment.order;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.MyApplication;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Foods;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.databinding.FragmentOrderListBinding;
import com.life.waimaishuo.databinding.HeadOrderUnpayBinding;
import com.life.waimaishuo.databinding.ItemGoodInfoBinding;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseChildFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.OrderListItemViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "订单页 ViewPager子页")
public class OrderBarItemFragment extends BaseChildFragment {

    private OrderListItemViewModel mViewModel;

    private String mPageType = "";

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new OrderListItemViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    public void setPageType(String pageType) {
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

    private void initRecycler(){
        RecyclerView recyclerView = ((FragmentOrderListBinding)mViewDataBinding).recyclerOrderList;

        MyBaseRecyclerAdapter<Order> myBaseRecyclerAdapter
                = new MyBaseRecyclerAdapter<Order>(R.layout.item_order_list, mViewModel.getOrderData()) {
            @Override
            protected void initView(BaseViewHolder helper,Order item) {
                MyBaseRecyclerAdapter adapter1 = new MyBaseRecyclerAdapter<Foods>(R.layout.item_good_info,item.getFoodsList(), BR.goods);

                View headView = View.inflate(MyApplication.getMyApplication(),R.layout.head_order_unpay,null);
                ((HeadOrderUnpayBinding)DataBindingUtil.bind(headView)).setOrder(item);

                adapter1.addHeaderView(headView);
                View footView = View.inflate(MyApplication.getMyApplication(),R.layout.foot_order_unpay,null);
                adapter1.addFooterView(footView);

                ((RecyclerView)helper.getView(R.id.recycler_goods_list)).setLayoutManager(
                        new LinearLayoutManager(MyApplication.getMyApplication(),
                                LinearLayoutManager.VERTICAL,false));
                ((RecyclerView)helper.getView(R.id.recycler_goods_list)).setAdapter(adapter1);
            }
        };
        // TODO: 2020/11/27 添加viewType 根据不同的type加载不同的布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(myBaseRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
