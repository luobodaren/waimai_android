package com.example.myapplication.mvvm.view.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.R;
import com.example.myapplication.bean.Message;
import com.example.myapplication.constant.Constant;
import com.example.myapplication.databinding.FragmentMessageBinding;
import com.example.myapplication.mvvm.vm.BaseViewModel;
import com.example.myapplication.mvvm.vm.MessageViewModel;
import com.example.myapplication.util.StatusBarUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xpage.utils.TitleUtils;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.popupwindow.PopWindow;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.List;

@Page(name = "消息中心",anim = CoreAnim.fade)
public class MessageFragment extends BaseFragment {

    MessageViewModel mViewModel;
    FragmentMessageBinding binding;

    private MessageRecyclerAdapter mMessageReyclerAdapter;

    private PopWindow mAllMessageReadPopWindow;
    private Runnable mCancelPopRunnable = new Runnable() {
        @Override
        public void run() {
            mAllMessageReadPopWindow.dismiss();
        }
    };

    @Override
    protected BaseViewModel setViewModel() {
        mViewModel = new MessageViewModel();
        return mViewModel;
    }

    @Override
    protected void bindViewModel() {
        binding = (FragmentMessageBinding)mViewDataBinding;
        binding.setViewModel(mViewModel);
    }

    @Override
    protected int getLayoutId() {
        // TODO: 2020/12/1 布局自适应
        return R.layout.fragment_message;
    }

    @Override
    protected void initArgs() {
        super.initArgs();
        setmStatusBarLightMode(StatusBarUtils.STATUS_BAR_MODE_LIGHT);
        setFitStatusBarHeight(true);
    }

    @Override
    protected TitleBar initTitleBar() {
        TitleBar titleBar = super.initTitleBar();

        titleBar.setLeftImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black));

        titleBar.setBackgroundColor(getResources().getColor(R.color.white));
        titleBar.getCenterText().setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        titleBar.setCenterTextBold(true);
        titleBar.setTitleColor(getResources().getColor(R.color.text_normal));
        titleBar.setActionTextColor(getResources().getColor(R.color.text_normal));
        TextView actionTextView = (TextView) titleBar.addAction(new TitleBar.TextAction(getString(R.string.read_all_message_bt)) {
            @Override
            public void performAction(View view) {
                doReadAllMessage();
            }
        });
        actionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.font_size_30));
        return titleBar;
    }

    @Override
    protected void initViews() {
        super.initViews();
        initMessageRecycler();
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //下拉刷新
        binding.swipeRefreshLayout.setOnRefreshListener(this::loadData);
//        refresh();
    }

    @Override
    protected void onLifecycleResume() {
        super.onLifecycleResume();
        setStatusBarMode();
    }

    @Override
    protected void onLifecycleDestroy() {
        super.onLifecycleDestroy();
    }

    private void doReadAllMessage(){
        // TODO: 2020/12/1 viewModel层读消息
        // TODO: 2020/12/1 改为在回调时调用
        if(mAllMessageReadPopWindow == null){
            mAllMessageReadPopWindow = new PopWindow(getContext(),R.layout.pop_read_all_message);
            mAllMessageReadPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.removeCallbacks(mCancelPopRunnable);
                }
            });
        }
        mAllMessageReadPopWindow.showAtLocation(mRootView, Gravity.CENTER,0,0);
        mHandler.postDelayed(mCancelPopRunnable, Constant.POP_WINDOW_SHOW_TIME);
    }

    private void initMessageRecycler(){
        FragmentMessageBinding binding = ((FragmentMessageBinding)mViewDataBinding);
//        WidgetUtils.initRecyclerView(binding.swipeRecyclerView);
        binding.swipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
//        binding.swipeRecyclerView.addItemDecoration(new DividerItemDecoration(binding.swipeRecyclerView.getContext(), VERTICAL));
//        binding.swipeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //必须在setAdapter之前调用
        binding.swipeRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        //必须在setAdapter之前调用
        binding.swipeRecyclerView.setOnItemMenuClickListener(mMenuItemClickListener);

        mMessageReyclerAdapter = new MessageRecyclerAdapter(R.layout.item_message,mViewModel.getMessageData());
        binding.swipeRecyclerView.setAdapter(mMessageReyclerAdapter);

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

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, position) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.message_recycler_swipe_width);
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
                    .setText(getString(R.string.message_swipe_item1))
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

            SwipeMenuItem addItem = new SwipeMenuItem(getContext()).setBackground(R.color.bg_recycler_swipe_item2)
                    .setText(getString(R.string.message_swipe_item2))
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


    private void refresh() {
        ((FragmentMessageBinding)mViewDataBinding).swipeRefreshLayout.setRefreshing(true);
        loadData();
    }

    private void loadData() {
        mHandler.postDelayed(()->{
            List<Message> list =  mMessageReyclerAdapter.getData();
            list.clear();
            list.addAll(list);
            mMessageReyclerAdapter.notifyDataSetChanged();
            if (binding.swipeRefreshLayout != null) {
                binding.swipeRefreshLayout.setRefreshing(false);
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
