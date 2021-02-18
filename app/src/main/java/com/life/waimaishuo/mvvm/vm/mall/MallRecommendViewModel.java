package com.life.waimaishuo.mvvm.vm.mall;

import android.view.View;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallRecommendModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallMainTypeFragment;
import com.life.waimaishuo.mvvm.view.fragment.mall.MallRecyclerRecommendFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallRecommendViewModel extends BaseViewModel {

    MallRecommendModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallRecommendModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public void onBackClick(View view){

    }

    public void onShareClick(View view){

    }

    public void onAllTypeClick(View view){

    }

    public String[] getTypeStrings() {
        return new String[]{"全部","男装","女装","饰品","内衣","洗护"};
    }

    /**
     * 根据title和type 获取具体商品列表信息
     * @param title 好物、好店、配饰
     * @param type  全部 男装 女装等
     * @return
     */
    public List<Goods> getRecommendData(String title,String type) {
        List<Goods> goods = new ArrayList<>();
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        goods.add(new Goods("茶叶蛋","月售五十 好评率100%","https://img.pic88.com/preview/2020/08/10/15970307461454932.jpg!s640",600,"18.0"));
        return goods;
    }
}
