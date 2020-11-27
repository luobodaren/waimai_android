package com.example.myapplication.mvvm.view.fragment.order;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.order.OrderListRecyclerAdapter;
import com.example.myapplication.databinding.FragmentOrderListBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.order.OrderListItemViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "订单页 ViewPager子页")
public class OrderBarItemFragment extends BaseFragment {

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

        OrderListRecyclerAdapter myBaseRecyclerAdapter
                = new OrderListRecyclerAdapter(R.layout.item_order_list,mViewModel.getOrderData()
                ,mViewModel.getTopRecyclerViewModelList());
        // TODO: 2020/11/27 添加viewType 根据不同的type加载不同的布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(myBaseRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
