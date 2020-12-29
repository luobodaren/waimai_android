package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;

import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.BrandStoryModel;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class BrandStoryViewModel extends BaseViewModel {

    private BrandStoryModel mModel;

    public ObservableField<String> brandStory = new ObservableField<>();
    public ObservableField<String> storyTitle = new ObservableField<>();


    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new BrandStoryModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

        setData();
    }

    // FIXME: 2020/12/24 暂时使用
    private void setData(){
        brandStory.set("品牌故事 | 现切压沙瓜");
        storyTitle.set("这是发生在很久很久以前的故事标题");
    }

    public List<String> getBrandStoryPictures(){
        List<String> picUrlList = new ArrayList<>();
        picUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3866087961,4129399840&fm=26&gp=0.jpg");
        picUrlList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.suning.cn%2Fuimg%2Fsop%2Fcommodity%2F199239464610994834126940_x.jpg&refer=http%3A%2F%2Fimage.suning.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611391425&t=2177c5ee31040b1cfe00ad8c7763c34d");
        picUrlList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb.vpimg1.com%2Fupload%2Factpics%2Fbrand%2F0%2F2015%2F01%2F06%2F112%2F912e59e37e81420524687.8213.jpg&refer=http%3A%2F%2Fb.vpimg1.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611391445&t=1c94cac44bc1eedc1efdad403dc52104");
        return picUrlList;
    }



}
