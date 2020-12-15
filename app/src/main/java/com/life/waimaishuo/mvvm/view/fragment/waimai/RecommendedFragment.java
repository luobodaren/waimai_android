package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.view.fragment.BaseChildFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "推荐列表")
public class RecommendedFragment extends BaseChildFragment {

    private List<Shop> mShopList;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommended_shop;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    public void setData(List<Shop> shopList) {
        mShopList = shopList;
        // TODO: 2020/12/2 刷新列表
    }

    @Override
    protected void initViews() {
        super.initViews();
        initRecycler(findViewById(R.id.recycler_recommended_shop));
    }

    private void initRecycler(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(
                new BaseQuickAdapter<Shop,BaseViewHolder>(R.layout.item_recommended_shop,mShopList) {
                    @Override
                    protected void convert(@NonNull BaseViewHolder helper, Shop item) {

                    }

                    @Override
                    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

                    }
                });
        ((BaseQuickAdapter)recyclerView.getAdapter()).setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                openPage(ShopDetailFragment.class);
            }
        });
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != 0){
                    outRect.top = (int)(getContext().getResources().getDimensionPixelOffset(R.dimen.interval_size_xs)* UIUtils.getInstance(getContext()).getHorValue());
                }
            }
        });
    }
}
