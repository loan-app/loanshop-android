package com.ailiangbao.alb.util;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * author:hll
 * time:2018/6/28
 */
public class GlideUtil {
    public static final int TYPE_BITMAP = 0;
    public static final int TYPE_DRAWABLE = 1;
    public static final int TYPE_GIF = 2;
    public static final int TYPE_FILE = 3;

    @IntDef({TYPE_BITMAP, TYPE_DRAWABLE, TYPE_GIF, TYPE_FILE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Load_Pic_Type {

    }

    public static void loadImageRadius(Context context, ImageView imageView, Object url, int defaultsPic) {
        loadImageRadius(context, imageView, url, defaultsPic, 0, TYPE_DRAWABLE);
    }

    public static void loadImageRadius(Context context, ImageView imageView, Object url, int defaultsPic, int radius) {
        loadImageRadius(context, imageView, url, defaultsPic, ResUtil.dip2px(radius), TYPE_DRAWABLE);
    }

    public static void loadImageRadius(Context context, ImageView imageView, Object url, int defaultsPic, float radius, @Load_Pic_Type int picType) {
        RequestOptions requestOptions = getRequestOptions(defaultsPic)
                .transform(new RoundedCornersTransformation((int) radius, 0));
        loadPic(context, imageView, url, picType, requestOptions);
    }

    public static void loadLocalImageRadius(Context context, ImageView imageView, int resId, int defaultsPic) {
        loadLocalImageRadius(context, imageView, resId, defaultsPic, 0);
    }

    public static void loadLocalImageRadius(Context context, ImageView imageView, int resId, int defaultsPic, float radius) {
        RequestOptions requestOptions = getRequestOptions(defaultsPic).transform(new RoundedCornersTransformation((int) radius, 0));
        loadBitmapPic(context, resId, imageView, requestOptions);
    }

    public static void loadImageCircle(Context context, ImageView imageView, Object url, int defaultPic) {
        loadImageCircle(context, imageView, url, TYPE_DRAWABLE, defaultPic);
    }

    public static void loadImageCircle(Context context, ImageView imageView, Object url, @Load_Pic_Type int picType, int defaultPic) {
        RequestOptions options = getRequestOptions(defaultPic)
                .circleCrop();
        loadPic(context, imageView, url, picType, options);
    }

    private static void loadPic(Context context, ImageView imageView, Object url, @Load_Pic_Type int picType, RequestOptions requestOptions) {
        switch (picType) {
            case TYPE_BITMAP:
                loadBitmapPic(context, url, imageView, requestOptions);
                break;
            case TYPE_DRAWABLE:
                loadDrawablePic(context, url, imageView, requestOptions);
                break;
            case TYPE_GIF:
                loadGifPic(context, url, imageView, requestOptions);
                break;
            case TYPE_FILE:
                loadFilePic(context, url, imageView, requestOptions);
                break;
        }
    }

    private static void loadBitmapPic(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .transition(BitmapTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    private static void loadDrawablePic(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context)
                .asDrawable()
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    private static void loadBitmapPic(Context context, int resId, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context)
                .asBitmap()
                .load(resId)
                .transition(BitmapTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    private static void loadGifPic(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context)
                .asGif()
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    private static void loadFilePic(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context)
                .asFile()
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    private static RequestOptions getRequestOptions(int defaultsPic) {
        return new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(defaultsPic)
                .error(defaultsPic);
    }

}
