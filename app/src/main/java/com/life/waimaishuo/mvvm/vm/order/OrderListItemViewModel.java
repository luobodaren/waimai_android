package com.life.waimaishuo.mvvm.vm.order;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.enumtype.OrderPageEnum;
import com.life.waimaishuo.enumtype.OrderStateEnum;
import com.life.waimaishuo.enumtype.OrderTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.order.OrderBarItemModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrderListItemViewModel extends BaseViewModel {

    OrderBarItemModel orderBarItemModel;

    private List<Order> orderList = new ArrayList<>();
    private List<Order> orderList1 = new ArrayList<>();
    private List<Order> orderList2 = new ArrayList<>();
    private List<Order> orderList3 = new ArrayList<>();
    private List<Order> orderList4 = new ArrayList<>();
    private List<Order> orderList5 = new ArrayList<>();

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
        Goods goods = new Goods("来吃烧烤！！！来吃烧烤！！！","这是一堆烧烤",
                "https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",
                "2","550.00");
        goodsList.add(goods);
        goodsList.add(goods);
        goodsList.add(goods);
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_PAY.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_DELIVER.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.DELIVER.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.DELIVER.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.FINISH.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.AFTER_SALES.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.AFTER_SALES.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));

        orderList1.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_PAY.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList1.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_PAY.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList1.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_PAY.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));

        orderList2.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_DELIVER.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList2.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_DELIVER.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList2.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.UN_DELIVER.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));

        orderList3.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.DELIVER.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList3.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.DELIVER.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList3.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.DELIVER.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));

        orderList4.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.FINISH.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList4.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.FINISH.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList4.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.FINISH.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));

        orderList5.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.AFTER_SALES.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
        orderList5.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.AFTER_SALES.getCode(), goodsList, OrderTypeEnum.MALL.getCode()));
        orderList5.add(new Order("烧烤店",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1925352510,4053759500&fm=26&gp=0.jpg",
                "2020-11-27",
                OrderStateEnum.AFTER_SALES.getCode(), goodsList, OrderTypeEnum.WAI_MAI.getCode()));
    }

    /**
     * 根据页面类型返回不同的订单list
     * @param orderPageEnum
     * @return
     */
    public List<Order> getOrderData(OrderPageEnum orderPageEnum) {
        if(orderPageEnum == OrderPageEnum.ALL){
            return orderList;
        }else if(orderPageEnum == OrderPageEnum.UN_PAY){
            return orderList1;
        }else if(orderPageEnum == OrderPageEnum.UN_DELIVER){
            return orderList2;
        }else if(orderPageEnum == OrderPageEnum.DELIVER){
            return orderList3;
        }else if(orderPageEnum == OrderPageEnum.FINISH){
            return orderList4;
        }else if(orderPageEnum == OrderPageEnum.AFTER_SALES){
            return orderList5;
        }else{
            LogUtil.e("获取数据失败 配有匹配到页面类型");
            return null;
        }
    }


}
