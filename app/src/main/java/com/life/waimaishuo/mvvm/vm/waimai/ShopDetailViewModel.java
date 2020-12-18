package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.MemberCard;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.enumtype.ShopTabTypeEnum;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopDetailModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.ShopOrderDishesFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailViewModel extends BaseViewModel {

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
                baseFragment = new ShopOrderDishesFragment();
                break;
            case MERCHANT:
                baseFragment = new ShopOrderDishesFragment();
                break;
        }
        return baseFragment;
    }

    public String[] getBannerSource() {
        return new String[]{"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2515911597,1913645471&fm=26&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=172347525,3232800407&fm=26&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2755313968,2418553549&fm=26&gp=0.jpg"};
    }
}
