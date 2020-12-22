package com.life.waimaishuo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.life.waimaishuo.R;
import com.life.waimaishuo.mvvm.view.fragment.waimai.WaimaiExclusiveFragment;
import com.life.waimaishuo.views.FilletImageView;

public class ImageViewAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("imageUrl")
    public static void setSrc(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_waimai_brand)
                .into(imageView);
    }

/*    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }*/

    @BindingAdapter("imageUrlBitmap")
    public static void setBitSrc(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .asBitmap()
                .placeholder(R.drawable.ic_waimai_brand)
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

}
