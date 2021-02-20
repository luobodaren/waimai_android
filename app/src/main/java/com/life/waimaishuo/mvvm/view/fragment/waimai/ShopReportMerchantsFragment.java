package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageSelectGridAdapter;
import com.life.waimaishuo.databinding.FragmentReportMerchantsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopReportMerchantsViewModel;
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

@Page(name = "举报商家", anim = CoreAnim.slide)
public class ShopReportMerchantsFragment extends BaseFragment {

    FragmentReportMerchantsBinding mBinding;
    ShopReportMerchantsViewModel mViewModel;

    ImageSelectGridAdapter imageSelectGridAdapter;

    private int maxSelectNum = 6;   //注意若要修改时 需要连同提示语一起修改
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ShopReportMerchantsViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentReportMerchantsBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_merchants;
    }

    @Override
    protected TitleBar initTitleBar() {
        initTitle();
        return null;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
    }

    @Override
    protected void initViews() {
        super.initViews();

        mBinding.tvMaxPictureCountTip.setText(getString(R.string.max_num_of_selected_picture,"六"));
        initSelectedPictureRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    public void onFragmentDataReset(Bundle bundle) {
        super.onFragmentDataReset(bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);
    }

    private void initSelectedPictureRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4, RecyclerView.VERTICAL, false);
        mBinding.recyclerSelectedPicture.setLayoutManager(manager);
        mBinding.recyclerSelectedPicture.setAdapter(imageSelectGridAdapter =
                new ImageSelectGridAdapter(
                        getActivity(),
                        () -> Utils.getPictureSelector(
                                ShopReportMerchantsFragment.this, maxSelectNum)
                .selectionMedia(mSelectList)
                .forResult(PictureConfig.CHOOSE_REQUEST)) {
            @Override
            public int getItemLayoutId() {
                return R.layout.adapter_select_image_grid_item_default;
            }
        });
        imageSelectGridAdapter.setSelectList(mSelectList);
        imageSelectGridAdapter.setSelectMax(maxSelectNum);
        imageSelectGridAdapter.setOnItemClickListener((position, v) -> Utils.previewSelectedPicture(this,position,mSelectList));
    }



}
