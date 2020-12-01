package com.example.myapplication.bean;

public class Message {

    String name;
    String content;
    String date;
    String unCheckCount;
    String headSrc;

    public Message(String name, String content, String date, String unCheckCount, String headSrc) {
        this.name = name;
        this.content = content;
        this.date = date;
        this.unCheckCount = unCheckCount;
        this.headSrc = headSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnCheckCount() {
        return unCheckCount;
    }

    public void setUnCheckCount(String unCheckCount) {
        this.unCheckCount = unCheckCount;
    }

    public String getHeadSrc() {
        return headSrc;
    }

    public void setHeadSrc(String headSrc) {
        this.headSrc = headSrc;
    }
}
