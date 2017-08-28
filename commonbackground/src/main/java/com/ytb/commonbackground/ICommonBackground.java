package com.ytb.commonbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * 通用背景接口
 */
interface ICommonBackground {
    /**
     * 设置形状
     *
     * @param shape 形状
     * @return this
     */
    ICommonBackground shape(int shape);

    /**
     * 设置填充模式
     *
     * @param fillMode 填充模式
     * @return this
     */
    ICommonBackground fillMode(int fillMode);

    /**
     * 设置描边模式
     *
     * @param strokeMode 描边模式
     * @return this
     */
    ICommonBackground strokeMode(int strokeMode);

    /**
     * 设置描边宽度
     *
     * @param strokeWidth 设置描边宽度
     * @return this
     */
    ICommonBackground strokeWidth(int strokeWidth);

    /**
     * 设置虚线描边时，单个实线的长度
     *
     * @param strokeDashSolid 单个实线的长度
     * @param strokeDashSpace 单个空白的长度
     * @return this
     */
    ICommonBackground strokeDash(int strokeDashSolid, int strokeDashSpace);

    /**
     * 设置圆角或圆形的半径
     *
     * @param radius 圆角或圆形的半径
     * @return this
     */
    ICommonBackground radius(int radius);

    /**
     * 设置圆角或圆形的半径
     *
     * @param radiusLeftTop     左上角半径
     * @param radiusRightTop    右上角半径
     * @param radiusRightBottom 右下角半径
     * @param radiusLeftBottom  左下角半径
     * @return this
     */
    ICommonBackground radius(int radiusLeftTop, int radiusRightTop,
                             int radiusRightBottom, int radiusLeftBottom);

    /**
     * 设置填充颜色
     *
     * @param colorFill 填充颜色
     * @return this
     */
    ICommonBackground colorFill(int colorFill);

    /**
     * 设置填充颜色
     *
     * @param colorFillResId 填充颜色资源ID
     * @return this
     */
    ICommonBackground colorFill(Context context, int colorFillResId);

    /**
     * 设置描边颜色
     *
     * @param colorStroke 描边颜色
     * @return this
     */
    ICommonBackground colorStroke(int colorStroke);

    /**
     * 设置描边颜色
     *
     * @param colorStrokeResId 描边颜色资源ID
     * @return this
     */
    ICommonBackground colorStroke(Context context, int colorStrokeResId);

    /**
     * 设置渐变颜色
     *
     * @param startColor  渐变开始色
     * @param endColor    渐变结束色
     * @param orientation 渐变方向
     * @return this
     */
    ICommonBackground linearGradient(int startColor, int endColor, int orientation);

    /**
     * 设置渐变颜色
     *
     * @param context         Context
     * @param startColorResId 渐变开始色id
     * @param endColorResId   渐变结束色id
     * @param orientation     渐变方向
     * @return this
     */
    ICommonBackground linearGradient(Context context, int startColorResId, int endColorResId,
                                     int orientation);

    /**
     * 设置填充位图
     *
     * @param bitmap 填充位图
     * @param scaleType 缩放模式
     * @return this
     */
    ICommonBackground bitmap(Bitmap bitmap, int scaleType);

    /**
     * 显示到View上
     *
     * @param yourView 你的View
     */
    void showOn(View yourView);
}
