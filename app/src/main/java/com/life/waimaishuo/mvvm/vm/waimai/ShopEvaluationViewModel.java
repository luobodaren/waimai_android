package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.ShopEvaluation;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopEvaluationModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopEvaluationViewModel extends BaseViewModel {

    private ShopEvaluationModel mModel;

    public ObservableField<String> scoreObservable = new ObservableField<>();
    public ObservableField<String> tasteScoreObservable = new ObservableField<>();
    public ObservableField<String> packingScoreObservable = new ObservableField<>();
    public ObservableField<String> deliverScoreObservable = new ObservableField<>();

    public BaseObservable refreshScoreStar = new BaseObservable();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new ShopEvaluationModel();
        }
        return mModel;
    }

    @Override
    public void initData() {
        scoreObservable.set("未知");
        tasteScoreObservable.set("未知");
        packingScoreObservable.set("未知");
        deliverScoreObservable.set("未知");
    }

    /**
     * 刷新评分星星
     */
    public void refreshScoreStar(){
        refreshScoreStar.notifyChange();
    }

    private ShopEvaluation mShopEvalution;

    public ShopEvaluation getShopEvaluation(){
        if(mShopEvalution == null){
            List<ImageViewInfo> goodsPicture = new ArrayList<>();
            goodsPicture.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm.360buyimg.com%2Fmobilecms%2Fs750x750_jfs%2Ft15664%2F112%2F2098280724%2F297361%2Fb8c7a73%2F5a912a4aN819bfa82.jpg%21q80.jpg&refer=http%3A%2F%2Fm.360buyimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311786&t=e572bfbe9cd710ec7a1f8bd31d6b5ffd"));
            goodsPicture.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2059946940,1091050790&fm=26&gp=0.jpg"));
            goodsPicture.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm.360buyimg.com%2Fmobilecms%2Fs750x750_jfs%2Ft15664%2F112%2F2098280724%2F297361%2Fb8c7a73%2F5a912a4aN819bfa82.jpg%21q80.jpg&refer=http%3A%2F%2Fm.360buyimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311786&t=e572bfbe9cd710ec7a1f8bd31d6b5ffd"));
            goodsPicture.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2059946940,1091050790&fm=26&gp=0.jpg"));
            goodsPicture.add(new ImageViewInfo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fm.360buyimg.com%2Fmobilecms%2Fs750x750_jfs%2Ft15664%2F112%2F2098280724%2F297361%2Fb8c7a73%2F5a912a4aN819bfa82.jpg%21q80.jpg&refer=http%3A%2F%2Fm.360buyimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311786&t=e572bfbe9cd710ec7a1f8bd31d6b5ffd"));
            goodsPicture.add(new ImageViewInfo("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2059946940,1091050790&fm=26&gp=0.jpg"));

            List<String> goodsTypeList = new ArrayList<>();
            goodsTypeList.add("小米南瓜粥");
            goodsTypeList.add("小粉冬瓜顿排骨");
            goodsTypeList.add("大粉冬瓜顿鸡");

            List<Comment> commentList = new ArrayList<>();
            for(int i = 0; i < 5; i++){
                Comment comment = new Comment("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx1.sinaimg.cn%2Fmw690%2F006d49NDgy1ghwabkqtshj30ku0kumym.jpg&refer=http%3A%2F%2Fwx1.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611311715&t=dcbc7cb9227d453ece373af4c881bf7a",
                        "huhu同学",
                        "4",
                        "2020-12-23",
                        "小米南瓜粥非常棒，很好喝，下次还来点。小米南瓜粥非常棒，很好喝，下次还来点。",
                        goodsPicture,
                        goodsTypeList,"");
                commentList.add(comment);
            }

            mShopEvalution = new ShopEvaluation("4.9","4.9","4.9","4.9",commentList);

            scoreObservable.set(mShopEvalution.getScore());
            tasteScoreObservable.set(mShopEvalution.getTasteScore());
            packingScoreObservable.set(mShopEvalution.getPackingScore());
            deliverScoreObservable.set(mShopEvalution.getDeliverScore());
        }
        return mShopEvalution;
    }

    public List<Comment> getCommentsData() {
        return getShopEvaluation().getCommentsList();
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
