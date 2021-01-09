package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageSelectGridAdapter;
import com.life.waimaishuo.bean.Order;
import com.life.waimaishuo.databinding.FragmentRefundBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.order.EvaluateWaiMaiFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.RefundViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.ArrayList;
import java.util.List;

@Page(name = "退货退款", anim = CoreAnim.slide)
public class RefundFragment extends BaseFragment {

    private final static String IS_RETURN_GOODS_KEY = "is_return_goods";
    private final static String DATA_KEY = "order";

    FragmentRefundBinding mBinding;
    RefundViewModel mViewModel;

    ImageSelectGridAdapter imageSelectGridAdapter;
    private int maxSelectNum = 6;   //注意若要修改时 需要连同提示语一起修改
    private List<LocalMedia> mSelectList = new ArrayList<>();

    private boolean isReturnGoods = true;  //决定页面类型 退款 或 退货退款

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new RefundViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentRefundBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refund;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        isReturnGoods = getArguments().getBoolean(IS_RETURN_GOODS_KEY);

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

        if(isReturnGoods){
            mBinding.layoutTitle.tvTitle.setText(getString(R.string.refund_return));
        }else{
            mBinding.clGoodsState.setVisibility(View.VISIBLE);
            mBinding.layoutTitle.tvTitle.setText(getString(R.string.refund));
        }
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);


        init();

        initPictureSelectedRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.clGoodsState.setOnClickListener(v -> {

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    imageSelectGridAdapter.setSelectList(mSelectList);
                    imageSelectGridAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    private void init() { // FIXME: 2021/1/8 暂时数据展示，后期修改
        mBinding.tvShopName.setText("欧舒丹甜蜜樱花沐浴啫喱/身体乳套装沐/欧舒丹甜蜜樱花沐浴啫喱/身体乳套装沐...");
        mBinding.tvGoodsInfo.setText("颜色分类：黄色");
        mBinding.tvRefundPrice.setText("￥199.00");
    }

    private void initPictureSelectedRecycler(){
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        mBinding.recyclerSelectedPicture.setLayoutManager(manager);
        mBinding.recyclerSelectedPicture.setAdapter(imageSelectGridAdapter = new ImageSelectGridAdapter(getActivity(), new ImageSelectGridAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                Utils.getPictureSelector(RefundFragment.this, maxSelectNum)
                        .selectionMedia(mSelectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        }) {
            @Override
            public int getItemLayoutId() {
                return R.layout.adapter_select_image_grid_item_shop_comment;
            }
        });
        imageSelectGridAdapter.setSelectList(mSelectList);
        imageSelectGridAdapter.setSelectMax(maxSelectNum);
        imageSelectGridAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(RefundFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
    }

    /**
     * 打开页面
     * @param baseFragment
     * @param isReturnGoods 决定页面类型 退货退款 或 仅退款
     */
    public static void openPageRefundReturn(BaseFragment baseFragment, Order order, boolean isReturnGoods){
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_RETURN_GOODS_KEY,isReturnGoods);
        bundle.putParcelable(DATA_KEY,order);
        baseFragment.openPage(RefundFragment.class, bundle);
    }

}