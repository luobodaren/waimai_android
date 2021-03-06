package com.life.waimaishuo.mvvm.vm.order;

import com.life.waimaishuo.enumtype.OrderPageEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.OrderModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.OrderBarItemFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends BaseViewModel {

    OrderModel orderModel;



    @Override
    public BaseModel getModel() {
        orderModel = new OrderModel();
        return orderModel;
    }

    @Override
    public void initData() {

    }

    public String[] getTabData() {
        return OrderPageEnum.getStateStrings();
    }

    public List<BaseFragment> getTabFragment() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        int count = OrderPageEnum.getStateStrings().length;
        for(int i = 0;i<count;i++){
            OrderBarItemFragment orderBarItemFragment = new OrderBarItemFragment();
            orderBarItemFragment.setPageType(OrderPageEnum.values()[i]);
            fragmentList.add(orderBarItemFragment);
        }
        return fragmentList;
    }
}
