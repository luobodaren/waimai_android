package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.api.respon.ShopStatEvaluate;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.ShopEvaluationModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.List;

public class ShopEvaluationViewModel extends BaseViewModel {

    private ShopEvaluationModel mModel;

    public ObservableInt onRequestShopEvaluation = new ObservableInt();

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

    public ShopStatEvaluate getShopStatEvaluate(){
        return mModel.getShopStatEvaluate();
    }


    public List<Comment> getCommentsData() {
        return mModel.getCommentsList();
    }

    public List<String> getCommentsType() {
        return mModel.getCommentsType();
    }

    public void requestStatEvaluate(int shopId) {
        mModel.requestStatEvaluate(new BaseModel.RequestCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                scoreObservable.set(mModel.shopStatEvaluate.getAllGrade());
                tasteScoreObservable.set(mModel.shopStatEvaluate.getSmellGrade());
                packingScoreObservable.set(mModel.shopStatEvaluate.getPackGrade());
                deliverScoreObservable.set(mModel.shopStatEvaluate.getDistGrade());
                refreshScoreStar();
            }

            @Override
            public void onFail(String error) {

            }
        }, shopId);
    }

    /**
     * 请求店铺评价
     * @param shopId
     * @param pageNum
     * @param pageSize
     */
    public void requestShopEvaluate(int shopId, int pageNum, int pageSize, int commentTypePosition){
        mModel.requestShopEvaluate(new BaseModel.NotifyChangeRequestCallBack(onRequestShopEvaluation),shopId,pageNum,pageSize, commentTypePosition);
    }
}
