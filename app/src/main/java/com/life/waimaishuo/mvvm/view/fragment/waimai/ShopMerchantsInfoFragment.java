package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ShopInfoItem;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.databinding.FragmentWaimaiShopMerchantsInfoBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopMerchantsInfoViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.PreViewUtil;
import com.life.waimaishuo.util.net.HttpUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Page(name = "外卖商家信息")
public class ShopMerchantsInfoFragment extends BaseFragment {

    private ShopMerchantsInfoViewModel mViewModel;
    private FragmentWaimaiShopMerchantsInfoBinding mBinding;

    private Shop shop;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ShopMerchantsInfoViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiShopMerchantsInfoBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_shop_merchants_info;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected void initViews() {
        super.initViews();

        initShopInfo();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.btReportTheMerchants.setOnClickListener(v -> openPage(ShopReportMerchantsFragment.class));
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    private LinearLayoutManager shopImgRecyclerLinearLayoutManager;
    private MyBaseRecyclerAdapter shopImgRecyclerAdapter;
    private void initShopInfo() {
        if(shop.getNotice() == null || "".equals(shop.getNotice())){
            setViewVisibility(mBinding.tvShopIntroduce,false);
        }else{
            setViewVisibility(mBinding.tvShopIntroduce,true);
            mViewModel.shopIntroduce.set(shop.getNotice());
        }

        LogUtil.d("-----------------" + shop.getShopId() + shop.getSynopsis_img());
        String[] imgUrls = (shop.getSynopsis_img() != null && !"".equals(shop.getSynopsis_img()))
                ? shop.getSynopsis_img().split(",") : null;
        if(imgUrls == null){
            setViewVisibility(mBinding.recyclerShopImg,false);
        }else{
            List<ImageViewInfo> urlList = new ArrayList<>();
            for (String url:imgUrls) {
                String tempUrl = HttpUtils.changeToHttps(url);
                if(!"".equals(tempUrl)){
                    urlList.add(new ImageViewInfo(tempUrl));
                }
            }
            if(urlList.size() == 0){
                setViewVisibility(mBinding.recyclerShopImg,false);
            }else{
                shopImgRecyclerLinearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
                shopImgRecyclerAdapter = new MyBaseRecyclerAdapter<ImageViewInfo>(R.layout.item_recycler_shop_picture, urlList, com.life.waimaishuo.BR.item);
                mBinding.recyclerShopImg.setLayoutManager(shopImgRecyclerLinearLayoutManager);
                mBinding.recyclerShopImg.setAdapter(shopImgRecyclerAdapter);
                mBinding.recyclerShopImg.addItemDecoration(new RecyclerView.ItemDecoration() {
                    int paddingStartAndEnd = (int) UIUtils.getInstance().scalePx(
                            getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
                    int padding = (int) UIUtils.getInstance().scalePx(
                            getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding));
                    @Override
                    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        int position = parent.getChildAdapterPosition(view);
                        if(position != 0){
                            outRect.left = padding;
                            if(position == state.getItemCount()-1){   //最后一个
                                outRect.right = paddingStartAndEnd;
                            }
                        }else{
                            outRect.left = paddingStartAndEnd;
                        }
                    }
                });

                //添加图片点击时间监听 看大图
                PreViewUtil.initRecyclerPictureClickListener(this,shopImgRecyclerAdapter, shopImgRecyclerLinearLayoutManager);
            }
        }

        mBinding.recyclerInfoDetail.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerInfoDetail.setAdapter(new MyBaseRecyclerAdapter<ShopInfoItem>(R.layout.item_recycler_shop_info_text,mViewModel.getShopInfoList(shop), com.life.waimaishuo.BR.item){
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, ShopInfoItem item) {
                if(item.getValue() != null && !"".equals(item.getValue())){
                    helper.getView(R.id.iv_right).setVisibility(View.GONE);
                }else{  //可点击进入子页面的项目
                    helper.getView(R.id.tv_right).setVisibility(View.GONE);
                    helper.itemView.setOnClickListener(new BaseActivity.OnMultiClickListener() {
                        @Override
                        public void onMultiClick(View view) {
                            goToInfoDetailFragment(item.getName());
                        }
                    });
                }
            }
        });

        if(mBinding.recyclerShopImg.getVisibility() == View.GONE && mBinding.tvShopIntroduce.getVisibility() == View.GONE){
            mBinding.tvMerchantsInfo.setVisibility(View.GONE);
        }

    }

    private void goToInfoDetailFragment(String name){  // FIXME: 2020/12/24 需要带入店铺或品牌的Id 或者是数据
        if(name.equals(mViewModel.getShopInfoList(shop).get(0).getName())){ // FIXME: 2020/12/28 判断逻辑需要修改
            openPage(ShopBrandStoryFragment.class, new Bundle());
        }else if(name.equals(mViewModel.getShopInfoList(shop).get(5).getName())){
            openPage(ShopBusinessQualificationFragment.class,new Bundle());
        }

    }



}
