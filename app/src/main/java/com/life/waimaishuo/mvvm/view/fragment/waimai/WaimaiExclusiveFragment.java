package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.databinding.FragmentExclusiveBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.WaimaiExclusiveViewModel;
import com.life.waimaishuo.views.FilletImageView;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.views.widget.StaggeredDividerItemDecoration;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

@Page(name = "专属早餐",anim = CoreAnim.slide)
public class WaimaiExclusiveFragment extends BaseFragment {

    WaimaiExclusiveViewModel viewModel;

    @Override
    protected BaseViewModel setViewModel() {
        viewModel = new WaimaiExclusiveViewModel();
        return viewModel;
    }

    @Override
    protected void bindViewModel() {
        ((FragmentExclusiveBinding)mViewDataBinding).setViewModel(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exclusive;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
        setFitStatusBarHeight(true);
    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();
        titleBar.getCenterText().setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        titleBar.setCenterTextBold(true);
        titleBar.setTitleColor(getResources().getColor(R.color.white));
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));

        int titleBarDrawableSizes = (int)UIUtils.getInstance(getContext()).scalePx(R.dimen.titlebar_drawable_size);
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
    protected void initViews() {
        super.initViews();
        initExclusiveRecycler();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    private void initExclusiveRecycler() {
        int spanCount = 2;
        RecyclerView recyclerView = ((FragmentExclusiveBinding)mViewDataBinding).recyclerExclusive;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                spanCount,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(getContext(),R.dimen.interval_size_xs,spanCount));
        recyclerView.setAdapter(new MyBaseRecyclerAdapter<Goods>(R.layout.item_exclusive_goods_info,
                viewModel.getmBreakFastList(),null) {
            @Override
            protected void initView(BaseViewHolder helper, Goods item) {
                helper.setText(R.id.tv_goods_name,item.getName());
                helper.setText(R.id.tv_img_describe,getString(R.string.arrive_in_minutes,String.valueOf(item.getTime_send())));
                helper.setText(R.id.tv_goods_deliver_info,item.getScore() +
                        " " +getString(R.string.price_of_sending,item.getPrice_deliver())
                        + " " + getString(R.string.sale_count_a_month,item.getCount_per_month()));
                Glide.with(WaimaiExclusiveFragment.this)
                        .asBitmap()
                        .placeholder(R.drawable.ic_waimai_brand)
                        .load(item.getGoodsImgUrl())
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                ((FilletImageView)helper.getView(R.id.iv_goods_img)).setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }
        });
    }
}
