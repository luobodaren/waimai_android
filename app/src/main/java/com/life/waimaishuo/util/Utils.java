package com.life.waimaishuo.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

public class Utils {

    public static int[] getDrawableResIdFormArray(Context context, @ArrayRes int arrayId){
        TypedArray ar = context.getResources().obtainTypedArray(arrayId);
        int length = ar.length();
        int[] drawableIds = new int[length];
        for(int i = 0; i < length; i++){
            drawableIds[i] = ar.getResourceId(i,0);
        }
        ar.recycle();
        return drawableIds;
    }

    public static FlexboxLayoutManager getFlexboxLayoutManager(Context context) {
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 按正常方向换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
    }

    public static GridLayoutManager getGridLayoutManagerWithHead(Context context,int spanCount){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,
                spanCount,
                LinearLayoutManager.VERTICAL,
                false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0)
                    return spanCount;
                return 1;
            }
        });

        return gridLayoutManager;
    }

    /**
     * 自适应高度的GridLayoutManager
     * @param context
     * @param spanCount
     * @param recyclerView
     * @return
     */
    public static GridLayoutManager getGridLayoutManagerAdapterHeight(Context context,int spanCount, RecyclerView recyclerView){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                context,
                spanCount,
                LinearLayoutManager.VERTICAL,
                false){
            @Override
            public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
                super.onMeasure(recycler, state, widthSpec, heightSpec);
                int measuredWidth = recyclerView.getMeasuredWidth();
                int measuredHeight = recyclerView.getMeasuredHeight();
                int myMeasureHeight = 0;
                int count = state.getItemCount();
                for (int i = 0; i < count; i++) {
                    View view = recycler.getViewForPosition(i);
                    if (view != null) {
                        if ( i % 3 == 0) {
                            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                                    getPaddingLeft() + getPaddingRight(), p.width);
                            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                                    getPaddingTop() + getPaddingBottom(), p.height);
                            view.measure(childWidthSpec, childHeightSpec);
                            myMeasureHeight += view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                        }
                        recycler.recycleView(view);
                    }
                }
                setMeasuredDimension(measuredWidth, Math.max(measuredHeight, myMeasureHeight));
            }
        };
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0)
                    return spanCount;
                return 1;
            }
        });
        return gridLayoutManager;
    }

    public static RecyclerView.ItemDecoration getItemDecoration(Context context){
        return new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(context.getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != RecyclerView.NO_POSITION){
                    outRect.top = interval;
                    if(position == state.getItemCount()-1){
                        outRect.bottom = interval;
                    }
                }
            }
        };
    }

    /**
     * 清除图片选择后的缓存
     * //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
     *  PictureFileUtils.deleteCacheDirFile(MainActivity.this);
     */

    //==========图片选择===========//
    /**
     * 获取图片选择的配置
     *
     * @param fragment
     * @return
     */
    public static PictureSelectionModel getPictureSelector(Fragment fragment, int maxSelectNum) {
        /*return PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .theme(SettingSPUtils.getInstance().isUseCustomTheme() ? R.style.XUIPictureStyle_Custom : R.style.XUIPictureStyle)  //自定义主题
                .maxSelectNum(maxSelectNum)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .compress(true)
                .previewEggs(true);*/
        return PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.XUIPictureStyle)
                .maxSelectNum(maxSelectNum)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .compress(true)
                .previewEggs(true);
    }

    public static PictureSelectionModel getPictureSelector(Activity activity, int maxSelectNum) {
        return PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.XUIPictureStyle)
                .maxSelectNum(maxSelectNum)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .compress(true)
                .previewEggs(true);
    }

    /**
     * 单独启动拍照 根据PictureMimeType自动识别
     * @param baseFragment
     */
    public static void openCameraTakePhoto(BaseFragment baseFragment, int requestCode){
        PictureSelector.create(baseFragment)
                .openCamera(PictureMimeType.ofImage())
                .forResult(requestCode);
    }

    /**
     * 视频
     * @param baseFragment
     */
    public static void openCameraTakeVideo(BaseFragment baseFragment, int requestCode){
        PictureSelector.create(baseFragment)
                .openCamera(PictureMimeType.ofVideo())
                .forResult(requestCode);
    }

}
