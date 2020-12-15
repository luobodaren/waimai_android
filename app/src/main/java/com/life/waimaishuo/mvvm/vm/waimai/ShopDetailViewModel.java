package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.MemberCard;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.enumtype.ShopTabType;
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
            shop.setAnnouncement("感受一碗粥的小幸福的小幸福。");
            shop.setNumber_of_fans("21");
            shop.setSale_count_per_month("2000");
            shop.setScore(4.5f);
            shop.setShopName("1点点奶茶店");
            shop.setShopIcon("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1907944722,1971859883&fm=26&gp=0.jpg");

            MemberCard memberCard = new MemberCard();
            memberCard.setName(shop.getShopName());
            memberCard.setDescribe("新会员注册即可享受海量优惠");
            shop.setMemberCard(memberCard);
        }
        return shop;
    }

    List<ShopTabType> titleList = new ArrayList<>();

    public List<ShopTabType> getRecommendedTitle() {
        titleList.add(ShopTabType.ORDER_DISHES);
        titleList.add(ShopTabType.EVALUATION);
        titleList.add(ShopTabType.MERCHANT);
        return titleList;
    }

    public BaseFragment getTabBarFragment(ShopTabType title) {
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
