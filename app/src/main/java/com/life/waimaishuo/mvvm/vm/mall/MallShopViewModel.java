package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.bean.ui.MallNewArrival;
import com.life.waimaishuo.bean.ui.MallShopGoodGoods;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallShopModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallRecommendFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopGoodGoodsFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopNewArrivalFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopRecommendFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallShopViewModel extends BaseViewModel {

    MallShopModel mModel;

    Shop shop;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallShopModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void onBackClick(View view){

    }

    public void onShareClick(View view){

    }

    public void onMenuClick(View view){

    }

    public String[] getTabTitle() {
        return new String[]{"推荐","宝贝","新品","好物","买家秀"};
    }

    public BaseFragment getTabFragment(String title) {
        if("推荐".equals(title)){
            MallShopRecommendFragment fragment = new MallShopRecommendFragment();
            fragment.baseViewModel = this;
            return fragment;
        } else if ("宝贝".equals(title)){
            return new MallRecommendFragment();
        } else if("新品".equals(title)){
            MallShopNewArrivalFragment fragment = new MallShopNewArrivalFragment();
            fragment.baseViewModel = this;
            return fragment;
        }else if("好物".equals(title)){
            MallShopGoodGoodsFragment fragment = new MallShopGoodGoodsFragment();
            fragment.baseViewModel = this;
            return fragment;
        }else if("买家秀".equals(title)){
            return new MallRecommendFragment();
        }
        return null;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ImageUrlNameData> getGoodsTypeData() {
        List<ImageUrlNameData> list = new ArrayList<>();
        list.add(new ImageUrlNameData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69312f5442316b6b2e52475858585858586f5858585858585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613372975&t=48bc5c04dbcb5dafa74432e7b141a020","abcd"));
        list.add(new ImageUrlNameData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69312f5442316b6b2e52475858585858586f5858585858585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613372975&t=48bc5c04dbcb5dafa74432e7b141a020","abcd"));
        list.add(new ImageUrlNameData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69312f5442316b6b2e52475858585858586f5858585858585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613372975&t=48bc5c04dbcb5dafa74432e7b141a020","abcd"));
        list.add(new ImageUrlNameData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69312f5442316b6b2e52475858585858586f5858585858585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613372975&t=48bc5c04dbcb5dafa74432e7b141a020","abcd"));
        list.add(new ImageUrlNameData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69312f5442316b6b2e52475858585858586f5858585858585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613372975&t=48bc5c04dbcb5dafa74432e7b141a020","abcd"));
        list.add(new ImageUrlNameData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69312f5442316b6b2e52475858585858586f5858585858585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613372975&t=48bc5c04dbcb5dafa74432e7b141a020","abcd"));
        return list;
    }

    public String getMainImgUrl() {
        return "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2638387768,1373713136&fm=26&gp=0.jpg";
    }

    public List<TypeDescribeValue> getGoodsData() {
        List<TypeDescribeValue> list = new ArrayList<>();
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        return list;
    }

    public List<MallNewArrival> getNewArrivalData() {
        List<MallNewArrival> list = new ArrayList<>();

        List<TypeDescribeValue> list1 = new ArrayList<>();  //最多显示三个
        list1.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list1.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        list1.add(new TypeDescribeValue("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg","欧舒丹甜蜜樱桃服装","$378.88"));
        MallNewArrival mallNewArrival = new MallNewArrival("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=118193154,2398332685&fm=26&gp=0.jpg",
                "2021春夏上新系列","春夏上新",list1);
        list.add(mallNewArrival);
        mallNewArrival = new MallNewArrival("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=118193154,2398332685&fm=26&gp=0.jpg",
                "2021春夏上新系列","春夏上新",list1);
        list.add(mallNewArrival);
        mallNewArrival = new MallNewArrival("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=118193154,2398332685&fm=26&gp=0.jpg",
                "2021春夏上新系列","春夏上新",list1);
        list.add(mallNewArrival);
        return list;
    }

    public List<MallShopGoodGoods> getGoodGoodsData() {
        List<MallShopGoodGoods> list = new ArrayList<>();

        List<String> imgUrlList = new ArrayList<>();    //最多显示三个
        imgUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        imgUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");
        imgUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg");

        list.add(new MallShopGoodGoods("picnic 苹果斜挎包","1581",null,imgUrlList));
        list.add(new MallShopGoodGoods("picnic 苹果斜挎包","1581",null,imgUrlList));
        list.add(new MallShopGoodGoods("picnic 苹果斜挎包","1581",null,imgUrlList));
        list.add(new MallShopGoodGoods("picnic 苹果斜挎包","1581",null,imgUrlList));
        return list;
    }
}
