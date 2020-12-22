package com.life.waimaishuo.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.citypicker.CityListAdapter;

import java.util.ArrayList;

public class ShopPreferentialRecyclerAdapter extends RecyclerView.Adapter<ShopPreferentialRecyclerAdapter.BaseViewHolder> {

    private static final int VIEW_TYPE_RED_PACKET = 10;
    private static final int VIEW_TYPE_PREFERENTIAL = 11;
    private static final int VIEW_TYPE_SERVICE = 12;
    private static final int VIEW_TYPE_NOTICE = 13;

    private List<RedPackInfo> redPackInfoList = new ArrayList<>();
    private List<Preferential> preferentialList = new ArrayList<>();
    private List<MerchantService> merchantServiceList = new ArrayList<>();
    private String notice = null;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && redPackInfoList.size() > 0) {
            return VIEW_TYPE_RED_PACKET;
        }
        if (position == 1 && preferentialList.size() > 0) {
            return VIEW_TYPE_PREFERENTIAL;
        }
        if(position == 2 && merchantServiceList.size() > 0){
            return VIEW_TYPE_SERVICE;
        }
        if(position == 3 && notice != null){
            return VIEW_TYPE_NOTICE;
        }
        return super.getItemViewType(position);
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class NoticeViewHolder extends BaseViewHolder {
        TextView tv_notice;

        NoticeViewHolder(View itemView) {
            super(itemView);

            tv_notice = itemView.findViewById(R.id.tv_notice);
        }
    }

    public static class ChilrenRecyclerViewHolder extends BaseViewHolder {
        TextView tv_notice;

        ChilrenRecyclerViewHolder(View itemView) {
            super(itemView);
            tv_notice = itemView.findViewById(R.id.tv_notice);
        }
    }


}
