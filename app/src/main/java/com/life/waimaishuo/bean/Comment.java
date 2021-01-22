package com.life.waimaishuo.bean;

import com.life.waimaishuo.bean.ui.ImageViewInfo;

import java.util.List;

public class Comment {

    String userIcon;
    String userName;
    String score;
    String createData;
    String commentContent;

    String merchantReply;

    List<ImageViewInfo> commentPictureList;

    List<String> goodsList; //购买的商品名称列表

    boolean isClickedLike;
    String countOfLike;

    public Comment(String userIcon, String userName, String commentContent, boolean isClickedLike, String countOfLike) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.commentContent = commentContent;
        this.isClickedLike = isClickedLike;
        this.countOfLike = countOfLike;
    }

    public Comment(String userIcon, String userName, String score, String createData, String commentContent, List<ImageViewInfo> commentPictureList, List<String> goodsList, String merchantReply) {
        this.userIcon = userIcon;
        this.userName = userName;
        this.score = score;
        this.createData = createData;
        this.commentContent = commentContent;
        this.commentPictureList = commentPictureList;
        this.goodsList = goodsList;
        this.merchantReply = merchantReply;
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

    public List<ImageViewInfo> getCommentPictureList() {
        return commentPictureList;
    }

    public void setCommentPictureList(List<ImageViewInfo> commentPictureList) {
        this.commentPictureList = commentPictureList;
    }

    public List<String> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<String> goodsList) {
        this.goodsList = goodsList;
    }

    public String getMerchantReply() {
        return merchantReply;
    }

    public void setMerchantReply(String merchantReply) {
        this.merchantReply = merchantReply;
    }

    public boolean isClickedLike() {
        return isClickedLike;
    }

    public void setClickedLike(boolean clickedLike) {
        isClickedLike = clickedLike;
    }

    public String getCountOfLike() {
        return countOfLike;
    }

    public void setCountOfLike(String countOfLike) {
        this.countOfLike = countOfLike;
    }
}
