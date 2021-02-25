package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecyclerViewAdapter;
import com.life.waimaishuo.adapter.statelayout.CustomSingleViewAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.bean.Shop;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentWaimaiBrandZoneBinding;
import com.life.waimaishuo.databinding.ItemRecyclerBrandGoodsBinding;
import com.life.waimaishuo.databinding.ItemRecyclerBrandZoneBinding;
import com.life.waimaishuo.enumtype.SortTypeEnum;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.view.fragment.BaseStatusLoaderFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.BrandZoneViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.util.Utils;
import com.life.waimaishuo.views.SortTypeView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Page(name = "品牌专区", anim = CoreAnim.slide)
public class BrandZoneFragment extends BaseStatusLoaderFragment {
    private final static String BUNDLE_KEY_BRAND_ID = "bundle_key_brand_id";

    private FragmentWaimaiBrandZoneBinding mBinding;
    private BrandZoneViewModel mViewModel;

    private int brandId;    //品牌Id

    private MyBaseRecyclerAdapter<Shop> brandShopRVAdapter;   //店铺RV的适配器

    private SelectedPositionRecyclerViewAdapter<IconStrData> adapter;
    private LinearLayoutManager layoutManager;

    private String mFoodType;   //选中的食物类别

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new BrandZoneViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiBrandZoneBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_brand_zone;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        if(getArguments() == null){
            throw new Error(Constant.ERROR_MSG_NO_BUNDLE);
        }

        brandId = getArguments().getInt(BUNDLE_KEY_BRAND_ID);

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

        //initSubtypeRecycler();    //不需要顶部recycler了
        setViewVisibility(mBinding.recyclerFoodSubtype,false);

        initSortView();
        initBrandShopRecycler();

        mViewModel.setBrandId(brandId);

        showLoading();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();

        if(adapter != null){
            adapter.setSelectedListener((holder, item, isCancel) -> {
                mFoodType = item.getIconType();
                mBinding.recyclerFoodSubtype.scrollToPosition(adapter.getSelectedPosition());
                refreshSortType(SortTypeEnum.SCORE);
                refreshShopContent();
            });
        }

        if(brandShopRVAdapter != null){
            brandShopRVAdapter.setOnItemClickListener((adapter, view, position) ->
                    ShopDetailFragment.openPage(BrandZoneFragment.this,
                            brandShopRVAdapter.getItem(position).getShopId()));
        }

        mBinding.stickyView.setOnSortTypeChangeListener(new SortTypeView.onSortTypeChangeListener() {
            @Override
            public void onSortPopShow() {
                // TODO: 2020/12/16 StickyView滚动到最上面
            }

            @Override
            public void onSortTypeChange(SortTypeEnum sortTypeEnum) {
                mViewModel.setSortRules(sortTypeEnum);
                refreshShopContent();
            }

            @Override
            public void onPreferentialChange(int selectedPosition) {
                refreshShopContent();
            }

            @Override
            public void onScreenChange() {
                mViewModel.setActivityType(mBinding.stickyView.getSelectedPreferential());
                mViewModel.setScreenData(String.valueOf(mBinding.stickyView.getMinPrice()),
                        String.valueOf(mBinding.stickyView.getMaxPrice()));
                refreshShopContent();
            }

        });
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();

