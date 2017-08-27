package com.ytb.commonbackground;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * 通用背景工厂类
 *
 * @author yintaibing
 * @date 2016/10/29
 */
public class CommonBackgroundFactory {

    /**
     * 从XML解析通用背景
     *
     * @param view         View
     * @param attributeSet AttributeSet
     */
    public static void fromXml(View view, AttributeSet attributeSet) {
        CommonBackgroundAttrs attrs = obtainAttrs(view.getContext(), attributeSet);
        fromAttrSet(view, attrs);
    }

    /**
     * 从AttrSet解析通用背景
     *
     * @param view    View
     * @param attrSet AttrSet
     */
    public static void fromAttrSet(View view, CommonBackgroundAttrs attrSet) {
        if (attrSet != null) {
            if (attrSet.stateMode == CommonBackgroundSet.STATE_MODE_CLICK ||
                    attrSet.stateMode == CommonBackgroundSet.STATE_MODE_CHECK) {
                CommonBackgroundSet set = stateful(attrSet);
                set.showOn(view);
            } else {
                ICommonBackground drawable = stateless(attrSet);
                drawable.showOn(view);
            }

            attrSet.recycle();
        }
    }

    /**
     * 创建不区分状态（disabled, normal, pressed）的通用背景
     *
     * @return 单独一个CommonBackground
     */
    public static CommonBackground createStateless() {
        return new CommonBackground();
    }

    /**
     * 创建区分状态（disabled, normal, pressed）的通用背景
     *
     * @return CommonBackgroundSet
     */
    public static CommonBackgroundSet createClickable() {
        return new CommonBackgroundSet(CommonBackgroundSet.STATE_MODE_CLICK);
    }

    /**
     * 创建区分状态（disabled, unchecked, checked）的通用背景
     *
     * @return CommonBackgroundSet
     */
    public static CommonBackgroundSet createCheckable() {
        return new CommonBackgroundSet(CommonBackgroundSet.STATE_MODE_CHECK);
    }

