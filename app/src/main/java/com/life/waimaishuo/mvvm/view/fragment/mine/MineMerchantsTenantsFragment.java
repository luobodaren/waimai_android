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
import com.life.waimaishuo.util.MyTabSegmentBoldTypeFaceProvider;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
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

        mBinding.layoutTitle.tvRight.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                MineApplyRecordFragment.openPageWithPageType(MineMerchantsTenantsFragment.this,MineApplyRecordFragment.PAGE_TYPE_OPEN_SHOP);
            }
        });

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);
        mBinding.layoutTitle.tvRight.setText(R.string.record_of_apply);
    }

    private void initTabSegment() {
        String[] tabTitleList = mViewModel.getTabTitleStrings();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegment, adapter, tabTitleList);
        mBinding.tabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widget_horizontal_bar));
        mBinding.tabSegment.setTabTextSize(34);
        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);
        mBinding.tabSegment.setTypefaceProvider(new MyTabSegmentBoldTypeFaceProvider());

        mBinding.viewPager.setOffscreenPageLimit(tabTitleList.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles) {
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            adapter.addFragment(mViewModel.getViewPagerFragment(title), title);
            tabSegment.addTab(tab);
        }
    }

}
