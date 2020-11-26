package com.example.myapplication.bean.ui;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

public class IconStrRecyclerViewItemData {

    int resImgId;
    String iconType;

    public IconStrRecyclerViewItemData(@NonNull int resImgId, @NonNull String iconType) {
        this.resImgId = resImgId;
        this.iconType = iconType;
    }

    public int getResImgId() {
        return resImgId;
    }

    public void setResImgId(int resImgId) {
        this.resImgId = resImgId;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public void setData(IconStrRecyclerViewItemData data) {
        this.resImgId = data.resImgId;
        this.iconType = data.iconType;
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

}
