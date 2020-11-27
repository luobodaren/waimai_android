package com.example.myapplication.mvvm.view.fragment.mine;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.mine.FunctionRecommendedRecyclerAdapter;
import com.example.myapplication.adapter.mine.GoodsLogisticsRecyclerAdapter;
import com.example.myapplication.adapter.mine.TopDataRecyclerAdapter;
import com.example.myapplication.databinding.FragmentMineBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.mine.MineViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "我的", anim = CoreAnim.fade)
public class MineFragment extends BaseFragment {

    MineViewModel mViewModel;

    RecyclerView mGoodLogisticsRecycler;

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
        super.initViews();

        // TODO: 2020/11/26 改为使用流布局实现
        initTopRecycler();
        initLogisticsRecycler();
        initMoreRecommendedRecycler();
    }

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel =  new MineViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentMineBinding)mViewDataBinding).setViewModel((MineViewModel)baseViewModel);
    }

    private void initTopRecycler(){
        FragmentMineBinding fragmentMineBinding = ((FragmentMineBinding)mViewDataBinding);

        TopDataRecyclerAdapter myBaseRecyclerAdapter
                = new TopDataRecyclerAdapter(R.layout.vertical_data_show_item,mViewModel.getmTopDataList()
                ,mViewModel.getTopRecyclerViewModelList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4, LinearLayoutManager.VERTICAL,false);

        fragmentMineBinding.recyclerTopData.setAdapter(myBaseRecyclerAdapter);
        fragmentMineBinding.recyclerTopData.setLayoutManager(gridLayoutManager);
    }

    private void initLogisticsRecycler(){
        FragmentMineBinding fragmentMineBinding = ((FragmentMineBinding)mViewDataBinding);

        GoodsLogisticsRecyclerAdapter myBaseRecyclerAdapter =
                new GoodsLogisticsRecyclerAdapter(R.layout.item_mine_recycler_good_logistics,mViewModel.getGoodLogisticsdata()
                        ,mViewModel.getGoodLogisticsRecyclerViewModelList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, LinearLayoutManager.VERTICAL,false);

        fragmentMineBinding.recyclerGoodsLogistics.setAdapter(myBaseRecyclerAdapter);
        fragmentMineBinding.recyclerGoodsLogistics.setLayoutManager(gridLayoutManager);
    }

    private void initMoreRecommendedRecycler() {
        FragmentMineBinding fragmentMineBinding = ((FragmentMineBinding)mViewDataBinding);

        FunctionRecommendedRecyclerAdapter myBaseRecyclerAdapter =
                new FunctionRecommendedRecyclerAdapter(R.layout.item_mine_recycler_more_recommended,mViewModel.getMoreRecommenedData()
                        ,mViewModel.getFunctionRecomendedViewModeList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4, LinearLayoutManager.VERTICAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0)
                    return 4;
                return 1;
            }
        });

        View view = View.inflate(getContext(),R.layout.head_text_recycler,null);
        ((TextView)view.findViewById(R.id.tv_head)).setText(getContext().getString(R.string.more_recommended));
        myBaseRecyclerAdapter.addHeaderView(view);

        fragmentMineBinding.recyclerMoreRecommended.setAdapter(myBaseRecyclerAdapter);
        fragmentMineBinding.recyclerMoreRecommended.setLayoutManager(gridLayoutManager);
    }
}
