package com.life.waimaishuo.mvvm.view.fragment;

import com.life.waimaishuo.R;

public abstract class BaseTabSegmentRecyclerFragment extends BaseRecyclerFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_tag_recycler;
    }

    @Override
    protected void initViews() {
        initTabSegment();
        super.initViews();
    }

    public abstract void initTabSegment();
}
