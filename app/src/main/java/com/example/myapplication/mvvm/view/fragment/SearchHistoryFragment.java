package com.example.myapplication.mvvm.view.fragment;

import android.content.Context;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SearchRecordTagAdapter;
import com.example.myapplication.bean.SearchRecord;
import com.example.myapplication.databinding.FragmentSearchBinding;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.SearchFragmentViewModel;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;

@Page(name = "历史搜索内容与发现",anim = CoreAnim.fade)
public class SearchHistoryFragment extends BaseFragment {

    SearchFragmentViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new SearchFragmentViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentSearchBinding)mViewDataBinding).setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initSearchFound();
        initSearchHistory();
    }

    private void initSearchFound(){
        SearchRecord[] searchRecord = mViewModel.getSearchRecord();
        SearchRecordTagAdapter mAdapter;
        FragmentSearchBinding fragmentSearchBinding = ((FragmentSearchBinding)mViewDataBinding);
        fragmentSearchBinding.recyclerViewFound.setLayoutManager(getFlexboxLayoutManager(getContext()));
        fragmentSearchBinding.recyclerViewFound.setAdapter(mAdapter = new SearchRecordTagAdapter());
        mAdapter.refresh(searchRecord);
    }

    private void initSearchHistory(){
        SearchRecord[] searchRecord = mViewModel.getSearchRecord();
        SearchRecordTagAdapter mAdapter;
        FragmentSearchBinding fragmentSearchBinding = ((FragmentSearchBinding)mViewDataBinding);
        fragmentSearchBinding.recyclerViewHistory.setLayoutManager(getFlexboxLayoutManager(getContext()));
        fragmentSearchBinding.recyclerViewHistory.setAdapter(mAdapter = new SearchRecordTagAdapter());
        mAdapter.refresh(searchRecord);
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
