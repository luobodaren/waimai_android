package com.life.waimaishuo.views;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.ListAdapter;

import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;

public class SortPopup extends XUIListPopup {
    public SortPopup(Context context, int direction, ListAdapter adapter) {
        super(context, direction, adapter);
    }

    public SortPopup(Context context, ListAdapter adapter) {
        super(context, adapter);
    }

    @Override
    protected Point onShow(View attachedView) {
        Point point = super.onShow(attachedView);
        if(mDirection == DIRECTION_NONE){   //无法判断mPreferredDirection
            point.y += attachedView.getHeight();
        }
        return point;
    }
}
