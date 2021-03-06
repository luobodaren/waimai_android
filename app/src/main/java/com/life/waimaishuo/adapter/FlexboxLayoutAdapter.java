/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.life.waimaishuo.adapter;

import android.util.SparseBooleanArray;
import android.view.View;

import androidx.annotation.NonNull;

import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.recyclerview.XRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FlexboxLayoutAdapter extends BaseRecyclerAdapter<String> {

    private int mLayoutId;

    private boolean mIsMultiSelectMode;
    private boolean mCancelable;

    private SparseBooleanArray mSparseArray = new SparseBooleanArray();

    public FlexboxLayoutAdapter(int layoutId, String[] data) {
        super(data);
        this.mLayoutId = layoutId;
    }

    public FlexboxLayoutAdapter setIsMultiSelectMode(boolean isMultiSelectMode) {
        mIsMultiSelectMode = isMultiSelectMode;
        return this;
    }

    public FlexboxLayoutAdapter setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return mLayoutId;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
        holder.text(R.id.tv_tag, item);
        if (mIsMultiSelectMode) {
            holder.select(R.id.tv_tag, mSparseArray.get(position));
        } else {
            holder.select(R.id.tv_tag, getSelectPosition() == position);
        }
    }

    @Override
    public XRecyclerAdapter setOnItemClickListener(RecyclerViewHolder.OnItemClickListener<String> listener) {
        return super.setOnItemClickListener(listener);
    }

    /**
     * ??????
     *
     * @param position
     * @return
     */
    public boolean select(int position) {
        return mIsMultiSelectMode ? multiSelect(position) : singleSelect(position);
    }

    /**
     * ??????
     *
     * @param positions
     */
    public void multiSelect(int... positions) {
        if (!mIsMultiSelectMode) {
            return;
        }
        for (int position : positions) {
            multiSelect(position);
        }
    }

    /**
     * ??????
     *
     * @param position
     */
    public boolean multiSelect(int position) {
        if (!mIsMultiSelectMode) {
            return false;
        }
        mSparseArray.append(position, !mSparseArray.get(position));
        notifyItemChanged(position);
        return true;
    }

    /**
     * ??????
     *
     * @param position
     */
    public boolean singleSelect(int position) {
        return singleSelect(position, mCancelable);
    }

    /**
     * ??????
     *
     * @param position
     * @param cancelable
     */
    public boolean singleSelect(int position, boolean cancelable) {
        if (position == getSelectPosition()) {
            if (cancelable) {
                setSelectPosition(-1);
                return true;
            }
        } else {
            setSelectPosition(position);
            return true;
        }
        return false;
    }


    /**
     * @return ?????????????????????
     */
    public String getSelectContent() {
        String value = getSelectItem();
        if (value == null) {
            return "";
        }
        return value;
    }


    /**
     * @return ?????????????????????
     */
    public List<String> getMultiContent() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (mSparseArray.get(i)) {
                list.add(getItem(i));
            }
        }
        return list;
    }

}
