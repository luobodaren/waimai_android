package com.life.waimaishuo.mvvm.view.fragment.mine;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.R;
import com.life.waimaishuo.adapter.MyBaseRecyclerAdapter;
import com.life.waimaishuo.bean.Address;
import com.life.waimaishuo.databinding.FragmentMineAddressManagerBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.mine.MineAddressManagerViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

@Page(name = "地址管理", anim = CoreAnim.slide)
public class MineAddressManagerFragment extends BaseFragment {

    private FragmentMineAddressManagerBinding mBinding;
    private MineAddressManagerViewModel mViewModel;

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {

        int width = getResources().getDimensionPixelSize(R.dimen.swipe_width_address_manager_recycler_item);
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
        // 添加右侧的，如果不添加，则右侧不会出现菜单。
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item1)
                    .setText(R.string.deleted)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
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
        return null;
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_address_manager;
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

        initAddressRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    private void initTitle(){
        mBinding.layoutTitle.tvTitle.setText(getPageName());
        mBinding.layoutTitle.ivBack.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                popToBack();
            }
        });
    }

    private void initAddressRecycler(){

        mBinding.recycler.setSwipeMenuCreator(swipeMenuCreator);
        mBinding.recycler.setOnItemMenuClickListener(mMenuItemClickListener);
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        mBinding.recycler.setAdapter(new MyBaseRecyclerAdapter<Address>(R.layout.item_recycler_address_info,mViewModel.getAddressData()){
            @Override
            protected void initView(BaseViewHolder helper, Address item) {
                super.initView(helper, item);
                helper.setText(R.id.tv_user_name,item.getUser_name() + "  " + TextUtil.phoneHide(item.getPhone()));
                helper.setText(R.id.tv_address,item.getAddress());
            }
        });
    }


}
