package com.life.waimaishuo.util;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.ImageViewInfo;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;

public class PreViewUtil {

    public static void initRecyclerPictureClickListener(BaseActivity activity, BaseQuickAdapter adapter, LinearLayoutManager linearLayoutManager) {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int firstCompletelyVisiblePos = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                computeBoundsBackward(adapter, firstCompletelyVisiblePos,linearLayoutManager);
                PreviewBuilder.from(activity)
                        .setImgs(adapter.getData())
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setProgressColor(R.color.xui_config_color_main_theme)
                        .setType(PreviewBuilder.IndicatorType.Number)
                        .start();
            }
        });
    }

    public static void initRecyclerPictureClickListener(BaseFragment baseFragment, BaseQuickAdapter adapter, LinearLayoutManager linearLayoutManager) {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int firstCompletelyVisiblePos = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                computeBoundsBackward(adapter, firstCompletelyVisiblePos,linearLayoutManager);
                PreviewBuilder.from(baseFragment)
                        .setImgs(adapter.getData())
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setProgressColor(R.color.xui_config_color_main_theme)
                        .setType(PreviewBuilder.IndicatorType.Number)
                        .start();
            }
        });
    }

    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     * recyclerView adapter的数据类型需要为ImageViewInfo
     * viewId 需要为iv_radius
     */
    private static void computeBoundsBackward(BaseQuickAdapter adapter , int firstCompletelyVisiblePos, RecyclerView.LayoutManager layoutManager) {
        for (int i = firstCompletelyVisiblePos; i < adapter.getItemCount(); i++) {
            View itemView = layoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView imageView = itemView.findViewById(R.id.iv);
                imageView.getGlobalVisibleRect(bounds);
            }
            ((ImageViewInfo)adapter.getItem(i)).setBounds(bounds);
        }
    }

}
