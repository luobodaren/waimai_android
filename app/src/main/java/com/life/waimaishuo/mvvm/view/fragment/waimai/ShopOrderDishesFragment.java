package com.life.waimaishuo.mvvm.view.fragment.waimai;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.BaseBannerAdapter;
import com.life.waimaishuo.adapter.config.CustomLinkagePrimaryShopGoodsAdapterConfig;
import com.life.waimaishuo.adapter.config.CustomLinkageSecondaryShopGoodsAdapterConfig;
import com.life.waimaishuo.adapter.tag.SpecificationWaiMaiTagAdapter;
import com.life.waimaishuo.bean.ui.LinkageShopGoodsGroupedItemInfo;
import com.life.waimaishuo.databinding.FragmentWaimaiShopOrderDishesBinding;
import com.life.waimaishuo.databinding.LayoutDialogChoseSpecificationBinding;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.life.waimaishuo.listener.OnSecondaryShopGoodsItemClickListener;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.waimai.ShopOrderDishesViewModel;
import com.life.waimaishuo.util.MyDataBindingUtil;
import com.life.waimaishuo.util.StatusBarUtils;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.views.MyLinkageRecyclerView;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;

import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Page(name = "点餐", anim = CoreAnim.slide)
public class ShopOrderDishesFragment extends BaseFragment
        implements OnPrimaryItemClickListener,
        OnSecondaryShopGoodsItemClickListener {

    private FragmentWaimaiShopOrderDishesBinding mBinding;
    private ShopOrderDishesViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new ShopOrderDishesViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentWaimaiShopOrderDishesBinding) mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_waimai_shop_order_dishes;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_DARK);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initBanner();

    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCallBack();
    }

    @Override
    protected void firstRequestData() {
        super.firstRequestData();
    }

    @Override
    public void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

    @Override
    public void onPrimaryItemChange(int position) {

    }

    @Override
    public void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        openPage(GoodsDetailFragment.class, new Bundle()); // FIXME: 2020/12/28 后续需要传入商品id
    }

    @Override
    public void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        if (view.getId() == R.id.bt_chose_specification) {
            showChoseSpecificationDialog(item);
        }
    }

    @Override
    public void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {

    }

    private void initBanner() {
        BaseBannerAdapter mAdapterHorizontal
                = new BaseBannerAdapter(mViewModel.getBannerSource(), R.layout.adapter_banner_image_item_shop_detail);
        mAdapterHorizontal.setOnBannerItemClickListener(position ->
                Toast.makeText(getContext(), "点击了轮播图：" + position, Toast.LENGTH_SHORT).show());
        mBinding.contentLayout.setAdapter(mAdapterHorizontal);
        mBinding.contentLayout.setItemSpace((int) UIUtils.getInstance().scalePx(20));
    }

    private void initLinkageRecycler() {
        MyLinkageRecyclerView<LinkageShopGoodsGroupedItemInfo> linkage = mBinding.linkageOrderDishes;
        linkage.init(mViewModel.getShopGoodsItems(),
                new CustomLinkagePrimaryShopGoodsAdapterConfig<>(this, linkage),
                new CustomLinkageSecondaryShopGoodsAdapterConfig<>(this));
        linkage.setGridMode(false);
    }

    Dialog specificationDialog;

    private void showChoseSpecificationDialog(BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        if (specificationDialog == null) {
            specificationDialog = new Dialog(requireContext(), R.style.mySimpleNoTitleDialog);
            specificationDialog.setContentView(getChoseSpecificationView(item));

            specificationDialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams params = specificationDialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = (int) UIUtils.getInstance().scalePx(
                    requireContext().getResources().getDimensionPixelSize(R.dimen.width_of_specification_dialog));
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            specificationDialog.getWindow().setAttributes(params);
        }

        if (!specificationDialog.isShowing()) {
            specificationDialog.show();
        }
    }

    // FIXME: 2021/1/4 修改数据获取的方式
    private View getChoseSpecificationView(BaseGroupedItem<LinkageShopGoodsGroupedItemInfo> item) {
        View view = View.inflate(requireContext(), R.layout.layout_dialog_chose_specification, null);
        LayoutDialogChoseSpecificationBinding binding = DataBindingUtil.bind(view);

        binding.ivClose.setOnClickListener(v -> {
            specificationDialog.dismiss();
        });

        Glide.with(requireContext())
                .load(item.info.getImgUrl())
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(binding.ivGoodsImg);

        binding.tvName.setText(item.info.getTitle());
        binding.tvGoodsPrice.setText("18.99");
        binding.layoutGoodsOptionAddShoppingCart.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = parseInt(binding.layoutGoodsOptionAddShoppingCart.tvCount.getText().toString()) + 1 + "";
                binding.layoutGoodsOptionAddShoppingCart.tvCount.setText(count);
            }
        });
        binding.layoutGoodsOptionAddShoppingCart.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = parseInt(binding.layoutGoodsOptionAddShoppingCart.tvCount.getText().toString()) - 1;
                if (count >= 0) {
                    binding.layoutGoodsOptionAddShoppingCart.tvCount.setText(String.valueOf(count));
                }
            }
        });

        int goodsId = 0;
        binding.recyclerSpecification.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerSpecification.setAdapter(new BaseRecyclerAdapter<String>(mViewModel.getGoodsSpecificationsInfo(goodsId)) {
            int titleViewType = 0;
            int flowTabViewType = 1;
            List<Integer> tagKeyList = new ArrayList<>();
            Map<Integer,String> tagStringMap = new HashMap<>();
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
                }
                if (holder.getItemViewType() == flowTabViewType) {
                    FlowTagLayout flowTagLayout = holder.findViewById(R.id.flowTagLayout);
                    if(flowTagLayout.getAdapter() == null){
                        SpecificationWaiMaiTagAdapter tagAdapter = new SpecificationWaiMaiTagAdapter(getContext());
                        tagAdapter.setSelectedPosition(0);
                        tagAdapter.selectedIndex = 0;
                        String[] strings = new String[]{"微微辣", "特辣", "中辣", "不要香菜"};

                        flowTagLayout.setAdapter(tagAdapter);
                        flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                        flowTagLayout.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
                            @Override
                            public void onItemSelect(FlowTagLayout parent, int tagPosition, List<Integer> selectedList) {
                                LogUtil.d("流标签选中index:" + tagPosition);
                                tagAdapter.selectedIndex = tagPosition;
                                tagAdapter.setSelectedPosition(tagPosition);
                                tagAdapter.notifyDataSetChanged();

                                //保存选中的数据
                                if(!tagKeyList.contains(position)){
                                    tagKeyList.add(position);
                                }else{
                                    tagStringMap.remove(position);
                                }
                                tagStringMap.put(position,strings[tagPosition]);

                                StringBuilder selectedInfoBuilder = new StringBuilder("已选：");
                                for(int key:tagKeyList){
                                    selectedInfoBuilder.append(tagStringMap.get(key)).append(',');
                                }
                                selectedInfoBuilder.deleteCharAt(selectedInfoBuilder.length()-1);
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

        return view;

    }

    private void addCallBack(){
        MyDataBindingUtil.addCallBack(this, mViewModel.requestShopGoodsObservable, new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                initLinkageRecycler();
            }
        });
    }

}
