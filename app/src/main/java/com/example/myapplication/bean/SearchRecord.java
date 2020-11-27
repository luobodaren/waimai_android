package com.example.myapplication.bean;

public class SearchRecord {

    private long Id;
    /**
     * 搜索内容
     */
    private String content;

    /**
     * 搜索时间
     */
    private long time;

    public SearchRecord() {
    }

    public SearchRecord(long id, String content, long time) {
        Id = id;
        this.content = content;
        this.time = time;
    }

    public long getId() {
        return Id;
    }

    public SearchRecord setId(long id) {
        Id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SearchRecord setContent(String content) {
        this.content = content;
        return this;
    }

    public long getTime() {
        return time;
    }

    public SearchRecord setTime(long time) {
        this.time = time;
        return this;
    }

    @Override
    public String toString() {
        return "QueryRecord{" +
                "Id=" + Id +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
