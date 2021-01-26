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
import com.life.waimaishuo.databinding.FragmentOrderEvaluateWaimaiBinding;
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

@Page(name = "外卖发表评价", anim = CoreAnim.slide)
public class EvaluateWaiMaiFragment extends BaseFragment {

    FragmentOrderEvaluateWaimaiBinding mBinding;
    EvaluateViewModel mViewModel;

    ImageSelectGridAdapter imageSelectGridAdapter;
    private int maxSelectNum = 6;   //注意若要修改时 需要连同提示语一起修改
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
        mBinding = (FragmentOrderEvaluateWaimaiBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_evaluate_waimai;
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
        initDriverCommentFlowTag();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.scaleRatingBarPackaging.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(RatingBar ratingBar, float rating) {
                refreshRatingInfo((int)rating,mBinding.tvPackagingScoreInfo,mViewModel.getPackagingRankInfo());
            }
        });
        mBinding.scaleRatingBarQuality.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(RatingBar ratingBar, float rating) {
                refreshRatingInfo((int)rating,mBinding.tvQualityScoreInfo,mViewModel.getQualityRankInfo());
            }
        });
        mBinding.scaleRatingBarDriver.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(RatingBar ratingBar, float rating) {
                refreshRatingInfo((int)rating,mBinding.tvQualityScoreInfo,mViewModel.getRatingRankInfo());
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
        mBinding.tvShopName.setText("德克士炸鸡店");
        mBinding.tvDriverName.setText("王小二");
        mBinding.tvDeliverTime.setText("7月10日12:45左右送达");
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvRight.setText(R.string.release);
        mBinding.layoutTitle.tvRight.setOnClickListener(v -> Toast.makeText(requireContext(), "发布！！", Toast.LENGTH_SHORT).show());
        mBinding.layoutTitle.rlTitle.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private void initPictureSelectedRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        mBinding.recyclerSelectedPicture.setLayoutManager(manager);
        mBinding.recyclerSelectedPicture.setAdapter(imageSelectGridAdapter = new ImageSelectGridAdapter(getActivity(), new ImageSelectGridAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                Utils.getPictureSelector(EvaluateWaiMaiFragment.this, maxSelectNum)
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
        imageSelectGridAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(EvaluateWaiMaiFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
    }

    private void initDriverCommentFlowTag(){
        FlowTagLayout flowTagLayout = mBinding.flowTagLayoutDriverComment;
        DriverCommentTagAdapter tagAdapter = new DriverCommentTagAdapter(getContext());

        flowTagLayout.setAdapter(tagAdapter);
        flowTagLayout.setOnTagClickListener((parent, view, position) ->
                Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                        Toast.LENGTH_SHORT).show());

        tagAdapter.addTags(mViewModel.getDriverCommentsType());
    }

    /**
     * 刷新界面显示等级信息
     * @param rating
     * @param infoTV
     * @param infoStrList
     *  注意：list.size必须与级别数相同
     */
    private void refreshRatingInfo(int rating, TextView infoTV, List<String> infoStrList){
        for(int i = 0;i<=5;i++){
            if(i == rating){
                infoTV.setText(infoStrList.get(i));
                return;
            }
        }
    }

}
