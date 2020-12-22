package com.life.waimaishuo.adapter.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;


/**
 * 列表分割线
 *
 * @author xuexiang
 * @since 2018/12/30 下午6:29
 */
public class CityPickerDividerItemDecoration extends RecyclerView.ItemDecoration {
    private float dividerHeight;
    private Paint mPaint;

    public CityPickerDividerItemDecoration(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.mCpDividerBackground, typedValue, true);
        mPaint.setColor(context.getResources().getColor(typedValue.resourceId));
        dividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = (int) dividerHeight;
    }

    boolean firstDraw = true;
    float right;
    float left;
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();

//        left = view.getLeft()+view.getPaddingStart();
//        right = view.getRight() - UIUtils.getInstance(view.getContext()).dpToPx(36);
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            if(firstDraw){
                left = view.getPaddingStart();
                right = parent.getWidth() - UIUtils.getInstance(view.getContext()).dpToPx(36);
            }
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
