package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.databinding.FragmentRefundDetailBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.RefundDetailViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "退款详情", anim = CoreAnim.slide)
public class RefundDetailFragment extends BaseFragment {

    private final static String KEY_STATE = "state_key";
    private final static String KEY_ORDER = "order_key";

    private final static int REQUEST_CODE_FILLING_LOGISTICS = 1001;

    public final static int STATE_WAIT_FOR_MERCHANTS_AGREE = 1;    //等待商家同意退款
    public final static int STATE_WAIT_FOR_REFUND = 2;  //商家同意退款，等待退款处理
    public final static int STATE_WAIT_FOR_FILLING_RETURN_LOGISTICS = 3;   //商家同意退货退款，等待填入退货订单
    public final static int STATE_WAIT_FOR_RECEIVER_GOODS = 4; //等待商家收货
    public final static int STATE_REFUND_SUCCESS = 5;  //退款成功
    public final static int STATE_REFUND_FAIL = 6; //退款失败

    private FragmentRefundDetailBinding mBinding;
    private RefundDetailViewModel mViewModel;

    private int myPageState = -1;

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new RefundDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentRefundDetailBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refund_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);

        Bundle bundle = getArguments();
        if (bundle != null) {
            myPageState = bundle.getInt(KEY_STATE);
            mViewModel.setOrder(bundle.getParcelable(KEY_ORDER));
        } else {
            throw new Error("没有传入bundle 无法确定页面类型");
        }
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();

        setPageElementByPageState();

        initOrderInfoRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.layoutTitle.ivBack.setOnClickListener(v -> popToBack());

        mBinding.tvOptionRight.setText(getString(R.string.modify_application)); //任何状态其点击时间均是一样的，可在这里初始化
        mBinding.tvOptionRight.setOnClickListener(v -> modifyApplication());

        // FIXME: 2021/1/11 后续完善时删除
        mBinding.layoutTitle.ivMenu.setOnClickListener(v -> {
            LogUtil.d("切换页面状态显示");
            myPageState++;
            if(myPageState > 6){
                myPageState = 1;
            }
            setPageElementByPageState();
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE_FILLING_LOGISTICS){
                if(myPageState == STATE_WAIT_FOR_FILLING_RETURN_LOGISTICS){
                    myPageState++;
                    setPageElementByPageState();
                }else{
                    myPageState = STATE_WAIT_FOR_RECEIVER_GOODS;
                    setPageElementByPageState();
                }
            }
        }else{
            LogUtil.e("返回结果状态失败，请求码：" + requestCode + " 结果码：" + resultCode);
        }
    }

    private void initTitle(){
        setViewVisibility(mBinding.layoutTitle.ivShare,false);
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initOrderInfoRecycler() {
        mBinding.recyclerOrderInfo.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerOrderInfo.setAdapter(new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_refund_info_text, mViewModel.getRefundOrderInfo()) {
            @Override
            protected void initView(BaseViewHolder helper, String item) {
                int splitCharPosition = item.indexOf('：'); //中文字符'：'
                if (splitCharPosition != -1) {
                    helper.setText(R.id.tv_left_type, item.substring(0, splitCharPosition) + "：");
                    helper.setText(R.id.tv_right_content,
                            item.substring(splitCharPosition + 1));
                }
            }
        });
        mBinding.recyclerOrderInfo.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance(requireContext()).scalePx(40);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildAdapterPosition(view) != 0){
                    outRect.top = interval;
                }
            }
        });
    }

    /**
     * 重置页面元素
     * 不涉及数据设置，数据设置可根据网络请求的数据项进行设置
     */
    private void setPageElementByPageState() {
        //设置标题
        if(myPageState == STATE_REFUND_FAIL){
            mBinding.layoutTitle.tvTitle.setText(getString(R.string.fail_detail));
        }else{
            mBinding.layoutTitle.tvTitle.setText(getPageName());
        }

        switch (myPageState) {
            case STATE_WAIT_FOR_MERCHANTS_AGREE:
            case STATE_WAIT_FOR_REFUND:
                setViewVisibility(mBinding.clRefundStateLayout1, true);
                setViewVisibility(mBinding.clRefundStateLayout2, false);
                break;
            case STATE_WAIT_FOR_FILLING_RETURN_LOGISTICS:
            case STATE_WAIT_FOR_RECEIVER_GOODS:
                setViewVisibility(mBinding.clRefundStateLayout1, false);

                setViewVisibility(mBinding.clRefundStateLayout2, true);
                resetResultLayout();

                setViewVisibility(mBinding.clMerchantsAddress, true);
                break;
            case STATE_REFUND_SUCCESS:
            case STATE_REFUND_FAIL:
                setViewVisibility(mBinding.clRefundStateLayout1, false);

                setViewVisibility(mBinding.clRefundStateLayout2, true);
                resetResultLayout();

                setViewVisibility(mBinding.clMerchantsAddress, false);
                break;
        }
        resetRefundOptionLeftButtonClick();
        resetBottomButton();
    }

    Drawable refundSuccessfulDrawable;
    Drawable refundFailDrawable;
    /**
     * 重置退款结果布局
     */
    private void resetResultLayout(){
        if (refundSuccessfulDrawable == null) {   //初始化申请成功图标
            int size = (int) UIUtils.getInstance(requireContext()).scalePx(88);
            refundSuccessfulDrawable = getResources().getDrawable(R.drawable.ic_check_round_fill_red);
            refundSuccessfulDrawable.setBounds(0, 0, size, size);
        }
        if (refundFailDrawable == null) {   //初始化申请失败图标
            int size = (int) UIUtils.getInstance(requireContext()).scalePx(88);
            refundFailDrawable = getResources().getDrawable(R.drawable.ic_error);
            refundFailDrawable.setBounds(0, 0, size, size);
        }

        if(myPageState != STATE_REFUND_SUCCESS && myPageState != STATE_REFUND_FAIL){
            setViewVisibility(mBinding.tvApplicationResultDetail,false);
        }else{
            setViewVisibility(mBinding.tvApplicationResultDetail,true);
        }

        if(myPageState == STATE_REFUND_SUCCESS
                || myPageState == STATE_WAIT_FOR_FILLING_RETURN_LOGISTICS
                || myPageState == STATE_WAIT_FOR_RECEIVER_GOODS){
            mBinding.ivApplicationResultIcon.setImageDrawable(refundSuccessfulDrawable);
        }else if(myPageState == STATE_REFUND_FAIL){
            mBinding.ivApplicationResultIcon.setImageDrawable(refundFailDrawable);
        }

    }

    /**
     * 重置退款信息中两个功能按钮
     */
    private void resetRefundOptionLeftButtonClick() {
        if (myPageState != STATE_WAIT_FOR_MERCHANTS_AGREE && myPageState != STATE_REFUND_FAIL) {
            setViewVisibility(mBinding.tvOptionLeft, false);
            setViewVisibility(mBinding.tvOptionRight, false);
            return;
        }

        setViewVisibility(mBinding.tvOptionLeft, true);
        setViewVisibility(mBinding.tvOptionRight, true);

        if (myPageState == STATE_WAIT_FOR_MERCHANTS_AGREE) {

            mBinding.tvOptionLeft.setText(getString(R.string.cancel_the_application));
            mBinding.tvOptionLeft.setOnClickListener(v -> cancelTheApplication());
        } else if (myPageState == STATE_REFUND_FAIL) {
            mBinding.tvOptionLeft.setText(getString(R.string.connect_merchants));
            mBinding.tvOptionLeft.setOnClickListener(v -> connectMerchants());
        }
    }

    Drawable customerServiceDrawable;
    /**
     * 设置底部按钮显示文字
     */
    private void resetBottomButton() {
        if (customerServiceDrawable == null) {   //初始化客服图标
            int size = (int) UIUtils.getInstance(requireContext()).scalePx(36);
            customerServiceDrawable = getResources().getDrawable(R.drawable.ic_customer_service_white);
            customerServiceDrawable.setBounds(0, 0, size, size);
        }

        if (myPageState == STATE_WAIT_FOR_REFUND || myPageState == STATE_REFUND_SUCCESS
                || myPageState == STATE_WAIT_FOR_RECEIVER_GOODS) {
            setViewVisibility(mBinding.btBottom, false);
            setViewVisibility(mBinding.tvBottomButtonText, false);
            return;
        }

        setViewVisibility(mBinding.btBottom, true);
        setViewVisibility(mBinding.tvBottomButtonText, true);

        if (myPageState == STATE_WAIT_FOR_MERCHANTS_AGREE) {
            mBinding.tvBottomButtonText.setText(getString(R.string.connect_customer_service));
            mBinding.tvBottomButtonText.setCompoundDrawables(customerServiceDrawable, null, null, null);
            mBinding.btBottom.setOnClickListener(v -> {
                connectCustomerService();
            });
        } else if (myPageState == STATE_WAIT_FOR_FILLING_RETURN_LOGISTICS) {
            mBinding.tvBottomButtonText.setText(getString(R.string.return_goods));
            mBinding.tvBottomButtonText.setCompoundDrawables(null, null, null, null);
            mBinding.btBottom.setOnClickListener(v -> {
                fillingReturnLogistics();
            });
        } else if (myPageState == STATE_REFUND_FAIL) {
            mBinding.tvBottomButtonText.setText(getString(R.string.connect_third_party_customer_service));
            mBinding.tvBottomButtonText.setCompoundDrawables(customerServiceDrawable, null, null, null);
            mBinding.btBottom.setOnClickListener(v -> {
                connectThirdPartyCustomerService();
            });
        }
    }

    /**
     * 撤销申请
     */
    private void cancelTheApplication() {

    }

    /**
     * 修改申请
     */
    private void modifyApplication() {

    }

    /**
     * 联系商家
     */
    private void connectMerchants() {

    }

    /**
     * 联系客服
     */
    private void connectCustomerService(){

    }

    /**
     * 联系第三方客服
     */
    private void connectThirdPartyCustomerService(){

    }

    /**
     * 填写寄回商品单号信息
     */
    private void fillingReturnLogistics(){
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ORDER,mViewModel.getOrder());
        openPageForResult(FillingReturnLogisticsFragment.class,bundle,REQUEST_CODE_FILLING_LOGISTICS);
    }



    /**
     * 打开页面 传入页面类型
     * @param baseFragment
     * @param state 页面类型值在该类上面有定义
     */
    public static void openPageWithState(BaseFragment baseFragment, int state, Order order){
        boolean isStateCorrect = false;
        switch (state){
            case STATE_WAIT_FOR_MERCHANTS_AGREE:
            case STATE_WAIT_FOR_REFUND:
            case STATE_WAIT_FOR_FILLING_RETURN_LOGISTICS:
            case STATE_WAIT_FOR_RECEIVER_GOODS:
            case STATE_REFUND_SUCCESS:
            case STATE_REFUND_FAIL:
                isStateCorrect = true;
                break;
        }
        if(isStateCorrect){
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_STATE,state);
            bundle.putParcelable(KEY_ORDER,order);
            baseFragment.openPage(RefundDetailFragment.class,bundle);
        }else{
            LogUtil.e("页面类型匹配失败，找不到对应类型");
        }
    }

}
