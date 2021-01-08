package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.ShopInfoItem;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopMerchantsInfoModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopMerchantsInfoViewModel extends BaseViewModel {

    private ShopMerchantsInfoModel mModel;

    public ObservableField<String> shopIntroduce = new ObservableField<>();

    public ObservableInt refreshIntroduceData = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new ShopMerchantsInfoModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        refreshIntroduce(); // FIXME: 2020/12/24 暂时在这里进行数据插入
    }

    private void refreshIntroduce(){
        shopIntroduce.set("感受一碗粥的小幸福的小幸福。感受一碗粥的小幸福的小幸福。感受一碗粥的小幸福的小幸福。");

        refreshIntroduceData.notifyChange();
    }

    public List<ImageViewInfo> getShopIntroducePictureUrl() {
        List<ImageViewInfo> imgUrl = new ArrayList<>();
        imgUrl.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69362f5442316d6676746e72355942754e6a5373706f585862654e4658615f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611382905&t=71c9e5ee3a1472129d6cbaa9aa7f1060"));
        imgUrl.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg007.hc360.cn%2Fk2%2FM06%2F99%2FF6%2FwKhQxVpRVb-EPQF_AAAAAEiFN_0044.jpg&refer=http%3A%2F%2Fimg007.hc360.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611382905&t=288ef5c8600bc70d2246caed5e3d73b9"));
        imgUrl.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.szthks.com%2Flocalimg%2F687474703a2f2f6777312e616c6963646e2e636f6d2f62616f2f75706c6f616465642f69372f54316c6c5656464a566158585858585858585f2121302d6974656d5f7069632e6a7067.jpg&refer=http%3A%2F%2Fwww.szthks.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611382977&t=316f3ec3a08eb3224e5f97cb690aa16e"));
        imgUrl.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fgw.alicdn.com%2Fbao%2Fuploadedhttps%3A%2F%2Fimg.alicdn.com%2Fbao%2Fuploaded%2Fi4%2F1924839682%2FO1CN01n0D3572LOPGMjZAQz_%21%211924839682.jpg&refer=http%3A%2F%2Fgw.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611382993&t=7307b65f767da2a54e14486bdce6b0df"));
        imgUrl.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1301113993,865656943&fm=26&gp=0.jpg"));
        return imgUrl;
    }

    public List<ShopInfoItem> getShopInfoList() {
        List<ShopInfoItem> shopInfoItemList = new ArrayList<>();
        shopInfoItemList.add(new ShopInfoItem("品牌故事",""));
        shopInfoItemList.add(new ShopInfoItem("商家品类","粥店/其他快餐"));
        shopInfoItemList.add(new ShopInfoItem("商家地址","广州市朝阳区东风中路258号1109-2220"));
        shopInfoItemList.add(new ShopInfoItem("商家电话","101-23456789"));
        shopInfoItemList.add(new ShopInfoItem("营业时间","06:30-22:00"));
        shopInfoItemList.add(new ShopInfoItem("营业资质",""));
        return shopInfoItemList;
    }
}
