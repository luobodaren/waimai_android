package com.life.waimaishuo.mvvm.vm.waimai;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.api.request.WaiMaiShopReqData;
import com.life.waimaishuo.mvvm.model.BaseModel;
import com.life.waimaishuo.mvvm.model.waimai.WaiMaiGoodsDetailModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.GoodsEvaluationFragment;
import com.life.waimaishuo.mvvm.view.fragment.waimai.GoodsMatchFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaiMaiGoodsDetailViewModel extends BaseShopDetailViewModel {

    WaiMaiGoodsDetailModel mModel;

    public ObservableInt onGetGoodsDetailObservable = new ObservableInt();

    @Override
    public BaseModel getModel() {
        if(mModel == null){
            mModel = new WaiMaiGoodsDetailModel();
        }
        return mModel;
    }

    @Override
    public void initData() {

    }

    public List<String> getGoodsPictures() {
        List<String> picUrlList = new ArrayList<>();
        picUrlList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3866087961,4129399840&fm=26&gp=0.jpg");
        picUrlList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.suning.cn%2Fuimg%2Fsop%2Fcommodity%2F199239464610994834126940_x.jpg&refer=http%3A%2F%2Fimage.suning.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611391425&t=2177c5ee31040b1cfe00ad8c7763c34d");
        picUrlList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb.vpimg1.com%2Fupload%2Factpics%2Fbrand%2F0%2F2015%2F01%2F06%2F112%2F912e59e37e81420524687.8213.jpg&refer=http%3A%2F%2Fb.vpimg1.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1611391445&t=1c94cac44bc1eedc1efdad403dc52104");
        return picUrlList;
    }

    public List<String> getTabTitle() {
        List<String> strings = new ArrayList<>();
        strings.add("搭配");
        strings.add("评价");
        return strings;
    }

    public BaseFragment getTabBarFragment(String title) {
        BaseFragment baseFragment = null;
        switch (title){
            case "搭配":
                baseFragment = new GoodsMatchFragment();
                break;
            case "评价":
                baseFragment = new GoodsEvaluationFragment();
                break;
        }
        return baseFragment;
    }

    public void requestGoodsDetail(int goodsId) {
        mModel.requestGoodsDetail(new BaseModel.NotifyChangeRequestCallBack(onGetGoodsDetailObservable),new WaiMaiShopReqData.WaiMaiSimpleReqData(goodsId));
    }

    public Goods getGoodsData(){
        return mModel.goods;
    }
}
