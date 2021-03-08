package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.request.bean.ShoppingCartOption;
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

    public ObservableInt requestGoodsSpecificationObservable = new ObservableInt();
    public ObservableInt requestShopGoodsObservable = new ObservableInt();

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

    /**
     * 请求规格
     */
    public void requestGoodsSpecification(Goods goods) {
        mModel.requestGoodsSpecification(new BaseModel.NotifyChangeRequestCallBack(requestGoodsSpecificationObservable), goods);
    }

    /**
     * 加入购物车
     */
    public void requestAddShoppingCart(String shopId, Goods goods){
        mModel.requestAddShoppingCart(shopId,goods);
    }

    /**
     * 修改购物车数据
     */
    public void requestChangeShoppingCart(String shopId, Goods goods){
        mModel.requestChangeShoppingCart(shopId,goods);
    }
}
