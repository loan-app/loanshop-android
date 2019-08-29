package com.ailiangbao.alb.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import com.ailiangbao.alb.HApplication;


/**
 * Created by zhanghongjie on 2018/4/2.
 */

public class DrawableUtils {

    public static Drawable getGradientDrawable(int bgColor) {
        return getGradientDrawable(bgColor, 0);
    }

    public static Drawable getGradientDrawable(int bgColor, float radius) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(bgColor);
        gd.setCornerRadius(ResUtil.dip2px(radius));
        return gd;
    }

    public static Drawable getGradientDrawable(int bgColor, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(bgColor);
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        return gd;
    }

    /**
     * @param colors 颜色渐变数组
     * @param radius corner
     * @return
     */
    public static Drawable getGradientDrawable(float radius, int... colors) {
        return getGradientDrawable(GradientDrawable.Orientation.TL_BR, radius, radius, radius, radius, colors);
    }

    /**
     * 从左到右渐变的，带圆角的drawable
     *
     * @param colors      颜色渐变数组
     * @param topLeft     corner
     * @param topRight    corner
     * @param bottomRight corner
     * @param bottomLeft  corner
     * @return 对应drawable
     */
    public static Drawable getGradientDrawable(GradientDrawable.Orientation orientation, float topLeft, float topRight, float bottomRight, float bottomLeft, int... colors) {
        GradientDrawable gd = new GradientDrawable(orientation, colors);//创建drawable
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        return gd;
    }

//    /**
//     * 10px圆角焦点框
//     */
//    public static Drawable getFocusDrawable(Context context) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(12), Color.BLACK);
//        gd.setCornerRadius(GlobalSize.getRadius());
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(7), context.getResources().getColor(R.color.focus_color));
//        gd.setCornerRadius(GlobalSize.getRadius());
//
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }

    public static Drawable getLoanStrokeDrawable() {
        Drawable[] drawables = new Drawable[2];
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.TRANSPARENT);
//        gd.setStroke(ResUtil.dip2px(1), Color.parseColor("#FFDDDDDD"));
//        gd.setCornerRadius(GlobalSize.getRadius());
        drawables[0] = gd;
        gd = new GradientDrawable();
        gd.setStroke(ResUtil.dip2px(1), Color.parseColor("#FFDDDDDD"));
//        gd.setCornerRadius(GlobalSize.getRadius());

        drawables[1] = gd;
        return new LayerDrawable(drawables);
    }
//
//    public static Drawable getFocusDrawableWithRadius(Context context, int radius) {
//        Drawable[] drawables = new Drawable[1];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(12), Color.BLACK);
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(7), context.getResources().getColor(R.color.focus_color));
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//
////        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }

    /**
     * 圆形焦点框
     */
//    public static Drawable getFocusCircleDrawable(Context context) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(12), Color.BLACK);
//        gd.setShape(GradientDrawable.OVAL);
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(7), context.getResources().getColor(R.color.focus_color));
//        gd.setShape(GradientDrawable.OVAL);
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }

    /**
     * 首页头像焦点框
     */
    public static Drawable getUserHeadPicBgDrawable(int colorRes) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(HApplication.getInstance().getResources().getColor(colorRes));

        return gradientDrawable;
    }
//
//    public static Drawable getRoomSettingSeekBarCircleBg() {
//        GradientDrawable gd = new GradientDrawable();
//        gd.setShape(GradientDrawable.OVAL);
//        gd.setSize(ScreenAdapter.scaleX(20), ScreenAdapter.scaleY(20));
//        gd.setStroke(ScreenAdapter.scaleX(1), ResUtil.getColor(R.color.white));
//        gd.setColor(ResUtil.getColor(R.color.focus_color));
//
//        return gd;
//    }

    public static Drawable generateSelectedSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, pressed);
//        drawable.addState(new int[]{android.R.attr.state_selected}, pressed);
//        drawable.addState(new int[]{-android.R.attr.state_selected}, normal);


        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    /**
     * 获取清晰度背景框
     //     */
//    public static Drawable getDefinitionBgDrawable(int color) {
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
//        gradientDrawable.setStroke(ScreenAdapter.scaleX(2), ResUtil.getColor(color));
//        gradientDrawable.setCornerRadius(ScreenAdapter.scaleX(30));
//        return gradientDrawable;
//    }

