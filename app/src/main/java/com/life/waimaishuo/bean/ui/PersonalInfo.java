package com.life.waimaishuo.bean.ui;

public class PersonalInfo {

    String leftTextTitle;
    String leftIvUrl;
    String rightText;
    String rightIvUrl;

    boolean isShowRightArrow;   //是否显示右侧箭头
    boolean isHighlightRT;  //是否高亮右侧文字

    public PersonalInfo(String leftTextTitle, String leftIvUrl, String rightText, String rightIvUrl,
                        boolean isShowRightArrow, boolean isHighlightRT) {
        this.leftTextTitle = leftTextTitle;
        this.leftIvUrl = leftIvUrl;
        this.rightText = rightText;
        this.rightIvUrl = rightIvUrl;
        this.isShowRightArrow = isShowRightArrow;
        this.isHighlightRT = isHighlightRT;
    }

    public String getLeftTextTitle() {
        return leftTextTitle;
    }

    public void setLeftTextTitle(String leftTextTitle) {
        this.leftTextTitle = leftTextTitle;
    }

    public String getLeftIvUrl() {
        return leftIvUrl;
    }

    public void setLeftIvUrl(String leftIvUrl) {
        this.leftIvUrl = leftIvUrl;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getRightIvUrl() {
        return rightIvUrl;
    }

    public void setRightIvUrl(String rightIvUrl) {
        this.rightIvUrl = rightIvUrl;
    }

    public boolean isShowRightArrow() {
        return isShowRightArrow;
    }

    public void setShowRightArrow(boolean showRightArrow) {
        isShowRightArrow = showRightArrow;
    }

    public boolean isHighlightRT() {
        return isHighlightRT;
    }

    public void setHighlightRT(boolean highlightRT) {
        isHighlightRT = highlightRT;
    }
}
