package com.example.myapplication.mvvm.view.fragment.waimai;


import android.content.Context;
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

import com.bumptech.glide.Glide;
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
import com.example.myapplication.mvvm.vm.waimai.WaimaiViewModel;
import com.example.myapplication.util.DataBindingUtils;
import com.example.myapplication.views.widget.StickyNavigationLayout;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.DividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

import static com.example.myapplication.util.Utils.getFlexboxLayoutManager;
import static com.example.myapplication.util.Utils.getGridLayoutManagerWithHead;
import static com.google.android.material.tabs.TabLayout.MODE_FIXED;

@Page(name = "外卖", anim = CoreAnim.fade)
public class WaimaiFragment extends BaseFragment {

    WaimaiViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaimaiViewModel();
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

        initSlideShow();
        initSearchView();
        initFoodTypeRecycler();

        initMyExclusiveRecycler();
        initLimitedTimeRecycler();

        initNavigationTab();
    }


    @Override
    protected void initArgs() {
        setFitWindow(true);
        setStatusBarLightMode(false);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    private void initFoodTypeRecycler(){
        int spanCount = 5;
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),spanCount
                , LinearLayoutManager.VERTICAL,false);

        fragmentWaimaiBinding.recyclerFoodType.setAdapter(getFoodRecyclerAdapter());
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

    private RecyclerView.Adapter getFoodRecyclerAdapter() {
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

    private void initSearchView(){
        SearchRecord[] searchRecord = mViewModel.getSearchRecord();
        SearchRecordTagWaimaiAdapter mAdapter;
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);
        fragmentWaimaiBinding.searchRecord.setLayoutManager(getFlexboxLayoutManager(getContext()));
        fragmentWaimaiBinding.searchRecord.setAdapter(mAdapter = new SearchRecordTagWaimaiAdapter());
        mAdapter.refresh(searchRecord);
    }

    private void initSlideShow(){
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);
        fragmentWaimaiBinding.sibSimpleUsage
                .setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view,t,position)->{
                    Toast.makeText(getContext(),"点击了轮播图",Toast.LENGTH_SHORT).show();
                })
                .setIsOnePageLoop(false).startScroll();
    }

    private void initNavigationTab() {
        FragmentWaimaiBinding binding = ((FragmentWaimaiBinding)mViewDataBinding);

        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getChildFragmentManager());
        binding.tabLayout.setTabMode(MODE_FIXED);
        String[] titles = mViewModel.getRecommendedTitle();
        for (String title : titles) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(title));
            adapter.addFragment(mViewModel.getRecommendedFragment(), title);
        }
        binding.viewPager.setOffscreenPageLimit(titles.length - 1);
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        binding.stickyNavigationLayout.setOnScrollChangeListener(moveRatio -> {
            // TODO: 2020/12/2 排序按钮可点击
        });
    }


    private void initMyExclusiveRecycler() {
        FragmentWaimaiBinding binding = ((FragmentWaimaiBinding)mViewDataBinding);

        MyBaseRecyclerAdapter<ExclusiveShopData> adapter = getExclusiveRecyclerAdapter();

        View view = View.inflate(getContext(),R.layout.head_cart_view,null);
        ((TextView)view.findViewById(R.id.left_text)).setText("专属早餐");
        ((TextView)view.findViewById(R.id.right_tv)).setText("更多好店");
        ((ImageView)view.findViewById(R.id.right_iv)).setImageResource(R.drawable.ic_arrow_right_gray);
        adapter.addHeaderView(view);

        int spanCount = 2;
        binding.recyclerMyExclusive.setAdapter(adapter);
        binding.recyclerMyExclusive.setLayoutManager(
                getGridLayoutManagerWithHead(getContext(),spanCount));
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

    private MyBaseRecyclerAdapter<ExclusiveShopData> getExclusiveRecyclerAdapter() {
        return new MyBaseRecyclerAdapter<ExclusiveShopData>(R.layout.item_exclusive_shop
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
        binding.recyclerSecondsKill.setLayoutManager(
                getGridLayoutManagerWithHead(getContext(),2));

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
//                        .setNewActivity(true).setAnim(CoreAnim.fade).open(WaimaiFragment.this);
                openPage(MessageFragment.class);
//                openPage(Mess)
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
//                startActivity(new Intent(getContext(), SearchActivity.class));
//                startActivity();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataBindingUtils.removeFragmentCallBack(this);
    }
}
