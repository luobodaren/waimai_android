package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallGoodsDetailModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallGoodsDetailViewModel extends BaseViewModel {

    public ObservableField<String> onGoToShopPageObservable = new ObservableField<>();

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
}
