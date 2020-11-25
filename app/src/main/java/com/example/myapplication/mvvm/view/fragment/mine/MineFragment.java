package com.example.myapplication.mvvm.view.fragment.mine;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RecyclerViewHolder.MyBaseRecyclerAdapter;
import com.example.myapplication.adapter.mine.GoodLogisticsRecyclerAdapter;
import com.example.myapplication.adapter.mine.TopDataRecyclerAdapter;
import com.example.myapplication.adapter.mine.TopDataRecyclerViewHolder;
import com.example.myapplication.databinding.FragmentMineBinding;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.mine.MineViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Page(name = "我的", anim = CoreAnim.fade)
public class MineFragment extends BaseFragment {

    MineViewModel mViewModel;

    RecyclerView mGoodLogisticsRecycler;

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected void initPage() {
        super.initPage();
    }

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

        initTopRecycler();
        initLogisticsRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4, LinearLayoutManager.VERTICAL,false);
        TopDataRecyclerAdapter myBaseRecyclerAdapter
                = new TopDataRecyclerAdapter(R.layout.vertical_data_show_item,mViewModel.getDataList()
                ,mViewModel.getTopRecyclerViewModelList());

        fragmentMineBinding.recyclerTopData.setAdapter(myBaseRecyclerAdapter);
        fragmentMineBinding.recyclerTopData.setLayoutManager(gridLayoutManager);
    }

    private void initLogisticsRecycler(){
        List<GoodLogisticsRecyclerAdapter.Data> data = new ArrayList<>();
        data.add(new GoodLogisticsRecyclerAdapter.Data(R.drawable.ic_wait_payment,"待付款"));
        data.add(new GoodLogisticsRecyclerAdapter.Data(R.drawable.ic_wait_delivery,"待配送"));
        data.add(new GoodLogisticsRecyclerAdapter.Data(R.drawable.ic_in_deliver,"配送中"));
        data.add(new GoodLogisticsRecyclerAdapter.Data(R.drawable.ic_wait_common,"待评价"));
        data.add(new GoodLogisticsRecyclerAdapter.Data(R.drawable.ic_after_sales,"售后/退款"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, LinearLayoutManager.VERTICAL,false);
        GoodLogisticsRecyclerAdapter goodLogisticsRecyclerAdapter = new GoodLogisticsRecyclerAdapter(data);

        mGoodLogisticsRecycler = findViewById(R.id.recycler_good_logistics);
        mGoodLogisticsRecycler.setAdapter(goodLogisticsRecyclerAdapter);
        mGoodLogisticsRecycler.setLayoutManager(gridLayoutManager);
    }

}
