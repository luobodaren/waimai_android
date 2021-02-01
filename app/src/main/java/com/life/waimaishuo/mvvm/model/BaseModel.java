package com.life.waimaishuo.mvvm.model;

public class BaseModel {

    public interface RequestCallBack<T>{
        void onSuccess(T result);

        void onFail(String error);
    }

}
