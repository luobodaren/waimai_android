package com.life.waimaishuo.bean.ui;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;

import java.util.List;

public class MallShoppingCartItemData {

    Shop shop;
    boolean selectedAll;
    List<MallShoppingCartShopGoods> goodsList;
    boolean isEffective;

    public MallShoppingCartItemData(Shop shop, boolean selectedAll, List<MallShoppingCartShopGoods> goodsList, boolean isEffective) {
        this.shop = shop;
        this.selectedAll = selectedAll;
        this.goodsList = goodsList;
        this.isEffective = isEffective;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public boolean isSelectedAll() {
        return selectedAll;
    }

    public void setSelectedAll(boolean selectedAll) {
        this.selectedAll = selectedAll;
    }

    public List<MallShoppingCartShopGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<MallShoppingCartShopGoods> goodsList) {
        this.goodsList = goodsList;
    }

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }
}
