package com.life.waimaishuo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BuyersShow implements Parcelable {

    String userName;
    String userIconUrl;
    String evaluation;
    String[] evaluationImgUrl;
    String likeCount;//点赞数

    public BuyersShow(String userName, String userIconUrl, String evaluation, String[] evaluationImgUrl, String likeCount) {
        this.userName = userName;
        this.userIconUrl = userIconUrl;
        this.evaluation = evaluation;
        this.evaluationImgUrl = evaluationImgUrl;
        this.likeCount = likeCount;
    }

    protected BuyersShow(Parcel in) {
        userName = in.readString();
        userIconUrl = in.readString();
        evaluation = in.readString();
        evaluationImgUrl = in.createStringArray();
        likeCount = in.readString();
    }

    public static final Creator<BuyersShow> CREATOR = new Creator<BuyersShow>() {
        @Override
        public BuyersShow createFromParcel(Parcel in) {
            return new BuyersShow(in);
        }

        @Override
        public BuyersShow[] newArray(int size) {
            return new BuyersShow[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String[] getEvaluationImgUrl() {
        return evaluationImgUrl;
    }

    public void setEvaluationImgUrl(String[] evaluationImgUrl) {
        this.evaluationImgUrl = evaluationImgUrl;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userIconUrl);
        dest.writeString(evaluation);
        dest.writeStringArray(evaluationImgUrl);
        dest.writeString(likeCount);
    }
}
