package com.life.waimaishuo.mvvm.vm.waimai;

import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiGoodsEvaluationModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiGoodsEvaluationViewModel extends BaseViewModel {

    WaiMaiGoodsEvaluationModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiGoodsEvaluationModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<Comment> getTopFiveComment() {
        List<Comment> commentList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Comment comment = new Comment();
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
        commentType.add("推荐100");
        commentType.add("吐槽0");
        commentType.add("赞不绝口100");
        return commentType;
    }
}
