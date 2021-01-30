package com.life.waimaishuo.mvvm.view.fragment.order.widget;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Toast;

import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.OrderNoteTagAdapter;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentOrderNoteBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.OrderNoteViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "订单备注", anim = CoreAnim.slide)
public class OrderNoteFragment extends BaseFragment {

    public static final String RESULT_KEY_NOTE = "result_key_note";

    OrderNoteViewModel mViewModel;

    FragmentOrderNoteBinding mBinding;

    private int maxNoteCharts = 50; //备注最大字符数

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new OrderNoteViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentOrderNoteBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_note;
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

        initInputNoteEditText();
        initUsualNote();
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.tvFinish.setOnClickListener(v -> popToBackWithResult(false));
        mBinding.layoutTitle.ivBack.setOnClickListener(v -> popToBackWithResult(true));
    }

    private void initInputNoteEditText() {
        mViewModel.orderNoteTextNumberObservable.set(getString(R.string.text_input_number,0,maxNoteCharts));
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
                mViewModel.orderNoteTextNumberObservable.set(getString(R.string.text_input_number,s.length(),maxNoteCharts));
            }
        });
    }

    private void initUsualNote() {
        OrderNoteTagAdapter tagAdapter = new OrderNoteTagAdapter(getContext());
        mBinding.flowTagLayoutNote.setAdapter(tagAdapter);
        mBinding.flowTagLayoutNote.setOnTagClickListener((parent, view, position) ->
                Toast.makeText(getContext(), "点击了：" + parent.getAdapter().getItem(position),
                        Toast.LENGTH_SHORT).show());

        tagAdapter.addTags(mViewModel.getRedPacketData());
    }

    private void popToBackWithResult(boolean isReturnEmptyData){
        resultIntent = new Intent();
        if(!isReturnEmptyData){
            resultIntent.putExtra(RESULT_KEY_NOTE,mViewModel.orderNoteObservable.get());
        }
        resultCode = Constant.RESULT_CODE_SUCCESS;
        popToBack();
    }

}
