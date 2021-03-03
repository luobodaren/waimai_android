package com.life.waimaishuo.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.linkage.adapter.LinkageSecondaryAdapter;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;
import com.kunminx.linkage.defaults.DefaultLinkagePrimaryAdapterConfig;
import com.kunminx.linkage.defaults.DefaultLinkageSecondaryAdapterConfig;
import com.kunminx.linkage.manager.RecyclerViewScrollHelper;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.LinkagePrimaryAdapter;
import com.life.waimaishuo.adapter.config.ILinkagePrimaryAdapterConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 双列表联动View
 * 修复了左侧recyclerView点击最底下Item无法实现点击效果的问题
 * @param <T>
 */
public class MyLinkageRecyclerView <T extends BaseGroupedItem.ItemInfo> extends RelativeLayout {
    private static final int DEFAULT_SPAN_COUNT = 1;
    private static final int SCROLL_OFFSET = 0;

    private Context mContext;

    private RecyclerView mRvPrimary;
    private RecyclerView mRvSecondary;
    private LinearLayout mLinkageLayout;

    private LinkagePrimaryAdapter mPrimaryAdapter;
    private LinkageSecondaryAdapter mSecondaryAdapter;
    private TextView mTvHeader;
    private FrameLayout mHeaderContainer;

    private List<String[]> mInitGroupNames;
    private List<BaseGroupedItem<T>> mInitItems;

    private List<Integer> mHeaderPositions = new ArrayList<>();
    private int mTitleHeight;
    private int mFirstVisiblePosition;
    private String mLastGroupName;
    private LinearLayoutManager mSecondaryLayoutManager;
    private LinearLayoutManager mPrimaryLayoutManager;

    private boolean mScrollSmoothly = true;

    public MyLinkageRecyclerView(Context context) {
        super(context);
    }

