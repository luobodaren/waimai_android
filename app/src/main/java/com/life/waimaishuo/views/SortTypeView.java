package com.life.waimaishuo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.base.views.UiAdapterFrameLayout;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.FlexboxLayoutAdapter;
import com.life.waimaishuo.adapter.tag.ScreenTagAdapter;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.LayoutSortBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.xuexiang.xui.widget.picker.XRangeSlider;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import java.util.ArrayList;
import java.util.List;

public class SortTypeView extends FrameLayout {

    private int sortViewBackground;
    private boolean isShowPreferential;
    private boolean isShowScreen;

    private int flowTagBackground;

    private int textUnCheckColor;
    private int textCheckColor;

    private LayoutSortBinding mBinding;

    private List<String> mPreferentialTabs = new ArrayList<>();//tab title
    private SortPopup mSortPopup;    //综合排序点击弹出pop

    private XUIPopup screenPop;
    private BaseRecyclerAdapter screenRecyclerAdapter;
    private List<String> screenTitleList = new ArrayList<>();
    private onSortTypeChangeListener mOnSortTypeChangeListener;

    private int mCurrentSelectedSort = 1;    //当前选中的排序类型
    private SortTypeEnum mPopSelectedTypeEnum = SortTypeEnum.SCORE;  //pop选中的排序Enum
    private SortTypeEnum mCurrentSortTypeEnum = SortTypeEnum.SCORE; //当前选择的排序Enum

    private FlexboxLayoutAdapter preferentialRecyclerAdapter;

    private int defaultMaxPrice = 100;
    private int defaultMinPrice = 0;
    private int maxPrice = defaultMaxPrice;
    private int minPrice = defaultMinPrice;

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
        isShowPreferential = typedArray.getBoolean(R.styleable.SortTypeView_showPreferential, false);
        isShowScreen = typedArray.getBoolean(R.styleable.SortTypeView_showScreen,false);
        flowTagBackground = typedArray.getResourceId(R.styleable.SortTypeView_flowTagBackground, R.color.background);
        flowTagBackground = typedArray.getResourceId(R.styleable.SortTypeView_flowTagBackground, R.color.background);

        int textUnCheckColorId = typedArray.getResourceId(R.styleable.SortTypeView_sortTextColor, R.color.text_uncheck);
        textUnCheckColor = getContext().getResources().getColor(textUnCheckColorId);
        initAttribute();
        addSortTypeView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initAttribute() {
        textCheckColor = getContext().getResources().getColor(R.color.text_normal);
    }

    private void addSortTypeView() {
        View sortTypeView = View.inflate(getContext(), R.layout.layout_sort, null);
        sortTypeView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        sortTypeView.setBackgroundColor(getContext().getResources().getColor(sortViewBackground));

        mBinding = LayoutSortBinding.bind(sortTypeView);

        mBinding.tvSortType.setTextColor(textUnCheckColor);
        mBinding.tvDistance.setTextColor(textUnCheckColor);
        mBinding.tvSales.setTextColor(textUnCheckColor);
        mBinding.tvScreen.setTextColor(textUnCheckColor);

        addView(sortTypeView);
        initSortTypeView();
        initPreferentialRecycler();
    }

    private void initSortTypeView() {
        mBinding.tvSortType.setOnClickListener(this::onTypeClick);
        mBinding.tvDistance.setOnClickListener(this::onDistanceClick);
        mBinding.tvSales.setOnClickListener(this::onSalesClick);
        mBinding.tvScreen.setOnClickListener(this::onScreenClick);

        if(isShowScreen){
            Drawable drawable;
            if (textUnCheckColor == R.color.text_uncheck) {
                drawable = getResources().getDrawable(R.drawable.ic_screen_gray);
            } else {
                drawable = getResources().getDrawable(R.drawable.ic_screen_white);
            }

            int drawableSize = (int) UIUtils.getInstance()
                    .scalePx(getResources().getDimensionPixelSize(R.dimen.sort_layout_text_size));
            drawable.setBounds(0, 0, drawableSize, drawableSize);
            mBinding.tvScreen.setCompoundDrawables(null, null, drawable, null);
        }else{
            mBinding.tvScreen.setVisibility(GONE);
        }
    }

