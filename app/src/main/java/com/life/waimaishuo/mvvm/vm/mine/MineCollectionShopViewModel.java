package com.life.waimaishuo.mvvm.vm.mine;

import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mine.MineCollectionShopModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MineCollectionShopViewModel extends BaseViewModel {

    private MineCollectionShopModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MineCollectionShopModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<Shop> getShopCollectionData() {
        List<Shop> shopList = new ArrayList<>();
        Shop shop = new Shop();
        shop.setShop_name("嘉禾服装店 (国展店）");
        shop.setShop_head_portrait("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        shopList.add(shop);

        shop = new Shop();
        shop.setShop_name("嘉禾服装店 (国展店）");
        shop.setShop_head_portrait("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        shopList.add(shop);

        return shopList;
    }
}
