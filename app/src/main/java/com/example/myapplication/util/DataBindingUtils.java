package com.example.myapplication.util;

import android.util.ArrayMap;

import androidx.databinding.Observable;

import com.example.myapplication.mvvm.view.activity.BaseActivity;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;

public class DataBindingUtils {

    private static ArrayMap<BaseActivity, ArrayMap<Observable, Observable.OnPropertyChangedCallback>> activityCommonMap = new ArrayMap<>();

    private static ArrayMap<BaseFragment, ArrayMap<Observable, Observable.OnPropertyChangedCallback>> fragmentCommonMap = new ArrayMap<>();

    public static void addCallBack(BaseActivity baseActivity,
                                   Observable observable, Observable.OnPropertyChangedCallback callback) {
        ArrayMap<Observable, Observable.OnPropertyChangedCallback> callbackArrayMap = activityCommonMap.get(baseActivity);
        if (callbackArrayMap == null) {
            callbackArrayMap = new ArrayMap<>();
            activityCommonMap.put(baseActivity, callbackArrayMap);
        }
        observable.addOnPropertyChangedCallback(callback);
        callbackArrayMap.put(observable, callback);
    }

    public static void addCallBack(BaseFragment baseFragment,
                                   Observable observable, Observable.OnPropertyChangedCallback callback) {
        ArrayMap<Observable, Observable.OnPropertyChangedCallback> callbackArrayMap = fragmentCommonMap.get(baseFragment);
        if (callbackArrayMap == null) {
            callbackArrayMap = new ArrayMap<>();
            fragmentCommonMap.put(baseFragment, callbackArrayMap);
        }
        observable.addOnPropertyChangedCallback(callback);
        callbackArrayMap.put(observable, callback);
    }

    public static void removeActivityCallBack(BaseActivity activity) {
        ArrayMap<Observable, Observable.OnPropertyChangedCallback> callbackArrayMap = activityCommonMap.get(activity);
        if (callbackArrayMap != null) {
            for (Observable observable : callbackArrayMap.keySet()) {
                observable.removeOnPropertyChangedCallback(callbackArrayMap.get(observable));
            }
        }
    }

    public static void removeFragmentCallBack(BaseFragment fragment) {
        ArrayMap<Observable, Observable.OnPropertyChangedCallback> callbackArrayMap = fragmentCommonMap.get(fragment);
        if (callbackArrayMap != null) {
            for (Observable observable : callbackArrayMap.keySet()) {
                observable.removeOnPropertyChangedCallback(callbackArrayMap.get(observable));
            }
        }
    }
}
