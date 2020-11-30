package com.example.myapplication.mvvm.vm;

import com.example.myapplication.bean.SearchRecord;
import com.example.myapplication.mvvm.model.BaseModel;
import com.example.myapplication.mvvm.model.SearchFragmentModel;

public class SearchFragmentViewModel extends BaseViewModel {

    SearchFragmentModel searchFragmentModel;

    @Override
    public BaseModel getModel() {
        searchFragmentModel = new SearchFragmentModel();
        return searchFragmentModel;
    }

    @Override
    public void initData() {

    }

    public SearchRecord[] getSearchRecord() {
        return new SearchRecord[]{
                new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(1L,"外卖", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(2L,"秒杀吧", System.currentTimeMillis())
                ,new SearchRecord(3L,"饮料", System.currentTimeMillis())
                ,new SearchRecord(4L,"免费配送", System.currentTimeMillis())};
    }
}
