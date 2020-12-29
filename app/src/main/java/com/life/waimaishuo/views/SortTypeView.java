package com.life.waimaishuo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.databinding.LayoutSortBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;
import com.xuexiang.xui.widget.tabbar.TabSegment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SortTypeView extends FrameLayout {

    private int sortViewBackground;
    private boolean isShowTabSegment;
    private int flowTagBackground;

    private int textUnCheckColor;
    private int textCheckColor;

    private LayoutSortBinding mBinding;

    List<String> tabTypes = new ArrayList<>();//tab title
    private onSortTypeChangeListener mOnSortTypeChangeListener;
    int currentSelectedSort = 1;    //当前选中的排序类型
    private SortTypeEnum currentSortTypeEnum = SortTypeEnum.SCORE; //当前选择的排序Enum

    private SortPopup mSortPopup;    //综合排序点击弹出pop

    @Override
    public View getRootView() {
        return super.getRootView();
    }

    public SortTypeView(@NonNull Context context) {
        this(context, null);
    }

    public SortTypeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortTypeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortTypeView);
        sortViewBackground = typedArray.getResourceId(R.styleable.SortTypeView_sortViewBackground, R.color.background);
        isShowTabSegment = typedArray.getBoolean(R.styleable.SortTypeView_showTabSegment, false);
        flowTagBackground = typedArray.getResourceId(R.styleable.SortTypeView_flowTagBackground, R.color.background);
        flowTagBackground = typedArray.getResourceId(R.styleable.SortTypeView_flowTagBackground, R.color.background);

        int textUnCheckColorId = typedArray.getResourceId(R.styleable.SortTypeView_sortTextColor, R.color.text_uncheck);
        textUnCheckColor = getContext().getResources().getColor(textUnCheckColorId);

        initAttribute();
        addSortTypeView();
        initTabSegment();
    }

    /**
     * 更新排序
     */
    public void setSortType(SortTypeEnum sortTypeEnum) {
        switch (sortTypeEnum) {
            case DISTANCE:
                setSelectedSort(2);
                break;
            case SALES:
                setSelectedSort(3);
                break;
            default:
                setSelectedSort(1);
                break;
        }
    }

    public void setOnSortTypeChangeListener(onSortTypeChangeListener mOnSortTypeChangeListener) {
        this.mOnSortTypeChangeListener = mOnSortTypeChangeListener;
    }

    public void setPreferentialTab(List<String> preferentialList) {
        addTab(mBinding.tabSegment, preferentialList);
    }

    private void addSortTypeView() {
        View sortTypeView = View.inflate(getContext(), R.layout.layout_sort, null);
        sortTypeView.setBackgroundColor(getContext().getResources().getColor(sortViewBackground));

        mBinding = LayoutSortBinding.bind(sortTypeView);

        mBinding.tvSortType.setTextColor(textUnCheckColor);
        mBinding.tvDistance.setTextColor(textUnCheckColor);
        mBinding.tvSales.setTextColor(textUnCheckColor);
        mBinding.tvScreen.setTextColor(textUnCheckColor);

        addView(sortTypeView);
        initSortTypeView();
    }

    private void initAttribute() {
        textCheckColor = getContext().getResources().getColor(R.color.text_normal);
    }

    private void initSortTypeView() {
        mBinding.tvSortType.setOnClickListener(this::onTypeClick);
        mBinding.tvDistance.setOnClickListener(this::onDistanceClick);
        mBinding.tvSales.setOnClickListener(this::onSalesClick);


        Drawable drawable;
        if (textUnCheckColor == R.color.text_uncheck) {
            drawable = getResources().getDrawable(R.drawable.ic_screen_gray);
        } else {
            drawable = getResources().getDrawable(R.drawable.ic_screen_white);
        }

        int drawableSize = (int) UIUtils.getInstance(getContext())
                .scalePx(getResources().getDimensionPixelSize(R.dimen.sort_layout_text_size));
        drawable.setBounds(0, 0, drawableSize, drawableSize);
        mBinding.tvScreen.setCompoundDrawables(null, null, drawable, null);
    }

    private void initTabSegment() {
        if (!isShowTabSegment) {
            LogUtil.d("不显示flowTagLayout");
            return;
        }

        int space = getResources().getDimensionPixelOffset(R.dimen.preferential_item_space);

        mBinding.tabSegment.setVisibility(VISIBLE);
        mBinding.tabSegment.setItemSpaceInScrollMode(space);
        mBinding.tabSegment.setTabTextSize(getResources().getDimensionPixelSize(R.dimen.sort_layout_preferential_text_size));
        addTab(mBinding.tabSegment, tabTypes);
        mBinding.tabSegment.setOnTabClickListener(new TabSegment.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                mOnSortTypeChangeListener.onPreferentialChange(index);
                // TODO: 2020/12/29 添加选中后的背景itemView的背景
            }
        });
    }

    private void addTab(TabSegment tabSegment,
                        List<String> titles) {
        if (!isShowTabSegment) {
            return;
        }
        tabSegment.reset();
        Iterator<String> stringIterator = titles.iterator();
        while (stringIterator.hasNext()) {
            String s = stringIterator.next();
            MyTabSegmentTab tab = new MyTabSegmentTab(s);
            tabSegment.addTab(tab);
        }
        tabSegment.notifyDataChanged();
        TabSegment.TabAdapter adapter = invokeGetAdapted(tabSegment);
        if(adapter != null){
            int size = adapter.getViews().size();
            for (int i = 0; i < size; i++) {
                TextView textView = adapter.getViews().get(i).getTextView();
                textView.setBackgroundResource(R.drawable.sr_bg_10radius_white);
                textView.setPadding(24,16,24,16);
            }
        }else{
            LogUtil.e("反射getAdapter方法失败");
        }
    }

    /**
     * 综合排序按钮点击处理
     *
     * @param view
     */
    private void onTypeClick(View view) {
        if (currentSelectedSort == 1) {   //当前为已选中状态
            showSortPop();//显示popWindow 选择排序方法
        }
        setSelectedSort(1);
        if (mOnSortTypeChangeListener != null) {
            mOnSortTypeChangeListener.onSortTypeChange(currentSortTypeEnum);
        }
    }

    private void onDistanceClick(View view) {
        setSelectedSort(2);
        currentSortTypeEnum = SortTypeEnum.DISTANCE;
        if (mOnSortTypeChangeListener != null) {
            mOnSortTypeChangeListener.onSortTypeChange(SortTypeEnum.DISTANCE);
        }
    }

    private void onSalesClick(View view) {
        setSelectedSort(3);
        currentSortTypeEnum = SortTypeEnum.SALES;
        if (mOnSortTypeChangeListener != null) {
            mOnSortTypeChangeListener.onSortTypeChange(SortTypeEnum.SALES);
        }
    }

    /**
     * 设置当前选中的排序类型
     *
     * @param position 1：综合排序   2：距离    3：销量
     */
    private void setSelectedSort(int position) {
        currentSelectedSort = position;
        if (position == 1) {
            mBinding.tvSortType.setTextColor(textCheckColor);
            mBinding.tvDistance.setTextColor(textUnCheckColor);
            mBinding.tvSales.setTextColor(textUnCheckColor);
        } else if (position == 2) {
            mBinding.tvSortType.setTextColor(textUnCheckColor);
            mBinding.tvDistance.setTextColor(textCheckColor);
            mBinding.tvSales.setTextColor(textUnCheckColor);
        } else if (position == 3) {
            mBinding.tvSortType.setTextColor(textUnCheckColor);
            mBinding.tvDistance.setTextColor(textUnCheckColor);
            mBinding.tvSales.setTextColor(textCheckColor);
        }
    }

    /**
     * 显示排序pop
     */
    private void showSortPop() {
        initListPopupIfNeed();
        mSortPopup.setAnimStyle(XUIPopup.DIRECTION_TOP);
        mSortPopup.setPreferredDirection(XUIPopup.DIRECTION_NONE);
        mSortPopup.show(mBinding.llSortLayout);
        mOnSortTypeChangeListener.onSortPopShow();
    }

    private void initListPopupIfNeed() {
        if (mSortPopup == null) {
            XUISimpleAdapter adapter = XUISimpleAdapter.create(getContext(), SortTypeEnum.getKeyList());
            mSortPopup = new SortPopup(getContext(), XUIListPopup.DIRECTION_NONE, adapter);
            mSortPopup.create((int) UIUtils.getInstance(getContext()).getDisplayMetricsWidth(), 0, (adapterView, view, i, l) -> { //maxHeight = 0 表示warContent
                mSortPopup.dismiss();
                currentSortTypeEnum = SortTypeEnum.get(adapter.getItem(i).getTitle().toString());
                mBinding.tvSortType.setText(currentSortTypeEnum.getType());
                mOnSortTypeChangeListener.onSortTypeChange(currentSortTypeEnum);
            });
            mSortPopup.setOnDismissListener(() -> LogUtil.e("排序pop dismiss"));
        }
    }

    public interface onSortTypeChangeListener {
        void onSortPopShow();

        void onSortTypeChange(SortTypeEnum sortTypeEnum);

        void onPreferentialChange(int selectedPosition);
    }

    /**
     * 获取并调用私有方法
     */
    private TabSegment.TabAdapter invokeGetAdapted(TabSegment tabSegment) {
        try {
            // 获取方法名为showName，参数为String类型的方法
            Class<TabSegment> cls = (Class<TabSegment>) tabSegment.getClass();
            Method method = cls.getDeclaredMethod("getAdapter", null);
            // 若调用私有方法，必须抑制java对权限的检查
            method.setAccessible(true);
            // 使用invoke调用方法，并且获取方法的返回值，需要传入一个方法所在类的对象，new Object[]
            // {"Kai"}是需要传入的参数，与上面的String.class相对应
            TabSegment.TabAdapter adapter = (TabSegment.TabAdapter) method.invoke(tabSegment, null);
            return adapter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
