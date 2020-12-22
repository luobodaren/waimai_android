package com.life.waimaishuo.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.life.waimaishuo.R;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.banner.recycler.BannerLayout;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.strategy.DiskCacheStrategyEnum;

import java.util.Arrays;
import java.util.List;

public class BaseBannerAdapter extends BaseRecyclerAdapter<String> {

    private int layoutId;

    /**
     * 默认加载图片
     */
    private ColorDrawable mColorDrawable;

    /**
     * 是否允许进行缓存
     */
    private boolean mEnableCache = true;

    private BannerLayout.OnBannerItemClickListener mOnBannerItemClickListener;


    public BaseBannerAdapter(int layoutId) {
        super();
        this.layoutId = layoutId;
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    public BaseBannerAdapter(List<String> list, int layoutId) {
        super(list);
        this.layoutId = layoutId;
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    public BaseBannerAdapter(String[] list, int layoutId) {
        super(Arrays.asList(list));
        this.layoutId = layoutId;
        mColorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    /**
     * 适配的布局
     *
     * @param viewType
     * @return
     */
    @Override
    public int getItemLayoutId(int viewType) {
        return layoutId;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     * @param imgUrl
     */
    @Override
    public void bindData(@NonNull RecyclerViewHolder holder, final int position, String imgUrl) {
        ImageView imageView = holder.findViewById(R.id.iv_item);

        if (!TextUtils.isEmpty(imgUrl)) {
            ImageLoader.get().loadImage(imageView, imgUrl, mColorDrawable,
                    mEnableCache ? DiskCacheStrategyEnum.RESOURCE : DiskCacheStrategyEnum.NONE);
        } else {
            imageView.setImageDrawable(mColorDrawable);
        }

        imageView.setOnClickListener(v -> {
            if (mOnBannerItemClickListener != null) {
                mOnBannerItemClickListener.onItemClick(position);
            }
        });
    }

    /**
     * 设置是否允许缓存
     *
     * @param enableCache
     * @return
     */
    public BaseBannerAdapter enableCache(boolean enableCache) {
        mEnableCache = enableCache;
        return this;
    }

    /**
     * 获取是否允许缓存
     *
     * @return
     */
    public boolean getEnableCache() {
        return mEnableCache;
    }

    public ColorDrawable getColorDrawable() {
        return mColorDrawable;
    }

    public BaseBannerAdapter setColorDrawable(ColorDrawable colorDrawable) {
        mColorDrawable = colorDrawable;
        return this;
    }

    public BaseBannerAdapter setOnBannerItemClickListener(BannerLayout.OnBannerItemClickListener onBannerItemClickListener) {
        mOnBannerItemClickListener = onBannerItemClickListener;
        return this;
    }

}
