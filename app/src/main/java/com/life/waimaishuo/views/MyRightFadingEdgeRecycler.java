package com.life.waimaishuo.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyRightFadingEdgeRecycler extends RecyclerView {
    public MyRightFadingEdgeRecycler(@NonNull Context context) {
        super(context);
    }

    public MyRightFadingEdgeRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRightFadingEdgeRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
//        return super.getLeftFadingEdgeStrength();
        return 0;
    }
}
