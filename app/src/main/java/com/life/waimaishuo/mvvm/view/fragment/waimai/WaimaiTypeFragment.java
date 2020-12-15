package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.PreferentialFlowTagAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.FragmentWaimaiTypeBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiTypeViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

@Page(name = "外卖子类型页",anim = CoreAnim.slide)
public class WaimaiTypeFragment extends BaseFragment {

    public static final String BUNDLE_FOOD_TYPE_STR_KEY = "my_food_type";
    private static String mFoodType;

    private WaiMaiTypeViewModel mViewModel;
    private FragmentWaimaiTypeBinding mBinding;

    SelectedPositionRecylerViewAdapter<IconStrData> adapter;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiTypeViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiTypeBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_type;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        mFoodType = getArguments().getString(BUNDLE_FOOD_TYPE_STR_KEY);

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
        setStatusBarShowByType(SHOW_STATUS_BAR);
    }

    @Override
    protected void onFragmentShow() {
        super.onFragmentShow();
    }

    @Override
    protected void initViews() {
        super.initViews();

        initSubtypeRecycler();
        initFlowLayout();
        initShopContent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        adapter.setmSelectedListener((holder, item) -> {
            mBinding.recyclerFoodSubtype.scrollToPosition(adapter.getSelectedPosition());
            handleSelectedSign();
            refreshSortType(item.getIconType());
            refreshShopContent();
        });

        addOnScrollListener(mBinding.recyclerFoodSubtype);
    }

    LinearLayoutManager layoutManager;
    private void initSubtypeRecycler(){
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mBinding.recyclerFoodSubtype.setLayoutManager(layoutManager);

        adapter = getSubtypeRecyclerAdapter();
        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent
                    , @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position == 0){
                    outRect.left = (int)(22* UIUtils.getInstance(getContext()).getHorValue());
                }else{
                    outRect.left = (int)(8* UIUtils.getInstance(getContext()).getHorValue());
                    if(position == state.getItemCount()-1){
                        outRect.right = (int)(22* UIUtils.getInstance(getContext()).getHorValue());
                    }
                }
            }
        });

        //往上移 覆盖部分选中标志 已达到选中标志滚动，背景延伸的效果
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) mBinding.stickyNavigationLayout.getLayoutParams();
        layoutParams.topMargin = -getResources().getDimensionPixelOffset(R.dimen.waimai_subtype_content_bg_radius);
    }

    private SelectedPositionRecylerViewAdapter<IconStrData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecylerViewAdapter<IconStrData>(mViewModel.getSubtypeTitles()){
            @Override
            public int getLayoutId() {
                return R.layout.item_waimai_food_subtype;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setImageResource(R.id.iv_subtype_icon,item.getResImgId());
                holder.setText(R.id.tv_subtype_name,item.getIconType());
                if(selected){
                    holder.setBackgroundRes(R.id.tv_subtype_name,R.drawable.sr_bg_full_corners_white);
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.colorTheme));
                    holder.setVisible(R.id.iv_selected_sign,true);
                    holder.setVisible(R.id.view_tl_tr_24_radius,true);
                }else{
                    holder.setBackgroundColor(R.id.tv_subtype_name,getResources().getColor(R.color.transparent));
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.white));
                    holder.setVisible(R.id.iv_selected_sign,false);
                    holder.setVisible(R.id.view_tl_tr_24_radius,false);
                }
            }
        };
    }

    private void addOnScrollListener(RecyclerView recyclerFoodSubtype) {
        recyclerFoodSubtype.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                handleSelectedSign();
            }
        });
    }

    private int currentSelectedPosition = -1;
    private View selectedView;
    private int halfWidth = -1;
    /**
     * 处理滚动tab底部指示标志
     */
    private void handleSelectedSign(){
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
        if(currentSelectedPosition != adapter.getSelectedPosition()){
            currentSelectedPosition = adapter.getSelectedPosition();
            selectedView = layoutManager.getChildAt(adapter.getSelectedPosition()-firstVisiblePosition);
            LogUtil.d("selectedView getSelectedPosition:" + adapter.getSelectedPosition());
            if(halfWidth == -1){
                halfWidth = selectedView.getWidth()/2;  //仅计算一次
            }
        }

        if(firstVisiblePosition > currentSelectedPosition){ //左滑消失
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tr_24radius_bg);
        }else if(lastVisiblePosition < currentSelectedPosition){    //右滑消失
            setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_24radius_bg);
        }else{
            //选中的itemView显示在屏幕上
            Rect rect = new Rect();
            if(firstVisiblePosition == adapter.getSelectedPosition()){  //第一个为选中的
                selectedView = layoutManager.getChildAt(0);
                selectedView.getGlobalVisibleRect(rect);
                if((rect.right-rect.left) < halfWidth){
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tr_24radius_bg);
                }else{
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_24radius_bg);
                }
            }else if(lastVisiblePosition == adapter.getSelectedPosition()){ //最后一个为选中的
                selectedView = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
                if(selectedView.getGlobalVisibleRect(rect)){
                    //若显示
                    if((rect.right-rect.left) < halfWidth){
                        setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_24radius_bg);
                    }else{
                        setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_24radius_bg);
                    }
                }else{
                    //若不显示
                    setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_24radius_bg);
                }
            }else{  //显示在中间
                setStickyNavigationLayoutBackground(R.drawable.sr_bg_tl_tr_24radius_bg);
            }
        }
    }

    private int currentStickyLayoutBackgroundId = -1;
    private void setStickyNavigationLayoutBackground(@DrawableRes int drawableId){
        if(currentStickyLayoutBackgroundId != drawableId){
            currentStickyLayoutBackgroundId = drawableId;
            mBinding.stickyNavigationLayout.setBackgroundResource(drawableId);
        }
    }

    private void initFlowLayout() {
        PreferentialFlowTagAdapter tagAdapter = new PreferentialFlowTagAdapter(getContext());
        mBinding.stickyView.flowlayoutPreferential.setAdapter(tagAdapter);
        mBinding.stickyView.flowlayoutPreferential.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        mBinding.stickyView.flowlayoutPreferential.setOnTagSelectListener((parent, position, selectedList) -> {
            refreshShopContent();
        });
        tagAdapter.addTags(mViewModel.getPreferential());
        tagAdapter.setSelectedPositions(2, 3, 4);

        Drawable drawable = getResources().getDrawable(R.drawable.ic_screen_gray);
        drawable.setBounds(0,0,
                (int) (getResources().getDimensionPixelOffset(R.dimen.sort_layout_text_size)
                        * UIUtils.getInstance(getContext()).getHorValue()),
                (int) (getResources().getDimensionPixelOffset(R.dimen.sort_layout_text_size)
                        * UIUtils.getInstance(getContext()).getHorValue()));
        mBinding.stickyView.tvScreen.setCompoundDrawables(null,null,drawable,null);
    }

    private void initShopContent() {
        FragmentManager fm= getChildFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();

        ft.add(R.id.adaptive_size_view,mViewModel.getRecommendedFragment(),null);
        ft.commit();
    }

    private void refreshSortType(String typeName){

    }

    /**
     *
     */
    private void refreshShopContent() {

    }

}
