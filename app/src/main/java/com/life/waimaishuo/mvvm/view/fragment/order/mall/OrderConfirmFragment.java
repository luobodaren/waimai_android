package com.life.waimaishuo.mvvm.view.fragment.order.mall;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.adapter.SelectedPositionRecylerViewAdapter;
import com.life.waimaishuo.bean.ui.IconStrData;
import com.life.waimaishuo.bean.ui.MallGoods;
import com.life.waimaishuo.bean.ui.MallOrder;
import com.life.waimaishuo.bean.ui.TypeDescribeValue;
import com.life.waimaishuo.databinding.FragmentConfirmAnOrderMallBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.order.MallConfirmOrderViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;

import java.util.List;

@Page(name = "确认订单-商城")
public class OrderConfirmFragment extends BaseFragment {

    private int mPageState = -1;
    public final static int ORDER_CONFIRM = 1;
    public final static int ORDER_CANCEL = 2;   //已取消
    public final static int ORDER_WAIT_FOR_PAY = 3;
    public final static int ORDER_WAIT_FOR_DELIVER = 4;
    public final static int ORDER_DELIVERING_EXPRESSAGE = 5;    //快递配送
    public final static int ORDER_DELIVERING_INTRA_CITY_SERVICE = 6;    //同城配送
    public final static int ORDER_DELIVERED = 7;

    private FragmentConfirmAnOrderMallBinding mBinding;
    private MallConfirmOrderViewModel mViewModel;

    @Override
    protected BaseViewModel setViewModel() {
        if(mViewModel == null){
            mViewModel = new MallConfirmOrderViewModel();
        }
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentConfirmAnOrderMallBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_confirm_an_order_mall;
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

        setPageElementByOrderType();

        initOrderShopsGoods();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getString(R.string.confirm_order));
        mBinding.layoutTitle.ivShare.setVisibility(View.GONE);
    }

    private boolean hasInitPayType = false;
    // 判断底部三个按钮的信息
    private void setPageElementByOrderType(){

        switch (mPageState){
            case ORDER_CONFIRM:
                setViewVisibility(mBinding.clDeliver,false);
                setViewVisibility(mBinding.layoutPayType.llContent,true);

                if(!hasInitPayType){
                    hasInitPayType = true;
                    initPayTypeLayout();
                }

                break;
            case ORDER_CANCEL:
            case ORDER_WAIT_FOR_PAY:
                setViewVisibility(mBinding.clDeliver,false);
                setViewVisibility(mBinding.layoutPayType.llContent,false);
                break;
            case ORDER_WAIT_FOR_DELIVER:
            case ORDER_DELIVERING_EXPRESSAGE:
            case ORDER_DELIVERING_INTRA_CITY_SERVICE:
            case ORDER_DELIVERED:
                setViewVisibility(mBinding.clDeliver,true);
                setViewVisibility(mBinding.layoutPayType.llContent,false);
                break;

        }
    }

    private void initOrderShopsGoods(){
        mBinding.recyclerShopsGoods.setLayoutManager(
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerShopsGoods.setAdapter(
                new MyBaseRecyclerAdapter<MallOrder>(R.layout.item_recycler_mall_comfirm_order_shops_goods,mViewModel.getMallOrder()){
                    @Override
                    protected void initView(BaseViewHolder helper, MallOrder item) {
                        super.initView(helper, item);
                        ImageView shopIcon = helper.getView(R.id.iv_shop_icon);
                        Glide.with(requireContext())
                                .load(item.getShop().getShop_head_portrait())
                                .centerCrop()
                                .placeholder(R.drawable.ic_waimai_brand)
                                .into(shopIcon);

                        helper.setText(R.id.tv_shop_name,item.getShop().getShop_name());

                        RecyclerView goodsInfoRecycler = helper.getView(R.id.recycler_goods_info);
                        initGoodsInfoRecycler(goodsInfoRecycler,item.getMallGoodsList());

                        RecyclerView orderInfoRecycler = helper.getView(R.id.recycler_order_info);
                        initOrderInfoRecycler(orderInfoRecycler,item.getTypeDescribeValueList());

                        helper.setText(R.id.tv_all_goods_price_tip,String.valueOf(item.getOrderPrice()));
                        helper.setText(R.id.tv_goods_count,getString(R.string.goods_number_2,String.valueOf(item.getMallGoodsList().size())));
                    }
                });
    }

    private void initGoodsInfoRecycler(RecyclerView recyclerView, List<MallGoods> mallGoodsList){
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(
                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_confirm_order_shops_goods_info,mallGoodsList, com.life.waimaishuo.BR.item));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance(requireContext()).scalePx(24);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
    }

    private void initOrderInfoRecycler(RecyclerView recyclerView, List<TypeDescribeValue> typeDescribeValueList){
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(
                new MyBaseRecyclerAdapter(R.layout.item_recycler_mall_confirm_order_info,typeDescribeValueList, com.life.waimaishuo.BR.item));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            int interval = (int) UIUtils.getInstance(requireContext()).scalePx(40);
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = interval;
            }
        });
    }

    private boolean isShowAllPayType = false;
    private int showCountOfPayType = 2;//支付方式显示个数
    private void initPayTypeLayout(){
        SelectedPositionRecylerViewAdapter<IconStrData> adapter = new SelectedPositionRecylerViewAdapter<IconStrData>(mViewModel.getPayTypeList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_recycler_pay_type;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, boolean selected, IconStrData item) {
                holder.setText(R.id.tv_pay_type_name, item.getIconType());
                holder.setImageResource(R.id.iv_left, item.getResImgId());
                holder.setVisible(R.id.divider_top,false);
                if (selected) {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_fill_red);
                } else {
                    holder.setImageResource(R.id.iv_right, R.drawable.ic_check_round_fill_gray);
                }
            }

            @Override
            public int getItemCount() {
                if(isShowAllPayType){
                    return super.getItemCount();
                }else{
                    return showCountOfPayType;
                }
            }
        };
        mBinding.layoutPayType.recyclerPayTypeList.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.layoutPayType.recyclerPayTypeList.setAdapter(adapter);

        adapter.setSelectedListener((holder, item) -> {
            LogUtil.d("支付方式：" + item.getIconType());
        });

        mBinding.layoutPayType.tvShowMore.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                if(isShowAllPayType == true){
                    isShowAllPayType = false;
                }else{
                    isShowAllPayType = true;
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


}