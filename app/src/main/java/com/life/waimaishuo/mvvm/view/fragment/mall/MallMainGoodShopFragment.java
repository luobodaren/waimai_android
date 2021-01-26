package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.tagAdapter.MallShopSignAndClassificationTagAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.MallNewArrival;
import com.life.waimaishuo.databinding.ItemRecyclerMallGoodShopBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallMainTypeViewModel;
import com.life.waimaishuo.views.widget.ScoreView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.flowlayout.BaseTagAdapter;
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
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(
                new MyBaseRecyclerAdapter<Shop>(R.layout.item_recycler_mall_good_shop,
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
                    }
                });
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
