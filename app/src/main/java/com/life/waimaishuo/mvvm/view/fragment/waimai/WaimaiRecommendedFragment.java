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
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.view.fragment.BaseChildFragment;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "推荐列表")
public class WaimaiRecommendedFragment extends BaseRecyclerFragment {

    private List<Shop> mShopList;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
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

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_recycler_recommended_shop;
    }

    @Override
    protected RecyclerView.LayoutManager getRecyclerLayoutManager() {
        return new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected List getListData() {
        return mShopList;
    }

    @Override
    protected int getVariableId() {
        return 0;  // FIXME: 2020/12/28 后续修改成正确的值
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            int top_interval =(int)UIUtils.getInstance().scalePx(
                    getContext().getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != 0) {
                    outRect.top = top_interval;
                }
                if(position == state.getItemCount()-1){   //最后一项
                    outRect.bottom = top_interval;
                }
            }
        };
    }

    @Override
    protected void onRecyclerBindViewHolder(BaseViewHolder helper, Object item) {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ((BaseQuickAdapter)mRecyclerView.getAdapter()).setOnItemClickListener(
                (adapter, view, position) -> {
                    openPage(ShopDetailFragment.class);
                });
    }

    public void setData(List<Shop> shopList) {
        mShopList = shopList;
        // TODO: 2020/12/2 刷新列表
    }

}
