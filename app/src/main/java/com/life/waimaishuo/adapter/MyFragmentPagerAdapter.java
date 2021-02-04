package com.life.waimaishuo.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.life.base.utils.LogUtil;
import com.xuexiang.xui.adapter.FragmentAdapter;

import java.util.List;

/**
 * 配合动态更新fragmentList使用
 * @param <T>
 */
public class MyFragmentPagerAdapter<T extends Fragment> extends FragmentAdapter<T> {

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, T[] fragments) {
        super(fm, fragments);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, List<T> fragments) {
        super(fm, fragments);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, T[] fragments) {
        super(fm, behavior, fragments);
    }

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, List<T> fragments) {
        super(fm, behavior, fragments);
    }

    /*@Override
    public Object instantiateItem(final ViewGroup container, int position) {
        *//*Fragment fragment = getItem(position);
        view.setTag(page);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(view, params);
        return fragment;*//*
    }*/

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        for (T t:getFragmentList()) {
            if(object == t){
                LogUtil.d("POSITION_UNCHANGED");
                return POSITION_UNCHANGED;
            }
        }
        return POSITION_NONE;
    }

}
