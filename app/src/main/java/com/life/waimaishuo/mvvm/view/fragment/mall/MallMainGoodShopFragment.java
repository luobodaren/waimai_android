package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.tagAdapter.MallShopSignAndClassificationTagAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallMainTypeViewModel;
import com.life.waimaishuo.views.widget.ScoreView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

@Page(name = "商城-好店")
public class MallMainGoodShopFragment extends BaseFragment {

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

    }

    private void initRecycler(){
        MyBaseRecyclerAdapter<Shop> adapter = getRecyclerAdapter();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(
                    getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = interval;
            }
        });
    }

    private MyBaseRecyclerAdapter<Shop> getRecyclerAdapter(){
        int[] viewTypes = {1, 2};//1：六张展示图片的布局 2：三张
        SparseIntArray layouts = new SparseIntArray();
        layouts.put(viewTypes[0],R.layout.item_recycler_mall_good_shop_recycler_show);
        layouts.put(viewTypes[1],R.layout.item_recycler_mall_good_shop_three_pic);
        MyBaseRecyclerAdapter<Shop> adapter = new MyBaseRecyclerAdapter<Shop>(R.layout.item_recycler_mall_good_shop_recycler_show,
                ((MallMainTypeViewModel)baseViewModel).getGoodShopData(), com.life.waimaishuo.BR.item){
            @Override
            protected void initView(BaseViewHolder helper, Shop item) {
                super.initView(helper, item);

                ScoreView scoreView = helper.getView(R.id.score_view);
                scoreView.hideFans();
                scoreView.hideTitle();

                FlowTagLayout flowTagLayout = helper.getView(R.id.flowTagLayout);
                if(flowTagLayout.getAdapter() == null){
                    MallShopSignAndClassificationTagAdapter tagAdapter = new MallShopSignAndClassificationTagAdapter(requireContext());
                    flowTagLayout.setAdapter(tagAdapter);
                    flowTagLayout.setOnTagClickListener((parent, view, position) ->{

                    });
                    String[] strings = {"正品保证","行业优质"};
                    tagAdapter.addTags(strings);// FIXME: 2021/1/20 搞清楚数据来源
                }

                // FIXME: 2021/1/29 优化项目中 其他嵌套recyclerView的地方，在初始化时进行判断，避免多次初始化

                RecyclerView recyclerView = helper.getView(R.id.recycler_goods_img);
                if(helper.getItemViewType() == viewTypes[0] && recyclerView.getAdapter() == null){  //图片循环显示的布局
                    recyclerView.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(),3,LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_good_shop_recycler_item,item.getSynopsis_img(), com.life.waimaishuo.BR.str));
                    recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                        int interval = (int) UIUtils.getInstance().scalePx(
                                getResources().getDimensionPixelSize(R.dimen.interval_size_xs));
                        @Override
                        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                            super.getItemOffsets(outRect, view, parent, state);
                            outRect.bottom = interval;
                        }
                    });
                }
            }
        };
        adapter.setMultiTypeDelegate(new MultiTypeDelegate<Shop>(layouts) {
            @Override
            protected int getItemType(Shop shop) {
                if(shop.getSynopsis_img().size() != 3){
                    return viewTypes[0];   //循环展示
                }else{
                    return viewTypes[1];   //三张图片的布局
                }
            }
        });
        return adapter;
    }


}
