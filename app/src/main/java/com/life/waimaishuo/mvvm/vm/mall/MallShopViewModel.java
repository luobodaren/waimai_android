package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import androidx.databinding.ObservableInt;

import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.base.utils.GsonUtil;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.bean.ui.LinkageGroupedItemMallShopClassification;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.bean.ui.MallNewArrival;
import com.life.waimaishuo.bean.ui.MallShopGoodGoods;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallShopModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopAllPreciousGoodsFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallRecyclerRecommendFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopClassificationFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopEvaluationFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopGoodGoodsFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopNewArrivalFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallShopRecommendFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallShopViewModel extends BaseViewModel {

    MallShopModel mModel;

    Shop shop;

    List<String> pageList = new ArrayList<>();

    public ObservableInt onShopInfoClick = new ObservableInt();
    public ObservableInt onMenuClick = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallShopModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        pageList.add("全部宝贝");
        pageList.add("商品");
        pageList.add("分类");
        pageList.add("联系客服");
    }

    public void onShopInfoClick(View view){
        onShopInfoClick.notifyChange();
    }

    public void onBackClick(View view){

    }

    public void onShareClick(View view){

    }

    public void onMenuClick(View view){
        onMenuClick.notifyChange();
    }

    public List<String> getTabDataList() {
        return pageList;
    }

    public List<BaseFragment> getTabFragment() {
        List<BaseFragment> baseFragmentList = new ArrayList<>();
        //全部宝贝
        MallShopAllPreciousGoodsFragment mallShopAllPreciousGoodsFragment = new MallShopAllPreciousGoodsFragment();
        mallShopAllPreciousGoodsFragment.baseViewModel = this;
        baseFragmentList.add(mallShopAllPreciousGoodsFragment);
        //商品
        MallRecyclerRecommendFragment mallRecyclerRecommendFragment = new MallRecyclerRecommendFragment();
        mallRecyclerRecommendFragment.baseViewModel = this;
        baseFragmentList.add(mallRecyclerRecommendFragment);
        //分类
        MallShopClassificationFragment mallShopClassificationFragment = new MallShopClassificationFragment();
        mallShopClassificationFragment.baseViewModel = this;
        baseFragmentList.add(mallShopClassificationFragment);
        //联系客服
        mallRecyclerRecommendFragment = new MallRecyclerRecommendFragment();
        mallRecyclerRecommendFragment.baseViewModel = this;
        baseFragmentList.add(mallRecyclerRecommendFragment);
        return baseFragmentList;
    }

    public String[] getAllPreciousTabTitle() {
        return new String[]{"推荐","宝贝","新品","好物","评价"};
    }

    public BaseFragment getAllPreciousTabFragment(String title) {
        if("推荐".equals(title)){
            MallShopRecommendFragment fragment = new MallShopRecommendFragment();
            fragment.baseViewModel = this;
            return fragment;
        } else if ("宝贝".equals(title)){
            return new MallRecyclerRecommendFragment();
        } else if("新品".equals(title)){
            MallShopNewArrivalFragment fragment = new MallShopNewArrivalFragment();
            fragment.baseViewModel = this;
            return fragment;
        }else if("好物".equals(title)){
            MallShopGoodGoodsFragment fragment = new MallShopGoodGoodsFragment();
            fragment.baseViewModel = this;
            return fragment;
        }else if("评价".equals(title)){
            MallShopEvaluationFragment mallShopEvaluationFragment = new MallShopEvaluationFragment();
            mallShopEvaluationFragment.baseViewModel = this;
            return mallShopEvaluationFragment;
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

    public List<Goods> getGoodsData() {
        List<Goods> list = new ArrayList<>();
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
        list.add(new Goods("欧舒丹甜蜜樱桃服装","好商品","https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=798311711,2439352606&fm=26&gp=0.jpg",1,"$378.88"));
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

    public List<BaseGroupedItem<LinkageGroupedItemMallShopClassification.ItemInfo>> getClassificationData() {
        String dataJson = "[\n" +
                "  {\n" +
                "    \"header\": \"优惠\",\n" +
                "    \"isHeader\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"isHeader\": false,\"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全家桶\"  } },\n" +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全家\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全桶\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"家桶\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"家\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"优惠\",\"title\": \"桶\"  } }," +

                "  {\"header\": \"热卖\",\"isHeader\": true}," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤全翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"热卖\",\"title\": \"烤\"  } }," +

                "  {\"header\": \"超市便利\",\"isHeader\": true}," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤全翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"全\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"翅\"  } }," +
                "  {\"isHeader\": false, \"info\": { \"price\": \"99.00\",\"describeTags\": [\"正品保证\",\"行业优质\"],\"imgUrl\":\"https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640\",\"group\": \"超市便利\",\"title\": \"烤\"  } }" +
                "]" ;
        List<LinkageGroupedItemMallShopClassification> classificationList;
        classificationList =  GsonUtil.jsonToList(dataJson,  LinkageGroupedItemMallShopClassification.class);
        return (List)classificationList;
    }

    public List<Comment> getEvaluation() {
        List<Comment> commentList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            List<ImageViewInfo> goodsPicture = new ArrayList<>();
            goodsPicture.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm.360buyimg.com%2Fmobilecms%2Fs750x750_jfs%2Ft15664%2F112%2F2098280724%2F297361%2Fb8c7a73%2F5a912a4aN819bfa82.jpg%21q80.jpg&refer=http%3A%2F%2Fm.360buyimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311786&t=e572bfbe9cd710ec7a1f8bd31d6b5ffd"));
            goodsPicture.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2059946940,1091050790&fm=26&gp=0.jpg"));
            goodsPicture.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm.360buyimg.com%2Fmobilecms%2Fs750x750_jfs%2Ft15664%2F112%2F2098280724%2F297361%2Fb8c7a73%2F5a912a4aN819bfa82.jpg%21q80.jpg&refer=http%3A%2F%2Fm.360buyimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311786&t=e572bfbe9cd710ec7a1f8bd31d6b5ffd"));
            goodsPicture.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2059946940,1091050790&fm=26&gp=0.jpg"));
            goodsPicture.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm.360buyimg.com%2Fmobilecms%2Fs750x750_jfs%2Ft15664%2F112%2F2098280724%2F297361%2Fb8c7a73%2F5a912a4aN819bfa82.jpg%21q80.jpg&refer=http%3A%2F%2Fm.360buyimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311786&t=e572bfbe9cd710ec7a1f8bd31d6b5ffd"));
            goodsPicture.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2059946940,1091050790&fm=26&gp=0.jpg"));

            Comment comment = new Comment("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx1.sinaimg.cn%2Fmw690%2F006d49NDgy1ghwabkqtshj30ku0kumym.jpg&refer=http%3A%2F%2Fwx1.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311715&t=dcbc7cb9227d453ece373af4c881bf7a",
                    "huhu同学",
                    "4",
                    "2020-12-23",
                    "小米南瓜粥非常棒，很好喝，下次还来点。小米南瓜粥非常棒，很好喝，下次还来点。",
                    goodsPicture,
                    null,
                    "谢谢亲的喜欢和支持呀，感谢您的好评！！1");
            commentList.add(comment);
        }
        return commentList;
    }

    public List<String> getCommentsType() {
        List<String> commentType = new ArrayList<>();
        commentType.add("全部");
        commentType.add("有图95");
        commentType.add("推荐100");
        commentType.add("吐槽0");
        commentType.add("赞不绝口100");
        return commentType;
    }
}
