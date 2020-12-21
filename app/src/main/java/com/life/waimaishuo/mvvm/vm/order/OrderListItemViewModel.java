package com.life.waimaishuo.mvvm.vm.order;

import com.life.waimaishuo.bean.Foods;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.OrderBarItemModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderListItemViewModel extends BaseViewModel {

    OrderBarItemModel orderBarItemModel;

    private List<Order> orderList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        orderBarItemModel = new OrderBarItemModel();
        return orderBarItemModel;
    }

    @Override
    public void initData() {
        initOrderData();
    }

    private void initOrderData() {
        List<Foods> foodsList = new ArrayList<>();
        Foods foods = new Foods("来吃烧烤！！！来吃烧烤！！！来吃烧烤！！！来吃烧烤！！！来吃烧烤！！！来吃烧烤！！！","这是一堆烧烤",
                "https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",
                "2","550.00");
        foodsList.add(foods);
        foodsList.add(foods);
        foodsList.add(foods);
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UNDELIVER.getCode(), foodsList));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UNDELIVER.getCode(), foodsList));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UNDELIVER.getCode(), foodsList));
    }

    public List getOrderData() {
        return orderList;
    }

}
