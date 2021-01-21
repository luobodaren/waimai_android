package com.life.waimaishuo.mvvm.vm.mall;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.MallGoods;
import com.life.waimaishuo.bean.ui.MallShoppingCartItemData;
import com.life.waimaishuo.bean.ui.MallShoppingCartShopGoods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallShoppingCartModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallShoppingCartViewModel extends BaseViewModel {

    private MallShoppingCartModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallShoppingCartModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<MallShoppingCartItemData> getShoppingCartData() {
        List<MallShoppingCartItemData> mallShoppingCartItemDataArrayList = new ArrayList<>();
        List<MallShoppingCartShopGoods> mallShoppingCartShopGoodsArrayList = new ArrayList<>();

        Shop shop = new Shop();
        shop.setShop_name("美衣旗舰店");
        MallGoods goods = new MallGoods();
        goods.setGoodsName("春夏新款绣花桑蚕丝真丝连衣裙女装");
        goods.setGoodsImgUrl("https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640");
        goods.setGoodsPrice("18.99");
        String[] strings = {"白色，L","白色，L"};

        MallShoppingCartShopGoods mallShoppingCartShopGoods =
                new MallShoppingCartShopGoods(false,goods,3,strings,
                        "白色，L",
                        "限时抢购6月24日10:00结束\n限购5件",
                        "比上次加入降价￥1.2");
        mallShoppingCartShopGoodsArrayList.add(mallShoppingCartShopGoods);
        MallShoppingCartItemData mallShoppingCartItemData1 = new MallShoppingCartItemData(shop,false,mallShoppingCartShopGoodsArrayList,true);
        mallShoppingCartItemDataArrayList.add(mallShoppingCartItemData1);

        mallShoppingCartShopGoodsArrayList = new ArrayList<>();
        mallShoppingCartShopGoods =
                new MallShoppingCartShopGoods(true,goods,10,strings,
                        "白色，L",
                        "限时抢购6月24日10:00结束\n限购5件",
                        "");
        mallShoppingCartShopGoodsArrayList.add(mallShoppingCartShopGoods);
        mallShoppingCartShopGoods =
                new MallShoppingCartShopGoods(true,goods,1,strings,
                        "白色，L",
                        "限时抢购6月24日10:00结束\n限购5件",
                        "比上次加入降价￥1.2");
        mallShoppingCartShopGoodsArrayList.add(mallShoppingCartShopGoods);
        mallShoppingCartShopGoods =
                new MallShoppingCartShopGoods(true,goods,1,strings,
                        "白色，L",
                        "",
                        "比上次加入降价￥1.2");
        mallShoppingCartShopGoodsArrayList.add(mallShoppingCartShopGoods);
        mallShoppingCartItemData1 = new MallShoppingCartItemData(shop,true,mallShoppingCartShopGoodsArrayList,true);
        mallShoppingCartItemDataArrayList.add(mallShoppingCartItemData1);

        mallShoppingCartShopGoodsArrayList = new ArrayList<>();
        mallShoppingCartShopGoods =
                new MallShoppingCartShopGoods(true,goods,4,strings,
                        "白色，L",
                        "限时抢购6月24日10:00结束\n限购5件",
                        "比上次加入降价￥1.2");
        mallShoppingCartShopGoodsArrayList.add(mallShoppingCartShopGoods);
        mallShoppingCartShopGoods =
                new MallShoppingCartShopGoods(true,goods,10,strings,
                        "",
                        "限时抢购6月24日10:00结束\n限购5件",
                        "");
        mallShoppingCartShopGoodsArrayList.add(mallShoppingCartShopGoods);
        mallShoppingCartItemData1 = new MallShoppingCartItemData(shop,false,mallShoppingCartShopGoodsArrayList,false);
        mallShoppingCartItemDataArrayList.add(mallShoppingCartItemData1);
        return mallShoppingCartItemDataArrayList;
    }
}
