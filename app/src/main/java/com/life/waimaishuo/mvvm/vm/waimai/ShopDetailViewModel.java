package com.life.waimaishuo.mvvm.vm.waimai;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.MemberCard;
import com.life.waimaishuo.bean.MerchantsService;
import com.life.waimaishuo.bean.Preferential;
import com.life.waimaishuo.bean.PreferentialActivity;
import com.life.waimaishuo.bean.RedPacket;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.ShoppingCartGood;
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

public class ShopDetailViewModel extends BaseViewModel {

    public BaseObservable onMembersCodeClick = new ObservableInt();
    public BaseObservable onMorePreferentialClick = new ObservableInt();
    public BaseObservable onCancelDialogClick = new ObservableInt();
    public BaseObservable onShowShoppingCart = new ObservableInt();


    ShopDetailModel model;
    Shop shop;

    @Override
    public BaseModel getModel() {
        model = new ShopDetailModel();
        return model;
    }

    @Override
    public void initData() {

    }

    /**
     * 点击会员码
     * @param view
     */
    public void onMembersCodeClick(View view){
        onMembersCodeClick.notifyChange();
    }

    /**
     * 点击了优惠
     * @param view
     */
    public void onMorePreferentialClick(View view){
        onMorePreferentialClick.notifyChange();
    }

    /**
     * 关闭底部弹出窗
     * @return
     */
    public void onCancelDialogClick(View view){
        onCancelDialogClick.notifyChange();
    }

    /**
     * 展示购物车
     * @param view
     */
    public void onShowShoppingCart(View view){onShowShoppingCart.notifyChange();}

    public List<String> getCashBackData() {
        List<String> cashBackList = new ArrayList<>();
        cashBackList.add("40减20");
        cashBackList.add("60减25");
        cashBackList.add("40减20");
        cashBackList.add("9.8折起");
        cashBackList.add("40减20");
        cashBackList.add("60减25");
        cashBackList.add("40减20");
        cashBackList.add("40减20");
        cashBackList.add("60减25");
        cashBackList.add("40减20");
        return cashBackList;
    }

    public Shop getShopDetail() {
        if(shop == null){
            shop = new Shop();
            shop.setNotice("感受一碗粥的小幸福的小幸福。");
            shop.setNumber_of_fans("21");
            shop.setSale_count_per_month("2000");
            shop.setFavorable_rate("4.5");
            shop.setShop_name("1点点奶茶店");
            shop.setShop_head_portrait("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1907944722,1971859883&fm=26&gp=0.jpg");

            MemberCard memberCard = new MemberCard();
            memberCard.setName(shop.getShop_name());
            memberCard.setDescribe("新会员注册即可享受海量优惠");
            shop.setMemberCard(memberCard);
        }
        return shop;
    }

    List<ShopTabTypeEnum> titleList = new ArrayList<>();

    public List<ShopTabTypeEnum> getRecommendedTitle() {
        titleList.add(ShopTabTypeEnum.ORDER_DISHES);
        titleList.add(ShopTabTypeEnum.EVALUATION);
        titleList.add(ShopTabTypeEnum.MERCHANT);
        return titleList;
    }

    public BaseFragment getTabBarFragment(ShopTabTypeEnum title) {
        BaseFragment baseFragment = null;
        switch (title){
            case ORDER_DISHES:
                baseFragment = new ShopOrderDishesFragment();
                break;
            case EVALUATION:
                baseFragment = new ShopEvaluationFragment();
                break;
            case MERCHANT:
                baseFragment = new ShopMerchantsInfoFragment();
                break;
        }
        return baseFragment;
    }

    private Preferential preferential;
    public Preferential getPreferentialData() {
        if(preferential == null){
            preferential = new Preferential();

            List<RedPacket> redPackets = new ArrayList<>();
            redPackets.add(new RedPacket("吃货红包","2","满30可用，2020.10.27到期",false));
            redPackets.add(new RedPacket("吃货红包","2","满30可用，2020.10.27到期",true));
            redPackets.add(new RedPacket("吃货红包","2","满30可用，2020.10.27到期",false));
            redPackets.add(new RedPacket("吃货红包","2","满30可用，2020.10.27到期",true));
            preferential.setRedPacketList(redPackets);

            List<PreferentialActivity> preferentialActivities = new ArrayList<>();
            preferentialActivities.add(new PreferentialActivity("满减","满30可用，2020.10.27到期"));
            preferentialActivities.add(new PreferentialActivity("配送","配送费立减3元"));
            preferentialActivities.add(new PreferentialActivity("折扣","折扣商品98折起"));
            preferentialActivities.add(new PreferentialActivity("特价","特价商品3元起"));
            preferential.setPreferentialActivityList(preferentialActivities);

            List<MerchantsService> merchantsServices = new ArrayList<>();
            merchantsServices.add(new MerchantsService("无忧","该商户已购买食品安全责任险，食品安全有保障"));
            merchantsServices.add(new MerchantsService("无忧","该商户已购买食品安全责任险，食品安全有保障"));
            merchantsServices.add(new MerchantsService("无忧","该商户已购买食品安全责任险，食品安全有保障"));
            merchantsServices.add(new MerchantsService("无忧","该商户已购买食品安全责任险，食品安全有保障"));
            preferential.setMerchantsServiceList(merchantsServices);

            preferential.setNotice("公告：感谢光临1点点奶茶店，温馨提示 用餐高峰期请提早20分钟下单，科技园区的小伙伴们燥起来，如果您对我们的出品有任何意见和问题，来电13147046323，我们会及时处理");
        }
        return preferential;
    }

    public List<ShoppingCartGood> getShoppingCartData() {
        List<ShoppingCartGood> list = new ArrayList<>();
        list.add(new ShoppingCartGood("现切呀沙瓜","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3557750652,71235332&fm=26&gp=0.jpg"));
        list.add(new ShoppingCartGood("现切呀沙瓜","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3557750652,71235332&fm=26&gp=0.jpg"));
        list.add(new ShoppingCartGood("现切呀沙瓜","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3557750652,71235332&fm=26&gp=0.jpg"));
        list.add(new ShoppingCartGood("现切呀沙瓜","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3557750652,71235332&fm=26&gp=0.jpg"));
        return list;
    }
}
