package com.life.waimaishuo.mvvm.view.fragment;

import android.view.View;

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
public abstract class BaseRecyclerFragment extends BaseStatusLoaderFragment {

    protected RecyclerView mRecyclerView;

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

//        showLoading();  //默认显示loading布局   //需要调用

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getRecyclerLayoutManager());
        mRecyclerView.setAdapter(new MyBaseRecyclerAdapter(getItemLayoutId(),getListData(),getVariableId()) {
            @Override
            protected void initView(BaseViewHolder helper, Object item) {
                super.initView(helper, item);
                onRecyclerBindViewHolder(helper,item);
            }
        });
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if(itemDecoration != null){
            mRecyclerView.addItemDecoration(itemDecoration);
        }
    }

    protected abstract int getItemLayoutId();

    protected abstract RecyclerView.LayoutManager getRecyclerLayoutManager();

    protected abstract List getListData();

    protected abstract int getVariableId();

    protected abstract RecyclerView.ItemDecoration getItemDecoration();

    protected abstract void onRecyclerBindViewHolder(BaseViewHolder helper, Object item);

    @Override
    protected View getWrapView() {
        return mViewDataBinding.getRoot().findViewById(R.id.my_ll_content_view);
    }

    @Override
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return null;
    }
}
