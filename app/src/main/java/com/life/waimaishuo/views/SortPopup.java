package com.life.waimaishuo.views;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.ListAdapter;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;

public class SortPopup extends XUIListPopup {
    public SortPopup(Context context, int direction, ListAdapter adapter) {
        super(context, direction, adapter);
    }

    public SortPopup(Context context, ListAdapter adapter) {
        super(context, adapter);
    }

    int space = 0;
    @Override
    protected Point onShow(View attachedView) {
        Point point = super.onShow(attachedView);
//        if(mDirection == DIRECTION_NONE){   //无法判断mPreferredDirection
//        }
        if(space == 0){
            space = (int) UIUtils.getInstance(getContext()).scalePx(
                    getContext().getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
        }
        point.y += attachedView.getHeight() + space;
        return point;
    }
}
