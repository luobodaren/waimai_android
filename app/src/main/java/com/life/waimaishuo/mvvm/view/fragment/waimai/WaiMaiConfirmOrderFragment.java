package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.databinding.FragmentConfirmAnOrderBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiConfirmOrderViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "确认订单", anim = CoreAnim.slide)
public class WaiMaiConfirmOrderFragment extends BaseFragment {

    public static int ACCESS_WAIMAI = 1;
    public static int ACCESS_ZIQU = 2;
    public static int ACCESS_WAIMAI_ONLY = 3;
    public static int ACCESS_ZIQU_ONLY = 4;
    public static int ACCESS_NO_CHOSE = 5;
    int currentSelectedAccessType = -1; //初始值-1  1：外卖 2：自取 3：仅有外卖 4：仅有自取 5：均没有


    FragmentConfirmAnOrderBinding mBinding;
    WaiMaiConfirmOrderViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaiMaiConfirmOrderViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentConfirmAnOrderBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_an_order;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected void initViews() {
        super.initViews();
        currentSelectedAccessType = 1;  // FIXME: 2020/12/29 后续更具实际情况修改

        mBinding.layoutTitle.tvTitle.setText(getPageName());

        initOrderInfoRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mBinding.tvOrderAccessType.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(currentSelectedAccessType != 1){
                    mBinding.tvOrderAccessType.setTextColor(getResources().getColor(R.color.text_normal));
                    mBinding.tvOrderAccessType2.setTextColor(getResources().getColor(R.color.white));
                    mBinding.tvOrderAccessType.setBackgroundResource(R.drawable.ic_bg_order_access_type_left);
                    mBinding.tvOrderAccessType2.setBackgroundResource(R.color.transparent);
                    refreshOrderAccessData();
                }
            }
        });

        mBinding.tvOrderAccessType2.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(currentSelectedAccessType != 2){
                    mBinding.tvOrderAccessType.setTextColor(getResources().getColor(R.color.white));
                    mBinding.tvOrderAccessType2.setTextColor(getResources().getColor(R.color.text_normal));
                    mBinding.tvOrderAccessType.setBackgroundResource(R.color.transparent);
                    mBinding.tvOrderAccessType2.setBackgroundResource(R.drawable.ic_bg_order_access_type_right);
                    refreshOrderAccessData();
                }
            }
        });
    }

    private void refreshOrderAccessData() {
        LogUtil.d("刷新配送信息");
    }

    private void initOrderInfoRecycler() {
        mBinding.recyclerOrderInfo.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerOrderInfo.setAdapter(new MyBaseRecyclerAdapter(R.layout.item_recycler_order_info_text,mViewModel.getOrderInfo(currentSelectedAccessType)));
    }

}
