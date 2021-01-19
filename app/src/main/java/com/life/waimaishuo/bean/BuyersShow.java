package com.life.waimaishuo.bean;

public class BuyersShow {

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
}
