package com.life.waimaishuo.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xui.utils.DrawableUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.Hashtable;
import java.util.List;

public class Utils {

    /**
     * 从配置的资源文件中获取到DrawableId
     * @param context
     * @param arrayId
     * @return
     */
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

    /**
     * 获得默认的Recycler装饰器
     * 顶部与底部增加间距 24px(相对于一倍图750*1624)
     * @param context
     * @return
     */
    public static RecyclerView.ItemDecoration getDefaultItemDecoration(Context context){
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

    /**
     * 提供外部预览图片方法
     * @param baseFragment 调用该方法的碎片
     * @param position 默认显示图片列表中图片的位置
     * @param mSelectList 图片列表
     */
    public static void previewSelectedPicture(BaseFragment baseFragment, int position, List<LocalMedia> mSelectList){
        PictureSelector.create(baseFragment).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList);
    }

    /**
     * 检查是否获取权限
     * @param context
     * @param permName  例："android.permission.READ_EXTERNAL_STORAGE"
     * @return
     */
    public static boolean checkPermission(Context context, String permName) {
        // 检查权限是否获取（android6.0及以上系统可能默认关闭权限，且没提示）
        int perm = context.checkCallingOrSelfPermission(permName);
        return perm == PackageManager.PERMISSION_GRANTED;

        /*if (!(permission_readStorage && permission_writeStorage && permission_caremera)) {
            ActivityCompat.requestPermissions(AudioRecordActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
            }, 0x01);
        }*/
    }

    //==========截图===========//

    /**
     * 显示截图结果
     *
     * @param view
     */
    public static void showCaptureBitmap(View view) {
        final MaterialDialog dialog = new MaterialDialog.Builder(view.getContext())
                .customView(R.layout.dialog_drawable_utils_createfromview, true)
                .title("截图结果")
                .build();
        ImageView displayImageView = dialog.findViewById(R.id.createFromViewDisplay);
        Bitmap createFromViewBitmap = DrawableUtils.createBitmapFromView(view);
        displayImageView.setImageBitmap(createFromViewBitmap);

        displayImageView.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    /**
     * 显示截图结果
     */
    public static void showCaptureBitmap(Context context, Bitmap bitmap) {
        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_drawable_utils_createfromview, true)
                .title("截图结果")
                .build();
        ImageView displayImageView = dialog.findViewById(R.id.createFromViewDisplay);
        displayImageView.setImageBitmap(bitmap);

        displayImageView.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    /**
     * 截图RecyclerView
     *
     * @param recyclerView
     * @return
     */
    public static Bitmap getRecyclerViewScreenSpot(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmapCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {
                    bitmapCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }
            // 这个地方容易出现OOM，关键是要看截取RecyclerView的展开的宽高
            bigBitmap = DrawableUtils.createBitmapSafely(recyclerView.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888, 1);
            if (bigBitmap == null) {
                return null;
            }
            Canvas canvas = new Canvas(bigBitmap);
            Drawable background = recyclerView.getBackground();
            //先画RecyclerView的背景色
            if (background instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) background;
                int color = lColorDrawable.getColor();
                canvas.drawColor(color);
            }
            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmapCache.get(String.valueOf(i));
                canvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
            canvas.setBitmap(null);
        }
        return bigBitmap;
    }

    /**
     * 生成简单二维码
     *
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @return BitMap
     */
    public static Bitmap createQRCodeBitmap(String content, int width,int height,
                                            String character_set,String error_correction_level,
                                            String margin,int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 简单生成条形码
     * @param contents      字符串内容
     * @param desiredWidth  条形码宽度
     * @param desiredHeight 条形码高度
     * @param color_black   黑色色块
     * @param color_white   白色色块
     * @return
     */
    public  static Bitmap createBarcodeBitmap(String contents, int desiredWidth, int desiredHeight, int color_black, int color_white) {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result=null;
        try {
            result = writer.encode(contents, BarcodeFormat.CODE_128, desiredWidth,
                    desiredHeight);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? color_black : color_white;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
