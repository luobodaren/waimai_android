package com.example.myapplication.mvvm.view.fragment.waimai;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.base.utils.LogUtil;
import com.example.base.utils.UIUtils;
import com.example.myapplication.R;
import com.example.myapplication.adapter.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.SearchRecordTagWaimaiAdapter;
import com.example.myapplication.bean.ExclusiveShopData;
import com.example.myapplication.bean.SearchRecord;
import com.example.myapplication.bean.ui.IconStrData;
import com.example.myapplication.bean.ui.LimitedTimeGoodsData;
import com.example.myapplication.databinding.FragmentWaimaiBinding;
import com.example.myapplication.databinding.ItemWaimaiRecyclerFoodTypeBinding;
import com.example.myapplication.databinding.ItemWaimaiRecyclerFoodTypeSmallBinding;
import com.example.myapplication.mvvm.view.activity.SearchActivity;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.MessageFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaiMaiViewModel;
import com.example.myapplication.util.DataBindingUtils;
import com.example.myapplication.util.StatusBarUtils;
import com.example.myapplication.util.Utils;
import com.example.myapplication.views.MyTabSegmentTab;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.tabbar.TabSegment;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseFragment {

    WaiMaiViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaiMaiViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentWaimaiBinding)mViewDataBinding).setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai;
    }

    @Override
    protected void initViews() {
        super.initViews();

        addCallBack();

        initBanner();
        initSearchView();
        initFoodTypeRecycler();

        initMyExclusiveRecycler();
        initLimitedTimeRecycler();

        initNavigationTab();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        FragmentWaimaiBinding mBinding = ((FragmentWaimaiBinding)mViewDataBinding);
        mBinding.myLlContentView.setOnScrollChangeListener(moveRatio -> {
            if(moveRatio == 1){
                if(isShowStatusBar()){
                    setStatusBarShowByType(HIDE_STATUS_BAR);
                }
            } else {
                if(isHideStatusBar()){
                    setStatusBarShowByType(SHOW_STATUS_BAR);
                }
            }
        });
    }


    @Override
    protected void initArgs() {
        setFitStatusBarHeight(true);
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    private void addCallBack() {
        DataBindingUtils.addCallBack(this, mViewModel.goToSearch, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LogUtil.d("跳转搜索页");
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
                startActivity(new Intent(getContext(), SearchActivity.class));  // FIXME: 2020/11/30
//                startActivity();
            }
        });

        DataBindingUtils.addCallBack(this, mViewModel.goToMessage, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LogUtil.d("跳转消息页");
//                PageOption.to(MessageFragment.class)
//                        .setNewActivity(true).setAnim(CoreAnim.slide).open(WaimaiFragment.this);
                openPage(MessageFragment.class);
//                openPage(Mess)
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
//                startActivity(new Intent(getContext(), SearchActivity.class));
//                startActivity();
            }
        });
    }

    /**
     * 初始化历史搜索View
     */
    private void initSearchView(){
        SearchRecord[] searchRecord = mViewModel.getSearchRecord();
        SearchRecordTagWaimaiAdapter mAdapter;
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);
        fragmentWaimaiBinding.searchRecord.setLayoutManager(Utils.getFlexboxLayoutManager(getContext()));
        fragmentWaimaiBinding.searchRecord.setAdapter(mAdapter = new SearchRecordTagWaimaiAdapter());
        mAdapter.refresh(searchRecord);
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(){
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);
        fragmentWaimaiBinding.simpleImageBanner
                .setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view,t,position)->{
                    Toast.makeText(getContext(),"点击了轮播图",Toast.LENGTH_SHORT).show();
                })
                .setIsOnePageLoop(false).startScroll();
    }

    /**
     * 初始化食物类型Recycler
     */
    private void initFoodTypeRecycler(){
        int spanCount = 5;
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),spanCount
                , LinearLayoutManager.VERTICAL,false);
        BaseRecyclerAdapter<IconStrData> adapter = getFoodRecyclerAdapter();
        adapter.setOnItemClickListener((itemView, item, position) -> {
            if(position == adapter.getItemCount()-1){
                openPage(WaimaiAllTypeFragment.class);
            }
        });
        fragmentWaimaiBinding.recyclerFoodType.setAdapter(adapter);
        fragmentWaimaiBinding.recyclerFoodType.setLayoutManager(gridLayoutManager);
        fragmentWaimaiBinding.recyclerFoodType.addItemDecoration(new RecyclerView.ItemDecoration() {
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
                if(position >= 5){
                    if(position < 10){
                        outRect.top = (int)(40* UIUtils.getInstance(getContext()).getHorValue());
                    }else{
                        outRect.top = (int)(32* UIUtils.getInstance(getContext()).getHorValue());
                    }
                }
            }
        });
    }

    /**
     * 初始化专属recycler
     */
    private void initMyExclusiveRecycler() {
        FragmentWaimaiBinding binding = ((FragmentWaimaiBinding)mViewDataBinding);

        MyBaseRecyclerAdapter<ExclusiveShopData> adapter = getExclusiveRecyclerAdapter();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPage(WaimaiExclusiveFragment.class);
            }
        });

        View view = View.inflate(getContext(),R.layout.head_cart_view,null);
        ((TextView)view.findViewById(R.id.left_text)).setText("专属早餐");
        ((TextView)view.findViewById(R.id.right_tv)).setText("更多好店");
        ((ImageView)view.findViewById(R.id.right_iv)).setImageResource(R.drawable.ic_arrow_right_gray);
