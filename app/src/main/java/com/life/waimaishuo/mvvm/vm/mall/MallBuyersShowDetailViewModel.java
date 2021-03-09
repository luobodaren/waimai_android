package com.life.waimaishuo.mvvm.vm.mall;

import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallBuyerShowDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;

import java.util.ArrayList;
import java.util.List;

public class MallBuyersShowDetailViewModel extends BaseViewModel {

    private MallBuyerShowDetailModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallBuyerShowDetailModel();
        }
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

    public List<Comment> getCommentData() {
        List<Comment> commentList = new ArrayList<>();
        return commentList;
    }
}
