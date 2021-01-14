package com.life.waimaishuo.mvvm.view.fragment.mall;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentMallBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "商城", anim = CoreAnim.slide)
public class MallFragment extends BaseFragment {

    FragmentMallBinding mBinding;
    MallViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initGoodsType();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        LogUtil.d("pageName:" + getPageName() + " title:" + getPageTitle());
        mBinding.layoutTitle.tvTitle.setText(getPageTitle());
    }

    private int textSizeSelected;
    private int textSizeNormal;
    private void initGoodsType() {
        textSizeSelected = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size_selected);
        textSizeNormal = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size);
        String[] titles = mViewModel.getGoodsTypeStrings();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegmentGoodsType,adapter,titles);   // FIXME: 2020/12/10 第一次进入 拿不到tab 设置的大小
        mBinding.tabSegmentGoodsType.setHasIndicator(false);
        mBinding.tabSegmentGoodsType.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegmentGoodsType.setDefaultNormalColor(getResources().getColor(R.color.white));
        mBinding.tabSegmentGoodsType.setDefaultSelectedColor(getResources().getColor(R.color.white));
        mBinding.tabSegmentGoodsType.setTabTextSize(textSizeNormal);
        mBinding.tabSegmentGoodsType.setupWithViewPager(mBinding.viewPager,true);

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        boolean isFirstItem = true;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(isFirstItem){
                tab.setTextSize(textSizeSelected);
                isFirstItem = false;
            }else{
                tab.setTextSize(textSizeNormal);
            }
            adapter.addFragment(mViewModel.getViewPagerFragment(title), title);
            tabSegment.addTab(tab);
        }
    }



}
