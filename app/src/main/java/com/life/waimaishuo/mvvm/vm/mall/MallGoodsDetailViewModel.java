package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallGoodsDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

public class MallGoodsDetailViewModel extends BaseViewModel {


    public ObservableInt preferentialClickObservable = new ObservableInt();
    public ObservableInt serviceClickObservable = new ObservableInt();
    public ObservableInt specificationsClickObservable = new ObservableInt();
    public ObservableInt shippingAddressClickObservable = new ObservableInt();

    public ObservableField<String> onGoToShopPageObservable = new ObservableField<>();

    public ObservableInt onCustomServiceClick = new ObservableInt();
    public ObservableInt onShopClick = new ObservableInt();
    public ObservableInt onShoppingCartClick = new ObservableInt();
    public ObservableInt onAddShoppingCartClick = new ObservableInt();
    public ObservableInt onBuyNowClick = new ObservableInt();

    private MallGoodsDetailModel mModel;

    private Goods mGoods;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallGoodsDetailModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void onPreferentialClick(View view){
        preferentialClickObservable.notifyChange();
    }
    public void onServiceClick(View view){
        serviceClickObservable.notifyChange();
    }
    public void onSpecificationsClick(View view){
        specificationsClickObservable.notifyChange();
    }
    public void onShippingAddressClick(View view){
        shippingAddressClickObservable.notifyChange();
    }

    public void onCustomServiceClick(View view){
        onCustomServiceClick.notifyChange();
    }
    public void onShopClick(View view){
        onShopClick.notifyChange();
    }
    public void onShoppingCartClick(View view){
        onShoppingCartClick.notifyChange();
    }
    public void onAddShoppingCartClick(View view){
        onAddShoppingCartClick.notifyChange();
    }
    public void onBuyNowClick(View view){
        onBuyNowClick.notifyChange();
    }

    public void onGoToShopPageClick(View view){
        onGoToShopPageObservable.notifyChange();
    }

    public void setGoods(Goods goods) {
        this.mGoods = goods;
    }

    public Goods getGoods() {
        return mGoods;
    }

    public List<Comment> getTopTwoComment() {
        List<Comment> commentList = new ArrayList<>();
        for(int i = 0; i < 2; i++){
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

    public List<Address> getAddressList() { // FIXME: 2021/1/22 添加“失效地址整理”有序排序
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",true,true));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,true));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,true));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,true));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,false));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,false));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,false));
        addresses.add(new Address("的撒合法破伤风啊大师风范就","的撒发","13715714099",false,false));
        return addresses;
    }
}