    private void initPreferentialRecycler() {
        if (!isShowPreferential) {
            LogUtil.d("不显示flowTagLayout");
            return;
        }

        mPreferentialTabs.clear();
        mPreferentialTabs.addAll(Constant.PREFERENTIAL_TABS);

        mBinding.preferentialRecyclerView.setVisibility(VISIBLE);
        mBinding.preferentialRecyclerView.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        mBinding.preferentialRecyclerView.setAdapter(
                preferentialRecyclerAdapter = new FlexboxLayoutAdapter(
                        R.layout.adapter_flexbox_layout_item, mPreferentialTabs.toArray(new String[]{})));

        preferentialRecyclerAdapter.setIsMultiSelectMode(true); //多选
        preferentialRecyclerAdapter.setCancelable(true);    //可取消

        preferentialRecyclerAdapter.setOnItemClickListener((itemView, item, position) -> {
            preferentialRecyclerAdapter.select(position);
            mOnSortTypeChangeListener.onPreferentialChange(position);
        });
    }

    private void refreshPreferentialTab(){
        if(preferentialRecyclerAdapter != null){
            preferentialRecyclerAdapter.resetDataSource(mPreferentialTabs);
        }
    }

    /**
     * 综合排序按钮点击处理
     *
     * @param view
     */
    private void onTypeClick(View view) {
        if (mCurrentSelectedSort == 1) {   //当前为已选中状态
            showSortPop();//显示popWindow 选择排序方法
        }else{
            setSelectedSort(1);
            mCurrentSortTypeEnum = mPopSelectedTypeEnum;
            if (mOnSortTypeChangeListener != null) {
                mOnSortTypeChangeListener.onSortTypeChange(mCurrentSortTypeEnum);
            }
        }
    }

    private void onDistanceClick(View view) {
        setSelectedSort(2);
        mCurrentSortTypeEnum = SortTypeEnum.DISTANCE;
        if (mOnSortTypeChangeListener != null) {
            mOnSortTypeChangeListener.onSortTypeChange(SortTypeEnum.DISTANCE);
        }
    }

    private void onSalesClick(View view) {
        setSelectedSort(3);
        mCurrentSortTypeEnum = SortTypeEnum.SALES;
        if (mOnSortTypeChangeListener != null) {
            mOnSortTypeChangeListener.onSortTypeChange(SortTypeEnum.SALES);
        }
    }

    private void onScreenClick(View view) {
        initScreenPop();
        screenPop.showDown(mBinding.tvScreen);
    }

    private void initScreenPop() {
        if (screenPop == null) {
            screenPop = new XUIPopup(getContext()) {
                int space = 0;
                @Override
                protected Point onShow(View attachedView) {
                    Point point = super.onShow(attachedView);
                    if(space == 0){
                        /*space = (int) UIUtils.getInstance().scalePx(
                                getContext().getResources().getDimensionPixelSize(R.dimen.interval_size_xs));*/
                        space = getContext().getResources().getDimensionPixelSize(R.dimen.interval_size_xs);
                    }
                    point.y += space;
                    return point;
                }
            };
            screenPop.setAnimStyle(XUIPopup.DIRECTION_TOP);
            screenPop.setPreferredDirection(XUIPopup.DIRECTION_BOTTOM);
            screenPop.setContentView(getScreenView());
            screenPop.getPopupWindow().getContentView().findViewById(R.id.box).setBackground(
                    getResources().getDrawable(R.drawable.sr_bg_sort_pop));
        }
    }

