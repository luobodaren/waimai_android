package com.life.waimaishuo.mvvm.view.fragment.mall;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.decoration.StaggeredDividerItemDecoration;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallRecommendViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "商城推荐商品列表")
public class MallRecommendFragment extends BaseFragment {

    private MallRecommendViewModel mViewModel;

    private RecyclerView recyclerView;
    private MyBaseRecyclerAdapter<Goods> adapter;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallRecommendViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() { }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler;
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
        initRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        adapter.setOnItemClickListener((adapter, view, position) ->
                MallGoodsDetailFragment.openPageWithGoodsId(
                        MallRecommendFragment.this
                        ,(Goods) adapter.getData().get(position)));
    }

    /**
     * 设置页面类型 更具类型获取数据
     */
    public void setTypeKey(){

    }

    private void initRecycler(){
        recyclerView = findViewById(R.id.recycler);
        int spanCount = 2;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                spanCount,StaggeredGridLayoutManager.VERTICAL);
        adapter = new MyBaseRecyclerAdapter<Goods>(R.layout.item_recycler_mall_goods_list,
                mViewModel.getRecommendGoodsList(), com.life.waimaishuo.BR.goods){
            int textSize = (int) UIUtils.getInstance().scalePx(20);
            @Override
            protected void initView(BaseViewHolder helper, Goods item) {
                super.initView(helper, item);

                helper.setText(R.id.tv_goods_price,
                        TextUtil.getAbsoluteSpannable("￥" + item.getPrice(),
                                textSize,0,1));
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new StaggeredDividerItemDecoration(requireContext(),R.dimen.interval_size_xs,
                        (int) UIUtils.getInstance().scalePx(16), spanCount));
        recyclerView.setAdapter(adapter);
    }

}
