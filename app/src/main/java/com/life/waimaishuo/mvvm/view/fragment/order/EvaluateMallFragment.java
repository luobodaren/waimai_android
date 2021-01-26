package com.life.waimaishuo.mvvm.view.fragment.order;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageSelectGridAdapter;
import com.life.waimaishuo.adapter.tagAdapter.DriverCommentTagAdapter;
import com.life.waimaishuo.databinding.FragmentOrderEvaluateMallBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.EvaluateViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.xuexiang.xui.widget.progress.ratingbar.RatingBar;

import java.util.ArrayList;
import java.util.List;

@Page(name = "商场发表评价", anim = CoreAnim.slide)
public class EvaluateMallFragment extends BaseFragment {

    FragmentOrderEvaluateMallBinding mBinding;
    EvaluateViewModel mViewModel;

    ImageSelectGridAdapter imageSelectGridAdapter;
    private int maxSelectNum = 3;   //注意若要修改时 需要连同提示语一起修改
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new EvaluateViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentOrderEvaluateMallBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_evaluate_mall;
    }

    @Override
    protected TitleBar initTitleBar() {
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

        init();
        initTitle();
        initPictureSelectedRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.scaleRatingBarDescribe.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(RatingBar ratingBar, float rating) {

            }
        });
        mBinding.scaleRatingBarLogistics.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(RatingBar ratingBar, float rating) {

            }
        });
        mBinding.scaleRatingBarAttitude.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(RatingBar ratingBar, float rating) {

            }
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
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvRight.setText(R.string.release_comment);
        mBinding.layoutTitle.tvRight.setOnClickListener(v -> Toast.makeText(requireContext(), "发布！！", Toast.LENGTH_SHORT).show());
    }

    private void initPictureSelectedRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        mBinding.recyclerSelectedPicture.setLayoutManager(manager);
        mBinding.recyclerSelectedPicture.setAdapter(imageSelectGridAdapter = new ImageSelectGridAdapter(getActivity(), new ImageSelectGridAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                Utils.getPictureSelector(EvaluateMallFragment.this, maxSelectNum)
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
        imageSelectGridAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(EvaluateMallFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
    }


}
