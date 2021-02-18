package com.life.waimaishuo.mvvm.view.fragment;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import java.util.List;

/**
 * 带RecyclerView的布局
 * 实现了状态切换
 */
public abstract class BaseRecyclerFragment<T> extends BaseStatusLoaderFragment {

    protected RecyclerView mRecyclerView;

    protected MyBaseRecyclerAdapter<T> recyclerAdapter;

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recycler_padding_start_end;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

//        showLoading();  //默认显示loading布局   //需要调用

        recyclerAdapter = new MyBaseRecyclerAdapter<T>(getItemLayoutId(),getListData(),getVariableId()) {
            @Override
            protected void initView(ViewDataBinding binding, BaseViewHolder helper, T item) {
                super.initView(binding, helper, item);
                onRecyclerBindViewHolder(binding,helper,item);
            }
        };

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getRecyclerLayoutManager());
        mRecyclerView.setAdapter(recyclerAdapter);
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if(itemDecoration != null){
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }

    protected abstract int getItemLayoutId();

    protected abstract RecyclerView.LayoutManager getRecyclerLayoutManager();

    protected abstract List<T> getListData();

    protected abstract int getVariableId();

    protected abstract RecyclerView.ItemDecoration getItemDecoration();

    protected abstract void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding,BaseViewHolder helper, T item);

    @Override
    protected View getWrapView() {
        return mViewDataBinding.getRoot().findViewById(R.id.fl_state);
    }

    @Override
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return null;
    }
}
