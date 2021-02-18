package com.life.waimaishuo.mvvm.view.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.bean.LimitedGoods;
import com.life.waimaishuo.bean.LimitedTime;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentWaimaiLimitedTimeGoodsBinding;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaimaiLimitedViewModel;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.views.MyHorizontalProgressView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;


@Page(name = "限时秒杀",anim = CoreAnim.slide)
public class LimitedTimeGoodsFragment extends BaseFragment {

    private final static String KEY_BACKGROUND_ID = "background_id";

    private FragmentWaimaiLimitedTimeGoodsBinding mBinding;
    private WaimaiLimitedViewModel mViewModel;
    private int mPageType;
    private int mBackGroundDrawableId;

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

        Bundle bundle = getArguments();
        if(bundle != null){
            mPageType = bundle.getInt(Constant.PAGE_TYPE);
            mBackGroundDrawableId = bundle.getInt(KEY_BACKGROUND_ID);
        }

        setFitStatusBarHeight(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_limited_time_goods;
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    public void setFitStatusBarHeight(boolean fitStatusBarHeight) {
        super.setFitStatusBarHeight(fitStatusBarHeight);
    }

    @Override
    protected void initViews() {
        super.initViews();

        initTitle();
        initBackground();
        initIntroduce();

        initLimitedTimeRecycler();
        // FIXME: 2021/1/19 根据类型 选择不同的界面
        if(mPageType == Constant.PAGE_TYPE_WAIMAI){
            initWaiMaiLimitedGoodsRecycler();
        }else if(mPageType == Constant.PAGE_TYPE_MALL){
            initMallLimitedGoodsRecycler();
        }
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        setViewVisibility(mBinding.layoutTitle.ivMenu,false);
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);
    }

    private void initBackground(){
        if(mPageType == Constant.PAGE_TYPE_WAIMAI){
            mBinding.ivTopImageBg.setImageResource(mBackGroundDrawableId);
        }else if(mPageType == Constant.PAGE_TYPE_MALL){
            mBinding.tvBackground.setBackgroundResource(mBackGroundDrawableId);
        }
    }

