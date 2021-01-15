package com.life.waimaishuo.adapter.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;

public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;

    private int mSpanCount;

    private int mItemViewWidth = 0;
    private int mItemSpace = 0; //item 横向剩余空间
    private int mBottomInterval;

    private int centerSpace; //布局中间间距的一半

    /**
     * @param mBottomInterval item bottom的间距
     * @param spanCount 列数
     * */
    public StaggeredDividerItemDecoration(Context context, @DimenRes int mBottomInterval,
                                          int centerInterval, int spanCount) {
        this.context = context;
        this.mBottomInterval = (int) UIUtils.getInstance().scalePx(
                context.getResources().getDimensionPixelOffset(mBottomInterval));
        this.mSpanCount = spanCount;
        centerSpace = centerInterval/2;
        LogUtil.d("centerSpace:" + centerSpace);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        //获取item在span中的下标 0为左 1为右
        int spanIndex = params.getSpanIndex();

        if(mItemSpace == 0){
            if(view.getMeasuredWidth() == 0){
                mItemViewWidth = UIUtils.getInstance().getViewMeasureWidth(view);
            }else{
                mItemViewWidth = view.getMeasuredWidth();
            }
            float screenWidth = UIUtils.getInstance().getDisplayMetricsWidth();
            mItemSpace = (int) (screenWidth/2 - mItemViewWidth);
        }

        if(mBottomInterval == 0){
            mBottomInterval = (int)(UIUtils.getInstance().scalePx(
                    context.getResources().getDimensionPixelSize(R.dimen.interval_size_xs)));
        }

        if(spanIndex == 0){
            outRect.left = mItemSpace - centerSpace;
        } else {
            outRect.left = centerSpace;
        }
        // 下方间隔
        outRect.bottom = mBottomInterval;
    }

}
