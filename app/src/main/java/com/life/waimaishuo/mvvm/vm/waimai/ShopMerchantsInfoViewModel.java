package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ShopInfoItem;
import com.life.waimaishuo.bean.api.respon.WaiMaiShopBrandStory;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopMerchantsInfoModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopMerchantsInfoViewModel extends BaseViewModel {

    private ShopMerchantsInfoModel mModel;

    public ObservableInt onRequestBrandStory = new ObservableInt();

    public ObservableField<String> shopIntroduce = new ObservableField<>();

    @Override
    public BaseModel getModel() {
        if (mModel == null) {
            mModel = new ShopMerchantsInfoModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    List<ShopInfoItem> shopInfoItemList;

    public List<ShopInfoItem> getShopInfoList(Shop shop) {
        if (shopInfoItemList == null) {

            List<Category> categories;
            if(shop.getShop_category() != null){
                categories = GsonUtil.parserJsonToArrayBeans(shop.getShop_category(), Category.class);
            }else{
                categories = new ArrayList<>();
                categories.add(new Category("未知",null));
            }

            shopInfoItemList = new ArrayList<>();
            shopInfoItemList.add(new ShopInfoItem("品牌故事", ""));
            shopInfoItemList.add(new ShopInfoItem("商家品类", categories.get(0).oneType));
            shopInfoItemList.add(new ShopInfoItem("商家地址", shop.getShop_address()));
            shopInfoItemList.add(new ShopInfoItem("商家电话", shop.getReservation_call()));
            shopInfoItemList.add(new ShopInfoItem("营业时间", shop.getEffectiveDate()));
            shopInfoItemList.add(new ShopInfoItem("营业资质", ""));
        }
        return shopInfoItemList;
    }

    public void requestBrandStory(int shopId) {
        mModel.requestBrandStory(new BaseModel.NotifyChangeRequestCallBack(onRequestBrandStory), shopId);
    }

    public WaiMaiShopBrandStory getBrandStory(){
        return mModel.brandStory;
    }

    static class Category {
        String oneType;
        String[] twoType;

        public Category(String oneType, String[] twoType) {
            this.oneType = oneType;
            this.twoType = twoType;
        }
    }
}
