package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.adapter.tag.SpecificationMallTagAdapter;
import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.bean.Comment;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMallGoodsDetailBinding;
import com.life.waimaishuo.databinding.LayoutDialogMallSelectSpecificationsBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.mine.MineAddShippingAddressFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallGoodsDetailViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.PreViewUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.GridDividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Page(name = "商城商品详情页", anim = CoreAnim.slide)
public class MallGoodsDetailFragment extends BaseFragment {

    private final static String KEY_GOODS_DATA = "goods";

    private FragmentMallGoodsDetailBinding mBinding;
    private MallGoodsDetailViewModel mViewModel;

    public static void openPageWithGoodsId(BaseFragment baseFragment, Goods goods) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_GOODS_DATA, goods);
        baseFragment.openPage(MallGoodsDetailFragment.class, bundle);
    }

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MallGoodsDetailViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallGoodsDetailBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
        mBinding.layoutGoodsOtherInfo.setViewModel(mViewModel);
        mBinding.layoutBottom.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_goods_detail;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

        setFitStatusBarHeight(true);
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);

        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }
        mViewModel.setGoods(bundle.getParcelable(KEY_GOODS_DATA));
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();

        initBanner();
        initAppBarLayoutToolbar();
        initEvaluationRecycler();

        initBottomLayout();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        MyDataBindingUtil.addCallBack(this, mViewModel.preferentialClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.serviceClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.specificationsClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showSelectedSpecificationsBottomDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.shippingAddressClickObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                showSelectedAddressBottomDialog();
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onGoToShopPageObservable,
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        // FIXME: 2021/1/16 传入正确的shop对象
                        MallShopFragment.openPageWithShop(MallGoodsDetailFragment.this, new Shop());
                    }
                });
        MyDataBindingUtil.addCallBack(this, mViewModel.onCustomServiceClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onShopClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onShoppingCartClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                openPage(MallShoppingCartFragment.class);
            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onAddShoppingCartClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        MyDataBindingUtil.addCallBack(this, mViewModel.onBuyNowClick, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });

        mBinding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int folding = 0;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {    // FIXME: 2020/12/25 存在问题：由于布局使用fitSystemWindow 布局会预留出状态栏空间，但activity又是沉浸式，所以会存在空白
                if (folding == 0) {
                    folding = mBinding.flFolding.getMeasuredHeight()
                            - ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).topMargin
                            - mBinding.appbarLayoutToolbar.getMeasuredHeight();    //可折叠的距离
                }
                float gradient = (float) -verticalOffset / (float) folding;//渐变值
                if ((-verticalOffset) < (folding / 2)) {            //折叠未超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);//状态栏白色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(false);
                    /*mBinding.layoutTitleShopDetail.etSearch.setFocusable(false);    // FIXME: 2020/12/25 无效
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(false);*/
                } else {                                          //折叠超过一半
                    setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);//状态栏黑色字体
                    changeStatusBarMode();
                    setTitleBarFoldingStyle(true);
                    /*mBinding.layoutTitle.etSearch.setFocusable(true);
                    mBinding.layoutTitleShopDetail.etSearch.setClickable(true);*/
                }
                LogUtil.d("渐变值：" + gradient);
                /*mBinding.layoutTitleShopDetail.etSearch.setAlpha(gradient);*/

                if ((-verticalOffset) == folding) {//完全折叠
                    LogUtil.d("完全折叠");
                }
            }
        });
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_CODE_ADD_NEW_ADDRESS){
            if(resultCode == Constant.RESULT_CODE_SUCCESS){
                //刷新地址信息？
            }
        }
    }

    private void initBanner() {
        mBinding.bannerLayout.setItemLayoutId(R.layout.adapter_banner_image_item_mall_goods_detail);
        mBinding.bannerLayout.setSource(mViewModel.getBannerItemList())
                .setOnItemClickListener((view, t, position) -> {
                })
                .setIsOnePageLoop(false).startScroll();
    }

    private void initAppBarLayoutToolbar() {
        ((ViewGroup.MarginLayoutParams) mBinding.appbarLayoutToolbar.getLayoutParams()).setMargins(
                0, (int) (UIUtils.getInstance().getSystemBarHeight() / UIUtils.getInstance().getHorValue()), 0, 0);
    }

    private void setTitleBarFoldingStyle(boolean isFolding) {

    }

    private void initEvaluationRecycler() {
        MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_goods_detail_comment, mViewModel.getTopTwoComment(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(BaseViewHolder helper, Object item) {
                super.initView(helper, item);
                RecyclerView recyclerView = helper.getView(R.id.recycler_comment_picture);
                if(recyclerView.getAdapter() == null){
                    MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<String>(R.layout.item_recycler_shop_picture, ((Comment) item).getCommentPictureList(), com.life.waimaishuo.BR.item);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new GridDividerItemDecoration(requireContext(), 3,
                            (int) UIUtils.getInstance().scalePx(
                                    getResources().getDimensionPixelSize(R.dimen.shop_grid_recycler_item_padding))));
                    PreViewUtil.initRecyclerPictureClickListener(MallGoodsDetailFragment.this, adapter, gridLayoutManager);
                }

                helper.setText(R.id.tv_goods_style_and_count, "普通款，1件");
            }
        };
        adapter.addFooterView(getEvaluationFooterView());
        mBinding.recyclerEvaluation.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerEvaluation.setAdapter(adapter);
        mBinding.recyclerEvaluation.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildAdapterPosition(view) != state.getItemCount() - 1) {
                    outRect.top = interval;
                }
            }
        });
    }

    private View getEvaluationFooterView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.bottomMargin = 32;
        Button button = new Button(requireContext());
        button.setText(R.string.view_all_comments);
        button.setTextColor(getResources().getColor(R.color.text_uncheck));
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
        button.setBackgroundResource(R.drawable.sr_stroke_1px_full_radius_gray);
        button.setPadding(24, 20, 24, 20);
        button.setGravity(Gravity.CENTER);
        button.setLayoutParams(layoutParams);
        return button;
    }


    private void initBottomLayout() {
        initBottomLayoutDrawable();

        mBinding.layoutBottom.tvCustomerService.setCompoundDrawables(null, customerServiceDrawable, null, null);
        mBinding.layoutBottom.tvShop.setCompoundDrawables(null, shopDrawable, null, null);
        mBinding.layoutBottom.tvShoppingCart.setCompoundDrawables(null, shoppingCartDrawable, null, null);
    }

    private Drawable customerServiceDrawable;
    private Drawable shopDrawable;
    private Drawable shoppingCartDrawable;

    private void initBottomLayoutDrawable() {
        int size = (int) UIUtils.getInstance().scalePx(40);
        customerServiceDrawable = getResources().getDrawable(R.drawable.ic_customer_service_2_black);
        customerServiceDrawable.setBounds(0, 0, size, size);
        shopDrawable = getResources().getDrawable(R.drawable.ic_shop_black);
        shopDrawable.setBounds(0, 0, size, size);
        shoppingCartDrawable = getResources().getDrawable(R.drawable.ic_shopping_cart_black);
        shoppingCartDrawable.setBounds(0, 0, size, size);
    }

    private BottomSheet selectAddressDialog;
    /**
     * 显示选择收货地址dialog
     */
    private void showSelectedAddressBottomDialog() {
        if (selectAddressDialog == null) {
            View view = getSelectAddressDialogView();

            selectAddressDialog = new BottomSheet(requireContext());
            selectAddressDialog.setContentView(view);
        }
        if (!selectAddressDialog.isShowing()) {
            selectAddressDialog.show();
        }
    }

    /**
     * 获得地址dialog的contentView
     * @return
     */
    private View getSelectAddressDialogView() {
        View view = View.inflate(requireContext(), R.layout.layout_dialog_mall_select_shipping_address, null);

        SelectedPositionRecyclerViewAdapter<Address> adapter = new SelectedPositionRecyclerViewAdapter<Address>(mViewModel.getAddressList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_address_info_selectable;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, Address item) {
                if (selected) {   //选中图标设置
                    holder.setImageResource(R.id.iv_selected, R.drawable.ic_selected_sign);
                } else {
                    if (item.isEffective()) {
                        holder.setImageResource(R.id.iv_selected, R.drawable.ic_round_gray);
                    } else {
                        holder.setImageResource(R.id.iv_selected, 0);
                    }
                }
                if (item.isDefaultAddress()) {    //默认地址设置
                    holder.setVisible(R.id.tv_default, true);
                } else {
                    holder.setVisible(R.id.tv_default, false);
                }
                holder.setText(R.id.tv_recipients_info, item.getUser_name() + "  " + TextUtil.phoneHide(item.getPhone()));
                holder.setText(R.id.tv_address, item.getAddress());
            }
        };
        adapter.setSelectedListener((holder, item) -> {
            // TODO: 2021/1/25  保存选中地址信息
            selectAddressDialog.dismiss();

        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler_address_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        List<Address> noneffectiveAddressList = mViewModel.getnoneEffectiveAddressList();
        if (noneffectiveAddressList.size() > 0) {
            RecyclerView noneffectiveAddressRecycler = view.findViewById(R.id.recycler_address_list_noneffective);
            noneffectiveAddressRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            noneffectiveAddressRecycler.setAdapter(new MyBaseRecyclerAdapter<Address>(R.layout.item_recycler_address_info_selectable, noneffectiveAddressList) {
                @Override
                protected void initView(BaseViewHolder helper, Address item) {
                    super.initView(helper, item);
                    helper.setText(R.id.tv_recipients_info, item.getUser_name() + "  " + TextUtil.phoneHide(item.getPhone()));
                    helper.setText(R.id.tv_address, item.getAddress());
                    helper.setTextColor(R.id.tv_address, getResources().getColor(R.color.text_tip));

                    setViewVisibility(helper.getView(R.id.iv_selected), false);
                    setViewVisibility(helper.getView(R.id.tv_default), false);
                }
            });
        } else {
            setViewVisibility(view.findViewById(R.id.tv_noneffective_address), false);
        }

        view.findViewById(R.id.bt_add_new_address).setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                selectAddressDialog.dismiss();
                openPageForResult(MineAddShippingAddressFragment.class,null,Constant.REQUEST_CODE_ADD_NEW_ADDRESS);
            }
        });

        return view;
    }

    private BottomSheet selectSpecificationsDialog;

    private void showSelectedSpecificationsBottomDialog() {
        if (selectSpecificationsDialog == null) {
            View view = getSelectSpecificationsDialogView();

            selectSpecificationsDialog = new BottomSheet(requireContext());
            selectSpecificationsDialog.setContentView(view);
        }
        if (!selectSpecificationsDialog.isShowing()) {
            selectSpecificationsDialog.show();
        }
    }

    // FIXME: 2021/1/23 修改规格内容 应该均保存再一个bean类里面
    private View getSelectSpecificationsDialogView() {
        //构造假数据
        String[] strings = {"颜色", "颜色", "尺码", "尺码"};

        View view = View.inflate(requireContext(), R.layout.layout_dialog_mall_select_specifications, null);
        LayoutDialogMallSelectSpecificationsBinding binding = DataBindingUtil.bind(view);

        binding.tvTitle.setText("选择颜色尺码");
        binding.tvSelectedInfo.setText("请选择颜色、尺码");
        binding.ivClose.setOnClickListener(v -> {
            //取消选择
            selectSpecificationsDialog.dismiss();
        });

        binding.recyclerSpecification.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerSpecification.setAdapter(new BaseRecyclerAdapter<String>(strings) {
            int titleViewType = 0;
            int flowTabViewType = 1;
            List<Integer> tagKeyList = new ArrayList<>();
            Map<Integer, String> tagStringMap = new HashMap<>();

            @Override
            protected int getItemLayoutId(int viewType) {
                if (viewType == titleViewType) {
                    return R.layout.layout_specification_dialog_reycler_child_text;
                } else if (viewType == flowTabViewType) {
                    return R.layout.layout_simple_flowtag;
                }
                return -1;
            }

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
                if (holder.getItemViewType() == titleViewType) {
                    holder.text(R.id.tv_title, item);
                    holder.textColorId(R.id.tv_title, R.color.text_normal);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.getView(R.id.tv_title).getLayoutParams();
                    if (layoutParams == null) {
                        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    layoutParams.setMarginStart(24);
                }
                if (holder.getItemViewType() == flowTabViewType) {
                    FlowTagLayout flowTagLayout = holder.findViewById(R.id.flowTagLayout);

                    if (flowTagLayout.getAdapter() == null) {
                        SpecificationMallTagAdapter tagAdapter = new SpecificationMallTagAdapter(getContext());
                        tagAdapter.setSelectedPosition(0);
                        tagAdapter.selectedIndex = 0;
                        String[] strings = new String[]{"玫瑰红色", "豆沙色", "白色", "玫瑰红色", "玫瑰红色", "豆沙色", "白色", "玫瑰红色"};

                        flowTagLayout.setAdapter(tagAdapter);
                        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                        flowTagLayout.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
                            @Override
                            public void onItemSelect(FlowTagLayout parent, int tagPosition, List<Integer> selectedList) {
                                LogUtil.d("流标签选中index:" + tagPosition);
                                tagAdapter.selectedIndex = tagPosition;
                                tagAdapter.setSelectedPosition(tagPosition);
                                tagAdapter.notifyDataSetChanged();  //字体颜色没有被修正 需要notifyChange

                                //保存选中的数据
                                if (!tagKeyList.contains(position)) {
                                    tagKeyList.add(position);
                                } else {
                                    tagStringMap.remove(position);
                                }
                                tagStringMap.put(position, strings[tagPosition]);

                                StringBuilder selectedInfoBuilder = new StringBuilder("已选：");
                                for (int key : tagKeyList) {
                                    selectedInfoBuilder.append(tagStringMap.get(key)).append(',');
                                }
                                selectedInfoBuilder.deleteCharAt(selectedInfoBuilder.length() - 1);
                                LogUtil.d("string selected : " + selectedInfoBuilder.toString());
                                binding.tvSelectedInfo.setText(selectedInfoBuilder.toString());
                            }
                        });
                        tagAdapter.addTags(strings); // FIXME: 2021/1/4 修改内容
                    }
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position % 2 == 0) {
                    return titleViewType;
                } else {
                    return flowTabViewType;
                }
            }
        });
        binding.recyclerSpecification.addItemDecoration(new RecyclerView.ItemDecoration() {
            Paint mBgPaint;
            boolean hasInitPaint = false;

            int interval = (int) UIUtils.getInstance().scalePx(24);

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                if (!hasInitPaint) {
                    hasInitPaint = true;
                    mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mBgPaint.setColor(getResources().getColor(R.color.divider_2));
                }

                int left, top, right, bottom;
                for (int i = 1; i < parent.getChildCount() - 1; i++) {    //去掉第一个和最后一个
                    if (i % 2 == 1) {
                        View child = parent.getChildAt(i);
                        top = child.getBottom();
                        bottom = child.getBottom() + 1;
                        left = child.getLeft();
                        right = child.getRight();
                        c.drawRect(left, top, right, bottom, mBgPaint);
                    }
                }
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position != 0 && position % 2 == 0) {
                    outRect.top = interval;
                }
            }
        });

        return view;
    }

}
