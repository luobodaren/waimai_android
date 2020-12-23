package com.life.waimaishuo.bean;

import java.util.List;

public class Comment {

    String userIcon;
    String userName;
    String score;
    String createData;
    String commentContent;

    List<String> commentPictureList;

    List<String> goodsList; //购买的商品名称列表

    public Comment(String userIcon, String userName, String score, String createData, String commentContent, List<String> commentPictureList, List<String> goodsList) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.score = score;
        this.createData = createData;
        this.commentContent = commentContent;
        this.commentPictureList = commentPictureList;
        this.goodsList = goodsList;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public List<String> getCommentPictureList() {
        return commentPictureList;
    }

    public void setCommentPictureList(List<String> commentPictureList) {
        this.commentPictureList = commentPictureList;
    }

    public List<String> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<String> goodsList) {
        this.goodsList = goodsList;
    }
}
