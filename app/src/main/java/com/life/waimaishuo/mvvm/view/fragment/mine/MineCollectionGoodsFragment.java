package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.util.Util;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.BR;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Goods;
import com.life.waimaishuo.databinding.FragmentMineCollectionGoodsBinding;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineCollectionGoodsViewModel;
import com.life.waimaishuo.util.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.utils.TitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

@Page(name = "商品收藏")
public class MineCollectionGoodsFragment extends BaseFragment {

    private FragmentMineCollectionGoodsBinding mBinding;
    private MineCollectionGoodsViewModel mViewModel;

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.swipe_width_recycler_item);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item2)
                    .setText(R.string.cancel_collection)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
        }
    };
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator2 = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.swipe_width_recycler_item);
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        if (position == 0) {
            return;
        }
        {
            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item1)
                    .setText(R.string.cancel_collection)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
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
    protected void bindViewModel() {
        mBinding = (FragmentMineCollectionGoodsBinding) mViewDataBinding;


    }

    @Override
    protected BaseViewModel setViewModel() {
        if (mViewModel == null) {
            mViewModel = new MineCollectionGoodsViewModel();
        }
        return mViewModel;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_collection_goods;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
    }

    @Override
    protected void initViews() {
        super.initViews();
        initEffectiveRecycler();
        initNoneffectiveRecycler();
    }

    @Override
    protected TitleBar initTitleBar() {
        return null;
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initEffectiveRecycler() {
        mBinding.recyclerGoodsEffective.setSwipeMenuCreator(swipeMenuCreator);
        mBinding.recyclerGoodsEffective.setOnItemMenuClickListener(mMenuItemClickListener);
        mBinding.recyclerGoodsEffective.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerGoodsEffective.setAdapter(new MyBaseRecyclerAdapter<Goods>(R.layout.item_recycler_collection_goods, mViewModel.getCollectionGoodsData(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(BaseViewHolder helper, Goods item) {
                super.initView(helper, item);

            }
        });
        mBinding.recyclerGoodsEffective.addItemDecoration(Utils.getItemDecoration(requireContext()));
    }

    private void initNoneffectiveRecycler() {
        mBinding.recyclerGoodsNoneffective.setSwipeMenuCreator(swipeMenuCreator2);
        mBinding.recyclerGoodsNoneffective.setOnItemMenuClickListener(mMenuItemClickListener);
        mBinding.recyclerGoodsNoneffective.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        MyBaseRecyclerAdapter adapter = new MyBaseRecyclerAdapter<Goods>(R.layout.item_recycler_collection_goods_noneffective, mViewModel.getCollectionNoneffectiveGoodsData(), com.life.waimaishuo.BR.item) {
            @Override
            protected void initView(BaseViewHolder helper, Goods item) {
                super.initView(helper, item);

            }
        };
        View view = View.inflate(requireContext(), R.layout.head_noneffective_goods, null);
        adapter.addHeaderView(view);
        mBinding.recyclerGoodsNoneffective.setAdapter(adapter);
    }

}
