package com.life.waimaishuo.mvvm.model;

import androidx.databinding.BaseObservable;

import java.util.Observable;

public class BaseModel {

    public interface RequestCallBack<T>{
        void onSuccess(T result);

        void onFail(String error);
    }

    /**
     * 默认通过observable回调的CallBack
     */
    public static class NotifyChangeRequestCallBack implements RequestCallBack<Object> {

        private BaseObservable baseObservable;

        public NotifyChangeRequestCallBack(BaseObservable baseObservable) {
            this.baseObservable = baseObservable;
        }

        @Override
        public void onSuccess(Object result) {
            baseObservable.notifyChange();
        }

        @Override
        public void onFail(String error) {
            baseObservable.notifyChange();
        }
    }

}
