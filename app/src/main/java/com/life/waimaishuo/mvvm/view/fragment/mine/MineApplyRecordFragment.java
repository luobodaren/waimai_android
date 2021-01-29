package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.os.Bundle;

import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMineApplyRecordBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineApplyRecordRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.mine.MineApplyRecordViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "申请记录", anim = CoreAnim.slide)
public class MineApplyRecordFragment extends BaseFragment {

    private final static String PAGE_TYPE_KEY = "page_type_key";
    public final static int PAGE_TYPE_BUSINESS_COOPERATION = 1;
    public final static int PAGE_TYPE_OPEN_SHOP = 2;    //店铺开店
    public final static int PAGE_TYPE_RIDER_TO_RECRUIT = 3; //骑手招募

    private FragmentMineApplyRecordBinding mBinding;
    private MineApplyRecordViewModel mViewModel;

    private int mPageType;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MineApplyRecordViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMineApplyRecordBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_apply_record;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }
        mPageType = bundle.getInt(PAGE_TYPE_KEY, -1);
        if (mPageType == -1) {
            throw new Error(Constant.ERROR_MSG_BUNDLE_DATA_ERROR);
        }

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

    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle, true);
    }

    private void initTabSegment() {
        String[] titles = {"全部", "审核中", "已完成"};

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegment, adapter, titles);
        mBinding.tabSegment.setHasIndicator(true);
        mBinding.tabSegment.setIndicatorDrawable(getResources().getDrawable(R.drawable.sr_widget_horizontal_bar));
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager, false);

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles) {
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            MineApplyRecordRecyclerFragment fragment = new MineApplyRecordRecyclerFragment();
            fragment.baseViewModel = mViewModel;
            fragment.setRecordType(mPageType);
            fragment.setListState(title);
            adapter.addFragment(fragment, title);
            tabSegment.addTab(tab);
        }
    }

    public static void openPageWithPageType(BaseFragment baseFragment, int pageType) {
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_TYPE_KEY, pageType);
        baseFragment.openPage(MineApplyRecordFragment.class, bundle);
    }
}
