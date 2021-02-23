package com.life.waimaishuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public abstract class SelectedPositionRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private View mView;

    private boolean mCancelable = false;

    private int mSelectedPosition;

    private List<T> data;

    private OnSelectedListener<T> mSelectedListener;


    public SelectedPositionRecyclerViewAdapter(List<T> data) {
        this.data = data;
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

        holder.itemView.setOnClickListener(v -> {
            if(mSelectedPosition != position){  //选中不同位置
                //更新界面
                setSelectedPosition(position);

                if (mSelectedListener != null) {
                    mSelectedListener.onSelectChangeClick(holder, data.get(position),false);
                }
            }else{
                if(mCancelable){    //再次点击 切设置了可取消状态
                    setSelectedPosition(-1);
                    if(mSelectedListener != null){
                        mSelectedListener.onSelectChangeClick(holder,data.get(position),true);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public boolean isCancelable() {
        return mCancelable;
    }

    public void setCancelable(boolean mCancelable) {
        this.mCancelable = mCancelable;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
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

    public abstract int getLayoutId(int viewType);

    public abstract void onBindViewHolder(BaseViewHolder holder, boolean selected, T item);

    public interface OnSelectedListener<T> {
        void onSelectChangeClick(BaseViewHolder holder, T item, boolean isCancel);
    }
}