//    public static Drawable getDefinitionSelectedBgDrawable() {
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
////        gradientDrawable.setColor(ResUtil.getColor(R.color.focus_color_eighty_percent));
//        gradientDrawable.setCornerRadius(ScreenAdapter.scaleX(30));
//        return gradientDrawable;
//    }
//
//    public static Drawable getSbuscribeCancleBgDrawable() {
//        GradientDrawable gradientDrawable = new GradientDrawable();
//        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
//        gradientDrawable.setStroke(ScreenAdapter.scaleX(1), 0xFFFEB735);
//        gradientDrawable.setCornerRadius(ScreenAdapter.scaleX(5));
//        return gradientDrawable;
//    }
//
//    public static Drawable generateTabTitleLeftDrawable(Context context) {
//        GradientDrawable gd = new GradientDrawable();//创建drawable
////        gd.setColor(context.getResources().getColor(R.color.focus_color));
//        gd.setCornerRadius(ScreenAdapter.scaleX(5));
//        gd.setSize(ScreenAdapter.scaleX(5), ScreenAdapter.scaleY(40));
//        return gd;
//    }
//
//    public static Drawable getFocusDrawable(Context context, int blackWidth, int focusWidth, int radius) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(blackWidth), Color.BLACK);
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(focusWidth), context.getResources().getColor(R.color.focus_color));
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }

    /**
     * 刷新，搜索，设置按钮焦点框与默认颜色
     */
    public static Drawable getCircleDrawable(Context context, int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(color);
        return gd;
    }

    public static Drawable getCircleDrawable(Context context, int color, int width, int height) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(color);
        gd.setSize(width, height);
        return gd;
    }

    /**
     * 获取live条目焦点框
     */
    public static Drawable getLiveItemFocusDrawable(int color) {
        GradientDrawable gd = new GradientDrawable();
//        float[] floats = new float[]{0.0f, 0.0f, 0.0f, 0.0f, radius, radius, radius, radius};
        gd.setShape(GradientDrawable.RECTANGLE);
//        gd.setCornerRadii(floats);
        gd.setCornerRadius(0);
        gd.setColor(color);
        return gd;
    }

//    /**
//     * 获取live条目图片下方黑色遮盖切圆解
//     */
//    public static Drawable getLiveItemBottomFocusDrawable(int color, int radius) {
//        radius = ScreenAdapter.scaleX(radius);
//        GradientDrawable gd = new GradientDrawable();
//        float[] floats = new float[]{0.0f, 0.0f, 0.0f, 0.0f, radius, radius, radius, radius};
//        gd.setShape(GradientDrawable.RECTANGLE);
//        gd.setCornerRadii(floats);
//        gd.setColor(color);
//        return gd;
//    }
//
//    /**
//     * 启页页倒计时圆圈
//     */
//    public static Drawable getStrokeCircleDrawable(int color, int strokeWidth, int strokeColor) {
//        GradientDrawable gd = new GradientDrawable();
//        gd.setShape(GradientDrawable.OVAL);
//        gd.setStroke(ScreenAdapter.scaleX(strokeWidth), strokeColor);
//        gd.setColor(color);
//        return gd;
//    }
//
//    public static Drawable getStrokeDrawable(int color, int strokeWidth, int strokeColor, int radius) {
//        GradientDrawable gd = new GradientDrawable();
//        gd.setShape(GradientDrawable.RECTANGLE);
//        gd.setStroke(ScreenAdapter.scaleX(strokeWidth), strokeColor);
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//        gd.setColor(color);
//        return gd;
//    }
//
//    public static Drawable getFocusDrawable(Context context, int strokeWidth) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(strokeWidth), Color.BLACK);
//        gd.setCornerRadius(GlobalSize.getRadius());
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(3), context.getResources().getColor(R.color.focus_color));
//        gd.setCornerRadius(GlobalSize.getRadius());
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }

//    public static Drawable getFocusDrawable(Context context, int radius, int strokeWidth) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(strokeWidth), Color.BLACK);
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(ScreenAdapter.scaleX(strokeWidth), context.getResources().getColor(R.color.focus_color));
//        gd.setCornerRadius(ScreenAdapter.scaleX(radius));
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }
//
//    public static Drawable generateTabSelector(Drawable selected, Drawable unselected, Drawable focus, Drawable unfocus) {
//        StateListDrawable drawable = new StateListDrawable();
//        drawable.addState(new int[]{android.R.attr.state_selected}, selected);
//        drawable.addState(new int[]{-android.R.attr.state_selected}, unselected);
//        drawable.addState(new int[]{android.R.attr.state_focused}, focus);
//        drawable.addState(new int[]{-android.R.attr.state_focused}, unfocus);
//        return drawable;
//    }

//    public static Drawable generateTabSelectedDrawable() {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
////        gd.setColor(LiveApplication.getInstance().getResources().getColor(R.color.tab_selected_color));
//        gd.setCornerRadius(GlobalSize.getRadius());
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setSize(ScreenAdapter.scaleX(6), ScreenAdapter.scaleY(120));
////        gd.setColor(LiveApplication.getInstance().getResources().getColor(R.color.tab_selected_color));
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }
}
