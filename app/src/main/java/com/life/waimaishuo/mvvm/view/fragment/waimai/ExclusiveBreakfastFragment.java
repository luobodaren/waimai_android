package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.databinding.FragmentWaimaiExclusiveBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaimaiExclusiveViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "专属早餐",anim = CoreAnim.slide)
public class ExclusiveBreakfastFragment extends BaseFragment {

    private FragmentWaimaiExclusiveBinding mBinding;
    private WaimaiExclusiveViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new WaimaiExclusiveViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiExclusiveBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_exclusive;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
        setFitStatusBarHeight(true);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initExclusiveRecycler();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    private void initTitle(){
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        setViewVisibility(mBinding.layoutTitle.ivMenu,false);
    }

    private void initExclusiveRecycler() {
        int spanCount = 2;
        RecyclerView recyclerView = mBinding.recyclerExclusive;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                spanCount,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(
//                new StaggeredDividerItemDecoration(requireContext(), R.dimen.interval_size_xs,
//                        (int) UIUtils.getInstance().scalePx(16), spanCount));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(24);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
                int position = parent.getChildAdapterPosition(view);
                if(position == state.getItemCount() - 1 || position == state.getItemCount() - 2){
                    outRect.bottom = interval;
                }
            }
        });
        recyclerView.setAdapter(new MyBaseRecyclerAdapter<Goods>(R.layout.item_recycler_exclusive_goods_info,
                mViewModel.getmBreakFastList(), BR.goods));
    }
}
