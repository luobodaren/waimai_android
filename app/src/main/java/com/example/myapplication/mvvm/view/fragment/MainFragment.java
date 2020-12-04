package com.example.myapplication.mvvm.view.fragment;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMainBinding;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.MainViewModel;
import com.example.myapplication.views.widget.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;

@Page(name = "主页",anim = CoreAnim.fade)
public class MainFragment extends BaseFragment {

    FragmentMainBinding mBinding;

    MainViewModel myViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        myViewModel = new MainViewModel();
        return myViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentMainBinding)mViewDataBinding).setViewModel((MainViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initViews() {
        super.initViews();

        mBinding = (FragmentMainBinding)mViewDataBinding;
        initTabAndViewPager();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    private void initTabAndViewPager(){
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setDefaultTabIconPosition(TabSegment.ICON_POSITION_TOP);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
        mBinding.tabSegment.setDefaultNormalColor(getResources().getColor(R.color.view_uncheck));
        mBinding.tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.view_check));
        String[] tabData = myViewModel.getTabDatas();
        int[] tabIconId = myViewModel.getTabIcons();
        List<BaseFragment> tabFragment = ((MainViewModel)baseViewModel).getTabFragment();

        int tabSize = tabData.length;
        for(int i = 0;i < tabSize;i++){
            String tabStr = tabData[i];
            MyTabSegmentTab tab = new MyTabSegmentTab(
                    ContextCompat.getDrawable(getContext(), tabIconId[i]),
                    ContextCompat.getDrawable(getContext(), tabIconId[i]),  // TODO: 2020/11/25 动画添加
                    tabStr,
                    true
            );
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.main_tab_text_size));
            tab.setIconPosition(TabSegment.ICON_POSITION_TOP);
            mBinding.tabSegment.addTab(tab);
            adapter.addFragment(tabFragment.get(i),tabStr);
        }

        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setCurrentItem(0,false);
    }

}
