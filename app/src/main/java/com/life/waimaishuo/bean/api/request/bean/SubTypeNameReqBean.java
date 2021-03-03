package com.life.waimaishuo.bean.api.request.bean;

/**
 * 外卖子类集的请求体
 */
public class SubTypeNameReqBean {

    int category;   //1外卖 2商城 3首页推荐
    String typeName;

    public SubTypeNameReqBean(int category, String typeName) {
        this.category = category;
        this.typeName = typeName;
    }
}
