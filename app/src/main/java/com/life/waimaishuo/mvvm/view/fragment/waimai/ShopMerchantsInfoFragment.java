package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ShopInfoItem;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.databinding.FragmentWaimaiShopMerchantsInfoBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopMerchantsInfoViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.PreViewUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "外卖商家信息")
public class ShopMerchantsInfoFragment extends BaseFragment {

    private ShopMerchantsInfoViewModel mViewModel;
    private FragmentWaimaiShopMerchantsInfoBinding mBinding;


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
        addCallBack();

        //添加图片点击时间监听 看大图
        PreViewUtil.initRecyclerPictureClickListener(this,shopImgRecyclerAdapter, shopImgRecyclerLinearLayoutManager);
    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.refreshIntroduceData, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                // TODO: 2020/12/24 刷新数据
            }
        });
    }
    private LinearLayoutManager shopImgRecyclerLinearLayoutManager;
    private MyBaseRecyclerAdapter shopImgRecyclerAdapter;
    private void initShopInfo() {
        shopImgRecyclerLinearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
        shopImgRecyclerAdapter = new MyBaseRecyclerAdapter<ImageViewInfo>(R.layout.item_recycler_shop_picture,mViewModel.getShopIntroducePictureUrl(), com.life.waimaishuo.BR.item);
        mBinding.recyclerShopImg.setLayoutManager(shopImgRecyclerLinearLayoutManager);
        mBinding.recyclerShopImg.setAdapter(shopImgRecyclerAdapter);
        mBinding.recyclerShopImg.addItemDecoration(new RecyclerView.ItemDecoration() {
            int paddingStartAndEnd = (int) UIUtils.getInstance(requireContext()).scalePx(
                    getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
            int padding = (int) UIUtils.getInstance(requireContext()).scalePx(
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

        mBinding.recyclerInfoDetail.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerInfoDetail.setAdapter(new MyBaseRecyclerAdapter<ShopInfoItem>(R.layout.item_recycler_shop_info_text,mViewModel.getShopInfoList(), com.life.waimaishuo.BR.item){
            @Override
            protected void initView(BaseViewHolder helper, ShopInfoItem item) {
                super.initView(helper, item);
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

    }

    private void goToInfoDetailFragment(String name){  // FIXME: 2020/12/24 需要带入店铺或品牌的Id 或者是数据
        if(name.equals(mViewModel.getShopInfoList().get(0).getName())){ // FIXME: 2020/12/28 判断逻辑需要修改
            openPage(BrandStoryFragment.class, new Bundle());
        }else if(name.equals(mViewModel.getShopInfoList().get(5).getName())){
            openPage(WaiMaiBusinessQualificationFragment.class,new Bundle());
        }

    }



}