    private View getScreenView() {
        int viewWeight = (int) UIUtils.getInstance().getDisplayMetricsWidth();
        int viewHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        View view = View.inflate(getContext(), R.layout.pop_screen, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(viewWeight, viewHeight));
        initScreenRecyclerAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(screenRecyclerAdapter);
        view.findViewById(R.id.bt_reset).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {

            }
        });
        view.findViewById(R.id.bt_finish).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(screenPop != null && screenPop.isShowing()){
                    screenPop.dismiss();
                    mOnSortTypeChangeListener.onScreenChange();
                }
            }
        });
        return view;
    }

    private void initScreenRecyclerAdapter() {
        screenRecyclerAdapter = new BaseRecyclerAdapter<String>(screenTitleList) {
            int titleViewType = 0;
            int flowTabViewType = 1;
            int rangeSliderViewType = 2;

            @Override
            protected int getItemLayoutId(int viewType) {
                if (viewType == titleViewType) {
                    return R.layout.layout_screen_recycler_child_text;
                } else if (viewType == flowTabViewType) {
                    return R.layout.layout_simple_flowtag;
                } else if (viewType == rangeSliderViewType) {
                    return R.layout.layout_range_slider;
                }
                return -1;
            }

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
                if (holder.getItemViewType() == titleViewType) {
//                    ((TextView)holder.itemView).setText(item);
                    holder.text(R.id.tv_title, item);
                }
                if (holder.getItemViewType() == flowTabViewType) {
                    FlowTagLayout flowTagLayout = holder.findViewById(R.id.flowTagLayout);
                    ScreenTagAdapter tagAdapter = (ScreenTagAdapter) flowTagLayout.getAdapter();
                    if(tagAdapter == null){
                        tagAdapter = new ScreenTagAdapter(getContext());
                        flowTagLayout.setAdapter(tagAdapter);
                        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                        flowTagLayout.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
                            @Override
                            public void onItemSelect(FlowTagLayout parent, int tagPosition, List<Integer> selectedList) {
                                LogUtil.d("流标签选中index:" + tagPosition);
                            }
                        });
                        tagAdapter.addTags(Constant.PREFERENTIAL_TABS);
                    }
                    tagAdapter.setSelectedPosition(0);
                }
                if (holder.getItemViewType() == rangeSliderViewType) {
                    XRangeSlider xRangeSlider = holder.findViewById(R.id.rangeSlider);
                    xRangeSlider.setMax(defaultMaxPrice);
                    xRangeSlider.setMin(defaultMinPrice);
                    xRangeSlider.setOnRangeSliderListener(new XRangeSlider.OnRangeSliderListener() {
                        @Override
                        public void onMaxChanged(XRangeSlider slider, int maxValue) {
                            maxPrice = maxValue;
                        }

                        @Override
                        public void onMinChanged(XRangeSlider slider, int minValue) {
                            minPrice = minValue;
                        }
                    });
                }
            }

            @Override
            public int getItemViewType(int position) {
                int dataSize = getData().size();
                if (position == dataSize - 1) {   //最后一个
                    return rangeSliderViewType;
                }
                if (position % 2 == 0) {
                    return titleViewType;
                } else {
                    return flowTabViewType;
                }
            }


            @Override
            public int getItemCount() {
                return mData.size();
            }

        };
    }

    /**
     * 设置当前选中的排序类型
     *
     * @param position 1：综合排序   2：距离    3：销量
     */
    private void setSelectedSort(int position) {
        mCurrentSelectedSort = position;
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
        mSortPopup.showDown(mBinding.tvSortType);
        mOnSortTypeChangeListener.onSortPopShow();
    }

    private void initListPopupIfNeed() {
        if (mSortPopup == null) {
            XUISimpleAdapter adapter = XUISimpleAdapter.create(getContext(), SortTypeEnum.getKeyList());
            mSortPopup = new SortPopup(getContext(), XUIListPopup.DIRECTION_NONE, adapter);
            mSortPopup.create((int) UIUtils.getInstance().getDisplayMetricsWidth(), 0, (adapterView, view, i, l) -> { //maxHeight = 0 表示warContent
                mSortPopup.dismiss();
                mPopSelectedTypeEnum = SortTypeEnum.get(adapter.getItem(i).getTitle().toString());
                mCurrentSortTypeEnum = mPopSelectedTypeEnum;
                mBinding.tvSortType.setText(mCurrentSortTypeEnum.getType());
                mOnSortTypeChangeListener.onSortTypeChange(mCurrentSortTypeEnum);
            });
            mSortPopup.setContentViewBackground(R.drawable.sr_bg_sort_pop); //必须在创建create方法后调用才能生效
            mSortPopup.setOnDismissListener(() -> LogUtil.e("排序pop dismiss"));
        }
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

    /**
     * 设置优惠Tab
     * @param preferentialList
     */
    public void setPreferentialTab(List<String> preferentialList) {
        mPreferentialTabs.clear();
        mPreferentialTabs.addAll(preferentialList);

        refreshPreferentialTab();
    }


    /**
     * 更新筛选内容
     *
     * @param screenData
     */
    public void setScreenData(List<String> screenData) {
        screenTitleList.clear();
        screenTitleList.addAll(screenData);
        if (screenRecyclerAdapter != null) {
            screenRecyclerAdapter.notifyDataSetChanged();
        }
    }

    public SortTypeEnum getCurrentSortTypeEnum() {
        return mCurrentSortTypeEnum;
    }

    /**
     * 若在选中后立刻调用，由于动画原因会存在延时，导致获取到的数据错误
     * @return
     */
    public String[] getSelectedPreferential(){
        if(preferentialRecyclerAdapter != null){
            return preferentialRecyclerAdapter.getMultiContent().toArray(new String[]{});
        }else{
            return new String[]{};
        }
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public interface onSortTypeChangeListener {
        void onSortPopShow();

        void onSortTypeChange(SortTypeEnum sortTypeEnum);

        void onPreferentialChange(int selectedPosition);

        void onScreenChange();
    }
}
