package com.example.myapplication.mvvm.vm.order;

import com.example.myapplication.bean.Goods;
import com.example.myapplication.bean.Order;
import com.example.myapplication.enumtype.OrderState;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.order.OrderBarItemModel;
import com.example.myapplication.mvvm.vm.BaseRecyclerViewModel;
import com.example.myapplication.mvvm.vm.BaseViewModel;

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
        List<Goods> goodsList = new ArrayList<>();
        Goods goods = new Goods("小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋小白鞋","这是一双小白鞋！！！！！！",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2424071932,228392684&fm=15&gp=0.jpg",
                "2","550.00");
        goodsList.add(goods);
        goodsList.add(goods);
        goodsList.add(goods);
        orderList.add(new Order("嘉禾服装店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderState.UNDELIVER.getCode(),goodsList));
        orderList.add(new Order("嘉禾服装店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderState.UNDELIVER.getCode(),goodsList));
        orderList.add(new Order("嘉禾服装店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderState.UNDELIVER.getCode(),goodsList));
    }

    public List getOrderData() {
        return orderList;
    }

    public List getTopRecyclerViewModelList() {
        List<BaseRecyclerViewModel> baseViewHolders = new ArrayList<>();
        int orderCount = orderList.size();
        for(int i = 0; i<orderCount; i++){
            OrderItemViewModel orderItemViewModel = new OrderItemViewModel();
            orderItemViewModel.setData(orderList.get(i));
            baseViewHolders.add(orderItemViewModel);
        }
        return baseViewHolders;
    }
}
