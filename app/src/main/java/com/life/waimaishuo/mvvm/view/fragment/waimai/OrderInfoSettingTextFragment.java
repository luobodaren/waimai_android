package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.DrawableRes;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.databinding.LayoutOrderInfoSettingTextBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaiMaiConfirmOrderViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "订单头部信息设置")
public class OrderInfoSettingTextFragment extends BaseFragment {

    public static int ACCESS_WAIMAI = 1;
    public static int ACCESS_ZIQU = 2;

    public static int FOOD_ACCESS_PACK_OUT = 11;        //打包带走
    public static int FOOD_ACCESS_STORE_DINING = 12;    //店内就餐
    private int shopFoodAccessType = FOOD_ACCESS_PACK_OUT;

    LayoutOrderInfoSettingTextBinding mBinding;

    @Override
    protected BaseViewModel setViewModel() {
        return baseViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (LayoutOrderInfoSettingTextBinding)mViewDataBinding;
        mBinding.setViewModel((WaiMaiConfirmOrderViewModel) baseViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_order_info_setting_text;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null ;
    }

    @Override
    protected void initViews() {
        super.initViews();

        resetViewByType(((WaiMaiConfirmOrderViewModel) baseViewModel).getCurrentAccessType());

        initAccessTypeView();

        initReservedPhone();

    }

    private void initReservedPhone() {
        mBinding.ivRightPhoneNumber.setImageResource(R.drawable.ic_edit);
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.tvRightAccessType1.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mBinding.tvRightAccessType1.setCompoundDrawables(null,null,checkDrawableRight,null);
                mBinding.tvRightAccessType2.setCompoundDrawables(null,null,uncheckDrawableRight,null);
                shopFoodAccessType = FOOD_ACCESS_PACK_OUT;
            }
        });
        mBinding.tvRightAccessType2.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                mBinding.tvRightAccessType1.setCompoundDrawables(null,null,uncheckDrawableRight,null);
                mBinding.tvRightAccessType2.setCompoundDrawables(null,null,checkDrawableRight,null);
                shopFoodAccessType = FOOD_ACCESS_STORE_DINING;
            }
        });
    }

    /**
     * 根据类型更新界面元素
     * @param type
     */
    public void resetViewByType(int type){
        if(type == OrderInfoSettingTextFragment.ACCESS_WAIMAI){
            mBinding.layoutOrderAccessTypeZiqu.setVisibility(View.GONE);
            mBinding.layoutOrderAccessTypeWaimai.setVisibility(View.VISIBLE);
        }
        if(type == OrderInfoSettingTextFragment.ACCESS_ZIQU){
            mBinding.layoutOrderAccessTypeZiqu.setVisibility(View.VISIBLE);
            mBinding.layoutOrderAccessTypeWaimai.setVisibility(View.GONE);
        }
    }

    public void resetPayType(IconStrData iconStrData){
        mBinding.tvRightPayType.setText(iconStrData.getIconType());
        mBinding.tvRightPayType.setCompoundDrawables(getPayTypeLeftDrawable(iconStrData.getResImgId()),null,null,null);
    }

    /**
     * 获取订单设置信息，根据当前订单类型获取
     * @param type
     */
    public void getOrderSettingInfo(int type){  // FIXME: 2020/12/30 待完善 需要根据后续接口数据类考虑返回类型
        if(type == OrderInfoSettingTextFragment.ACCESS_WAIMAI){

        }
        if(type == OrderInfoSettingTextFragment.ACCESS_ZIQU){

        }
    }

    private Drawable getPayTypeLeftDrawable(@DrawableRes int drawableId) {
        int payTypeDrawableSize = (int) UIUtils.getInstance(requireContext()).scalePx(44);

        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0,0,payTypeDrawableSize,payTypeDrawableSize);
        return drawable;
    }

    Drawable checkDrawableRight;
    Drawable uncheckDrawableRight;
    /**
     * 初始化取餐方式
     */
    private void initAccessTypeView(){
        int checkDrawableSize = (int) UIUtils.getInstance(requireContext()).scalePx(40);
        checkDrawableRight = getResources().getDrawable(R.drawable.ic_check_round_fill_red);
        uncheckDrawableRight = getResources().getDrawable(R.drawable.ic_check_round_fill_gray);

        checkDrawableRight.setBounds(0,0,checkDrawableSize,checkDrawableSize);
        uncheckDrawableRight.setBounds(0,0,checkDrawableSize,checkDrawableSize);
        mBinding.tvRightAccessType1.setCompoundDrawables(null,null,checkDrawableRight,null);
        mBinding.tvRightAccessType2.setCompoundDrawables(null,null,uncheckDrawableRight,null);
    }

}