    public MyLinkageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyLinkageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linkage_view, this);
        mRvPrimary = (RecyclerView) view.findViewById(R.id.rv_primary);
        mRvSecondary = (RecyclerView) view.findViewById(R.id.rv_secondary);
        mHeaderContainer = (FrameLayout) view.findViewById(R.id.header_container);
        mLinkageLayout = (LinearLayout) view.findViewById(R.id.linkage_layout);
    }

    private void setLevel2LayoutManager() {
        if (mSecondaryAdapter.isGridMode()) {
            mSecondaryLayoutManager = new GridLayoutManager(mContext,
                    mSecondaryAdapter.getConfig().getSpanCountOfGridMode());
            ((GridLayoutManager) mSecondaryLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (((BaseGroupedItem<T>) mSecondaryAdapter.getItems().get(position)).isHeader) {
                        return mSecondaryAdapter.getConfig().getSpanCountOfGridMode();
                    }
                    return DEFAULT_SPAN_COUNT;
                }
            });
        } else {
            mSecondaryLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        }
        mRvSecondary.setLayoutManager(mSecondaryLayoutManager);
    }

    private void initRecyclerView(ILinkagePrimaryAdapterConfig primaryAdapterConfig,
                                  ILinkageSecondaryAdapterConfig secondaryAdapterConfig) {

        mPrimaryAdapter = new LinkagePrimaryAdapter(mInitGroupNames, primaryAdapterConfig,
                new LinkagePrimaryAdapter.OnLinkageListener() {
                    @Override
                    public void onLinkageClick(LinkagePrimaryViewHolder holder, String title) {
                        mPrimaryAdapter.setClickSelectedPosition(-1);

                        int selectedPosition = holder.getAdapterPosition(); //选中的位置
                        mPrimaryAdapter.setSelectedPosition(selectedPosition);
                        mPrimaryAdapter.setClickSelectedPosition(selectedPosition);

                        if (isScrollSmoothly()) {
                            RecyclerViewScrollHelper.smoothScrollToPosition(mRvSecondary,
                                    LinearSmoothScroller.SNAP_TO_START,
                                    mHeaderPositions.get(selectedPosition));
                        } else {
                            mSecondaryLayoutManager.scrollToPositionWithOffset(
                                    mHeaderPositions.get(selectedPosition), SCROLL_OFFSET);
                        }
                    }
                });

        mPrimaryLayoutManager = new LinearLayoutManager(mContext);
        mRvPrimary.setLayoutManager(mPrimaryLayoutManager);
        mRvPrimary.setAdapter(mPrimaryAdapter);

        mSecondaryAdapter = new LinkageSecondaryAdapter(mInitItems, secondaryAdapterConfig);
        setLevel2LayoutManager();
        mRvSecondary.setAdapter(mSecondaryAdapter);
    }

    private void initLinkageSecondary() {

        // Note: headerLayout is shared by both SecondaryAdapter's header and HeaderView

        if (mTvHeader == null && mSecondaryAdapter.getConfig() != null) {
            ILinkageSecondaryAdapterConfig config = mSecondaryAdapter.getConfig();
            int layout = config.getHeaderLayoutId();
            View view = LayoutInflater.from(mContext).inflate(layout, null);
            mHeaderContainer.addView(view);
            mTvHeader = view.findViewById(config.getHeaderTextViewId());
        }

        if (mInitItems.get(mFirstVisiblePosition).isHeader) {
            mTvHeader.setText(mInitItems.get(mFirstVisiblePosition).header);
        }

        mRvSecondary.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mTitleHeight = mHeaderContainer.getMeasuredHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstPosition = mSecondaryLayoutManager.findFirstVisibleItemPosition();
                int firstCompletePosition = mSecondaryLayoutManager.findFirstCompletelyVisibleItemPosition();
                List<BaseGroupedItem<T>> items = mSecondaryAdapter.getItems();

                // Here is the logic of the sticky:

                if (firstCompletePosition > 0 && (firstCompletePosition) < items.size()
                        && items.get(firstCompletePosition).isHeader) {

                    View view = mSecondaryLayoutManager.findViewByPosition(firstCompletePosition);
                    if (view != null && view.getTop() <= mTitleHeight) {
                        mHeaderContainer.setY(view.getTop() - mTitleHeight);
                    }
                }

                // Here is the logic of group title changes and linkage:

                boolean groupNameChanged = false;

                if (mFirstVisiblePosition != firstPosition && firstPosition >= 0) {
                    mFirstVisiblePosition = firstPosition;
                    mHeaderContainer.setY(0);

                    String currentGroupName = items.get(mFirstVisiblePosition).isHeader
                            ? items.get(mFirstVisiblePosition).header
                            : items.get(mFirstVisiblePosition).info.getGroup();

                    if (TextUtils.isEmpty(mLastGroupName) || !mLastGroupName.equals(currentGroupName)) {
                        mLastGroupName = currentGroupName;
                        groupNameChanged = true;
                        mTvHeader.setText(mLastGroupName);
                    }
                }

                // the following logic can not be perfect, because tvHeader's title may not
                // always equals to the title of selected primaryItem, while there
                // are several groups which has little items to stick group item to tvHeader.
                //
                // To avoid to this extreme situation, my idea is to add a footer on the bottom,
                // to help wholly execute this logic.
                //
                // Note: 2019.5.22 KunMinX

                if (groupNameChanged) {
                    List<String[]> groupNames = mPrimaryAdapter.getStrings();
                    for (int i = 0; i < groupNames.size(); i++) {
                        if (groupNames.get(i)[0].equals(mLastGroupName)) {
                            mPrimaryAdapter.setSelectedPosition(i);
                            RecyclerViewScrollHelper.smoothScrollToPosition(mRvPrimary,
                                    LinearSmoothScroller.SNAP_TO_END, i);
                        }
                    }
                }
            }
        });
    }

    private int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }

    public void init(List<BaseGroupedItem<T>> linkageItems,
                     ILinkagePrimaryAdapterConfig primaryAdapterConfig,
                     ILinkageSecondaryAdapterConfig secondaryAdapterConfig) {

        initRecyclerView(primaryAdapterConfig, secondaryAdapterConfig);

        this.mInitItems = linkageItems;

        String lastGroupName = null;
        List<String[]> groupNames = new ArrayList<>();
        if (mInitItems != null && mInitItems.size() > 0) {
            for (BaseGroupedItem<T> item1 : mInitItems) {
                if (item1.isHeader) {
                    groupNames.add(new String[]{item1.header,item1.info != null ? item1.info.getTitle() : ""});
                    lastGroupName = item1.header;
                }
            }
        }

        if (mInitItems != null) {
            for (int i = 0; i < mInitItems.size(); i++) {
                if (mInitItems.get(i).isHeader) {
                    mHeaderPositions.add(i);
                }
            }
        }

        DefaultGroupedItem.ItemInfo info = new DefaultGroupedItem.ItemInfo(null, lastGroupName);
        BaseGroupedItem<T> footerItem = (BaseGroupedItem<T>) new DefaultGroupedItem(info);
        mInitItems.add(footerItem);

        this.mInitGroupNames = groupNames;
        mPrimaryAdapter.initData(mInitGroupNames);
        mSecondaryAdapter.initData(mInitItems);
        initLinkageSecondary();
    }

    public void setLayoutHeight(float dp) {
        ViewGroup.LayoutParams lp = mLinkageLayout.getLayoutParams();
        lp.height = dpToPx(getContext(), dp);
        mLinkageLayout.setLayoutParams(lp);
    }

    public boolean isGridMode() {
        return mSecondaryAdapter.isGridMode();
    }

    public void setGridMode(boolean isGridMode) {
        mSecondaryAdapter.setGridMode(isGridMode);
        setLevel2LayoutManager();
        mRvSecondary.requestLayout();
    }

    public boolean isScrollSmoothly() {
        return mScrollSmoothly;
    }

    public void setScrollSmoothly(boolean scrollSmoothly) {
        this.mScrollSmoothly = scrollSmoothly;
    }

    public LinkagePrimaryAdapter getPrimaryAdapter() {
        return mPrimaryAdapter;
    }

    public LinkageSecondaryAdapter getSecondaryAdapter() {
        return mSecondaryAdapter;
    }

    public List<Integer> getHeaderPositions() {
        return mHeaderPositions;
    }


}
