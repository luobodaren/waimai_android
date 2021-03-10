package com.life.waimaishuo.mvvm.model.waimai;

import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.base.utils.GsonUtil;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.GoodsShoppingCart;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.request.bean.ShoppingCartOption;
import com.life.waimaishuo.bean.api.respon.WaiMaiShopGoodsGroup;
import com.life.waimaishuo.bean.api.respon.WaiMaiShoppingCart;
import com.life.waimaishuo.bean.event.ShoppingCartOptionEvent;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.constant.ApiConstant;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.util.net.HttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDishesModel extends BaseModel {

    public List<WaiMaiShopGoodsGroup> waiMaiShopGoodsGroupList = new ArrayList<>();
    public List<BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>> shopGoodsLinkageGroupList = new ArrayList<>();

    public void requestShopGoodsGroupList(RequestCallBack<Object> requestCallBack, WaiMaiShopReqData.WaiMaiSimpleReqData reqData) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GET_SHOP_GOODS_GROUP_LIST, GsonUtil.gsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                waiMaiShopGoodsGroupList.clear();
                shopGoodsLinkageGroupList.clear();
                if (!"".equals(data)) {
                    waiMaiShopGoodsGroupList = GsonUtil.parserJsonToArrayBeans(data, WaiMaiShopGoodsGroup.class);
                    BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> baseGroupedItem;
                    for (WaiMaiShopGoodsGroup waiMaiShopGoodsGroup : waiMaiShopGoodsGroupList) {
                        waiMaiShopGoodsGroup.setGroupIcon(HttpUtils.changeToHttps(waiMaiShopGoodsGroup.getGroupIcon()));
                        baseGroupedItem = new BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>(true, waiMaiShopGoodsGroup.getGroupName()) {
                        };
                        baseGroupedItem.info = new LinkageShopGoodsGroupedItemInfo(waiMaiShopGoodsGroup.getGroupIcon(), waiMaiShopGoodsGroup.getGroupName(), null);
                        shopGoodsLinkageGroupList.add(baseGroupedItem);
                        for (Goods goods : waiMaiShopGoodsGroup.getGoodsDeliveryListDtos()) {
                            goods.setGoodsImgUrl(HttpUtils.changeToHttps(goods.getGoodsImgUrl()));
                            goods.setGoodsMoreImgs(HttpUtils.changeToHttps(goods.getGoodsMoreImgs()));
                            baseGroupedItem = new BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>(
                                    new LinkageShopGoodsGroupedItemInfo(goods.getName(),
                                            waiMaiShopGoodsGroup.getGroupName(),
                                            goods)) {
                            };
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
     *
     * @param requestCallBack
     */
    public void requestGoodsSpecification(RequestCallBack<Object> requestCallBack, Goods goods) {
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_GET_GOODS_SPECIFICATION, GsonUtil.gsonString(new WaiMaiShopReqData.WaiMaiSimpleReqData(goods.getId())), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if (!"".equals(data)) {
                    Goods requestGoods = GsonUtil.parserJsonToBean(data, Goods.class);
                    if (requestGoods != null) {
                        boolean isFindGoods = false;
                        for (WaiMaiShopGoodsGroup waiMaiShopGoodsGroup : waiMaiShopGoodsGroupList) {
                            for (Goods goods1 : waiMaiShopGoodsGroup.getGoodsDeliveryListDtos()) {
                                if (goods.getId() == goods1.getId()) {
                                    isFindGoods = true;
                                    goods1.setAttributeList(requestGoods.getAttributeList());
                                    goods1.setSpecificationList(requestGoods.getSpecificationList());
                                    break;
                                }
                            }
                            if (isFindGoods) {
                                break;
                            }
                        }
                    }

                    requestCallBack.onSuccess(data);
                } else {
                    requestCallBack.onFail(data);
                }
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
    public void requestAddShoppingCart(String shopId, Goods goods) {
        WaiMaiShopReqData.WaiMaiShoppingCartOption reqData = new WaiMaiShopReqData.WaiMaiShoppingCartOption(
                new ShoppingCartOption(goods.getAttrs(), goods.getMealsFee(), goods.getWantBuyCount(),
                        String.valueOf(goods.getId()), goods.getName(),goods.getGoodsImgUrl(),
                        String.valueOf(goods.getIsBargainGoods()), "1",
                        goods.getSpecialPrice(), shopId, goods.getSpecSelected(), goods.getVersions()));

        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_ADD_SHOPPING_CART, GsonUtil.gsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if ("true".equals(data)) {
                    EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.SHOPPING_CART_ADD_SUCCESS,"",goods.getId(),1,Integer.valueOf(goods.getWantBuyCount())));
                } else {
                    EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.SHOPPING_CART_ADD_FALSE,"",goods.getId(),1,Integer.valueOf(goods.getWantBuyCount())));
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestAddShoppingCart error:" + error.getMessage());
                EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.SHOPPING_CART_ADD_FALSE,error.getMessage(),goods.getId(),1,Integer.valueOf(goods.getWantBuyCount())));
            }
        });
    }

    /**
     * 修改购物车
     */
    public void requestChangeShoppingCart(String shopId, Goods goods) {
        LogUtil.d(goods.toString());
        WaiMaiShopReqData.WaiMaiShoppingCartOption reqData = new WaiMaiShopReqData.WaiMaiShoppingCartOption(
                new ShoppingCartOption(goods.getAttrs(), goods.getMealsFee(), goods.getWantBuyCount(),
                        String.valueOf(goods.getId()), goods.getName(),goods.getGoodsImgUrl(),
                        String.valueOf(goods.getIsBargainGoods()), "1",
                        goods.getSpecialPrice(), shopId, goods.getSpecSelected(), goods.getVersions()));
        HttpUtils.getHttpUtils().doPostJson(ApiConstant.DOMAIN_NAME + ApiConstant.API_WAIMAI_CHANGE_SHOPPING_CART, GsonUtil.gsonString(reqData), true, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                if ("true".equals(data)) {
                    EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.SHOPPING_CART_CHANGE_SUCCESS,"",goods.getId(),2,Integer.valueOf(goods.getWantBuyCount())));
                } else {
                    EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.SHOPPING_CART_CHANGE_FALSE,"",goods.getId(),2,Integer.valueOf(goods.getWantBuyCount())));
                }
            }

            @Override
            public void onError(int errorType, Throwable error) {
                LogUtil.e("requestChangeShoppingCart error:" + error.getMessage());
                EventBus.getDefault().post(new ShoppingCartOptionEvent(MessageCodeConstant.SHOPPING_CART_CHANGE_FALSE,error.getMessage(),goods.getId(),2,Integer.valueOf(goods.getWantBuyCount())));
            }
        });
    }

}
