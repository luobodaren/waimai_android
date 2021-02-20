package com.life.waimaishuo.bean.api.request;

/**
 * 基类：请求接口时传入的json字符串转换类
 */
public class BaseReqData<T> {

    public T reqData;

    public BaseReqData(T reqData) {
        this.reqData = reqData;
    }

}
