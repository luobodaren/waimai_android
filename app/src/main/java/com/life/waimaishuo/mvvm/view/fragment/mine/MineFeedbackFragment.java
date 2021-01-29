package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageSelectGridAdapter;
import com.life.waimaishuo.databinding.FragmentOpinionFeedbackBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineFeedbackViewModel;
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

@Page(name = "意见反馈", anim = CoreAnim.slide)
public class MineFeedbackFragment extends BaseFragment {

    private FragmentOpinionFeedbackBinding mBinding;
    private MineFeedbackViewModel mViewModel;

    private ImageSelectGridAdapter imageSelectGridAdapter;
    private int maxSelectNum = 6;   //注意若要修改时 需要连同提示语一起修改
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineFeedbackViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentOpinionFeedbackBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_opinion_feedback;
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
        initSelectedPictureRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

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

    private void initTitle(){
        int size = (int) UIUtils.getInstance().scalePx(44);
        Drawable phoneCallDrawable = getResources().getDrawable(R.drawable.ic_phone_call);
        phoneCallDrawable.setBounds(0,0,size,size);
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvRight.setCompoundDrawables(phoneCallDrawable,null,null,null);
    }

    private void initSelectedPictureRecycler() {
        mBinding.tvPictureSelectTip.setText(getString(R.string.max_num_of_selected_picture,String.valueOf(maxSelectNum)));

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4, RecyclerView.VERTICAL, false);
        mBinding.recyclerSelectedPicture.setLayoutManager(manager);
        mBinding.recyclerSelectedPicture.setAdapter(imageSelectGridAdapter =
                new ImageSelectGridAdapter(
                        getActivity(),
                        () -> Utils.getPictureSelector(
                                MineFeedbackFragment.this, maxSelectNum)
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
