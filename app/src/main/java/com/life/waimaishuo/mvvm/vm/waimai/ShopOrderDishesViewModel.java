package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.request.bean.ShoppingCartOption;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.bean.event.ShoppingCartOptionEvent;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopOrderDishesModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.kunminx.linkage.bean.BaseGroupedItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ShopOrderDishesViewModel extends BaseViewModel {

    private ShopOrderDishesModel mModel;

    public ObservableInt requestShopGoodsObservable = new ObservableInt();
    public ObservableInt requestGoodsSpecificationObservable = new ObservableInt();

    @Override
    public BaseModel getModel() {
        mModel = new ShopOrderDishesModel();
        return mModel;
    }

    @Override
    public void initData() {

    }

    public String[] getBannerSource() {
        return new String[]{};
    }

    public List<BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>> getShopGoodsItems() {
        return mModel.shopGoodsLinkageGroupList;
    }

    /**
     * 请求店铺商品信息
     */
    public void requestShopGoodsGroupList(int shopID){
        mModel.requestShopGoodsGroupList(new BaseModel.NotifyChangeRequestCallBack(requestShopGoodsObservable), new WaiMaiShopReqData.WaiMaiSimpleReqData(shopID));
    }

    public void requestGoodsSpecification() {
        mModel.requestGoodsSpecification(new BaseModel.NotifyChangeRequestCallBack(requestGoodsSpecificationObservable), new WaiMaiShopReqData.WaiMaiSimpleReqData(0));
    }

    public Goods getGoodsSpecification(){
        return mModel.specificationGoods;
    }

    /**
     * 加入购物车
     */
    public void requestAddShoppingCart(String shopId, Goods goods, String buyCount,
                                       String specification, String attrs){
        mModel.requestAddShoppingCart(
                new BaseModel.RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.ADD_SHOPPING_CART_SUCCESS,true,Integer.valueOf(shopId),1,Integer.valueOf(buyCount)));
                    }

                    @Override
                    public void onFail(String error) {
                        EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.ADD_SHOPPING_CART_FALSE,false,Integer.valueOf(shopId),1,Integer.valueOf(buyCount)));
                    }
                },
                new WaiMaiShopReqData.WaiMaiShoppingCartOption(
                        new ShoppingCartOption(attrs, goods.getMealsFee(), buyCount,
                                String.valueOf(goods.getId()), goods.getName(),
                                String.valueOf(goods.getIsBargainGoods()), "1",
                                goods.getSpecialPrice(), shopId, specification, goods.getVersions())));
    }

    /**
     * 修改购物车数据
     */
    public void requestChangeShoppingCart(String shopId, Goods goods, String buyCount,
                                          String specification, String attrs){
        mModel.requestChangeShoppingCart(
                new BaseModel.RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(Object result) {
                        EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.ADD_SHOPPING_CART_SUCCESS,true,Integer.valueOf(shopId),2,Integer.valueOf(buyCount)));
                    }

                    @Override
                    public void onFail(String error) {
                        EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.ADD_SHOPPING_CART_SUCCESS,false,Integer.valueOf(shopId),2,Integer.valueOf(buyCount)));
                    }
                },
                new WaiMaiShopReqData.WaiMaiShoppingCartOption(
                        new ShoppingCartOption(attrs, goods.getMealsFee(), buyCount,
                                String.valueOf(goods.getId()), goods.getName(),
                                String.valueOf(goods.getIsBargainGoods()), "1",
                                goods.getSpecialPrice(), shopId, specification, goods.getVersions())));
    }
}
