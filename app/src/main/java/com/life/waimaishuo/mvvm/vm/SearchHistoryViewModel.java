package com.life.waimaishuo.mvvm.vm;

import com.life.waimaishuo.bean.SearchTag;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.SearchFragmentModel;

public class SearchHistoryViewModel extends BaseViewModel {

    SearchFragmentModel searchFragmentModel;

    @Override
    public BaseModel getModel() {
        if(searchFragmentModel == null){
            searchFragmentModel = new SearchFragmentModel();
        }
        return searchFragmentModel;
    }

    @Override
    public void initData() {

    }

    public SearchTag[] getSearchRecord() {
        return new SearchTag[]{
                new SearchTag(1L,"外卖", "")
                ,new SearchTag(2L,"秒杀吧", "")
                ,new SearchTag(3L,"饮料", "")
                ,new SearchTag(1L,"外卖", "")
                ,new SearchTag(2L,"秒杀吧", "")
                ,new SearchTag(3L,"饮料", "")
                ,new SearchTag(1L,"外卖", "")
                ,new SearchTag(2L,"秒杀吧","")
                ,new SearchTag(3L,"饮料", "")
                ,new SearchTag(1L,"外卖", "")
                ,new SearchTag(2L,"秒杀吧","")
                ,new SearchTag(3L,"饮料","")};
    }
}
