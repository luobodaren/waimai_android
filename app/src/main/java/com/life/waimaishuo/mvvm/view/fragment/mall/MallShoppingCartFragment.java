package com.life.waimaishuo.mvvm.view.fragment.mall;

import android.graphics.Color;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.ui.MallShoppingCartItemData;
import com.life.waimaishuo.bean.ui.MallShoppingCartShopGoods;
import com.life.waimaishuo.databinding.FragmentMallShoppingCartBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mall.MallShoppingCartViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

@Page(name = "商城-购物车", anim = CoreAnim.slide)
public class MallShoppingCartFragment extends BaseFragment {

    MallShoppingCartViewModel mViewModel;
    private FragmentMallShoppingCartBinding mBinding;

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {

        int width = getResources().getDimensionPixelSize(R.dimen.swipe_width_shopping_cart_recycler_item);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 添加左侧的，如果不添加，则左侧不会出现菜单。
        /*{
            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackgroundColor(R.drawable.menu_selector_green)
                    .setImage(R.drawable.ic_swipe_menu_add)
                    .setWidth(width)
                    .setHeight(height);
            swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

            SwipeMenuItem closeItem = new SwipeMenuItem(getContext()).setBackgroundColor(R.drawable.menu_selector_red)
                    .setImage(R.drawable.ic_swipe_menu_close)
                    .setWidth(width)
                    .setHeight(height);
            swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
        }*/
        if(position == 0){  //由于第一项为HeadView 不添加侧滑
            return;
        }
        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem moveToFavorites = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item1)
                    .setText(R.string.move_to_favorites)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(moveToFavorites);// 添加菜单到右侧。

            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item2)
                    .setText(R.string.deleted)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem); // 添加菜单到右侧。
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = (menuBridge, position) -> {
        menuBridge.closeMenu();

        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

        if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
            Toast.makeText(getContext(), "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
        } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
            Toast.makeText(getContext(), "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new MallShoppingCartViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMallShoppingCartBinding) mViewDataBinding;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall_shopping_cart;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
        setFitStatusBarHeight(true);
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initTitle();
        initSwipeRefreshLayout();
        initShopRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //下拉刷新
        mBinding.swipeRefreshLayout.setOnRefreshListener(this::loadData);
//        refresh();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        changeStatusBarMode();
    }

    @Override
    protected void onLifecycleDestroy() {
        super.onLifecycleDestroy();
    }

    private void initTitle() {
        mBinding.layoutTitle.tvTitle.setText(R.string.shopping_cart);
        mBinding.layoutTitle.tvFinish.setText(R.string.manager);
    }


    private void initSwipeRefreshLayout(){
        mBinding.swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);
        mBinding.swipeRefreshLayout.setRefreshing(false);
    }

    private void initShopRecycler() {
        int interval = (int) UIUtils.getInstance().scalePx(getResources().getDimensionPixelOffset(R.dimen.interval_size_xs));

        mBinding.recyclerShop.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false));

        MyBaseRecyclerAdapter adapter = getShopRecyclerAdapter();
        TextView textView = new TextView(requireContext());
        textView.setText("共23件商品");
        textView.setTextColor(getResources().getColor(R.color.text_tip));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);
        textView.setPadding(interval,0,0,0);
        adapter.addHeaderView(textView);
        mBinding.recyclerShop.setAdapter(adapter);
        mBinding.recyclerShop.addItemDecoration(Utils.getDefaultItemDecoration(requireContext()));
    }

    private MyBaseRecyclerAdapter getShopRecyclerAdapter() {
        return new MyBaseRecyclerAdapter<MallShoppingCartItemData>(R.layout.layout_recycler_swipe, mViewModel.getShoppingCartData()) {
            @Override
            protected void initView(ViewDataBinding viewDataBinding, BaseViewHolder helper, MallShoppingCartItemData item) {

                // TODO: 2021/1/21 注意一下这里 判断全选状态 后续对接接口时
                if(item.isSelectedAll()){
                    for(MallShoppingCartShopGoods mallShoppingCartShopGoods : item.getGoodsList()){
                        mallShoppingCartShopGoods.setSelected(true);
                    }
                }else{

                }

                SwipeRecyclerView recyclerView = helper.getView(R.id.recycler);
                if(recyclerView.getAdapter() == null){
                    //获得子recyclerView的适配器
                    BaseQuickAdapter adapter = getShopGoodsAdapter(item.getGoodsList(),item.isEffective());
                    //设置头布局 headView
                    if(item.isEffective()){
                        View view = View.inflate(helper.itemView.getContext(),R.layout.head_mall_shopping_cart_shop_info,null);
                        adapter.addHeaderView(view);
                        ((TextView)adapter.getHeaderLayout().findViewById(R.id.tv_shop_name)).setText(item.getShop().getShop_name());
                        if(item.isSelectedAll()){
                            ((ImageView)adapter.getHeaderLayout().findViewById(R.id.iv_select)).setImageResource(R.drawable.ic_check_round_fill_red);
                        }else{
                            ((ImageView)adapter.getHeaderLayout().findViewById(R.id.iv_select)).setImageResource(R.drawable.ic_round_gray);
                        }
                    }else{
                        View view = View.inflate(helper.itemView.getContext(),R.layout.head_noneffective_goods,null);
                        adapter.addHeaderView(view);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                            LinearLayoutManager.VERTICAL, false));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    //设置侧滑按键 必须在setAdapter之前调用
                    //会导致DataBind 报错
                    recyclerView.setSwipeMenuCreator(swipeMenuCreator);
                    recyclerView.setOnItemMenuClickListener(mMenuItemClickListener);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(Utils.getDefaultItemDecoration(requireContext()));
                }

            }
        };
    }

    private BaseQuickAdapter getShopGoodsAdapter(List<MallShoppingCartShopGoods> list, boolean isEffective){
        int[] viewTypes = {1, 2};//1：正常店铺商品 2：失效的素有商品
        BaseQuickAdapter<MallShoppingCartShopGoods, BaseViewHolder> adapter =  new BaseQuickAdapter<MallShoppingCartShopGoods,BaseViewHolder>(list) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, MallShoppingCartShopGoods item) {
                if (helper.getItemViewType() == viewTypes[0]) {    //有效的商品
                    if(item.isSelected()){
//                                holder.getView(R.id.iv_select).setBackground();
                    }else{

                    }

                    if(item.getPurchase_limitation() == null || item.getPurchase_limitation().equals("")){
                        setViewVisibility(helper.getView(R.id.tv_purchase_limitation),false);
                    }else{
                        setViewVisibility(helper.getView(R.id.tv_purchase_limitation),true);
                        helper.setText(R.id.tv_purchase_limitation,item.getPurchase_limitation());
                    }

                    if(item.getPrice_introduce() == null || item.getPrice_introduce().equals("")){
                        setViewVisibility(helper.getView(R.id.tv_price_introduce),false);
                    }else{
                        setViewVisibility(helper.getView(R.id.tv_price_introduce),true);
                        helper.setText(R.id.tv_price_introduce,item.getPrice_introduce());
                    }

                    if(item.isSelected()){
                        helper.setImageResource(R.id.iv_select,R.drawable.ic_check_round_fill_red);
                    }else{
                        helper.setImageResource(R.id.iv_select,R.drawable.ic_round_gray);
                    }

                    helper.setText(R.id.tv_goods_price,item.getGoods().getGoodsPrice());
                    helper.setText(R.id.tv_count,item.getBuyCount() + "");

                } else {    //失效的商品
                    if(item.getSelected_specification() == null || item.getSelected_specification().equals("")){
                        setViewVisibility(helper.getView(R.id.tv_goods_specification),false);
                    }else{
                        setViewVisibility(helper.getView(R.id.tv_goods_specification),true);
                        helper.setText(R.id.tv_goods_specification,item.getPrice_introduce());
                    }
                    if(item.getPurchase_limitation() == null || item.getPurchase_limitation().equals("")){
                        setViewVisibility(helper.getView(R.id.tv_purchase_limitation),false);
                    }else{
                        setViewVisibility(helper.getView(R.id.tv_purchase_limitation),true);
                        helper.setText(R.id.tv_goods_specification,item.getPrice_introduce());
                    }
                }

                helper.setText(R.id.tv_goods_name,item.getGoods().getGoodsName());
                helper.setText(R.id.tv_goods_specification,item.getSelected_specification());

                Glide.with(helper.itemView.getContext())
                        .load(item.getGoods().getGoodsImgUrl())
                        .placeholder(R.drawable.ic_waimai_brand)
                        .centerCrop()
                        .into((ImageView)helper.getView(R.id.iv_goods_img));

            }
        };

        SparseIntArray layouts = new SparseIntArray();
        layouts.put(viewTypes[0],R.layout.item_recycler_mall_shopping_cart_effective_goods);
        layouts.put(viewTypes[1],R.layout.item_recycler_mall_shopping_cart_noneffective_goods);
        adapter.setMultiTypeDelegate(new MultiTypeDelegate<MallShoppingCartShopGoods>(layouts) {
            @Override
            protected int getItemType(MallShoppingCartShopGoods o) {
                if(isEffective){
                    return viewTypes[0];
                }else{
                    return viewTypes[1];
                }
            }
        });
        return adapter;
    }

    private void refresh() {
        mBinding.swipeRefreshLayout.setRefreshing(true);
        loadData();
    }

    private void loadData() {
//        mHandler.postDelayed(()->{
//            List<Message> list =  mMessageRecyclerAdapter.getData();
//            list.clear();
//            list.addAll(list);
//            mMessageRecyclerAdapter.notifyDataSetChanged();
//            if (binding.swipeRefreshLayout != null) {
//                binding.swipeRefreshLayout.setRefreshing(false);
//            }
//        },1000);
    }

}