        refreshShopContent();
    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.requestShopListObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mHandler.post(() -> {
                    if(mViewModel.getBrandShopList().size() > 0){
                        showContent();
                    }else{
                        showEmpty();
                    }
                    brandShopRVAdapter.setNewData(mViewModel.getBrandShopList());
                    brandShopRVAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
    }

    private void initSubtypeRecycler(){
        /*layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mBinding.recyclerFoodSubtype.setLayoutManager(layoutManager);

        adapter = getSubtypeRecyclerAdapter();
        mBinding.recyclerFoodSubtype.setAdapter(adapter);
        mBinding.recyclerFoodSubtype.addItemDecoration(new RecyclerView.ItemDecoration() {
            int left_interval_22 = (int) UIUtils.getInstance().scalePx(22);
            int left_interval_8 = (int)UIUtils.getInstance().scalePx(8);

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.left = (position == 0) ? left_interval_22 : left_interval_8;
                outRect.right = (position == state.getItemCount()-1) ? left_interval_22 : 0;
            }
        });*/
    }

    private void initSortView(){
        mBinding.stickyView.setPreferentialTab(mViewModel.getPreferential());
        mBinding.stickyView.setScreenData(mViewModel.getScreenData());
    }

    private void initBrandShopRecycler() {
        brandShopRVAdapter = new MyBaseRecyclerAdapter<Shop>(R.layout.item_recycler_brand_zone,mViewModel.getBrandShopList(), com.life.waimaishuo.BR.item){
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, Shop item) {
                super.initView(viewDataBinding, helper, item);
                ItemRecyclerBrandZoneBinding binding = (ItemRecyclerBrandZoneBinding)viewDataBinding;

                String monSalesCount = getString(R.string.sale_count_a_month)+item.getMonSalesVolume();
                String distTime = getString(R.string.distribution_minute,String.valueOf(item.getDistTime()));

                binding.scoreLayout.tvScore.setText(item.getFavorable_rate());
                binding.tvSaleCount.setText(monSalesCount);
                binding.tvDeliverTime.setText(distTime);
                binding.tvDeliverPrice.setText(String.format("起送￥%s", item.getDeliveryPrice()));

                if(binding.recyclerTag.getAdapter() == null){
                    String[] tags = item.getTag_value().split(",");
                    List<String> tagList = new ArrayList<>(Arrays.asList(tags));
                    binding.recyclerTag.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
                    binding.recyclerTag.setAdapter(new MyBaseRecyclerAdapter<String>(R.layout.adapter_tag_item_cash_back,tagList){
                        @Override
                        protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, String item) {
                            helper.setText(R.id.tv_tag,item);
                        }
                    });
                }

                if(binding.recyclerGoodsList.getAdapter() == null){
                    binding.recyclerGoodsList.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(),3,LinearLayoutManager.VERTICAL,false));
                    binding.recyclerGoodsList.setAdapter(new MyBaseRecyclerAdapter<Goods>(R.layout.item_recycler_brand_goods,item.getGoodsInfoList(), com.life.waimaishuo.BR.item){
                        @Override
                        protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, Goods item) {
                            super.initView(viewDataBinding, helper, item);
                            ItemRecyclerBrandGoodsBinding binding = (ItemRecyclerBrandGoodsBinding)viewDataBinding;

                            binding.tvPrice.setText(TextUtil.getAbsoluteSpannable
                                    (String.format("￥%s", item.getMinPrice()),(int) UIUtils.getInstance().scalePx(20),0,1));
                        }

                        @Override
                        public int getItemCount() { //只展示三个
                            int size = getData().size();
                            if(size >= 3){
                                return 3;
                            }else{
                                return size;
                            }
                        }
                    });
                }
            }
        };

        mBinding.recyclerBrandShop.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerBrandShop.setAdapter(brandShopRVAdapter);
        mBinding.recyclerBrandShop.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance().scalePx(requireContext().getResources().getDimensionPixelSize(R.dimen.interval_size_xs));

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position != RecyclerView.NO_POSITION){
                    if(position == 0){
                        outRect.top = (int) UIUtils.getInstance().scalePx(20);
                    }else{
                        outRect.top = interval;
                    }
                    if(position == state.getItemCount()-1){
                        outRect.bottom = interval;
                    }
                }
            }
        });
    }

    private SelectedPositionRecyclerViewAdapter<IconStrData> getSubtypeRecyclerAdapter() {
        return new SelectedPositionRecyclerViewAdapter<IconStrData>(mViewModel.getSubtypeTitles()){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_waimai_zero_deliver_subtype;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setImageResource(R.id.iv_subtype_icon,item.getResImgId());
                holder.setText(R.id.tv_subtype_name,item.getIconType());
                if(selected){
                    holder.setBackgroundRes(R.id.tv_subtype_name,R.drawable.sr_bg_full_corners_white);
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.colorTheme));
                    holder.setVisible(R.id.iv_selected_sign,true);
                    holder.setVisible(R.id.view_tl_tr_24_radius,true);
                }else{
                    holder.setBackgroundColor(R.id.tv_subtype_name,getResources().getColor(R.color.transparent));
                    holder.setTextColor(R.id.tv_subtype_name,getResources().getColor(R.color.white));
                    holder.setVisible(R.id.iv_selected_sign,false);
                    holder.setVisible(R.id.view_tl_tr_24_radius,false);
                }
            }
        };
    }

    private void refreshSortType(SortTypeEnum sortType){
        mBinding.stickyView.setSortType(sortType);
    }

    /**
     * 重置内容（排序 或重新获取列表数据）
     */
    private void refreshShopContent() {
        LogUtil.e("重置内容");
        mViewModel.refreshListDate();
    }

    public static void openPage(BaseFragment baseFragment, int brandId){
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_BRAND_ID,brandId);
        baseFragment.openPage(BrandZoneFragment.class, bundle);
    }

    @Override
    protected View getWrapView() {
        return mBinding.flState;
    }

    @Override
    protected StatusLoader.Adapter getStatusLoaderAdapter() {
        return new CustomSingleViewAdapter();
    }
}
