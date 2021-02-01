/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.life.waimaishuo.adapter.statelayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.event.MessageEvent;
import com.life.waimaishuo.constant.MessageCodeConstant;
import com.life.waimaishuo.mvvm.view.activity.BaseActivity;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import org.greenrobot.eventbus.EventBus;

import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_CUSTOM;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_EMPTY_DATA;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_LOADING;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_LOAD_FAILED;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_LOAD_SUCCESS;

public class CustomSingleViewAdapter implements StatusLoader.Adapter {

    @Override
    public View getView(StatusLoader.Holder holder, View convertView, int status) {

        if(convertView == null){
//            if(status == STATUS_LOAD_SUCCESS && convertView.getTag(5420654) == null){
//                convertView.setVisibility(View.GONE);
//            }
            convertView = getConvertView(holder);
        }

        setConvertViewContent(convertView,status);
        return convertView;
    }

    private View getConvertView(StatusLoader.Holder holder){
        View view = View.inflate(holder.getContext(),R.layout.layout_state,null);
//        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    private void setConvertViewContent(View view, int status){
        if(status == STATUS_LOAD_SUCCESS){
            if(view.getVisibility() == View.VISIBLE){
                view.setVisibility(View.GONE);
            }
        }else{
            if(view.getVisibility() == View.GONE){
                view.setVisibility(View.VISIBLE);
            }
        }

        switch (status) {
            case STATUS_LOADING:
                ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.mipmap.png_state_locate_car);
                TextView textView = view.findViewById(R.id.tv);
                Button button = view.findViewById(R.id.bt);
                textView.setText(R.string.no_businesses_in_this_area);
                if(textView.getVisibility() == View.GONE){
                    textView.setVisibility(View.VISIBLE);
                }
                button.setText(R.string.go_shopping_at_the_mall);
                if(button.getVisibility() == View.GONE){
                    button.setVisibility(View.VISIBLE);
                }
                button.setOnClickListener(new BaseActivity.OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View view) {
                        EventBus.getDefault().post(new MessageEvent(MessageCodeConstant.MAIN_TAB_BAR_PAGE_CHANGE_MALL,""));
                    }
                });
                break;
            case STATUS_LOAD_FAILED:
                ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.mipmap.png_state_planet);
                ((TextView)view.findViewById(R.id.tv)).setText(R.string.loading_fail);
                break;
            case STATUS_EMPTY_DATA:
                ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.mipmap.png_state_box);
                ((TextView)view.findViewById(R.id.tv)).setText(R.string.no_businesses_in_this_area);
                break;
            case STATUS_CUSTOM:
                break;
            default:
                break;
        }
    }

}
