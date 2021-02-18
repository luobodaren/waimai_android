package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.BuyersShow;
import com.life.waimaishuo.bean.Coupon;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallMainTypeViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

@Page(name = "商城-领卷中心")
public class MallMainGetCouponCenterFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MyBaseRecyclerAdapter<Coupon> adapter;

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

        });
    }

    /**
     * 设置页面类型 根据类型获取数据
     */
    public void setTypeKey() {

    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler);
        adapter = new MyBaseRecyclerAdapter<Coupon>(R.layout.item_recycler_mall_get_coupon,
                ((MallMainTypeViewModel) baseViewModel).getCouponDataList(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, Coupon item) {
                super.initView(viewDataBinding, helper, item);
                HorizontalProgressView hpv = helper.getView(R.id.hpv_remaining_coupon_count);
                float progress = Float.valueOf(item.getRemainingCount()) / Float.valueOf(item.getAllCount());
                if (progress < 0) {
                    progress = 0;
                    LogUtil.e("进度计算出错了 remaining:" + item.getRemainingCount() + " all:" + item.getAllCount());

                }
                hpv.setProgress(progress);
                hpv.startProgressAnimation();
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = interval;
            }
        });
        recyclerView.setAdapter(adapter);
    }

}
