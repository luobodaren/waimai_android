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
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.statelayout.StatusLoader;

import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_CUSTOM;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_EMPTY_DATA;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_LOADING;
import static com.xuexiang.xui.widget.statelayout.StatusLoader.STATUS_LOAD_FAILED;

public class CustomSingleViewAdapter implements StatusLoader.Adapter {

    @Override
    public View getView(StatusLoader.Holder holder, View convertView, int status) {
        if(convertView != null){
            return convertView;
        }

        switch (status) {
            case STATUS_LOADING:
                convertView = getLoadingView(holder);
                break;
            case STATUS_LOAD_FAILED:
                convertView = getLoadFailedView(holder);
                break;
            case STATUS_EMPTY_DATA:
                convertView = getEmptyView(holder);
                break;
            case STATUS_CUSTOM:
                convertView = getCustomView(holder);
                break;
            default:
                break;
        }
        return convertView;
    }

    private View getLoadingView(StatusLoader.Holder holder){
        View view = View.inflate(holder.getContext(),R.layout.layout_state,null);
        ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.mipmap.png_state_locate_car);
        ((TextView)view.findViewById(R.id.tv)).setText(R.string.no_businesses_in_this_area);
        return view;
    }

    private View getLoadFailedView(StatusLoader.Holder holder){
        View view = View.inflate(holder.getContext(),R.layout.layout_state,null);
        ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.mipmap.png_state_planet);
        ((TextView)view.findViewById(R.id.tv)).setText(R.string.no_businesses_in_this_area);
        return view;
    }

    private View getEmptyView(StatusLoader.Holder holder){
        View view = View.inflate(holder.getContext(),R.layout.layout_state,null);
        ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.mipmap.png_state_box);
        ((TextView)view.findViewById(R.id.tv)).setText(R.string.no_businesses_in_this_area);
        return view;
    }

    private View getCustomView(StatusLoader.Holder holder){
        return new Space(holder.getContext());
    }

}
