package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.databinding.Observable;
import androidx.viewpager.widget.ViewPager;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentMallBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
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
        initGoodsTypeTabSegment();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        MyDataBindingUtil.addCallBack(this, mViewModel.onAllTypeClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                openPage(MallAllTypeFragment.class);
            }
        });

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // TODO: 2020/12/3 刷新内容

            }
        });
    }

    private void initTitle(){
        LogUtil.d("pageName:" + getPageName() + " title:" + getPageTitle());
        mBinding.layoutTitle.tvTitle.setText(getPageTitle());
    }

    private int textSizeNormal;
    private void initGoodsTypeTabSegment() {
        textSizeNormal = 24;
        String[] titles = mViewModel.getGoodsTypeStrings();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegmentGoodsType,adapter,titles);
        mBinding.tabSegmentGoodsType.setDefaultTabIconPosition(TabSegment.ICON_POSITION_TOP);
        mBinding.tabSegmentGoodsType.setHasIndicator(false);
        mBinding.tabSegmentGoodsType.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegmentGoodsType.setDefaultNormalColor(getResources().getColor(R.color.white));
        mBinding.tabSegmentGoodsType.setDefaultSelectedColor(getResources().getColor(R.color.white));
        mBinding.tabSegmentGoodsType.setTabTextSize(textSizeNormal);
        mBinding.tabSegmentGoodsType.setupWithViewPager(mBinding.viewPager,false);

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        Drawable drawable;
        Context context = requireContext();
        for (String title : titles) {
            drawable = mViewModel.getTitleDrawable(context,title);
            MyTabSegmentTab tab = new MyTabSegmentTab(drawable,drawable,title,false);
            tab.setTextSize(textSizeNormal);
            adapter.addFragment(mViewModel.getViewPagerFragment(title), title);
            tabSegment.addTab(tab);
        }
    }


}
