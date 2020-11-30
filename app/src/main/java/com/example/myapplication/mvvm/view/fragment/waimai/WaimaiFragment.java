package com.example.myapplication.mvvm.view.fragment.waimai;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.base.utils.LogUtil;
import com.example.base.utils.UIUtils;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SearchRecordTagWaimaiAdapter;
import com.example.myapplication.adapter.mine.FoodTypeRecyclerAdapter;
import com.example.myapplication.bean.SearchRecord;
import com.example.myapplication.databinding.FragmentWaimaiBinding;
import com.example.myapplication.mvvm.view.activity.BaseActivity;
import com.example.myapplication.mvvm.view.activity.SearchActivity;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.view.fragment.SearchHistoryFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.waimai.WaimaiViewModel;
import com.example.myapplication.util.DataBindingUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

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
        FragmentWaimaiBinding fragmentWaimaiBinding = ((FragmentWaimaiBinding)mViewDataBinding);

        FoodTypeRecyclerAdapter myBaseRecyclerAdapter
                = new FoodTypeRecyclerAdapter(R.layout.item_waimai_recycler_food_type,mViewModel.getMyFoodDataList()
                ,mViewModel.getFoodRecyclerViewModelList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, LinearLayoutManager.VERTICAL,false);

        fragmentWaimaiBinding.recyclerFoodType.setAdapter(myBaseRecyclerAdapter);
        fragmentWaimaiBinding.recyclerFoodType.setLayoutManager(gridLayoutManager);
        fragmentWaimaiBinding.recyclerFoodType.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
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


    private FlexboxLayoutManager getFlexboxLayoutManager(Context context){
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 不换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.NOWRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
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
                // TODO: 2020/11/27 跳转搜索页
                LogUtil.e("跳转搜索页");
//                openNewPage(SearchHistoryFragment.class,SearchActivity.class);
                startActivity(new Intent(getContext(), SearchActivity.class));  // FIXME: 2020/11/30 
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
