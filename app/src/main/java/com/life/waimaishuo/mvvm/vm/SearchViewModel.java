package com.life.waimaishuo.mvvm.vm;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.SearchActivityModel;

public class SearchViewModel extends BaseViewModel {

    public String searchStr;

    public BaseObservable cancelSearch = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if(mMode == null){
            mMode = new SearchActivityModel();
        }
        return mMode;
    }

    @Override
    public void initData() {

    }

    /**
     * 点击取消按钮
     * @param view
     */
    public void onSearchBtClick(View view){
        cancelSearch.notifyChange();
    }

}