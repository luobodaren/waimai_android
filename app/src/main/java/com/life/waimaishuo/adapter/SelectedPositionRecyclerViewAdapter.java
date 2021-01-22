package com.life.waimaishuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectedPositionRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private View mView;
    private int mSelectedPosition;

    private List<T> data;

    private OnSelectedListener<T> mSelectedListener;

    public SelectedPositionRecyclerViewAdapter(List<T> data) {
        this.data = data;
    }

    public void setSelectedListener(OnSelectedListener<T> mSelectedListener) {
        this.mSelectedListener = mSelectedListener;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mView = LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false);
        return new BaseViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        holder.itemView.setSelected(true);

        final int adapterPosition = holder.getAdapterPosition();

        onBindViewHolder(holder, adapterPosition == mSelectedPosition, data.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新界面
                setSelectedPosition(position);

                if (mSelectedListener != null) {
                    mSelectedListener.onSelectedClick(holder, data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public abstract int getLayoutId(int viewType);

    public abstract void onBindViewHolder(BaseViewHolder holder, boolean selected, T item);

    public interface OnSelectedListener<T> {
        void onSelectedClick(BaseViewHolder holder, T item);
    }
}