    /**
     * 提取自定义的属性集
     *
     * @param context      Context
     * @param attributeSet AttributeSet
     * @return AttrSet
     */
    public static CommonBackgroundAttrs obtainAttrs(Context context, AttributeSet attributeSet) {
        if (context != null && attributeSet != null) {
            CommonBackgroundAttrs attrs = new CommonBackgroundAttrs();
            TypedArray a = context.obtainStyledAttributes(attributeSet,
                    R.styleable.CommonBackground);
            attrs.stateMode = a.getInt(R.styleable.CommonBackground_bg_stateMode,
                    CommonBackgroundSet.STATE_MODE_NONE);
            attrs.shape = a.getInt(R.styleable.CommonBackground_bg_shape,
                    CommonBackground.SHAPE_RECT); // 默认直角矩形
            attrs.fillMode = a.getInt(R.styleable.CommonBackground_bg_fillMode,
                    CommonBackground.FILL_MODE_COLOR); // 默认颜色填充
            attrs.scaleType = a.getInt(R.styleable.CommonBackground_bg_scaleType,
                    CommonBackground.SCALE_TYPE_CENTER); // 默认无缩放
            attrs.strokeMode = a.getInt(R.styleable.CommonBackground_bg_strokeMode,
                    CommonBackground.STROKE_MODE_NONE); // 默认无描边
            if (attrs.strokeMode != CommonBackground.STROKE_MODE_NONE) {
                attrs.strokeWidth = a.getDimensionPixelSize(
                        R.styleable.CommonBackground_bg_strokeWidth, 0);
            }

            attrs.radius = a.getDimensionPixelSize(R.styleable.CommonBackground_bg_radius, 0);
            attrs.radiusLeftTop = a.getDimensionPixelSize(
                    R.styleable.CommonBackground_bg_radiusLeftTop, 0);
            attrs.radiusLeftBottom = a.getDimensionPixelSize(
                    R.styleable.CommonBackground_bg_radiusLeftBottom, 0);
            attrs.radiusRightTop = a.getDimensionPixelSize(
                    R.styleable.CommonBackground_bg_radiusRightTop, 0);
            attrs.radiusRightBottom = a.getDimensionPixelSize(
                    R.styleable.CommonBackground_bg_radiusRightBottom, 0);

            attrs.strokeDashSolid = a.getDimensionPixelSize(
                    R.styleable.CommonBackground_bg_strokeDashSolid, 0);
            attrs.strokeDashSpace = a.getDimensionPixelSize(
                    R.styleable.CommonBackground_bg_strokeDashSpace, 0);
            if (attrs.stateMode == CommonBackgroundSet.STATE_MODE_CLICK) {
                attrs.colorNormal = a.getColor(R.styleable.CommonBackground_bg_colorNormal,
                        Color.TRANSPARENT); // normal状态默认使用白色
                attrs.colorPressed = a.getColor(R.styleable.CommonBackground_bg_colorPressed,
                        attrs.colorNormal); // pressed状态默认与normal状态相同
                attrs.colorDisabled = a.getColor(R.styleable.CommonBackground_bg_colorDisabled,
                        Color.LTGRAY); // disabled状态默认使用浅灰色
            } else if (attrs.stateMode == CommonBackgroundSet.STATE_MODE_CHECK) {
                attrs.colorUnchecked = a.getColor(R.styleable.CommonBackground_bg_colorUnchecked,
                        Color.TRANSPARENT);
                attrs.colorChecked = a.getColor(R.styleable.CommonBackground_bg_colorChecked,
                        Color.TRANSPARENT);
                attrs.colorDisabled = a.getColor(R.styleable.CommonBackground_bg_colorDisabled,
                        Color.LTGRAY); // disabled状态默认使用浅灰色
            } else {
                attrs.colorNormal = a.getColor(R.styleable.CommonBackground_bg_colorNormal,
                        Color.TRANSPARENT);
            }
            attrs.colorStroke = a.getColor(R.styleable.CommonBackground_bg_colorStroke,
                    Color.TRANSPARENT); // 描边默认使用透明

            attrs.gradientStartColor = a.getColor(
                    R.styleable.CommonBackground_bg_gradientStartColor, Color.TRANSPARENT);
            attrs.gradientEndColor = a.getColor(
                    R.styleable.CommonBackground_bg_gradientEndColor, Color.TRANSPARENT);
            attrs.linearGradientOrientation = a.getInteger(
                    R.styleable.CommonBackground_bg_linearGradientOrientation, 0);

            int bitmapResId = a.getResourceId(R.styleable.CommonBackground_bg_bitmap, 0);
            attrs.bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapResId);

            a.recycle();
            return attrs;
        }
        return null;
    }

    private static ICommonBackground stateless(CommonBackgroundAttrs attrs) {
        return new CommonBackground()
                .shape(attrs.shape)
                .fillMode(attrs.fillMode)
                .strokeMode(attrs.strokeMode)
                .strokeWidth(attrs.strokeWidth)
                .strokeDash(attrs.strokeDashSolid, attrs.strokeDashSpace)
                .radius(attrs.radius)
                .radius(attrs.radiusLeftTop, attrs.radiusRightTop,
                        attrs.radiusRightBottom, attrs.radiusLeftBottom)
                .colorStroke(attrs.colorStroke)
                .colorFill(attrs.colorNormal)
                .linearGradient(attrs.gradientStartColor, attrs.gradientEndColor,
                        attrs.linearGradientOrientation)
                .bitmap(attrs.bitmap, attrs.scaleType);
    }

    private static CommonBackgroundSet stateful(CommonBackgroundAttrs attrs) {
        CommonBackgroundSet set = new CommonBackgroundSet(attrs.stateMode);
        set.shape(attrs.shape)
                .fillMode(attrs.fillMode)
                .strokeMode(attrs.strokeMode)
                .strokeWidth(attrs.strokeWidth)
                .strokeDash(attrs.strokeDashSolid, attrs.strokeDashSpace)
                .radius(attrs.radius)
                .radius(attrs.radiusLeftTop, attrs.radiusRightTop,
                        attrs.radiusRightBottom, attrs.radiusLeftBottom)
                .colorStroke(attrs.colorStroke)
                .linearGradient(attrs.gradientStartColor, attrs.gradientEndColor,
                        attrs.linearGradientOrientation)
                .bitmap(attrs.bitmap, attrs.scaleType);
        set.theDisabled().colorFill(attrs.colorDisabled);
        if (attrs.stateMode == CommonBackgroundSet.STATE_MODE_CLICK) {
            set.theNormal().colorFill(attrs.colorNormal);
            set.thePressed().colorFill(attrs.colorPressed);
        } else if (attrs.stateMode == CommonBackgroundSet.STATE_MODE_CHECK) {
            set.theUnchecked().colorFill(attrs.colorUnchecked);
            set.theChecked().colorFill(attrs.colorChecked);
        } else {
            set.theNormal().colorFill(attrs.colorNormal);
        }
        return set;
    }

}
