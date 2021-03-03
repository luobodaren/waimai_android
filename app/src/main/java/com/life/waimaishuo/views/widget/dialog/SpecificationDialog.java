package com.life.waimaishuo.views.widget.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.tag.SpecificationWaiMaiTagAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.databinding.LayoutDialogChoseSpecificationBinding;
import com.life.waimaishuo.views.MyFlowTagLayout;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * 选规格的Dialog
 */
public class SpecificationDialog extends Dialog {

    private TextView tvName;
    private TextView tvGoodsPrice;
    private TextView tvAllGoodsPrice;
    private ImageView ivGoodsImg;
    private TextView tvSelectedCount;
    private TextView tvSelectedInfo;
    private NestedScrollView sv_attribute;
    private RecyclerView attributeRecycler;
    private BaseRecyclerAdapter<Goods.Attribute> attributeRVAdapter;
    //规格
    private MyFlowTagLayout specificationFTL;
    private SpecificationWaiMaiTagAdapter tagAdapter;

    //保存选中的属性 信息
    private String specification;
    private List<Integer> tagKeyList = new ArrayList<>();
    private Map<Integer, String> tagStringMap = new HashMap<>();

    private Goods goods;    //数据来源

    public SpecificationDialog(@NonNull Context context) {
        super(context, R.style.mySimpleNoTitleDialog);

        setCanceledOnTouchOutside(true);

        initView();

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int) UIUtils.getInstance().scalePx(
                context.getResources().getDimensionPixelSize(R.dimen.width_of_specification_dialog));
        params.height = (int) UIUtils.getInstance().scalePx(
                context.getResources().getDimensionPixelSize(R.dimen.height_of_specification_dialog));
        getWindow().setAttributes(params);
    }

    // FIXME: 2021/1/4 修改数据获取的方式
    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_dialog_chose_specification, null);
        LayoutDialogChoseSpecificationBinding binding = DataBindingUtil.bind(view);

        //对象保存
        tvName = binding.tvName;
        tvGoodsPrice = binding.tvGoodsPrice;
        tvAllGoodsPrice = binding.tvAllGoodsPrice;
        ivGoodsImg = binding.ivGoodsImg;
        tvSelectedCount = binding.layoutGoodsOptionAddShoppingCart.tvCount;
        tvSelectedInfo = binding.tvSelectedInfo;
        sv_attribute = binding.svAttribute;
        attributeRecycler = binding.recyclerSpecification;
        specificationFTL = binding.ftlSpecification;

        binding.ivClose.setOnClickListener(v -> dismiss());

        binding.layoutGoodsOptionAddShoppingCart.ivAdd.setOnClickListener(v -> handleAddOrReduceGoodsCount(true));
        binding.layoutGoodsOptionAddShoppingCart.ivRemove.setOnClickListener(v -> handleAddOrReduceGoodsCount(false));

        //初始化规格 属性
        if (goods != null) {
            if (goods.getAttributeList() != null && goods.getAttributeList().size() > 0) {
                initAttributeRecycler();
            }
            if (goods.getSpecificationList() != null && goods.getSpecificationList().size() > 0) {
                initSpecificationFTL();
            }
        }

        resetSelectInfo();

        setContentView(view);
    }

    private void initAttributeRecycler() {
        attributeRVAdapter = new BaseRecyclerAdapter<Goods.Attribute>(goods.getAttributeList()) {

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.layout_specification_dialog_reycler_child;
            }

            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, Goods.Attribute item) {
                holder.text(R.id.tv_title, item.getName());

                FlowTagLayout flowTagLayout = holder.findViewById(R.id.flowTagLayout);
                if (flowTagLayout.getAdapter() == null) {
                    SpecificationWaiMaiTagAdapter tagAdapter = new SpecificationWaiMaiTagAdapter(getContext());
                    tagAdapter.setSelectedPosition(0);
                    tagAdapter.selectedIndex = 0;

                    flowTagLayout.setAdapter(tagAdapter);
                    flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                    flowTagLayout.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
                        @Override
                        public void onItemSelect(FlowTagLayout parent, int tagPosition, List<Integer> selectedList) {
                            LogUtil.d("流标签选中index:" + tagPosition);
                            //tagAdapter.selectedIndex = tagPosition;
                            tagAdapter.setSelectedPosition(tagPosition);

                            //保存选中的数据
                            if (!tagKeyList.contains(position)) {
                                tagKeyList.add(position);
                            } else {
                                tagStringMap.remove(position);
                            }
                            tagStringMap.put(position, item.getValue()[tagPosition]);

                            resetSelectInfo();
                        }
                    });
                    tagAdapter.addTags(item.getValue()); // FIXME: 2021/1/4 修改内容
                }
            }

        };
        attributeRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        attributeRecycler.setAdapter(attributeRVAdapter);
    }

    private void initSpecificationFTL() {
        tagAdapter = new SpecificationWaiMaiTagAdapter(getContext());
        specificationFTL.setAdapter(tagAdapter);
        specificationFTL.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        specificationFTL.setOnTagSelectListener(new FlowTagLayout.OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int tagPosition, List<Integer> selectedList) {
                LogUtil.d("流标签选中index:" + tagPosition);
                //tagAdapter.selectedIndex = tagPosition;
                tagAdapter.setSelectedPosition(tagPosition);

                specification = tagAdapter.getItem(tagPosition);

                resetSelectInfo();
            }
        });

        //初始化
        List<String> tags = new ArrayList<>();
        for (Goods.Specification specification : goods.getSpecificationList()) {
            tags.add(specification.getName());
        }
        tagAdapter.addTags(tags);
        tagAdapter.setSelectedPosition(0);
        tagAdapter.selectedIndex = 0;
        specification = goods.getSpecificationList().get(0).getName();  //默认选中第一个 保存数据
    }

    /**
     * 设置选中信息（从tagStringMap中获取）
     */
    private void resetSelectInfo() {
        StringBuilder selectedInfoBuilder = new StringBuilder("已选：");
        if (specification != null && !"".equals(specification)) {
            selectedInfoBuilder.append(specification).append("/");
        }
        for (int key : tagKeyList) {
            selectedInfoBuilder.append(tagStringMap.get(key)).append('/');
        }
        selectedInfoBuilder.deleteCharAt(selectedInfoBuilder.length() - 1);
        tvSelectedInfo.setText(selectedInfoBuilder.toString());
    }

    private void handleAddOrReduceGoodsCount(boolean isAdd) {
        int count;
        if (isAdd) {
            count = parseInt(tvSelectedCount.getText().toString()) + 1;

        } else {
            count = parseInt(tvSelectedCount.getText().toString()) - 1;
        }
        if (count > 0) {
            tvSelectedCount.setText(String.valueOf(count));
            tvAllGoodsPrice.setText(String.valueOf(
                    count * Float.valueOf(
                            goods.getSpecificationList().get(
                                    tagAdapter.getSelectedIndex()).getSpecialPrice())));
        }
    }


    /**
     * 设置内容更新界面数据
     *
     * @param goods
     */
    public void setData(Goods goods) {
        this.goods = goods;

        Glide.with(getContext())
                .load(goods.getGoodsImgUrl())
                .placeholder(R.drawable.ic_waimai_brand)
                .centerCrop()
                .into(ivGoodsImg);

        //初始展示价格选中规格第一项价格
        String price = goods.getSpecificationList().get(0).getSpecialPrice();
        tvGoodsPrice.setText(price);
        tvAllGoodsPrice.setText(price);
        tvName.setText(goods.getName());

        //初始选中的数据
        tagKeyList.clear();
        tagStringMap.clear();
        specification = goods.getSpecificationList().get(0).getName();
        int attributeSize = goods.getAttributeList().size();
        for (int i = 0; i < attributeSize; i++) {
            tagKeyList.add(i);
            tagStringMap.put(i,goods.getAttributeList().get(i).getValue()[0]);
        }
        resetSelectInfo();

        if (attributeRVAdapter == null) {
            if (goods.getAttributeList() != null && goods.getAttributeList().size() > 0) {
                initAttributeRecycler();
            }
        } else {
            attributeRVAdapter.refresh(goods.getAttributeList());
        }
        if (tagAdapter == null) {
            if (goods.getSpecificationList() != null && goods.getSpecificationList().size() > 0) {
                initSpecificationFTL();
            }
        } else {
            List<String> tags = new ArrayList<>();
            for (Goods.Specification specification : goods.getSpecificationList()) {
                tags.add(specification.getName());
            }
            tagAdapter.addTags(tags);
        }

        UIUtils.getInstance().setScaleTag(sv_attribute);
        int height = UIUtils.getInstance().getViewMeasureHeight(sv_attribute);
        int scaleHeight = (int) UIUtils.getInstance().scalePx(620);
        if (height > scaleHeight) {
            ViewGroup.LayoutParams lp = sv_attribute.getLayoutParams();
            lp.height = scaleHeight;
            sv_attribute.setLayoutParams(lp);
        }
    }

}
