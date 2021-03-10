package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.ui.MallQuickWindowData;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallMainTypeBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.common.LimitedTimeGoodsFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallMainTypeViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;

@Page(name = "商城-全部")
public class MallMainTypeFragment extends BaseFragment {

    FragmentMallMainTypeBinding mBinding;
    MallMainTypeViewModel mViewModel;

    FragmentAdapter<BaseFragment> viewPagerAdapter;  //
    SelectedPositionRecyclerViewAdapter<TypeDescribeValue> stickyRecyclerAdapter;
    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallMainTypeViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallMainTypeBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_main_type;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(false);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_NO_HANDLE);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initBanner();

        initFourQuickWindowRecycler();

        initStickyRecycler();
        initAdaptiveSizeViewPager();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        fourQuickWindowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String title = mViewModel.getMallQuickWindowDataList().get(position).getTitle();
                int backgroundTopDrawableId = 0;
                if(position == 0){
                    LimitedTimeGoodsFragment.openPageWithTitle(
                            MallMainTypeFragment.this, Constant.PAGE_TYPE_MALL,R.drawable.sr_bg_gradient_mall_limited_time);
                    return;
                }else if(position == 1){
                    backgroundTopDrawableId = R.mipmap.png_bg_good_goods;
                }else if(position == 2){
                    backgroundTopDrawableId = R.mipmap.png_bg_good_shop;
                }else if(position == 3){
                    backgroundTopDrawableId = R.mipmap.png_bg_fashion_accessories;
                }
                MallRecommendFragment.openPageWithTitle(MallMainTypeFragment.this,title,backgroundTopDrawableId);
            }
        });
    }

    private void initBanner(){
        mBinding.bannerLayout.setItemLayoutId(R.layout.adapter_banner_image_item_mall_main);
        mBinding.bannerLayout.setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view, t, position) -> {
                })
                .setIsOnePageLoop(false).startScroll();
    }

    private void initFourQuickWindowRecycler(){
        initFourQuickWindowRecyclerAdapter();
        mBinding.recyclerFourQuickWindow.setLayoutManager(
                new GridLayoutManager(requireContext(),2,
                        LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerFourQuickWindow.setAdapter(fourQuickWindowAdapter);
        mBinding.recyclerFourQuickWindow.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(16);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = interval;
            }
        });
    }

    MyBaseRecyclerAdapter<MallQuickWindowData> fourQuickWindowAdapter;
    private void initFourQuickWindowRecyclerAdapter(){
        fourQuickWindowAdapter = new MyBaseRecyclerAdapter<MallQuickWindowData>(R.layout.item_recycler_mall_quick_window
                ,mViewModel.getMallQuickWindowDataList()){
            int textSize = (int) UIUtils.getInstance().scalePx(22);
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, MallQuickWindowData item) {
                View head;
                if(helper.getAdapterPosition() == 0){   //秒杀抢购
                    head = View.inflate(requireContext(),R.layout.head_mall_quick_window_second_kill,null);
                    ((FrameLayout)helper.getView(R.id.fl_head)).addView(head);
                }else if(helper.getAdapterPosition() == 1){ //发现好物
                    head = View.inflate(requireContext(),R.layout.head_mall_quick_window_find_goods,null);
                    ((FrameLayout)helper.getView(R.id.fl_head)).addView(head);
                }else if(helper.getAdapterPosition() == 2){ //每日好店
                    head = View.inflate(requireContext(),R.layout.head_mall_quick_window_daily_good_shop,null);
                    ((FrameLayout)helper.getView(R.id.fl_head)).addView(head);
                } else if(helper.getAdapterPosition() == 3){    //时尚配饰
                    head = View.inflate(requireContext(),R.layout.head_mall_quick_window_fashion_accessories,null);
                    ((FrameLayout)helper.getView(R.id.fl_head)).addView(head);
                }

                setOneGoodsData(helper.getView(R.id.iv_left),
                        helper.getView(R.id.tv_left_current_price),
                        helper.getView(R.id.tv_left_pre_price),
                        item.getLeftImgUrl(),
                        item.getLeftCurrentPrice(),
                        item.getLeftPrePrice());
                setOneGoodsData(helper.getView(R.id.iv_right),
                        helper.getView(R.id.tv_right_current_price),
                        helper.getView(R.id.tv_right_pre_price),
                        item.getRightImgUrl(),
                        item.getRightCurrentPrice(),
                        item.getRightPrePrice());
            }

            private void setOneGoodsData(ImageView imageView, TextView currentPrice,
                                         TextView prePrice, String url, String cp, String pp){
                Glide.with(requireContext())
                        .load(url)
                        .placeholder(R.drawable.ic_waimai_brand)
                        .centerCrop()
                        .into(imageView);

                currentPrice.setText(TextUtil.getAbsoluteSpannable("￥" + cp,textSize,0,1));
                prePrice.setText(TextUtil.getStrikeThroughSpanSpannable("￥" + pp,0,4));
            }
        };
    }

    private void initStickyRecycler(){
        stickyRecyclerAdapter = new SelectedPositionRecyclerViewAdapter<TypeDescribeValue>(mViewModel.getStickTabList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_mall_sticky;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, TypeDescribeValue item) {
                if(selected){
                    holder.setTextColor(R.id.tv_type,holder.itemView.getContext().getResources().getColor(R.color.colorTheme));
                    holder.setTextColor(R.id.tv_describe,getResources().getColor(R.color.white));
                    holder.setBackgroundRes(R.id.tv_describe,R.drawable.sr_bg_full_corners_theme);
                }else{
                    holder.setTextColor(R.id.tv_type,holder.itemView.getContext().getResources().getColor(R.color.text_normal));
                    holder.setTextColor(R.id.tv_describe,getResources().getColor(R.color.text_uncheck));
                    holder.setBackgroundRes(R.id.tv_describe,0);
                }

                holder.setText(R.id.tv_type,item.getType());
                holder.setText(R.id.tv_describe,item.getDescribe());
            }
        };
        stickyRecyclerAdapter.setSelectedListener((holder, item, isCancel) -> {
            mBinding.adaptiveSizeView.setCurrentItem(viewPagerAdapter.getTitleList().indexOf(item.getType()),true);
            LogUtil.d("选择了" + item.getType());
        });
        mBinding.stickyView.setLayoutManager(new GridLayoutManager(requireContext(),4 ,LinearLayoutManager.VERTICAL,false));
        mBinding.stickyView.setAdapter(stickyRecyclerAdapter);
        mBinding.stickyView.addItemDecoration(new RecyclerView.ItemDecoration() {
            Paint mBgPaint;
            boolean hasInitPaint = false;
            int dividerTopSpace;
            int dividerBottomSpace;

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);

                if(!hasInitPaint){
                    hasInitPaint = true;
                    mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mBgPaint.setColor(getResources().getColor(R.color.divider_2));
                    dividerTopSpace = (int) UIUtils.getInstance().scalePx(44);
                    dividerBottomSpace = (int) UIUtils.getInstance().scalePx(62);
                }

                //LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
                int top,bottom,left,right;
                for(int i=0;i<parent.getChildCount();i++){
                    View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                            .getLayoutParams();
                    int position = params.getViewLayoutPosition();
                    // 判断是否位于边缘
                    if(position == 0) continue;
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                    top = child.getTop() + dividerTopSpace; //添加顶部与底部距离 44 62   //+ -margin逻辑待确定
                    bottom = child.getBottom() - dividerBottomSpace;
                    left = child.getLeft() - 2;
                    right = left + 2;
                    c.drawRect(left,top,right,bottom,mBgPaint);
                }
            }
        });
    }

    private void initAdaptiveSizeViewPager(){
        viewPagerAdapter = new FragmentAdapter<>(getChildFragmentManager());

        mViewModel.addViewPagerFragment(viewPagerAdapter);
        mBinding.adaptiveSizeView.setOffscreenPageLimit(mViewModel.getStickTabList().size() - 1);
        mBinding.adaptiveSizeView.setAdapter(viewPagerAdapter);
        mBinding.adaptiveSizeView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { LogUtil.d("onPageScrolled"); }
            @Override
            public void onPageSelected(int position) {
                LogUtil.d("onPageSelected");
                stickyRecyclerAdapter.setSelectedPosition(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) { LogUtil.d("onPageScrollStateChanged"); }
        });

    }

}
