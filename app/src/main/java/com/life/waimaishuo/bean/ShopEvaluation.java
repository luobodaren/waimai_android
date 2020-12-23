package com.life.waimaishuo.bean;

import java.util.List;

public class ShopEvaluation {

    String score;
    String tasteScore;
    String packingScore;
    String deliverScore;

    List<Comment> commentsList;

    public ShopEvaluation(String score, String tasteScore, String packingScore, String deliverScore, List<Comment> commentsList) {
        this.score = score;
        this.tasteScore = tasteScore;
        this.packingScore = packingScore;
        this.deliverScore = deliverScore;
        this.commentsList = commentsList;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTasteScore() {
        return tasteScore;
    }

    public void setTasteScore(String tasteScore) {
        this.tasteScore = tasteScore;
    }

    public String getPackingScore() {
        return packingScore;
    }

    public void setPackingScore(String packingScore) {
        this.packingScore = packingScore;
    }

    public String getDeliverScore() {
        return deliverScore;
    }

    public void setDeliverScore(String deliverScore) {
        this.deliverScore = deliverScore;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }
}
