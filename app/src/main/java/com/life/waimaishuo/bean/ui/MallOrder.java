package com.life.waimaishuo.bean.ui;

import com.life.waimaishuo.bean.Shop;

import java.util.Iterator;
import java.util.List;

public class MallOrder {

    Shop shop;

    List<MallGoods> mallGoodsList;  //保存订单的多个商品信息
    List<TypeDescribeValue> typeDescribeValueList;  //保存其他信息 包括配送方式 店铺优惠等

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<MallGoods> getMallGoodsList() {
        return mallGoodsList;
    }

    public void setMallGoodsList(List<MallGoods> mallGoodsList) {
        this.mallGoodsList = mallGoodsList;
    }

    public List<TypeDescribeValue> getTypeDescribeValueList() {
        return typeDescribeValueList;
    }

    public void setTypeDescribeValueList(List<TypeDescribeValue> typeDescribeValueList) {
        this.typeDescribeValueList = typeDescribeValueList;
    }

    public double getOrderPrice(){
        MallGoods goods;
        double prices = 0f;
        Iterator<MallGoods> iterator = mallGoodsList.iterator();
        if(iterator.hasNext()){
            goods = iterator.next();
            prices += Double.valueOf(goods.goodsPrice);
        }
        return prices;
    }
}