//        adapter.addHeaderView(view);

        int spanCount = 2;
        binding.recyclerMyExclusive.setAdapter(adapter);
//        binding.recyclerMyExclusive.setLayoutManager(
//                Utils.getGridLayoutManagerWithHead(getContext(),spanCount));
        binding.recyclerMyExclusive.setLayoutManager(
                Utils.getGridLayoutManagerAdapterHeight(getContext(),spanCount,binding.recyclerMyExclusive));
        binding.recyclerSecondsKill.addItemDecoration(new RecyclerView.ItemDecoration(){
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position >= spanCount){
                    outRect.top = (int)(40* UIUtils.getInstance(getContext()).getHorValue());
                }
            }
        });
    }

    /**
     * 初始化显示秒杀界面
     */
    private void initLimitedTimeRecycler() {
        FragmentWaimaiBinding binding = ((FragmentWaimaiBinding)mViewDataBinding);

        MyBaseRecyclerAdapter<LimitedTimeGoodsData> adapter =
                new MyBaseRecyclerAdapter<LimitedTimeGoodsData>(R.layout.item_simple_goods
                        ,mViewModel.getLimitedTimeGoodsData(),null) {
                    @Override
                    protected void initView(BaseViewHolder helper, LimitedTimeGoodsData item) {
                        Glide.with(WaimaiFragment.this)
                                .load(item.getShopIconStr())
                                .centerCrop()
                                .placeholder(R.drawable.ic_waimai_brand)
                                .into(((ImageView)helper.getView(R.id.iv_goods_img)));
                        helper.setText(R.id.tv_goods_name,item.getShopName());
                    }
                };

        binding.recyclerSecondsKill.setAdapter(adapter);
//        binding.recyclerSecondsKill.setLayoutManager(
//                Utils.getGridLayoutManagerWithHead(getContext(),2));

        binding.recyclerSecondsKill.setLayoutManager(
                Utils.getGridLayoutManagerAdapterHeight(getContext(),2,binding.recyclerSecondsKill));
    }

    private int space;
    private int textSizeSelected;
    private int textSizeNormal;
    /**
     * 初始化粘性导航栏
     */
    private void initNavigationTab() {
        FragmentWaimaiBinding binding = ((FragmentWaimaiBinding)mViewDataBinding);

        space = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_space);
        textSizeSelected = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size_selected);
        textSizeNormal = getResources().getDimensionPixelSize(R.dimen.waimai_tabbar_item_text_size);
        String[] titles = mViewModel.getRecommendedTitle();

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());

        addTab(binding.tabLayout,adapter,titles);   // FIXME: 2020/12/10 第一次进入 拿不到tab 设置的大小
        binding.tabLayout.setHasIndicator(false);
        binding.tabLayout.setMode(TabSegment.MODE_SCROLLABLE);
        binding.tabLayout.setItemSpaceInScrollMode(space);
        binding.tabLayout.setPadding(space, 0, space, 0);
        binding.tabLayout.setDefaultNormalColor(getResources().getColor(R.color.text_tip));
        binding.tabLayout.setDefaultSelectedColor(getResources().getColor(R.color.text_normal));
        binding.tabLayout.setTabTextSize(textSizeNormal);
        binding.tabLayout.setupWithViewPager(binding.viewPager,true);   // FIXME: 2020/12/3 解决第一次进入 文字大小不正确的问题

        binding.viewPager.setOffscreenPageLimit(titles.length - 1);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                refreshTabViewStyle(binding.tabLayout,titles,position);
                // TODO: 2020/12/3 刷新内容

            }
        });
    }

    private BaseRecyclerAdapter<IconStrData> getFoodRecyclerAdapter() {
        return new BaseRecyclerAdapter<IconStrData>(mViewModel.getMyFoodDataList()){

            private int mViewType[] = {1,2};    //1 大图  2 小图

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, IconStrData item) {
                if(holder.getItemViewType() == mViewType[0]){
                    ItemWaimaiRecyclerFoodTypeBinding binding
                            = ItemWaimaiRecyclerFoodTypeBinding.bind(holder.itemView);
                    binding.setItem(item);
                }else{
                    ItemWaimaiRecyclerFoodTypeSmallBinding binding
                            = ItemWaimaiRecyclerFoodTypeSmallBinding.bind(holder.itemView);
                    binding.setItem(item);
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                if(viewType == mViewType[0]){
                    return R.layout.item_waimai_recycler_food_type;
                }else if(viewType == mViewType[1]){
                    return R.layout.item_waimai_recycler_food_type_small;
                }
                return R.layout.item_waimai_recycler_food_type;//默认
            }

            @NonNull
            @Override
            protected RecyclerViewHolder getViewHolder(@NonNull ViewGroup parent, int viewType) {
                return super.getViewHolder(parent, viewType);
            }

            @Override
            public int getItemViewType(int position) {
                if(position > 9){
                    return mViewType[1];
                }else{
                    return mViewType[0];
                }
            }
        };
    }

    private MyBaseRecyclerAdapter<ExclusiveShopData> getExclusiveRecyclerAdapter() {
        return new MyBaseRecyclerAdapter<ExclusiveShopData>(R.layout.item_waimai_exclusive_shop
                ,mViewModel.getExclusiveShopData(),null) {
            @Override
            protected void initView(BaseViewHolder helper, ExclusiveShopData item) {
                helper.setText(R.id.tv_shopName,item.getShopName());
                helper.setText(R.id.tv_recent,item.getRecent());
                Glide.with(WaimaiFragment.this)
                        .load(item.getShopIconStr())
                        .centerCrop()
                        .placeholder(R.drawable.ic_waimai_brand)
                        .into((ImageView)helper.getView(R.id.iv_shopIcon));
            }
        };
    }

    /**
     * 更新TabBar样式
     */
    private void refreshTabViewStyle(TabSegment tabSegment,String[] titles,int position) {
        tabSegment.reset();
        resetTab(tabSegment,titles,position);

        refreshSortType(position);
        tabSegment.notifyDataChanged();
    }

    private void addTab(TabSegment tabSegment, FragmentAdapter<BaseFragment> adapter,
                        String[] titles){
        boolean isFirstItem = true;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(isFirstItem){
                tab.setTextSize(textSizeSelected);
                isFirstItem = false;
            }else{
                tab.setTextSize(textSizeNormal);
            }
            adapter.addFragment(mViewModel.getRecommendedFragment(), title);
            tabSegment.addTab(tab);
        }
    }

    /**
     * 仅更新 title 若需要改变个数 需要重写方法
     * @param tabSegment
     * @param titles
     * @param selectedPosition
     */
    private void resetTab(TabSegment tabSegment,String[] titles,
                          int selectedPosition){
        int position = 0;
        for (String title : titles) {
            MyTabSegmentTab tab = new MyTabSegmentTab(title);
            if(position == selectedPosition){
                tab.setTextSize((int) (textSizeSelected * UIUtils.getInstance(getContext()).getHorValue()));
            }else{
                tab.setTextSize((int) (textSizeNormal * UIUtils.getInstance(getContext()).getHorValue()));
            }
            tabSegment.addTab(tab);
            position++;
        }
    }

    /**
     * 更新排序界面
     */
    private void refreshSortType(int position) {

    }

    /**
     * 点击查询结果之后
     *
     * @param query
     */
    private void onQueryResult(String query) {
        //直接跳转到指定页面
//        openPage(query);
//        try {
//            SearchRecord record = mDBService.queryForColumnFirst("content", query);
//            if (record == null) {
//                record = new SearchRecord().setContent(query).setTime(DateUtils.getNowMills());
//                mDBService.insert(record);
//            } else {
//                record.setTime(DateUtils.getNowMills());
//                mDBService.updateData(record);
//            }
//            refreshRecord();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataBindingUtils.removeFragmentCallBack(this);
    }
}
