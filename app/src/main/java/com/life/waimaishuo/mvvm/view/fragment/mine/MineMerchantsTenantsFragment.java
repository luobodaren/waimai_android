package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentMineMerchantsTenantsBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineMerchantsTenantsViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "商家入驻", anim = CoreAnim.slide)
public class MineMerchantsTenantsFragment extends BaseFragment {

    private FragmentMineMerchantsTenantsBinding mBinding;
    private MineMerchantsTenantsViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MineMerchantsTenantsViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineMerchantsTenantsBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_merchants_tenants;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initTabSegment();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.layoutTitle.ivBack.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                popToBack();
            }
        });
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                refreshTabViewStyle(mBinding.tabSegment, mViewModel.getTabTitleStrings(), position);
                // TODO: 2020/12/3 刷新内容

            }
        });
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initTabSegment() {
        int space = getResources().getDimensionPixelOffset(R.dimen.goods_comment_tabSegment_item_space);
        String[] tabTitleList = mViewModel.getTabTitleStrings();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegment, adapter, tabTitleList);
        mBinding.tabSegment.setItemSpaceInScrollMode(space);
        mBinding.tabSegment.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.goods_comment_tabSegment_item_text_size));
        mBinding.tabSegment.setTypefaceProvider(new TabSegment.TypefaceProvider() {
            @Override
            public boolean isNormalTabBold() {
                return false;
            }

            @Override
            public boolean isSelectedTabBold() {
                return true;
            }

            @Nullable
            @Override
            public Typeface getTypeface() {
                return null;
            }
        });

        mBinding.viewPager.setOffscreenPageLimit(tabTitleList.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private int textSizeSelected;
    private int textSizeNormal;

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles) {
        boolean isFirst = true;
        textSizeSelected = getResources().getDimensionPixelSize(R.dimen.mall_tabbar_item_text_size_selected);
        textSizeNormal = getResources().getDimensionPixelSize(R.dimen.mall_tabbar_item_text_size);
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if (isFirst) {
                isFirst = false;
                tab.setTextSize(textSizeSelected);
            } else {
                tab.setTextSize(textSizeNormal);
            }
            adapter.addFragment(mViewModel.getViewPagerFragment(title), title);
            tabSegment.addTab(tab);
        }
    }

    /**
     * 更新TabBar样式
     */
    private void refreshTabViewStyle(TabSegment tabSegment, String[] titles, int position) {
        tabSegment.reset();
        resetTab(tabSegment, titles, position);
        tabSegment.notifyDataChanged();
    }

    int textSizeSelectedScale = 0;
    int textSizeNormalScale = 0;

    /**
     * 仅更新 title 若需要改变个数 需要修改方法内逻辑
     *
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(TabSegment tabSegment, String[] titles,
                          int selectedPosition) {
        if (textSizeSelectedScale == 0 || textSizeNormalScale == 0) {
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }
        int position = 0;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelectedScale : textSizeNormalScale);
            tabSegment.addTab(tab);
            position++;
        }
    }
}
