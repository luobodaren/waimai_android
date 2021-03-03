package com.life.waimaishuo.bean.event;

public class ShoppingCartOptionEvent extends MessageEvent {

    public int shopId;
    public int optionType; // 1 加入购物车 2 修改购物车 3 清空购物车
    public int buyCount;   //修改后的商品数量

    public ShoppingCartOptionEvent(int code, boolean message, int shopId, int optionType, int buyCount) {
        super(code, message);
        this.shopId = shopId;
        this.optionType = optionType;
        this.buyCount = buyCount;
    }

    @Override
    public String toString() {
        return "ShoppingCartOptionEvent{" +
                "shopId=" + shopId +
                ", optionType=" + optionType +
                ", buyCount=" + buyCount +
                '}';
    }
}
