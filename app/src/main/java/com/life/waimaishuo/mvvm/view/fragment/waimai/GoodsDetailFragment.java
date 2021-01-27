package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.databinding.FragmentWaimaiGoodsDetailBinding;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiGoodsDetailViewModel;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.Iterator;
import java.util.List;

@Page(name = "商品详情", anim = CoreAnim.slide)
public class GoodsDetailFragment extends BaseFragment {

    FragmentWaimaiGoodsDetailBinding mBinding;
    WaiMaiGoodsDetailViewModel mViewModel;

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
    protected void initViews() {
        super.initViews();
        initBanner();

        initNavigationTab();
    }

    private void initBanner() {
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
    }
    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        int space = getResources().getDimensionPixelOffset(R.dimen.goods_detail_tabbar_item_space);
        List<String> tabTitle = mViewModel.getTabTitle();
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        Drawable indicatorDrawable = getResources().getDrawable(R.drawable.sr_widght_horizontal_bar);
        indicatorDrawable.setBounds(0,0,
                (int) UIUtils.getInstance().scalePx(44), (int) UIUtils.getInstance().scalePx(6));

        mBinding.stickyView.setItemSpaceInScrollMode(space);
        mBinding.stickyView.setIndicatorDrawable(indicatorDrawable);
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

}