    private void initIntroduce() {
        int drawableSize = (int) UIUtils.getInstance()
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
        SelectedPositionRecyclerViewAdapter<LimitedTime> adapter = new SelectedPositionRecyclerViewAdapter<LimitedTime>(mViewModel.getLimitedTimeData(mPageType)) {
            int time_selected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_select_time_text_size);
            int time_unselected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_unselect_time_text_size);
            int state_selected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_select_state_text_size);
            int state_unselected_size = getContext().getResources().getDimensionPixelSize(R.dimen.waimai_limited_unselect_state_text_size);

            int time_selected_size_scale = (int)UIUtils.getInstance().scalePx(time_selected_size);
            int time_unselected_size_scale = (int)UIUtils.getInstance().scalePx(time_unselected_size);
            int state_selected_size_scale = (int)UIUtils.getInstance().scalePx(state_selected_size);
            int state_unselected_size_scale = (int)UIUtils.getInstance().scalePx(state_unselected_size);

            int colorTransparent = getResources().getColor(R.color.transparent);
            int redColorId = getResources().getColor(R.color.red);
            int whiteColorId = getResources().getColor(R.color.white);

            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_limited_time;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, LimitedTime item) {
                TextView time = holder.getView(R.id.tv_limited_time);
                TextView state = holder.getView(R.id.tv_limited_state);

                time.setText(item.getTime());
                state.setText(item.getState().getState());

                if(UIUtils.getInstance().isNeedScale(time)){
                    if(selected){
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_selected_size);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_selected_size);
                        state.setBackgroundResource(R.drawable.sr_bg_full_corners_white);
                        state.setTextColor(redColorId);
                        TextUtil.setFakeBoldText(state,true);
                    }else{
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_unselected_size);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_unselected_size);
                        state.setBackgroundColor(colorTransparent);
                        state.setTextColor(whiteColorId);
                        TextUtil.setFakeBoldText(state,false);
                    }
                    UIUtils.getInstance().autoAdapterUI(time);
                    UIUtils.getInstance().autoAdapterUI(state);
                }else{  //已进行大小适配处理
                    if(selected){
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_selected_size_scale);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_selected_size_scale);
                        state.setBackgroundResource(R.drawable.sr_bg_full_corners_white);
                        state.setTextColor(redColorId);
                        TextUtil.setFakeBoldText(state,true);
                    }else{
                        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, time_unselected_size_scale);
                        state.setTextSize(TypedValue.COMPLEX_UNIT_PX, state_unselected_size_scale);
                        state.setBackgroundColor(colorTransparent);
                        state.setTextColor(whiteColorId);
                        TextUtil.setFakeBoldText(state,false);
                    }
                }
            }

        };
        adapter.setSelectedListener((holder, item) -> {
            updateGoodsInfo();
        });
        mBinding.recyclerLimitedTime.setAdapter(adapter);

    }

    private void initWaiMaiLimitedGoodsRecycler() {
        mBinding.recyclerGoodsList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerGoodsList.setAdapter(new MyBaseRecyclerAdapter<LimitedGoods>(R.layout.item_recycler_limited_goods_waimai, mViewModel.getLimitedGoodsList(mPageType), BR.item) {

            int textSize = (int) UIUtils.getInstance().scalePx(40);
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, LimitedGoods item) {
                helper.setText(R.id.tv_foods_price_pre, TextUtil.getStrikeThroughSpanSpannable("￥189"));  //原始价格
                helper.setText(R.id.tv_limited_kill_price,
                        TextUtil.getAbsoluteSpannable(
                                getResources().getString(R.string.limited_second_kill_price, item.getLimitedPrice()),
                                textSize,
                                1,
                                item.getLimitedPrice().length()+1));
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
        mBinding.recyclerGoodsList.addItemDecoration(Utils.getDefaultItemDecoration(requireContext()));
    }

    // FIXME: 2021/1/19  add headView
    private void initMallLimitedGoodsRecycler(){
        MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<LimitedGoods>(
                R.layout.item_recycler_limited_goods_mall, mViewModel.getLimitedGoodsList(mPageType), BR.item) {
            int textSize = (int) UIUtils.getInstance().scalePx(28);
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, LimitedGoods item) {
                int position = getData().indexOf(item);
                if(position == 0){
                    helper.getView(R.id.cl_content).setBackground(
                            helper.itemView.getContext().getResources().getDrawable(R.drawable.sr_bg_bl_br_12dp_white));
                }
                helper.setText(R.id.tv_foods_price_pre, TextUtil.getStrikeThroughSpanSpannable("￥189"));  //原始价格
                helper.setText(R.id.tv_limited_kill_price,
                        TextUtil.getAbsoluteSpannable(
                                "$"+ item.getLimitedPrice(),
                                textSize,
                                1,
                                item.getLimitedPrice().length()+1));
                float progress = (Float.valueOf(item.getGoodsTotalCount()) - Float.valueOf(item.getRemainingCount()))
                        / Float.valueOf(item.getGoodsTotalCount()) * 100;
                if(progress < 0){
                    progress = 0;
                    LogUtil.e("进度计算出错了 remaining:" + item.getRemainingCount() + " all:" + item.getGoodsTotalCount());
                }

                Button button = helper.getView(R.id.bt_options);
                MyHorizontalProgressView horizontalProgressView = helper.getView(R.id.hpv_remaining_goods_count);
                switch (item.getLimitedTimeStateEnum()){
                    case NO_START:
                        horizontalProgressView.setStrBeforeProgress(requireContext().getString(R.string.wait_to_snapped_up));
                        horizontalProgressView.setEndProgress(0);
                        button.setText("提醒我");//fixme 根据情况切换 提醒我 与 取消提醒
                        break;
                    case STARTING:
                    case ROBBING:
                        horizontalProgressView.setStrBeforeProgress(requireContext().getString(R.string.has_snapped_up));
                        horizontalProgressView.setEndProgress(progress);
                        horizontalProgressView.startProgressAnimation();
                        if(progress == 100){
                            button.setText(R.string.remind_me);
                            button.setClickable(false);
                            button.setBackground(getResources().getDrawable(R.drawable.sr_bg_full_corners_gray_translucent));
                        }else{
                            button.setText(R.string.snapped_up_now);
                            button.setClickable(true);
                            button.setBackground(getResources().getDrawable(R.drawable.sr_bg_gradient_full_radius_theme));
                        }
                        break;
                    case SALE_OUT:
                        horizontalProgressView.setStrBeforeProgress(getContext().getString(R.string.has_snapped_up));
                        horizontalProgressView.setEndProgress(100);
                        horizontalProgressView.startProgressAnimation();

                        button.setText(R.string.remind_me);
                        button.setClickable(false);
                        button.setBackground(getResources().getDrawable(R.drawable.sr_bg_full_corners_gray_translucent));
                        break;
                }
            }
        };
        adapter.addHeaderView(getMallLimitedRecyclerHeadView());
        if(mBinding.recyclerGoodsList.getAdapter() == null){
            mBinding.recyclerGoodsList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            mBinding.recyclerGoodsList.setAdapter(adapter);
            mBinding.recyclerGoodsList.addItemDecoration(new RecyclerView.ItemDecoration() {
                int interval = (int)UIUtils.getInstance().scalePx(getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    int position = parent.getChildAdapterPosition(view);

                    if(position != RecyclerView.NO_POSITION){
                        if(position != adapter.getHeaderLayoutCount()){ //位于头布局下面的一个不设置顶部距离 使其连接
                            outRect.top = interval;
                        }
                        if(position == state.getItemCount()-1){
                            outRect.bottom = interval;
                        }
                    }
                }
            });
        }
    }

    private View getMallLimitedRecyclerHeadView(){
        View headView = View.inflate(requireContext(),R.layout.head_recycler_limited_goods_mall,null);

        ((TextView)headView.findViewById(R.id.tv_activity_introduce)).setText("距本场结束");

        headView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return headView;
    }

    /**
     * 更新商城或外卖recycler
     */
    private void updateGoodsInfo() {

    }

    public static void openPageWithTitle(BaseFragment baseFragment, int pageType,
                                         @DrawableRes int backgroundTopId){
        LogUtil.d("type:" + pageType);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.PAGE_TYPE,pageType);
        bundle.putInt(KEY_BACKGROUND_ID,backgroundTopId);
        baseFragment.openPage(LimitedTimeGoodsFragment.class,bundle);
    }

}
