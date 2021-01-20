package com.life.waimaishuo.mvvm.vm.mall;

import com.life.waimaishuo.bean.BuyersShow;
import com.life.waimaishuo.bean.Coupon;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.MallQuickWindowData;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallMainTypeModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainBuyersShowFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainGoodShopFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallRecyclerRecommendFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainGetCouponCenterFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

public class MallMainTypeViewModel extends BaseViewModel {

    MallMainTypeModel mModel;

    @Override
    public BaseModel getModel() {
        mModel = new MallMainTypeModel();
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<BannerItem> getBannerItemList() {
        List<BannerItem> bannerItemList = new ArrayList<>();
        BannerItem temp = new BannerItem();
        temp.setImgUrl("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1004682078,2886860504&fm=26&gp=0.jpg");
        bannerItemList.add(temp);
        temp = new BannerItem();
        temp.setImgUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fssr.suning.cn%2Fshts%2Ftpl%2Fds%2F09a37d2e2b704283ab17ddfd0127df6e%2F7f5cd1852f30415bb72da3252cd682a5%2Fmodules%2Fsshop-designer-lunbotu990%2Fassets%2Fimages%2F990.jpg&refer=http%3A%2F%2Fssr.suning.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613195247&t=4e5b9cbad1347fdc2c7ea9f73aeb8f08");
        bannerItemList.add(temp);
        temp = new BannerItem();
        temp.setImgUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp7.zbjimg.com%2Ftianpeng%2F2015-11%2F14%2Fproduct%2F5646e9c2ba730.jpg&refer=http%3A%2F%2Fp7.zbjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613195247&t=e718458d251ec36488be2c7fd5e8e57b");
        bannerItemList.add(temp);
        return bannerItemList;
    }

    public List<MallQuickWindowData> getMallQuickWindowDataList() {
        List<MallQuickWindowData> list = new ArrayList<>();
        //四项对应 秒杀抢购 发现好物 每日好店 时尚配饰
        list.add(new MallQuickWindowData("秒杀抢购","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2041797039,3390821396&fm=26&gp=0.jpg","100","200",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69352f5442325a725f48585f5a524d654a6a5373706b58585847705858615f21213739333430373635332e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613204347&t=0b0b4f865e43ba18dcb4270fdd72e40d","2000","3500"));
        list.add(new MallQuickWindowData("发现好物","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2041797039,3390821396&fm=26&gp=0.jpg","100","200",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69352f5442325a725f48585f5a524d654a6a5373706b58585847705858615f21213739333430373635332e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613204347&t=0b0b4f865e43ba18dcb4270fdd72e40d","2000","3500"));
        list.add(new MallQuickWindowData("每日好店","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2041797039,3390821396&fm=26&gp=0.jpg","100","200",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69352f5442325a725f48585f5a524d654a6a5373706b58585847705858615f21213739333430373635332e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613204347&t=0b0b4f865e43ba18dcb4270fdd72e40d","2000","3500"));
        list.add(new MallQuickWindowData("时尚配饰","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2041797039,3390821396&fm=26&gp=0.jpg","100","200",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69352f5442325a725f48585f5a524d654a6a5373706b58585847705858615f21213739333430373635332e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613204347&t=0b0b4f865e43ba18dcb4270fdd72e40d","2000","3500"));
        return list;
    }

    public List<TypeDescribeValue> getStickTabList() {
        List<TypeDescribeValue> list = new ArrayList<>();
        list.add(new TypeDescribeValue("发现","猜你喜欢",""));
        list.add(new TypeDescribeValue("好店","品质精选",""));
        list.add(new TypeDescribeValue("领券中心","低价抢购",""));
        list.add(new TypeDescribeValue("买家秀","真实晒图",""));
        return list;
    }

    /**
     * viewPager title 需要与getStickTabList的type 一致
     * @param viewPagerAdapter
     */
    public void addViewPagerFragment(FragmentAdapter<BaseFragment> viewPagerAdapter) {
        MallRecyclerRecommendFragment fragment;
        fragment = new MallRecyclerRecommendFragment();
        viewPagerAdapter.addFragment(fragment,"发现");
        MallMainGoodShopFragment mallMainGoodShopFragment = new MallMainGoodShopFragment();
        mallMainGoodShopFragment.baseViewModel = this;
        viewPagerAdapter.addFragment(mallMainGoodShopFragment,"好店");
        MallMainGetCouponCenterFragment mallMainGetCouponCenterFragment = new MallMainGetCouponCenterFragment();
        mallMainGetCouponCenterFragment.baseViewModel = this;
        viewPagerAdapter.addFragment(mallMainGetCouponCenterFragment,"领券中心");
        MallMainBuyersShowFragment mallMainBuyersShowFragment = new MallMainBuyersShowFragment();
        mallMainBuyersShowFragment.baseViewModel = this;
        viewPagerAdapter.addFragment(mallMainBuyersShowFragment,"买家秀");
    }

    public List<BuyersShow> getEvaluationList() {
        String[] evaluationsImg = {"https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/c75c10385343fbf2a215c11bb17eca8065388fc8.jpg","https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/c75c10385343fbf2a215c11bb17eca8065388fc8.jpg"};
        List<BuyersShow> list = new ArrayList<>();
        list.add(new BuyersShow("没有昵称没有…","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e",
                "第一次购买，发货很快东西马上查看了，…",evaluationsImg,"1000"));
        list.add(new BuyersShow("没有昵称没有…","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e",
                "第一次购买，发货很快东西马上查看了，…",evaluationsImg,"1000"));
        list.add(new BuyersShow("没有昵称没有…","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e",
                "第一次购买，发货很快东西马上查看了，…",evaluationsImg,"1000"));
        list.add(new BuyersShow("没有昵称没有…","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e",
                "第一次购买，发货很快东西马上查看了，…",evaluationsImg,"1000"));
        return list;
    }

    public List<Coupon> getCouponDataList() {
        List<Coupon> list = new ArrayList<>();
        list.add(new Coupon(1,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1599007979,1484951390&fm=26&gp=0.jpg","正品连衣裙","9","90","拼单价 ￥99","无门槛","20","100"));
        list.add(new Coupon(1,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1599007979,1484951390&fm=26&gp=0.jpg","正品连衣裙","9","90","拼单价 ￥99","无门槛","20","100"));
        list.add(new Coupon(1,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1599007979,1484951390&fm=26&gp=0.jpg","正品连衣裙","9","90","拼单价 ￥99","无门槛","20","100"));
        list.add(new Coupon(1,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1599007979,1484951390&fm=26&gp=0.jpg","正品连衣裙","9","90","拼单价 ￥99","无门槛","20","100"));
        list.add(new Coupon(1,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1599007979,1484951390&fm=26&gp=0.jpg","正品连衣裙","9","90","拼单价 ￥99","无门槛","20","100"));
        list.add(new Coupon(1,"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1599007979,1484951390&fm=26&gp=0.jpg","正品连衣裙","9","90","拼单价 ￥99","无门槛","20","100"));
        return list;
    }

    public List<Shop> getGoodShopData() {
        List<Shop> list = new ArrayList<>();
        List<String> imgList = new ArrayList<>();
        imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        imgList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");

        Shop shop = new Shop();
        shop.setShop_name("嘉禾服装店 (国展店）");
        shop.setShop_head_portrait("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        shop.setTag_value("品牌");
        shop.setSynopsis_img(imgList);
        list.add(shop);

        shop = new Shop();
        shop.setShop_name("嘉禾服装店 (国展店）");
        shop.setShop_head_portrait("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        shop.setTag_value("品牌");
        shop.setSynopsis_img(imgList);
        list.add(shop);

        shop = new Shop();
        shop.setShop_name("嘉禾服装店 (国展店）");
        shop.setShop_head_portrait("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        shop.setTag_value("品牌");
        shop.setSynopsis_img(imgList);
        list.add(shop);
        return list;
    }
}
