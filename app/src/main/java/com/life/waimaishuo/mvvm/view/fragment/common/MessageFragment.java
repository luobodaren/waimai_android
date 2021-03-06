package com.life.waimaishuo.mvvm.view.fragment.common;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.Message;
import com.life.waimaishuo.constant.Constant;
import com.life.waimaishuo.databinding.FragmentMessageBinding;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.life.waimaishuo.mvvm.view.fragment.BaseFragment;
import com.life.waimaishuo.mvvm.vm.BaseViewModel;
import com.life.waimaishuo.mvvm.vm.MessageViewModel;
import com.life.waimaishuo.util.StatusBarUtils;
import com.life.waimaishuo.util.TextUtil;
import com.life.waimaishuo.views.widget.pop.MyCheckRoundTextInfoPop;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

@Page(name = "消息中心",anim = CoreAnim.slide)
public class MessageFragment extends BaseFragment {

    private MessageViewModel mViewModel;
    private FragmentMessageBinding mBinding;

    private MessageRecyclerAdapter mMessageRecyclerAdapter;

    private MyCheckRoundTextInfoPop mAllMessageReadPopWindow;
    private Runnable mCancelPopRunnable;

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.swipe_width_message_recycler);
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
                    .setText(R.string.placed_at_the_top)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item2)
                    .setText(R.string.deleted)
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
            Toast.makeText(getContext(),"list第" + position + "; 右侧菜单第" + menuPosition,Toast.LENGTH_SHORT).show();
        } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
            Toast.makeText(getContext(),"list第" + position + "; 左侧菜单第" + menuPosition,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new MessageViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        mBinding = (FragmentMessageBinding)mViewDataBinding;
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        // TODO: 2020/12/1 布局自适应
        return R.layout.fragment_message;
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
        initMessageRecycler();
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

    private void initTitle(){
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvTitle,true);
        TextUtil.setFakeBoldText(mBinding.layoutTitle.tvRight,true);

        mBinding.layoutTitle.tvRight.setText(R.string.read_all_message_bt);
        mBinding.layoutTitle.tvRight.setOnClickListener(new BaseActivity.OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                doReadAllMessage(getString(R.string.read_all_message_success_tip),true);
            }
        });
    }

    private void doReadAllMessage(String info, boolean successful){
        // TODO: 2020/12/1 viewModel层读消息
        // TODO: 2020/12/1 改为在回调时调用
        if(mAllMessageReadPopWindow == null){
            mAllMessageReadPopWindow = new MyCheckRoundTextInfoPop(getContext(), info, successful);
            mAllMessageReadPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.removeCallbacks(mCancelPopRunnable);
                }
            });
        }

        if(mCancelPopRunnable == null){
             mCancelPopRunnable = new Runnable() {
                @Override
                public void run() {
                    mAllMessageReadPopWindow.dismiss();
                }
            };
        }

        mAllMessageReadPopWindow.showAtCenter(mRootView);
        mHandler.postDelayed(mCancelPopRunnable, Constant.POP_WINDOW_SHOW_TIME);
    }

    private void initMessageRecycler(){
        FragmentMessageBinding binding = ((FragmentMessageBinding)mViewDataBinding);
//        WidgetUtils.initRecyclerView(mBinding.swipeRecyclerView);
        binding.swipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        mBinding.swipeRecyclerView.addItemDecoration(new CityPickerDividerItemDecoration(mBinding.swipeRecyclerView.getContext(), VERTICAL));
//        mBinding.swipeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //必须在setAdapter之前调用
        binding.swipeRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //必须在setAdapter之前调用
        binding.swipeRecyclerView.setOnItemMenuClickListener(mMenuItemClickListener);

        mMessageRecyclerAdapter = new MessageRecyclerAdapter(R.layout.item_message,mViewModel.getMessageData());
        binding.swipeRecyclerView.setAdapter(mMessageRecyclerAdapter);

        binding.swipeRefreshLayout.setColorSchemeColors(0xff0099cc, 0xffff4444, 0xff669900, 0xffaa66cc, 0xffff8800);

        binding.swipeRefreshLayout.setRefreshing(false);
    }

    private void setDataToViewHolder(RecyclerViewHolder viewHolder, Message item) {

        /*Glide.with(this)
                .load(item.getHeadSrc())
                .centerCrop()
                .placeholder(R.drawable.ic_waimai_brand)
                .into((ImageView) viewHolder.getView(R.id.iv_message_head_icon));*/

    }

    private void refresh() {
        ((FragmentMessageBinding)mViewDataBinding).swipeRefreshLayout.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        mHandler.postDelayed(()->{
            List<Message> list =  mMessageRecyclerAdapter.getData();
            list.clear();
            list.addAll(list);
            mMessageRecyclerAdapter.notifyDataSetChanged();
            if (mBinding.swipeRefreshLayout != null) {
                mBinding.swipeRefreshLayout.setRefreshing(false);
            }
        },1000);
    }

    private static class MessageRecyclerAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

        public MessageRecyclerAdapter(int layoutResId, @Nullable List<Message> data) {
            super(layoutResId, data);
        }

        public MessageRecyclerAdapter(@Nullable List<Message> data) {
            super(data);
        }

        public MessageRecyclerAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Message item) {

            Glide.with(helper.itemView.getContext())
                    .load(item.getHeadSrc())
                    .placeholder(R.drawable.ic_waimai_brand)
                    .into((ImageView) helper
                            .getView(R.id.iv_message_head_icon));
            helper.setText(R.id.tv_message_name,item.getName());
            helper.setText(R.id.tv_message_content,item.getContent());
            helper.setText(R.id.tv_message_date,item.getDate());
            helper.setText(R.id.tv_uncheck_message_count,item.getUnCheckCount());
        }
    }

}
