package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.LimitedTime;
import com.life.waimaishuo.databinding.FragmentLimitedTimeGoodsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaimaiLimitedViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "限时秒杀",anim = CoreAnim.slide)
public class WaimaiLimitedTimeGoodsFragment extends BaseFragment {

    private FragmentLimitedTimeGoodsBinding mBinding;
    private WaimaiLimitedViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new WaimaiLimitedViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentLimitedTimeGoodsBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);

    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.getCenterText().setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        titleBar.setCenterTextBold(true);
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));

        int titleBarDrawableSizes = (int) UIUtils.getInstance(getContext()).scalePx(R.dimen.titlebar_drawable_size);
        Drawable leftDrawable = getResources().getDrawable(R.drawable.ic_arrow_left_white);
        leftDrawable.setBounds(0,0,titleBarDrawableSizes,titleBarDrawableSizes);
        titleBar.setLeftImageDrawable(leftDrawable);
        ImageView imageView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_share) {
            @Override
            public void performAction(View view) {
                Toast.makeText(getContext(),"分享···",Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        imageView.setLayoutParams(layoutParams);
        imageView.getDrawable().setBounds(0,0,titleBarDrawableSizes,titleBarDrawableSizes);
        return titleBar;
    }

    @Override
    public void setFitStatusBarHeight(boolean fitStatusBarHeight) {
        super.setFitStatusBarHeight(fitStatusBarHeight);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_limited_time_goods;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initIntroduce();
        initLimitedTimeRecycler();
        initGoodsRecycler();
    }

    private void initIntroduce() {
        int drawableSize = (int) UIUtils.getInstance(getContext())
                .scalePx(getResources().getDimensionPixelSize(R.dimen.waimai_limited_introduce_ic_size));
        Drawable drawable1 = getResources().getDrawable(R.drawable.ic_alarm_white);
        drawable1.setBounds(0,0,drawableSize,drawableSize);
        Drawable drawable2 = getResources().getDrawable(R.drawable.ic_trophy_white);
        drawable2.setBounds(0,0,drawableSize,drawableSize);
        Drawable drawable3 = getResources().getDrawable(R.drawable.ic_notes_white);
        drawable3.setBounds(0,0,drawableSize,drawableSize);
        mBinding.tvIntroduce1.setCompoundDrawables(drawable1,null,null,null);
        mBinding.tvIntroduce2.setCompoundDrawables(drawable2,null,null,null);
        mBinding.tvIntroduce3.setCompoundDrawables(drawable3,null,null,null);
    }

    private void initLimitedTimeRecycler() {
        mBinding.recyclerLimitedTime.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mBinding.recyclerLimitedTime.setAdapter(new SelectedPositionRecylerViewAdapter<LimitedTime>(mViewModel.getLimitedTimeData()) {
            int time_selected_size = (int)UIUtils.getInstance(getContext()).scalePx(getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_select_time_text_size);
            int time_unselected_size = (int)UIUtils.getInstance(getContext()).scalePx(getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_unselect_time_text_size));
            int state_selected_size = (int)UIUtils.getInstance(getContext()).scalePx(getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_select_state_text_size));
            int state_unselected_size = (int)UIUtils.getInstance(getContext()).scalePx(getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_unselect_state_text_size));

            int colorTransparent = getResources().getColor(R.color.transparent);
            int redColorId = getResources().getColor(R.color.red);
            int whiteColorId = getResources().getColor(R.color.white);

            @Override
            public int getLayoutId() {
                return R.layout.item_recycler_limited_time;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, LimitedTime item) {
                TextView time = holder.getView(R.id.tv_limited_time);
                TextView state = holder.getView(R.id.tv_limited_state);

                time.setText(item.getTime());
                state.setText(item.getState().getState());

                if(selected){
                    time.setTextSize(time_selected_size);
                    state.setTextSize(state_selected_size);
                    state.setBackgroundResource(R.drawable.sr_bg_full_corners_white);
                    state.setTextColor(redColorId);
                }else{
                    time.setTextSize(time_unselected_size);
                    state.setTextSize(state_unselected_size);
                    state.setBackgroundColor(colorTransparent);
                    state.setTextColor(whiteColorId);
                }
            }

        });

        // TODO: 2020/12/17 添加点击监听器

    }

    private void initGoodsRecycler() {

    }
}