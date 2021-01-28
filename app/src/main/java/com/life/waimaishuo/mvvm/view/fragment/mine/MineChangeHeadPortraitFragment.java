package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.life.base.utils.LogUtil;
import com.life.waimaishuo.R;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentChangeHeadPortraitBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineHeadPortraitViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

import java.util.List;

@Page(name = "个人头像", anim = CoreAnim.slide)
public class MineChangeHeadPortraitFragment extends BaseFragment {

    private FragmentChangeHeadPortraitBinding mBinding;
    private MineHeadPortraitViewModel mViewModel;

    private BottomSheet imgOptionDialog;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MineHeadPortraitViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentChangeHeadPortraitBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_head_portrait;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();

        Glide.with(requireContext())
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201901%2F17%2F20190117092809_ffwKZ.thumb.700_0.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613617512&t=2698bd76db54daeb2d7649fce96a9c4e")
                .into(mBinding.ivHeadPortrait);

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        mBinding.ivHeadPortrait.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                showBottomListDialog();
            }
        });
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_CODE_HEAD_PORTRAIT_TAKE_PICTURE){
            if(resultCode == Activity.RESULT_OK){
                //图片选择
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if(selectList.size() > 0){
                    LocalMedia localMedia = selectList.get(0);
                    LogUtil.d("Path:" + localMedia.getPath() + "  CompressPath:" + localMedia.getCompressPath() + "   CutPath:" + localMedia.getCutPath() + "   PictureType:" + localMedia.getPictureType());
                }
            }
        }
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void showBottomListDialog(){
        if (imgOptionDialog == null) {
            View view = getOptionDialogView();

            imgOptionDialog = new BottomSheet(requireContext());
            imgOptionDialog.setContentView(view);
        }
        if (!imgOptionDialog.isShowing()) {
            imgOptionDialog.show();
        }
    }

    private View getOptionDialogView(){
        View view = View.inflate(requireContext(),R.layout.layout_dialog_img_option,null);
        view.findViewById(R.id.tv_take_picture).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                imgOptionDialog.dismiss();
                Utils.openCameraTakePhoto(MineChangeHeadPortraitFragment.this, Constant.REQUEST_CODE_HEAD_PORTRAIT_TAKE_PICTURE);
            }
        });
        view.findViewById(R.id.tv_select_picture).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {

                imgOptionDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_save_picture).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {

                imgOptionDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel_picture).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {

                imgOptionDialog.dismiss();
            }
        });
        return view;
    }
}
