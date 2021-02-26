package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallRecommendViewModel;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;

import java.util.List;

@Page(name = "商城-推荐商品列表(好物、好店、配饰)")
public class MallRecommendChildFragment extends BaseRecyclerFragment {

    private String title;
    private String type;    // TODO: 2021/1/19 推荐title 类型 根据类型去获取对应的数据并进行展示

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_mall_recommend;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return ((MallRecommendViewModel)baseViewModel).getRecommendData(title, type);
    }

    @Override
    protected int getVariableId() {
        return com.life.waimaishuo.BR.item;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = interval;
            }
        };
    }

    @Override
    protected void onRecyclerBindViewHolder(ViewDataBinding viewDataBinding, BaseViewHolder helper, Object item) {
        TextView prePriceTV = helper.getView(R.id.tv_pre_price);
        String price = ((Goods)item).getPrice();
        prePriceTV.setText(TextUtil.getStrikeThroughSpanSpannable(price,0,price.length()));
    }

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }
}
