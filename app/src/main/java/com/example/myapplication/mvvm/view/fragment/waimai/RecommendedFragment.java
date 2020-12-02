package com.example.myapplication.mvvm.view.fragment.waimai;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.bean.Shop;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "推荐列表")
public class RecommendedFragment extends BaseFragment {

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
    }
}
