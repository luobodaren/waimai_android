package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.statelayout.CustomSingleViewAdapter;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.mvvm.view.fragment.BaseChildFragment;
import com.life.waimaishuo.mvvm.view.fragment.BaseRecyclerFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiReccommendedViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import java.util.List;
import java.util.zip.Inflater;

@Page(name = "推荐列表")
public class WaimaiRecommendedFragment extends BaseRecyclerFragment {

    private WaiMaiReccommendedViewModel mViewModel;

    private String title = "";
    private String child_sign = "";
    private String tag = "";

    /**
     * 第一次加载数据
     */
    private boolean firstRefreshData = true;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiReccommendedViewModel();
        }
        return mViewModel;
    }

    /**
     * 由于该fragment是用在
     * @param inflater
     * @param container
     * @return
     */
    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(getLayoutId(),null);
        mViewDataBinding = DataBindingUtil.bind(view);
        baseViewModel = setViewModel();
        bindViewModel();
        return view;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        showLoading();
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
        return mViewModel.getListData(title);
    }

    @Override
    protected int getVariableId() {
        return -1;  // FIXME: 2020/12/28 后续修改成正确的值
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
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return new CustomSingleViewAdapter();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ((BaseQuickAdapter)mRecyclerView.getAdapter()).setOnItemClickListener(
                (adapter, view, position) -> {
                    openPage(ShopDetailFragment.class);
                });
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        if(firstRefreshData){
            mViewModel.refreshListData(title);
        }
    }

    public void refreshListDate(){

    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setChild_sign(String child_sign) {
        this.child_sign = child_sign;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
