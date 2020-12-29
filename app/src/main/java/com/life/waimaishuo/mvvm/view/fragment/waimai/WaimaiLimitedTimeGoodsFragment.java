package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.LimitedFoods;
import com.life.waimaishuo.bean.LimitedTime;
import com.life.waimaishuo.databinding.FragmentWaimaiLimitedTimeGoodsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaimaiLimitedViewModel;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;


@Page(name = "限时秒杀",anim = CoreAnim.slide)
public class WaimaiLimitedTimeGoodsFragment extends BaseFragment {

    private FragmentWaimaiLimitedTimeGoodsBinding mBinding;
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
        mBinding = (FragmentWaimaiLimitedTimeGoodsBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setFitStatusBarHeight(true);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_limited_time_goods;
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
        SelectedPositionRecylerViewAdapter<LimitedTime> adapter = new SelectedPositionRecylerViewAdapter<LimitedTime>(mViewModel.getLimitedTimeData()) {
            int time_selected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_select_time_text_size);
            int time_unselected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_unselect_time_text_size);
            int state_selected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_select_state_text_size);
            int state_unselected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_unselect_state_text_size);

            int time_selected_size_scale = (int)UIUtils.getInstance(getContext()).scalePx(time_selected_size);
            int time_unselected_size_scale = (int)UIUtils.getInstance(getContext()).scalePx(time_unselected_size);
            int state_selected_size_scale = (int)UIUtils.getInstance(getContext()).scalePx(state_selected_size);
            int state_unselected_size_scale = (int)UIUtils.getInstance(getContext()).scalePx(state_unselected_size);

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

                if(UIUtils.getInstance(getContext()).isNeedScale(time)){
                    if(selected){
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_selected_size);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_selected_size);
                        state.setBackgroundResource(R.drawable.sr_bg_full_corners_white);
                        state.setTextColor(redColorId);
                        UIUtils.getInstance(getContext()).setFakeBoldText(state,true);
                    }else{
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_unselected_size);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_unselected_size);
                        state.setBackgroundColor(colorTransparent);
                        state.setTextColor(whiteColorId);
                        UIUtils.getInstance(getContext()).setFakeBoldText(state,false);
                    }
                    UIUtils.getInstance(getContext()).autoAdapterUI(time);
                    UIUtils.getInstance(getContext()).autoAdapterUI(state);
                }else{  //已进行大小适配处理
                    if(selected){
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_selected_size_scale);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_selected_size_scale);
                        state.setBackgroundResource(R.drawable.sr_bg_full_corners_white);
                        state.setTextColor(redColorId);
                        UIUtils.getInstance(getContext()).setFakeBoldText(state,true);
                    }else{
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_unselected_size_scale);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_unselected_size_scale);
                        state.setBackgroundColor(colorTransparent);
                        state.setTextColor(whiteColorId);
                        UIUtils.getInstance(getContext()).setFakeBoldText(state,false);
                    }
                }
            }

        };
        adapter.setSelectedListener((holder, item) -> {
            updateGoodsInfo();
        });
        mBinding.recyclerLimitedTime.setAdapter(adapter);

    }

    private void initGoodsRecycler() {
        mBinding.recyclerGoodsList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerGoodsList.setAdapter(new MyBaseRecyclerAdapter<LimitedFoods>(R.layout.item_recycler_limited_goods, mViewModel.getLimitedGoodsList(), BR.item) {
            @Override
            protected void initView(BaseViewHolder helper, LimitedFoods item) {
                helper.setText(R.id.tv_foods_price_pre, getStrikeThroughSpanSpannable("189"));  //原始价格
                helper.setText(R.id.tv_limited_kill_price,getLimitedPriceSpannable(item.getLimitedPrice()));

                switch (item.getLimitedTimeStateEnum()){
                    case NO_START:
                        helper.setBackgroundRes(R.id.cl_limited_sale_detail_info,R.drawable.ic_limited_no_start);
                        break;
                    case STARTING:
                    case ROBBING:
                        helper.setBackgroundRes(R.id.cl_limited_sale_detail_info,R.drawable.ic_limited_start);
                        break;
                    case SALE_OUT:
                        helper.getView(R.id.tv_remaining_foods_count).setVisibility(View.GONE);
                        helper.setBackgroundRes(R.id.cl_limited_sale_detail_info,R.drawable.ic_limited_goods_sale_out);
                        break;
                }
            }
        });
        mBinding.recyclerGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int)UIUtils.getInstance(getContext()).scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != RecyclerView.NO_POSITION){
                    outRect.top = interval;
                    if(position == state.getItemCount()-1){
                        outRect.bottom = interval;
                    }
                }
            }
        });
    }

    int limitedPriceTextSize = -1;
    private SpannableString getLimitedPriceSpannable(String price){
        if(limitedPriceTextSize == -1){
            limitedPriceTextSize = (int)UIUtils.getInstance(getContext()).scalePx(40f);
        }
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.limited_second_kill_price,price));
        spannableString.setSpan(new AbsoluteSizeSpan(limitedPriceTextSize,false),1,price.length()+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 添加删除线
     * @param prePrice
     * @return
     */
    private SpannableString getStrikeThroughSpanSpannable(String prePrice){
        SpannableString spannableString = new SpannableString("￥" + prePrice);
        spannableString.setSpan(new StrikethroughSpan(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private void updateGoodsInfo() {

    }

}
