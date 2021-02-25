package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableInt;

import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopOrderDishesModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.kunminx.linkage.bean.BaseGroupedItem;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDishesViewModel extends BaseViewModel {

    private ShopOrderDishesModel mModel;

    public ObservableInt requestShopGoodsObservable = new ObservableInt();

    List<LinkageShopGoodsGroupedItemInfo> shopGoodsLinkageGroupList = new ArrayList<>();

    @Override
    public BaseModel getModel() {
        mModel = new ShopOrderDishesModel();
        return mModel;
    }

    @Override
    public void initData() {

    }

    public String[] getBannerSource() {
        return new String[]{"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2515911597,1913645471&fm=26&gp=0.jpg",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=172347525,3232800407&fm=26&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2755313968,2418553549&fm=26&gp=0.jpg"};
    }

    public List<BaseGroupedItem<LinkageShopGoodsGroupedItemInfo>> getShopGoodsItems() {
        //shopGoodsLinkageGroupList = GsonUtil.jsonToList(dataJson, LinkageShopGoodsGroupedItemInfo.class);
        return (List) shopGoodsLinkageGroupList;
    }

    public String[] getGoodsSpecificationsInfo(int goodsId) {
        return new String[]{"口味","口味","规格","规格"};
    }
}
