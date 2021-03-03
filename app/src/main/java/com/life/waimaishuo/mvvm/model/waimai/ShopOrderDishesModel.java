package com.life.waimaishuo.mvvm.model.waimai;

import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.respon.WaiMaiShopGoodsGroup;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDishesModel extends BaseModel {

    public List<WaiMaiShopGoodsGroup> waiMaiShopGoodsGroupList = new ArrayList<>();
    public List<BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>> shopGoodsLinkageGroupList = new ArrayList<>();

    public Goods specificationGoods;    //保存请求商品规格获取的goods信息

    public void requestShopGoodsGroupList(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GET_SHOP_GOODS_GROUP_LIST, GsonUtil.toJsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                waiMaiShopGoodsGroupList.clear();
                shopGoodsLinkageGroupList.clear();
                if (!"".equals(data)) {
                    waiMaiShopGoodsGroupList = GsonUtil.parserJsonToArrayBeans(data, WaiMaiShopGoodsGroup.class);
                    BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> baseGroupedItem;
                    for (WaiMaiShopGoodsGroup waiMaiShopGoodsGroup : waiMaiShopGoodsGroupList) {
                        waiMaiShopGoodsGroup.setGroupIcon(HttpUtils.changeToHttps(waiMaiShopGoodsGroup.getGroupIcon()));
                        baseGroupedItem = new BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>(true, waiMaiShopGoodsGroup.getGroupName()) {};
                        baseGroupedItem.info = new LinkageShopGoodsGroupedItemInfo(waiMaiShopGoodsGroup.getGroupIcon(),waiMaiShopGoodsGroup.getGroupName(),null);
                        shopGoodsLinkageGroupList.add(baseGroupedItem);
                        for (Goods goods : waiMaiShopGoodsGroup.getGoodsDeliveryListDtos()) {
                            goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                            goods.setGoodsMoreImgs(HttpUtils.changeToHttps(goods.getGoodsMoreImgs()));
                            baseGroupedItem = new BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>(
                                    new LinkageShopGoodsGroupedItemInfo(goods.getName(),
                                            waiMaiShopGoodsGroup.getGroupName(),
                                            goods)) {};
                            shopGoodsLinkageGroupList.add(baseGroupedItem);
                        }
                    }
                }
                requestCallBack.onSuccess(data);
            }

            @Override
            public void onError(int type, Throwable error) {
                LogUtil.e("requestIsCollectShop error:" + error.getMessage());
                waiMaiShopGoodsGroupList.clear();
                shopGoodsLinkageGroupList.clear();
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 请求商品规格信息
     * @param requestCallBack
     * @param reqData
     */
    public void requestGoodsSpecification(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GET_GOODS_SPECIFICATION, GsonUtil.toJsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {

                requestCallBack.onSuccess(data);
            }

            @Override
            public void onError(int type, Throwable error) {
                LogUtil.e("requestGoodsSpecification error:" + error.getMessage());
                requestCallBack.onFail(error.getMessage());
            }
        });
    }

    /**
     * 加入购物车
     */
    public void requestAddShoppingCart(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiShoppingCartOption reqData){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_ADD_SHOPPING_CART,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if("true".equals(data)){
                    requestCallBack.onSuccess(data);
                }else{
                    requestCallBack.onFail("");
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestShoppingCartData error:" + error.getMessage());
                requestCallBack.onFail("");
            }
        });
    }

    /**
     * 加入购物车
     */
    public void requestChangeShoppingCart(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiShoppingCartOption reqData){
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_CHANGE_SHOPPING_CART,GsonUtil.toJsonString(reqData),true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if("true".equals(data)){
                    requestCallBack.onSuccess(data);
                }else{
                    requestCallBack.onFail("");
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestChangeShoppingCart error:" + error.getMessage());
                requestCallBack.onFail("");
            }
        });
    }
}
