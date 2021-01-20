package com.life.waimaishuo.mvvm.view.fragment.mall;

import androidx.viewpager.widget.ViewPager;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.FragmentMallShopAllPreciousBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShopViewModel;
import com.life.waimaishuo.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "商城-全部宝贝")
public class MallShopAllPreciousGoodsFragment extends BaseFragment {

    private FragmentMallShopAllPreciousBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallShopAllPreciousBinding)mViewDataBinding;
        mBinding.setViewModel((MallShopViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_shop_all_precious;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTabSegment();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                refreshTabViewStyle(mBinding.tabSegment,((MallShopViewModel)baseViewModel).getAllPreciousTabTitle(),position);
            }
        });
    }

    private int textSizeSelected = 46;
    private int textSizeNormal = 32;
    private void initTabSegment() {
        String[] titles = ((MallShopViewModel)baseViewModel).getAllPreciousTabTitle();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(mBinding.tabSegment,adapter,titles);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
//        mBinding.tabSegment.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
//        mBinding.tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        mBinding.tabSegment.setTabTextSize(textSizeNormal);
        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);

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
            LogUtil.d(title + "的textSize:" + tab.getTextSize());
            adapter.addFragment(((MallShopViewModel)baseViewModel).getAllPreciousTabFragment(title), title);
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
}
