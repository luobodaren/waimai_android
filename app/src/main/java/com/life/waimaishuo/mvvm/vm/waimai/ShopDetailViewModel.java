package com.life.waimaishuo.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.bean.api.respon.WaiMaiShoppingCart;
import com.life.waimaishuo.enumtype.ShopTabTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopDetailModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.ShopEvaluationFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.ShopMerchantsInfoFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.ShopOrderDishesFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailViewModel extends BaseShopDetailViewModel {

    public ObservableInt onRequestShopInfoObservable = new ObservableInt();
    public ObservableInt onRequestIsJoinShopFansObservable = new ObservableInt();
    public ObservableInt onRequestJoinShopFansObservable = new ObservableInt();
    public ObservableInt onRequestIsCollectShopObservable = new ObservableInt();
    public ObservableInt onRequestCollectOrCancelShopObservable = new ObservableInt();
    public ObservableInt onRequestShoppingCartObservable = new ObservableInt();

    public BaseObservable onMembersCodeClick = new ObservableInt();
    public BaseObservable onMorePreferentialClick = new ObservableInt();
    public BaseObservable onCancelDialogClick = new ObservableInt();
    public BaseObservable onBackClick = new ObservableInt();
    public BaseObservable onCollectClick = new ObservableInt();
    public BaseObservable onShareClick = new ObservableInt();

    private ShopDetailModel model;

    @Override
    public BaseModel getModel() {
        model = new ShopDetailModel();
        return model;
    }

    @Override
    public void initData() {

    }


    public void onBackClick(View view) {
        onBackClick.notifyChange();
    }

    public void onCollectClick(View view) {
        onCollectClick.notifyChange();
    }

    public void onShareClick(View view) {
        onShareClick.notifyChange();
    }

    /**
     * 点击会员码
     *
     * @param view
     */
    public void onMembersCodeClick(View view) {
        onMembersCodeClick.notifyChange();
    }

    /**
     * 点击了优惠
     *
     * @param view
     */
    public void onMorePreferentialClick(View view) {
        onMorePreferentialClick.notifyChange();
    }

    /**
     * 关闭底部弹出窗
     *
     * @return
     */
    public void onCancelDialogClick(View view) {
        onCancelDialogClick.notifyChange();
    }


    /**
     * 请求店铺信息
     * @param shopId
     */
    public void requestShopInfo(int shopId) {
        model.requestShopInfo(new BaseModel.NotifyChangeRequestCallBack(onRequestShopInfoObservable), new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId));
    }

    public Shop getShopDetail() {
        return model.shop;
    }

    /**
     * 请求是否加入了会员
     * @param shopId
     */
    public void requestIsJoinShopFans(int shopId){
        model.requestIsJoinShopFans(new BaseModel.NotifyChangeRequestCallBack(onRequestIsJoinShopFansObservable),new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId));
    }

    /**
     * 请求是否收藏店铺
     * @param shopId
     */
    public void requestIsCollectShop(int shopId) {
        model.requestIsCollectShop(new BaseModel.NotifyChangeRequestCallBack(onRequestIsCollectShopObservable),new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId));
    }

    /**
     * 加入会员
     */
    public void joinShopFans(int shopId) {
        model.joinInShopFans(new BaseModel.NotifyChangeRequestCallBack(onRequestJoinShopFansObservable),new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId));
    }

    public void doOrCancelCollectShop(int shopId){
        model.doOrCancelCollectShop(new BaseModel.NotifyChangeRequestCallBack(onRequestCollectOrCancelShopObservable),shopId);
    }

    /**
     * 获取会员加入状态
     */
    public boolean isJoinShopFans(){
        return model.isJoinShopFans;
    }

    /**
     * 获取收藏状态
     * @return
     */
    public boolean isCollectShop(){
        return model.isCollectShop;
    }

    /**
     * 请求购物车信息
     */
    public void requestShoppingCart(int shopId){
        model.requestShoppingCart(new BaseModel.NotifyChangeRequestCallBack(onRequestShoppingCartObservable),new WaiMaiShopReqData.WaiMaiSimpleReqData(shopId));
    }

    /**
     * 清空购物车
     * @param shopId
     */
    public void requestDelShoppingCartList(int shopId) {
        model.requestDelShoppingCartList(shopId);
    }

    List<ShopTabTypeEnum> titleList = new ArrayList<>();
    public List<ShopTabTypeEnum> getRecommendedTitle() {
        titleList.add(ShopTabTypeEnum.ORDER_DISHES);
        titleList.add(ShopTabTypeEnum.EVALUATION);
        titleList.add(ShopTabTypeEnum.MERCHANT);
        return titleList;
    }

    public BaseFragment getTabBarFragment(ShopTabTypeEnum title, int shopId) {
        BaseFragment baseFragment = null;
        switch (title) {
            case ORDER_DISHES:
                ShopOrderDishesFragment shopOrderDishesFragment = new ShopOrderDishesFragment();
                shopOrderDishesFragment.setShop(model.shop);
                shopOrderDishesFragment.waiMaiShoppingCart = model.waiMaiShoppingCart;
                baseFragment = shopOrderDishesFragment;
                break;
            case EVALUATION:
                ShopEvaluationFragment shopEvaluationFragment = new ShopEvaluationFragment();
                shopEvaluationFragment.setShopId(model.shop.getShopId());
                baseFragment = shopEvaluationFragment;
                break;
            case MERCHANT:
                ShopMerchantsInfoFragment shopMerchantsInfoFragment = new ShopMerchantsInfoFragment();
                shopMerchantsInfoFragment.setShop(model.shop);
                baseFragment = shopMerchantsInfoFragment;
                break;
        }
        return baseFragment;
    }

    public WaiMaiShoppingCart getWaiMaiShoppingCart(){
        return model.waiMaiShoppingCart;
    }

    public String getQrCode(){
        return model.shopMemberQRCode;
    }

}
