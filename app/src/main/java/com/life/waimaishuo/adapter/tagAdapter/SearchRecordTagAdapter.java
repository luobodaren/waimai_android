package com.life.waimaishuo.adapter.tagAdapter;

import androidx.annotation.NonNull;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.SearchRecord;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class SearchRecordTagAdapter extends BaseRecyclerAdapter<SearchRecord> {
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_tag_item_search_record;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, SearchRecord item) {
        if (item != null) {
            holder.text(R.id.tv_tag, item.getContent());
        }
    }
}