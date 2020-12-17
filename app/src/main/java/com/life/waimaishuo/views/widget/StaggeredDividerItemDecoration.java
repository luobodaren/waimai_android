package com.life.waimaishuo.views.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;

public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int mBottomInterval;
    private int mSpanCount;

    private int mItemViewWidth = 0;
    private int mCenterInterval = 0;
    /**
     * @param mBottomInterval item的间距
     * @param spanCount 列数
     * */
    public StaggeredDividerItemDecoration(Context context, @DimenRes int mBottomInterval, int spanCount) {
        this.context = context;
        this.mBottomInterval = context.getResources().getDimensionPixelOffset(mBottomInterval);
        this.mSpanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 获取item在span中的下标 0为左 1为右
//        int spanIndex = params.getSpanIndex();

        float screenWidth = UIUtils.getInstance(context).getDisplayMetricsWidth();
        if(mItemViewWidth == 0){
            if(view.getMeasuredWidth() == 0){
                mItemViewWidth = UIUtils.getInstance(context).getViewMeasureWidth(view);
            }else{
                mItemViewWidth = view.getMeasuredWidth();
            }
            LogUtil.d("mItemViewWidth = " + mItemViewWidth);
        }
        if(mCenterInterval == 0){
            mCenterInterval = (int)(UIUtils.getInstance(context).scalePx((screenWidth/2 - mItemViewWidth)/2));
            LogUtil.e("mCenterInterval = " + mCenterInterval);
        }

        outRect.left = mCenterInterval;
        // 下方间隔
        outRect.bottom = mBottomInterval;
    }

}
