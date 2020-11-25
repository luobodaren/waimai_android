package com.example.myapplication.mvvm.view.activity;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.mine.MineFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.MainViewModel;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.util.List;

import static com.xuexiang.xui.XUI.getContext;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;

    MainViewModel myViewModel;

    TabSegment.Tab[] components = {null,null,null,null};

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseViewModel setViewModel() {
        myViewModel = new MainViewModel();
        return myViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((ActivityMainBinding)mViewDataBinding).setViewModel((MainViewModel) baseViewModel);
    }

    @Override
    protected void initView() {
        mBinding = (ActivityMainBinding)mViewDataBinding;

        initTabAndViewPager();
    }

    private void initTabAndViewPager(){
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager());

        mBinding.tabSegment.setupWithViewPager(mBinding.viewPager,false);
        mBinding.tabSegment.setHasIndicator(false);
        mBinding.tabSegment.setDefaultTabIconPosition(TabSegment.ICON_POSITION_TOP);
        mBinding.tabSegment.setMode(TabSegment.MODE_FIXED);
        String[] tabData = myViewModel.getTabDatas();
        int[] tabIconId = myViewModel.getTabIcons();
        List<BaseFragment> tabFragment = ((MainViewModel)baseViewModel).getTabFragment();
        
        int tabSize = tabData.length;
        for(int i = 0;i < tabSize;i++){
            String tabStr = tabData[i];
            TabSegment.Tab tab = new TabSegment.Tab(
                    ContextCompat.getDrawable(getContext(), tabIconId[i]),
                    ContextCompat.getDrawable(getContext(), tabIconId[i]),  // TODO: 2020/11/25 动画添加
                    tabStr, true
            );
            tab.setTextSize(getResources().getDimensionPixelSize(R.dimen.main_tab_text_size));
            tab.setIconPosition(TabSegment.ICON_POSITION_TOP);
            mBinding.tabSegment.addTab(tab);
            adapter.addFragment(tabFragment.get(i),tabStr);
        }

        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setCurrentItem(0,false);
    }


}
