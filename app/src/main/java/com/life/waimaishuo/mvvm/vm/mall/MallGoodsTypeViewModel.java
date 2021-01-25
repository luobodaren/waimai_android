package com.life.waimaishuo.mvvm.vm.mall;

import com.life.waimaishuo.bean.ui.ImageUrlNameData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.mall.MallGoodsTypeModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MallGoodsTypeViewModel extends BaseViewModel {

    private MallGoodsTypeModel mModel;

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new MallGoodsTypeModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<ImageUrlNameData> getTypeTabData() {
        List<ImageUrlNameData> list = new ArrayList<>();
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        list.add(new ImageUrlNameData("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1706891534,3776889912&fm=26&gp=0.jpg","男装"));
        return list;
    }
}
