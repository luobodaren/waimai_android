package com.life.waimaishuo.mvvm.view.fragment.common;

import android.content.Context;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.SearchRecordTagAdapter;
import com.life.waimaishuo.bean.SearchTag;
import com.life.waimaishuo.databinding.FragmentSearchHistoryBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.SearchHistoryViewModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "历史搜索内容与发现",anim = CoreAnim.slide)
public class SearchHistoryFragment extends BaseFragment {

    SearchHistoryViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new SearchHistoryViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentSearchHistoryBinding)mViewDataBinding).setViewModel(mViewModel);
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initSearchFound();
        initSearchHistory();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    private void initSearchFound(){
        SearchTag[] searchTag = mViewModel.getSearchRecord();
        SearchRecordTagAdapter mAdapter;
        FragmentSearchHistoryBinding fragmentSearchBinding = ((FragmentSearchHistoryBinding)mViewDataBinding);
        fragmentSearchBinding.recyclerViewFound.setLayoutManager(getFlexboxLayoutManager(getContext()));
        fragmentSearchBinding.recyclerViewFound.setAdapter(mAdapter = new SearchRecordTagAdapter());
        mAdapter.refresh(searchTag);
    }

    private void initSearchHistory(){
        SearchTag[] searchTag = mViewModel.getSearchRecord();
        SearchRecordTagAdapter mAdapter;
        FragmentSearchHistoryBinding fragmentSearchBinding = ((FragmentSearchHistoryBinding)mViewDataBinding);
        fragmentSearchBinding.recyclerViewHistory.setLayoutManager(getFlexboxLayoutManager(getContext()));
        fragmentSearchBinding.recyclerViewHistory.setAdapter(mAdapter = new SearchRecordTagAdapter());
        mAdapter.refresh(searchTag);
    }

    private FlexboxLayoutManager getFlexboxLayoutManager(Context context){
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 不换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
    }

}
