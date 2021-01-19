package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.ViewPager;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallRecommendBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallRecommendViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

/**
 * 发现好物、每日好店、时尚配饰等
 */
@Page(name = "商城-推荐页", anim = CoreAnim.slide)
public class MallRecommendFragment extends BaseFragment {

    private final static String KEY_TITLE = "title";
    private final static String KEY_BACKGROUND_TOP = "backgroundTopID";

    private FragmentMallRecommendBinding mBinding;
    private MallRecommendViewModel mViewModel;

    private String title;
    private int backgroundTopDrawableId;

    /**
     * 供XPAGE使用，但自己调用需要调用下方带参数的构造方法
     */
    public MallRecommendFragment() { }

    public MallRecommendFragment(String title, int backgroundTopDrawableId) {
        this.title = title;
        this.backgroundTopDrawableId = backgroundTopDrawableId;
    }

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallRecommendViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallRecommendBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
        mBinding.layoutTitle.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_recommend;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);

        boolean error = false;
        if(title == null || "".equals(title)){
            Bundle bundle = getArguments();
            if(bundle != null){
                title = bundle.getString(KEY_TITLE);
                backgroundTopDrawableId = bundle.getInt(KEY_BACKGROUND_TOP);
                if(title == null || "".equals(title)){  // FIXME: 2021/1/19 暂时没判断title值是否正确
                    error = true;
                }
            }else{
                error = true;
            }
        }

        if(error){
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initBackgroundTop();
        initTypeTabSegment();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.ivMoreType.setOnClickListener(v -> {

        });

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                refreshTabViewStyle(mBinding.tabSegmentType,mViewModel.getTypeStrings(),position);
                // TODO: 2020/12/3 刷新内容
            }
        });
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(title);
    }

    private void initBackgroundTop(){
        if(backgroundTopDrawableId != 0 && backgroundTopDrawableId != -1){
            mBinding.ivBackgroundTop.setImageResource(backgroundTopDrawableId);
        }
    }

    int textSizeSelected;
    int textSizeNormal;
    private void initTypeTabSegment() {
        textSizeSelected = getResources().getDimensionPixelSize(R.dimen.mall_tabbar_item_text_size_selected);
        textSizeNormal = getResources().getDimensionPixelSize(R.dimen.mall_tabbar_item_text_size);
        String[] titles = mViewModel.getTypeStrings();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegmentType,adapter,titles);
        mBinding.tabSegmentType.setHasIndicator(false);
        mBinding.tabSegmentType.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegmentType.setDefaultNormalColor(getResources().getColor(R.color.white));
        mBinding.tabSegmentType.setDefaultSelectedColor(getResources().getColor(R.color.white));
        mBinding.tabSegmentType.setTabTextSize(textSizeNormal);
        mBinding.tabSegmentType.setupWithViewPager(mBinding.viewPager,false);

        mBinding.viewPager.setOffscreenPageLimit(titles.length - 1);
        mBinding.viewPager.setAdapter(adapter);
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] tabStrings){
        boolean isFirstItem = true;
        for (String tabStr : tabStrings) {
            MyTabSegmentTab tab = new MyTabSegmentTab(tabStr);
            if(isFirstItem){
                tab.setTextSize(textSizeSelected);
                isFirstItem = false;
            }else{
                tab.setTextSize(textSizeNormal);
            }
            MallRecommendChildFragment mallRecommendChildFragment = new MallRecommendChildFragment();
            mallRecommendChildFragment.baseViewModel = mViewModel;
            mallRecommendChildFragment.setTitle(title);
            mallRecommendChildFragment.setType(tabStr);
            adapter.addFragment(mallRecommendChildFragment, tabStr);
            tabSegment.addTab(tab);
        }
    }

    /**
     * 更新TabBar样式
     */
    private void refreshTabViewStyle(TabSegment tabSegment,String[] titles,int position) {
        tabSegment.reset();
        resetTab(tabSegment,titles,position);
        tabSegment.notifyDataChanged();
    }

    int textSizeSelectedScale = 0;
    int textSizeNormalScale = 0;
    /**
     * 仅更新 title 若需要改变个数 需要修改方法内逻辑
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(TabSegment tabSegment,String[] titles,
                          int selectedPosition){
        int position = 0;
        if(textSizeSelectedScale == 0 || textSizeNormalScale == 0){
            textSizeSelectedScale = (int) UIUtils.getInstance().scalePx(textSizeSelected);
            textSizeNormalScale = (int) UIUtils.getInstance().scalePx(textSizeNormal);
        }
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            tab.setTextSize(position == selectedPosition ? textSizeSelectedScale :textSizeNormalScale);
            tabSegment.addTab(tab);
            position++;
        }
    }

    public static void openPageWithTitle(BaseFragment baseFragment, String title,
                                         @DrawableRes int backgroundTopId){
        LogUtil.d("title:" + title);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE,title);
        bundle.putInt(KEY_BACKGROUND_TOP,backgroundTopId);
        baseFragment.openPage(MallRecommendFragment.class,bundle);
    }

}
