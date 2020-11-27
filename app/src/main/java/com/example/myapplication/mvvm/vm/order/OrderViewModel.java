package com.example.myapplication.mvvm.vm.order;

import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.order.OrderModel;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.order.OrderBarItemFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends BaseViewModel {

    OrderModel orderModel;

    String[] pages = {"全部订单","待付款","待配送","配送中","已完成","售后"};

    @Override
    public BaseModel getModel() {
        orderModel = new OrderModel();
        return orderModel;
    }

    @Override
    public void initData() {

    }

    public String[] getTabDatas() {
        return pages;
    }

    public List<BaseFragment> getTabFragment() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        int count = pages.length;
        for(int i = 0;i<count;i++){
            OrderBarItemFragment orderBarItemFragment = new OrderBarItemFragment();
            orderBarItemFragment.setPageType(pages[i]);
            fragmentList.add(orderBarItemFragment);
        }
        return fragmentList;
    }
}
