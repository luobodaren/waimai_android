package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.databinding.FragmentWaimaiGoodsDetailBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiGoodsDetailViewModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.life.waimaishuo.views.widget.dialog.SpecificationDialog;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

@Page(name = "商品详情", anim = CoreAnim.slide)
public class GoodsDetailFragment extends BaseFragment {

    private final static String BUNDLE_KEY_SHOP_ID = "bundle_key_shop_id";
    private final static String BUNDLE_KEY_GOODS = "bundle_key_goods";

    private FragmentWaimaiGoodsDetailBinding mBinding;
    private WaiMaiGoodsDetailViewModel mViewModel;

    private SpecificationDialog specificationDialog;

    private int shopId;
    private Goods goods;    //保存店铺传进来的Goods信息，有部分数据需要从这里获取

    public static void openPage(BaseFragment baseFragment, int shopId, Goods goods) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_SHOP_ID,shopId);
        bundle.putParcelable(BUNDLE_KEY_GOODS,goods);
        baseFragment.openPage(GoodsDetailFragment.class,bundle);
    }

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiGoodsDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiGoodsDetailBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_goods_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        shopId = bundle.getInt(BUNDLE_KEY_SHOP_ID);
        goods = bundle.getParcelable(BUNDLE_KEY_GOODS);

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {

        //初始化布局中的title
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);

        return null;
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        mViewModel.requestGoodsDetail(goods.getId());
    }

    @Override
    protected void initViews() {
        super.initViews();
        //initBanner();
        initNavigationTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBak();

        mBinding.layoutGoodsOptionChose.btChoseSpecification.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                showChoseSpecificationDialog(mViewModel.getGoodsData());
            }
        });

        mBinding.layoutGoodsOptionAddShoppingCart.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = parseInt(mBinding.layoutGoodsOptionAddShoppingCart.tvCount.getText().toString()) + 1 + "";
                mBinding.layoutGoodsOptionAddShoppingCart.tvCount.setText(count);
            }
        });
        mBinding.layoutGoodsOptionAddShoppingCart.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = parseInt(mBinding.layoutGoodsOptionAddShoppingCart.tvCount.getText().toString()) - 1;
                if (count >= 0) {
                    mBinding.layoutGoodsOptionAddShoppingCart.tvCount.setText(String.valueOf(count));
                }
            }
        });
    }

    private void addCallBak(){
        MyDataBindingUtil.addCallBack(this, mViewModel.onGetGoodsDetailObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(mViewModel.getGoodsData() != null){
                        refreshData();
                    }
                });
            }
        });
    }

    private void refreshData(){
        mBinding.setGoods(mViewModel.getGoodsData());

        if(mViewModel.getGoodsData().getDiscount() != null && !"".equals(mViewModel.getGoodsData().getDiscount())){
            mBinding.layoutPreferential.tvDiscount.setText(getString(R.string.discount,mViewModel.getGoodsData().getDiscount()));
        }else{
            mBinding.layoutPreferential.llContent.setVisibility(View.GONE);
        }

        //由店铺传入的goods信息获取价格
        mBinding.tvCurrentPrice.setText(String.format("￥%s", goods.getSpecialPrice()));
        mBinding.tvPrePrice.setText(String.format("￥%s", goods.getLinePrice()));
        TextUtil.setStrikeThrough(mBinding.tvPrePrice);

        if(goods.getDetails() == null || "".equals(goods.getDetails())){
            BaseFragment.setViewVisibility(mBinding.tvIntroduce,false);
        }else{
            BaseFragment.setViewVisibility(mBinding.tvIntroduce,true);
        }

        if(mViewModel.getGoodsData().getIsChooseSpecs() == 0){
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionAddShoppingCart.llContent,false);
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionChose.llContent,true);
        }else{
            mBinding.layoutGoodsOptionAddShoppingCart.tvCount.setText(String.valueOf(mViewModel.getGoodsData().getChoiceNumber()));
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionAddShoppingCart.llContent,true);
            BaseFragment.setViewVisibility(mBinding.layoutGoodsOptionChose.llContent,false);
        }

    }

    /*private void initBanner() {
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getGoodsPictures(),R.layout.adapter_banner_image_item_brand_story){
            @Override
            public void bindData(@NonNull RecyclerViewHolder holder, int position, String imgUrl) {
                ((ImageView)holder.findViewById(R.id.iv_item)).setScaleType(ImageView.ScaleType.CENTER_CROP);
                super.bindData(holder, position, imgUrl);
            }
        };
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(),"点击了轮播图：" + position,Toast.LENGTH_SHORT).show());
        mBinding.bannerLayout.setAdapter(mAdapterHorizontal);
    }*/

    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        int space = getResources().getDimensionPixelOffset(R.dimen.goods_detail_tabbar_item_space);
        List<String> tabTitle = mViewModel.getTabTitle();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.stickyView.setItemSpaceInScrollMode(space);
        mBinding.stickyView.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widget_horizontal_bar));
        mBinding.stickyView.setIndicatorWidthAdjustContent(false);
        mBinding.stickyView.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.goods_detail_tabbar_item_text_size));
        addTab(mBinding.stickyView, adapter, tabTitle);
        mBinding.stickyView.setupWithViewPager(mBinding.adaptiveSizeView, false);

        mBinding.adaptiveSizeView.setOffscreenPageLimit(tabTitle.size() - 1);
        mBinding.adaptiveSizeView.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        List<String> titles) {
        Iterator<String> iterator = titles.iterator();
        while (iterator.hasNext()) {
            String title = iterator.next();
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.waimai_shop_tabbar_item_text_size_selected));
            adapter.addFragment(mViewModel.getTabBarFragment(title), title);
            tabSegment.addTab(tab);
        }
    }

    private void showChoseSpecificationDialog(Goods goods) {
        if(goods == null){
            return;
        }
        if (specificationDialog == null) {
            specificationDialog = new SpecificationDialog(requireContext());
        }

        specificationDialog.setData(goods);

        if (!specificationDialog.isShowing()) {
            specificationDialog.show();
        }
    }


}
