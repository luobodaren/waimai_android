package com.life.waimaishuo.mvvm.view.fragment.waimai;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.RecommendGoodsViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;

import java.util.List;

@Page(name = "搭配")
public class GoodsMatchFragment extends BaseRecyclerFragment {

    RecommendGoodsViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new RecommendGoodsViewModel();
        }
        return mViewModel;
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_goods_match;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new GridLayoutManager(requireContext(),2, LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return mViewModel.getGoodsList();
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.goods;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new GridDividerItemDecoration(requireContext(),2,
                (int) UIUtils.getInstance().scalePx(
                        getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding)));
    }

    @Override
    protected void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding, BaseViewHolder helper, Object item) {

    }

}
