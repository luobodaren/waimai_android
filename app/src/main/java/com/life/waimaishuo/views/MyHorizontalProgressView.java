package com.life.waimaishuo.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xuexiang
 * @since 2019-05-12 12:34
 * 在原有的基础上进行了修改
 */
public class MyHorizontalProgressView extends View {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACCELERATE_DECELERATE_INTERPOLATOR, LINEAR_INTERPOLATOR, ACCELERATE_INTERPOLATOR, DECELERATE_INTERPOLATOR, OVERSHOOT_INTERPOLATOR})
    private @interface AnimateType {

    }

    /**
     * animation types supported
     */
    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 0;
    public static final int LINEAR_INTERPOLATOR = 1;
    public static final int ACCELERATE_INTERPOLATOR = 2;
    public static final int DECELERATE_INTERPOLATOR = 3;
    public static final int OVERSHOOT_INTERPOLATOR = 4;
    /**
     * the type of animation
     */
    private int mAnimateType = 0;
    /**
     * the progress of start point
     */
    private float mStartProgress = 0;
    /**
     * the progress of end point
     */
    private float mEndProgress = 60;
    /**
     * the color of start progress
     */
    private int mStartColor = getResources().getColor(R.color.xui_config_color_light_orange);
    /**
     * the color of end progress
     */
    private int mEndColor = getResources().getColor(R.color.xui_config_color_dark_orange);
    /**
     * has track of moving or not
     */
    private boolean trackEnabled = false;
    /**
     * the stroke width of progress
     */
    private int mTrackWidth = 6;
    /**
     * the size of inner text
     */
    private int mProgressTextSize = 48;
    /**
     * the color of inner text
     */
    private int mProgressTextColor;
    /**
     * the color of progress track
     */
    private int mTrackColor = getResources().getColor(R.color.default_pv_track_color);
    /**
     * the duration of progress moving
     */
    private int mProgressDuration = 1200;
    /**
     * show the inner text or not
     */
    private boolean textVisibility = true;
    /**
     * the round rect corner radius
     */
    private int mCornerRadius = 30;
    /**
     * the offset of text padding bottom
     */
    private int mTextPaddingBottomOffset = 5;
    /**
     * moving the text with progress or not
     */
    private boolean isTextMoved = true;

    /**
     * the animator of progress moving
     */
    private ObjectAnimator progressAnimator;
    /**
     * the progress of moving
     */
    private float moveProgress = 0;
    /**
     * the paint of drawing progress
     */
    private Paint progressPaint;
    /**
     * the paint of drawing progress background
     */
    private Paint progressBackgroundPaint;
    /**
     * the gradient of color
     */
    private LinearGradient mShader;
    /**
     * the oval's rect shape
     */
    private RectF mRect;
    private RectF mTrackRect;
    private Interpolator mInterpolator;
    private HorizontalProgressView.HorizontalProgressUpdateListener animatorUpdateListener;
    /**
     * 进度值之前的文字
     */
    private String strBeforeProgress;

    /**
     * 实现解决进度条动画开始时 动画变形
     */
    private PorterDuffXfermode mProgressPorterDuffXfermode;
    /**
     * 实现文字颜色随进度条变化
     */
    private PorterDuffXfermode mTextPorterDuffXfermode;

    //使Dst和Src生效 需要绘制Bitmap
    private Bitmap mDstBitmap;  //内容
    private Canvas mDstCanvas;
    private Bitmap mSrcBitmap;  //背景
    private Canvas mSrcCanvas;
    private Bitmap mTextBitmap;
    private Canvas mTextCanvas;

    public MyHorizontalProgressView(Context context) {
        this(context, null);
    }

    public MyHorizontalProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.HorizontalProgressViewStyle);
    }

    public MyHorizontalProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttrs(context, attrs, defStyleAttr);
        init();
    }

    private void obtainAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressView, defStyleAttr, 0);

        mStartProgress = typedArray.getInt(R.styleable.HorizontalProgressView_hpv_start_progress, 0);
        mEndProgress = typedArray.getInt(R.styleable.HorizontalProgressView_hpv_end_progress, 60);
        mStartColor = typedArray.getColor(R.styleable.HorizontalProgressView_hpv_start_color, getResources().getColor(R.color.xui_config_color_light_orange));
        mEndColor = typedArray.getColor(R.styleable.HorizontalProgressView_hpv_end_color, getResources().getColor(R.color.xui_config_color_dark_orange));
        trackEnabled = typedArray.getBoolean(R.styleable.HorizontalProgressView_hpv_isTracked, false);
        mProgressTextColor = typedArray.getColor(R.styleable.HorizontalProgressView_hpv_progress_textColor, ThemeUtils.resolveColor(getContext(), R.attr.colorAccent));
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_hpv_progress_textSize, getResources().getDimensionPixelSize(R.dimen.default_pv_horizontal_text_size));
        mTrackWidth = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_hpv_track_width, getResources().getDimensionPixelSize(R.dimen.default_pv_trace_width));
        mAnimateType = typedArray.getInt(R.styleable.HorizontalProgressView_hpv_animate_type, ACCELERATE_DECELERATE_INTERPOLATOR);
        mTrackColor = typedArray.getColor(R.styleable.HorizontalProgressView_hpv_track_color, getResources().getColor(R.color.default_pv_track_color));
        textVisibility = typedArray.getBoolean(R.styleable.HorizontalProgressView_hpv_progress_textVisibility, true);
        mProgressDuration = typedArray.getInt(R.styleable.HorizontalProgressView_hpv_progress_duration, 1200);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_hpv_corner_radius, getResources().getDimensionPixelSize(R.dimen.default_pv_corner_radius));
        mTextPaddingBottomOffset = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_hpv_text_padding_bottom, getResources().getDimensionPixelSize(R.dimen.default_pv_corner_radius));
        isTextMoved = typedArray.getBoolean(R.styleable.HorizontalProgressView_hpv_text_movedEnable, true);

        typedArray.recycle();
        moveProgress = mStartProgress;
        setAnimateType(mAnimateType);

        mTrackWidth = (int) UIUtils.getInstance().scalePx(mTrackWidth);
    }

    private void init() {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressBackgroundPaint.setStyle(Paint.Style.FILL);
        progressBackgroundPaint.setColor(mTrackColor);

        mProgressPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mTextPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

        setLayerType(LAYER_TYPE_HARDWARE,progressPaint);    //解决黑色背景
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        updateTheTrack();

        //绘制背景
        if (trackEnabled) {
            canvas.drawRoundRect(mTrackRect, mCornerRadius, mCornerRadius, progressBackgroundPaint);
        }

        //绘制进度条
        progressPaint.setShader(mShader);
        mDstCanvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, progressPaint);
        canvas.drawBitmap(mDstBitmap,0,0,progressPaint);

        progressPaint.setShader(null);
        mSrcCanvas.drawRoundRect(mTrackRect, mCornerRadius, mCornerRadius, progressPaint);
        progressPaint.setXfermode(mProgressPorterDuffXfermode);
        canvas.drawBitmap(mSrcBitmap,0,0,progressPaint);
        progressPaint.setXfermode(null);

        drawProgressText(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mShader = new LinearGradient(getPaddingLeft() - 50, (getHeight() - getPaddingTop()) - 50,
                getWidth() - getPaddingRight(), getHeight() / 2F + getPaddingTop() + mTrackWidth,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);

        //初始化
        mDstBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mDstCanvas = new Canvas(mDstBitmap);
        mSrcBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mSrcCanvas = new Canvas(mSrcBitmap);

    }

    private String preProgressText = "";
    /**
     * draw the progress text
     *
     * @param canvas mCanvas
     */
    private void drawProgressText(Canvas canvas) {

        if (textVisibility) {
            Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setTextSize(UIUtils.getInstance().scalePx(mProgressTextSize));
            mTextPaint.setColor(mProgressTextColor);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            String progressText = strBeforeProgress;
            if(moveProgress != 0){
                progressText += ((int) moveProgress) + "%";
            }
            if (isTextMoved) {
                canvas.drawText(progressText,
                        (getWidth() - getPaddingLeft() - getPaddingRight() - DensityUtils.dp2px(getContext(), 28)) * (moveProgress / 100),
                        getHeight() - getPaddingTop() - mTextPaddingBottomOffset, mTextPaint);
            } else {
                //当文字发生改变时，才进行绘制
                if(!preProgressText.equals(progressText)){

                    mTextBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    mTextCanvas = new Canvas(mTextBitmap);
                    mTextCanvas.drawText(progressText, (getWidth() - getPaddingLeft()) / 2F, getHeight() - getPaddingTop() - mTextPaddingBottomOffset, mTextPaint);
                    mTextPaint.setXfermode(mTextPorterDuffXfermode);

                    mTextPaint.setColor(Color.WHITE);
                    mTextCanvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mTextPaint);
                    canvas.drawBitmap(mTextBitmap,0,0,null);
                    mTextPaint.setXfermode(null);
                }
            }
        }

    }


    /**
     * set progress animate type
     *
     * @param type anim type
     */
    public void setAnimateType(@AnimateType int type) {
        this.mAnimateType = type;
        setObjectAnimatorType(type);
    }

    /**
     * set object animation type by received
     *
     * @param animatorType object anim type
     */
    private void setObjectAnimatorType(int animatorType) {
        switch (animatorType) {
            case ACCELERATE_DECELERATE_INTERPOLATOR:
                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new AccelerateDecelerateInterpolator();
                break;
            case LINEAR_INTERPOLATOR:
                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new LinearInterpolator();
                break;
            case ACCELERATE_INTERPOLATOR:
                if (mInterpolator != null) {
                    mInterpolator = null;
                    mInterpolator = new AccelerateInterpolator();
                }
                break;
            case DECELERATE_INTERPOLATOR:
                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new DecelerateInterpolator();
                break;
            case OVERSHOOT_INTERPOLATOR:
                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new OvershootInterpolator();
                break;
            default:
                break;
        }
    }

    /**
     * set move progress
     *
     * @param progress progress of moving
     */
    public void setProgress(float progress) {
        this.moveProgress = progress;
        refreshTheView();
    }

    public float getProgress() {
        return this.moveProgress;
    }

    /**
     * set start progress
     *
     * @param startProgress start progress
     */
    public void setStartProgress(float startProgress) {
        if (startProgress < 0 || startProgress > 100) {
            throw new IllegalArgumentException("Illegal progress value, please change it!");
        }
        this.mStartProgress = startProgress;
        this.moveProgress = mStartProgress;
        refreshTheView();
    }

    /**
     * set end progress
     *
     * @param endProgress end progress
     */
    public void setEndProgress(float endProgress) {
        if (endProgress < 0 || endProgress > 100) {
            throw new IllegalArgumentException("Illegal progress value, please change it!");
        }
        this.mEndProgress = endProgress;
        refreshTheView();

    }

    /**
     * set start color
     *
     * @param startColor start point color
     */
    public void setStartColor(@ColorInt int startColor) {
        this.mStartColor = startColor;
        mShader = new LinearGradient(getPaddingLeft() - 50, (getHeight() - getPaddingTop()) - 50,
                getWidth() - getPaddingRight(), getHeight() / 2F + getPaddingTop() + mTrackWidth,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);
        refreshTheView();
    }

    /**
     * set end color
     *
     * @param endColor end point color
     */
    public void setEndColor(@ColorInt int endColor) {
        this.mEndColor = endColor;
        mShader = new LinearGradient(getPaddingLeft() - 50, (getHeight() - getPaddingTop()) - 50,
                getWidth() - getPaddingRight(), getHeight() / 2F + getPaddingTop() + mTrackWidth,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);
        refreshTheView();
    }

    /**
     * set the width of progress stroke
     *
     * @param width stroke
     */
    public void setTrackWidth(int width) {
//        this.mTrackWidth = DensityUtils.dp2px(getContext(), width);
        this.mTrackWidth = (int) UIUtils.getInstance().scalePx(DensityUtils.dp2px(getContext(), width));
        refreshTheView();
    }

    /**
     * set track color for progress background
     *
     * @param color bg color
     */
    public void setTrackColor(@ColorInt int color) {
        this.mTrackColor = color;
        refreshTheView();
    }

    /**
     * set text color for progress text
     *
     * @param textColor
     */
    public void setProgressTextColor(@ColorInt int textColor) {
        this.mProgressTextColor = textColor;
    }

    /**
     * set text size for inner text
     *
     * @param size text size
     */
    public void setProgressTextSize(int size) {
        mProgressTextSize = DensityUtils.sp2px(getContext(), size);
        refreshTheView();
    }

    /**
     * set duration of progress moving
     *
     * @param duration
     */
    public void setProgressDuration(int duration) {
        this.mProgressDuration = duration;
    }

    /**
     * set track for progress
     *
     * @param trackAble whether track or not
     */
    public void setTrackEnabled(boolean trackAble) {
        this.trackEnabled = trackAble;
        refreshTheView();
    }

    /**
     * set the visibility for progress inner text
     *
     * @param visibility text visible or not
     */
    public void setProgressTextVisibility(boolean visibility) {
        this.textVisibility = visibility;
        refreshTheView();
    }

    /**
     * set progress text moving with progress view or not
     *
     * @param moved
     */
    public void setProgressTextMoved(boolean moved) {
        this.isTextMoved = moved;
    }

    /**
     * start the progress's moving
     */
    public void startProgressAnimation() {
        progressAnimator = ObjectAnimator.ofFloat(this, "progress", mStartProgress, mEndProgress);
        progressAnimator.setInterpolator(mInterpolator);
        progressAnimator.setDuration(mProgressDuration);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue("progress");
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onHorizontalProgressUpdate(MyHorizontalProgressView.this, progress);
                }

            }

        });
        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onHorizontalProgressStart(MyHorizontalProgressView.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onHorizontalProgressFinished(MyHorizontalProgressView.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        progressAnimator.start();
    }

    /**
     * stop the progress moving
     */
    public void stopProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator.cancel();
            progressAnimator = null;
        }
    }

    /**
     * set the corner radius for the rect of progress
     *
     * @param radius the corner radius
     */
    public void setProgressCornerRadius(int radius) {
        this.mCornerRadius = DensityUtils.dp2px(getContext(), radius);
        refreshTheView();
    }

    /**
     * set the text padding bottom offset
     *
     * @param offset the value of padding bottom
     */
    public void setProgressTextPaddingBottom(int offset) {
        this.mTextPaddingBottomOffset = DensityUtils.dp2px(getContext(), offset);
    }


    /**
     * refresh the layout
     */
    private void refreshTheView() {
        invalidate();
        //requestLayout();
    }

    /**
     * update the oval progress track
     */
    private void updateTheTrack() {
        mRect = new RectF(getPaddingLeft() + mStartProgress * (getWidth() - getPaddingLeft() - getPaddingRight() + 60) / 100,
                getPaddingTop(),
                (getWidth() - getPaddingRight() - 20) * ((moveProgress) / 100),
                getPaddingTop() + mTrackWidth);
        mTrackRect = new RectF(getPaddingLeft(),
                getPaddingTop(),
                (getWidth() - getPaddingRight() - 20),
                getPaddingTop() + mTrackWidth);
    }

    /**
     * 进度条更新监听
     */
    public interface HorizontalProgressUpdateListener {
        /**
         * 进度条开始更新
         *
         * @param view
         */
        void onHorizontalProgressStart(View view);

        /**
         * 进度条更新中
         *
         * @param view
         * @param progress
         */
        void onHorizontalProgressUpdate(View view, float progress);

        /**
         * 进度条更新结束
         *
         * @param view
         */
        void onHorizontalProgressFinished(View view);

    }

    /**
     * set the progress update listener for progress view
     *
     * @param listener update listener
     */
    public void setProgressViewUpdateListener(HorizontalProgressView.HorizontalProgressUpdateListener listener) {
        this.animatorUpdateListener = listener;
    }

    public void setStrBeforeProgress(String strBeforeProgress) {
        this.strBeforeProgress = strBeforeProgress;
    }
}
