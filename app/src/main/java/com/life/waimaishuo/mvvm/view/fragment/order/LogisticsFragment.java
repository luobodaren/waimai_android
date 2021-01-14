package com.life.waimaishuo.mvvm.view.fragment.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.LogisticsData;
import com.life.waimaishuo.databinding.FragmentViewLogisticsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.LogisticsViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;

@Page(name = "查看物流", anim = CoreAnim.slide)
public class LogisticsFragment extends BaseFragment {

    FragmentViewLogisticsBinding mBinding;
    LogisticsViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new LogisticsViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentViewLogisticsBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_logistics;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);

    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        init();
        initLogisticsRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        setViewVisibility(mBinding.layoutTitle.ivShare,false);
    }

    private void init(){
        int size = (int) UIUtils.getInstance().scalePx(24);
        Drawable copyDrawable = getResources().getDrawable(R.drawable.ic_copy);
        copyDrawable.setBounds(0,0,size,size);
        mBinding.tvCourierInfo.setCompoundDrawables(null,null,copyDrawable,null);
        mBinding.tvCourierInfo.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(
                    ClipData.newPlainText(null,mViewModel.logisticsOrderCode.get()));
            Toast.makeText(requireContext(), "复制成功了！", Toast.LENGTH_SHORT).show();
        });
    }

    private void initLogisticsRecycler(){
        mBinding.recyclerLogisticsState.setLayoutManager(
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerLogisticsState.setAdapter(new BaseRecyclerAdapter<LogisticsData>(mViewModel.getLogisticsData()) {

            int[] itemViewType = {0,1,2};   //分别对应 顶部 中间 以及 底部

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, LogisticsData item) {
                ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
                binding.setVariable(com.life.waimaishuo.BR.item, item);
                if(item.getState() == null || "".equals(item.getState())){
                    setViewVisibility(holder.getView(R.id.tv_state),false);
                }else{
                    setViewVisibility(holder.getView(R.id.tv_state),true);
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                if(viewType == 0){
                    return R.layout.item_recycler_logistics_top;
                }else if(viewType == 1){
                    return R.layout.item_recycler_logistics_content;
                }else{
                    return R.layout.item_recycler_logistics_bottom;
                }
            }

            @Override
            public int getItemViewType(int position) {
                if(position == 0){
                    return itemViewType[0];
                }
                if(position == getItemCount()-1){
                    return itemViewType[2];
                }
                return itemViewType[1];
            }
        });
    }


}
