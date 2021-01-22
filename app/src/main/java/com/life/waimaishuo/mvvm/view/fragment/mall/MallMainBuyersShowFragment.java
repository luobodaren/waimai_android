package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.BuyersShow;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallMainTypeViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "商城-首页买家秀")
public class MallMainBuyersShowFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MyBaseRecyclerAdapter<BuyersShow> adapter;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        adapter.setOnItemClickListener((adapter, view, position) -> {
            MallBuyersShowDetailFragment.openPageWhitData(this,new BuyersShow("","","",null,""));
        });
    }

    /**
     * 设置页面类型 根据类型获取数据
     */
    public void setTypeKey() {

    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler);
        int spanCount = 2;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                spanCount, StaggeredGridLayoutManager.VERTICAL);
        adapter = new MyBaseRecyclerAdapter<BuyersShow>(R.layout.item_recycler_mall_buyers_show,
                ((MallMainTypeViewModel) baseViewModel).getEvaluationList(), com.life.waimaishuo.BR.item) {
            Drawable likeDrawable;
            Drawable likeUncheckDrawable;

            @Override
            protected void initView(BaseViewHolder helper, BuyersShow item) {
                super.initView(helper, item);
                if(likeDrawable == null){
                    int size = (int) UIUtils.getInstance().scalePx(40);
                    likeDrawable = getResources().getDrawable(R.drawable.ic_like);
                    likeDrawable.setBounds(0,0,size,size);
                    likeUncheckDrawable = getResources().getDrawable(R.drawable.ic_like_uncheck);
                    likeUncheckDrawable.setBounds(0,0,size,size);
                }
                // FIXME: 2021/1/19 获得点赞状态 更新界面显示，具体方法看接口情况而定
                ((TextView) helper.getView(R.id.tv_like_count)).setCompoundDrawables(likeDrawable, null, null, null);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        /*recyclerView.addItemDecoration(
                new StaggeredDividerItemDecoration(requireContext(),R.dimen.interval_size_xs,
                        (int) UIUtils.getInstance().scalePx(16), spanCount));*/
        recyclerView.setAdapter(adapter);
    }

}
