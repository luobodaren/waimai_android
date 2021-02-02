package com.life.waimaishuo.adapter.tag;

import androidx.annotation.NonNull;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.SearchTag;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

public class SearchRecordTagWaimaiAdapter extends BaseRecyclerAdapter<SearchTag> {
    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.adapter_tag_item_waimai_search_record;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, SearchTag item) {
        if (item != null) {
            holder.text(R.id.tv_tag, item.getName());
        }
    }
}
