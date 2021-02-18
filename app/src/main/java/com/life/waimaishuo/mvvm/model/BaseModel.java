package com.life.waimaishuo.mvvm.model;

import androidx.databinding.ObservableInt;

public class BaseModel {

    public interface RequestCallBack<T>{

        void onSuccess(T result);

        void onFail(String error);
    }

    /**
     * 默认通过observable回调的CallBack
     */
    public static class NotifyChangeRequestCallBack implements RequestCallBack<Object> {

        private ObservableInt baseObservableInt;

        public static final int REQUEST_SUCCESS = 0;
        public static final int REQUEST_FALSE = -1;

        public NotifyChangeRequestCallBack(ObservableInt baseObservableInt) {
            this.baseObservableInt = baseObservableInt;
        }

        @Override
        public void onSuccess(Object result) {
            baseObservableInt.set(REQUEST_SUCCESS);
            baseObservableInt.notifyChange();
        }

        @Override
        public void onFail(String error) {
            baseObservableInt.set(REQUEST_FALSE);
            baseObservableInt.notifyChange();
        }
    }

}
