package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.TypeCountData;
import com.life.waimaishuo.databinding.FragmentMineBinding;
import com.life.waimaishuo.databinding.ItemMineRecyclerMoreRecommendedBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "我的", anim = CoreAnim.fade)
public class MineFragment extends BaseFragment {

    MineViewModel mViewModel;

    RecyclerView mGoodLogisticsRecycler;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initArgs() {
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
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

        MyBaseRecyclerAdapter<TypeCountData> myBaseRecyclerAdapter
                = new MyBaseRecyclerAdapter<TypeCountData>(R.layout.item_vertical_data_show,mViewModel.getmTopDataList()
                , BR.item){};
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4, LinearLayoutManager.VERTICAL,false);

        fragmentMineBinding.recyclerTopData.setAdapter(myBaseRecyclerAdapter);
        fragmentMineBinding.recyclerTopData.setLayoutManager(gridLayoutManager);
    }

    private void initLogisticsRecycler(){
        FragmentMineBinding fragmentMineBinding = ((FragmentMineBinding)mViewDataBinding);

        MyBaseRecyclerAdapter<IconStrData> myBaseRecyclerAdapter =
                new MyBaseRecyclerAdapter<IconStrData>(R.layout.item_mine_recycler_good_logistics,mViewModel.getGoodLogisticsdata()
                        , BR.item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5, LinearLayoutManager.VERTICAL,false);

        fragmentMineBinding.recyclerGoodsLogistics.setAdapter(myBaseRecyclerAdapter);
        fragmentMineBinding.recyclerGoodsLogistics.setLayoutManager(gridLayoutManager);
    }

    private void initMoreRecommendedRecycler() {
        FragmentMineBinding fragmentMineBinding = ((FragmentMineBinding)mViewDataBinding);

        MyBaseRecyclerAdapter<IconStrData> adapter = getFunctionRecyclerAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                4,
                LinearLayoutManager.VERTICAL,
                false);
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
        adapter.addHeaderView(view);

        fragmentMineBinding.recyclerMoreRecommended.setAdapter(adapter);
        fragmentMineBinding.recyclerMoreRecommended.setLayoutManager(gridLayoutManager);
    }

    private MyBaseRecyclerAdapter<IconStrData> getFunctionRecyclerAdapter() {
        return new MyBaseRecyclerAdapter<>
                (R.layout.item_mine_recycler_more_recommended,mViewModel.getMoreRecommenedData(),BR.item);
    }
}
