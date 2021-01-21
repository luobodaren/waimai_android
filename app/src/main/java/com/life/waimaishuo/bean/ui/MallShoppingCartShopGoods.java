package com.life.waimaishuo.bean.ui;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;

import java.util.List;

public class MallShoppingCartShopGoods {

    boolean selected;
    MallGoods goods;
    int buyCount;
    String specifications[];
    String selected_specification;
    String purchase_limitation; //购买限制
    String price_introduce;

    public MallShoppingCartShopGoods(boolean selected, MallGoods goods, int buyCount, String[] specifications, String selected_specification, String purchase_limitation, String price_introduce) {
        this.selected = selected;
        this.goods = goods;
        this.buyCount = buyCount;
        this.specifications = specifications;
        this.selected_specification = selected_specification;
        this.purchase_limitation = purchase_limitation;
        this.price_introduce = price_introduce;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public MallGoods getGoods() {
        return goods;
    }

    public void setGoods(MallGoods goods) {
        this.goods = goods;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(int buyCount) {
        this.buyCount = buyCount;
    }

    public String[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String[] specifications) {
        this.specifications = specifications;
    }

    public String getSelected_specification() {
        return selected_specification;
    }

    public void setSelected_specification(String selected_specification) {
        this.selected_specification = selected_specification;
    }

    public String getPurchase_limitation() {
        return purchase_limitation;
    }

    public void setPurchase_limitation(String purchase_limitation) {
        this.purchase_limitation = purchase_limitation;
    }

    public String getPrice_introduce() {
        return price_introduce;
    }

    public void setPrice_introduce(String price_introduce) {
        this.price_introduce = price_introduce;
    }
}
