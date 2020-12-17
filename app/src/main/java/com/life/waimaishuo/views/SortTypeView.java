package com.life.waimaishuo.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.PreferentialFlowTagAdapter;
import com.life.waimaishuo.databinding.LayoutSortBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;

import java.util.List;

public class SortTypeView extends FrameLayout {

    private LayoutSortBinding mBinding;

    private onSortTypeChangeListener mOnSortTypeChangeListener;

    private int textUnCheckColor;
    private int textCheckColor;

    private PreferentialFlowTagAdapter tagAdapter;  //流布局-优惠 的适配器

    private SortPopup mSortPopup;    //综合排序点击弹出pop

    int currentSelectedSort = 1;    //当前选中的排序类型

    @Override
    public View getRootView() {
        return super.getRootView();
    }

    public SortTypeView(@NonNull Context context) {
        this(context,null);
    }

    public SortTypeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SortTypeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute();
        addSortTypeView();
        initFlowTag();
    }

    /**
     * 更新排序
     */
    public void setSortType(SortTypeEnum sortTypeEnum){
        switch (sortTypeEnum){
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

    public void setPreferentialTab(List<String> preferentialList){
        tagAdapter.clearAndAddTags(preferentialList);
    }

    private void addSortTypeView(){
        View sortTypeView = View.inflate(getContext(), R.layout.layout_sort,null);
        mBinding = LayoutSortBinding.bind(sortTypeView);
        addView(sortTypeView);
        initSortTypeView();
    }

    private void initAttribute() {
        textUnCheckColor = getContext().getResources().getColor(R.color.text_uncheck);
        textCheckColor = getContext().getResources().getColor(R.color.text_normal);
    }

    private void initSortTypeView() {
        mBinding.tvSortType.setOnClickListener(this::onTypeClick);
        mBinding.tvDistance.setOnClickListener(this::onDistanceClick);
        mBinding.tvSales.setOnClickListener(this::onSalesClick);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_screen_gray);
        int drawableSize = (int)UIUtils.getInstance(getContext())
                .scalePx(getResources().getDimensionPixelSize(R.dimen.sort_layout_text_size));
        drawable.setBounds(0,0,drawableSize,drawableSize);
        mBinding.tvScreen.setCompoundDrawables(null,null,drawable,null);
    }

    private void initFlowTag() {
        tagAdapter = new PreferentialFlowTagAdapter(getContext());
        tagAdapter.setSelectedPositions(2, 3, 4);   // TODO: 2020/12/16 默认选中 后续需要删除该默认选中

        mBinding.flowlayoutPreferential.setAdapter(tagAdapter);
        mBinding.flowlayoutPreferential.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        mBinding.flowlayoutPreferential.setOnTagSelectListener((parent, position, selectedList) -> {
            mOnSortTypeChangeListener.onPreferentialChange(selectedList);
        });
    }

    /**
     * 综合排序按钮点击处理
     * @param view
     */
    private void onTypeClick(View view){
        if(currentSelectedSort == 1){   //当前为已选中状态
             showSortPop();//显示popWindow 选择排序方法
        }
        setSelectedSort(1);
        if(mOnSortTypeChangeListener != null){
            mOnSortTypeChangeListener.onSortPopShow();
        }
    }

    private void onDistanceClick(View view){
        setSelectedSort(2);
        if(mOnSortTypeChangeListener != null){
            mOnSortTypeChangeListener.onSortTypeChange(SortTypeEnum.DISTANCE);
        }
    }

    private void onSalesClick(View view){
        setSelectedSort(3);
        if(mOnSortTypeChangeListener != null){
            mOnSortTypeChangeListener.onSortTypeChange(SortTypeEnum.SALES);
        }
    }

    /**
     * 设置当前选中的排序类型
     * @param position 1：综合排序   2：距离    3：销量
     */
    private void setSelectedSort(int position){
        currentSelectedSort = position;
        if(position == 1){
            mBinding.tvSortType.setTextColor(textCheckColor);
            mBinding.tvDistance.setTextColor(textUnCheckColor);
            mBinding.tvSales.setTextColor(textUnCheckColor);
        }else if(position == 2){
            mBinding.tvSortType.setTextColor(textUnCheckColor);
            mBinding.tvDistance.setTextColor(textCheckColor);
            mBinding.tvSales.setTextColor(textUnCheckColor);
        }else if(position == 3){
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
    }

    private void initListPopupIfNeed() {
        if (mSortPopup == null) {
            XUISimpleAdapter adapter = XUISimpleAdapter.create(getContext(), SortTypeEnum.getKeyList());
            mSortPopup = new SortPopup(getContext(), XUIListPopup.DIRECTION_NONE,adapter);
            mSortPopup.create((int)UIUtils.getInstance(getContext()).getDisplayMetricsWidth(), 0, (adapterView, view, i, l) -> { //maxHeight = 0 表示warContent
                mSortPopup.dismiss();
                SortTypeEnum sortTypeEnum = SortTypeEnum.get(adapter.getItem(i).getTitle().toString());
                mBinding.tvSortType.setText(sortTypeEnum.getType());
                mOnSortTypeChangeListener.onSortTypeChange(sortTypeEnum);
            });
            mSortPopup.setOnDismissListener(() -> LogUtil.e("排序pop dismiss"));
        }
    }

    public interface onSortTypeChangeListener{
        void onSortPopShow();

        void onSortTypeChange(SortTypeEnum sortTypeEnum);

        void onPreferentialChange(List<Integer> selectedList);
    }


}
