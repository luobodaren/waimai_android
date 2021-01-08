package com.life.waimaishuo.mvvm.view.fragment.order;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.ImageSelectGridAdapter;
import com.life.waimaishuo.databinding.FragmentApplyAfterSalesBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
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

@Page(name = "申请售后", anim = CoreAnim.slide)
public class ApplyAfterSalesFragment extends BaseFragment {

    FragmentApplyAfterSalesBinding mBinding;

    private final int maxNoteCharts = 50;


    ImageSelectGridAdapter imageSelectGridAdapter;
    private int maxSelectNum = 6;   //注意若要修改时 需要连同提示语一起修改
    private List<LocalMedia> mSelectList = new ArrayList<>();

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentApplyAfterSalesBinding)mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply_after_sales;
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
        initInputNoteEditText();
        initPictureSelectedRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

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

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initInputNoteEditText() {
        mBinding.tvInputTextNumber.setText(getString(R.string.text_input_number,0,maxNoteCharts));
        InputFilter[] inputFilter = new InputFilter[]{new InputFilter.LengthFilter(maxNoteCharts)};
        mBinding.et.setFilters(inputFilter);    //设置最大字符数
        mBinding.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBinding.tvInputTextNumber.setText(getString(R.string.text_input_number,s.length(),maxNoteCharts));
            }
        });
    }

    private void initPictureSelectedRecycler() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
        mBinding.recyclerSelectedPicture.setLayoutManager(manager);
        mBinding.recyclerSelectedPicture.setAdapter(imageSelectGridAdapter = new ImageSelectGridAdapter(getActivity(), new ImageSelectGridAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                Utils.getPictureSelector(ApplyAfterSalesFragment.this, maxSelectNum)
                        .selectionMedia(mSelectList)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        }) {
            @Override
            public int getItemLayoutId() {
                return R.layout.adapter_select_image_grid_item_apply_after_sales;
            }
        });
        imageSelectGridAdapter.setSelectList(mSelectList);
        imageSelectGridAdapter.setSelectMax(maxSelectNum);
        imageSelectGridAdapter.setOnItemClickListener((position, v) -> PictureSelector.create(ApplyAfterSalesFragment.this).themeStyle(R.style.XUIPictureStyle).openExternalPreview(position, mSelectList));
    }


}
