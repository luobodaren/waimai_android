package com.life.waimaishuo.bean.event;

public class ShoppingCartOptionEvent extends MessageEvent {

    public int goodsId;
    public int optionType; // 1 加入购物车 2 修改购物车 3 清空购物车
    public int buyCount;   //修改后的商品数量

    public ShoppingCartOptionEvent(int code, String message, int goodsId, int optionType, int buyCount) {
        super(code, message);
        this.goodsId = goodsId;
        this.optionType = optionType;
        this.buyCount = buyCount;
    }

    @Override
    public String toString() {
        return "ShoppingCartOptionEvent{" +
                "code=" + getCode() +
                ", goodsId=" + goodsId +
                ", optionType=" + optionType +
                ", buyCount=" + buyCount +
                '}';
    }
}
