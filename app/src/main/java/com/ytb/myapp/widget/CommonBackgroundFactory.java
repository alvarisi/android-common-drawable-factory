package com.ytb.myapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.ytb.myapp.R;
import com.ytb.myapp.util.LogUtils;

/**
 * Created by Administrator on 2016-10-28.
 */

public class CommonBackgroundFactory {
    private static final int STATE_COUNT = 3;
    private static final int STATE_DISABLED = 0;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_PRESSED = 2;

    public static class AttrSet {
        boolean stateful;
        int shape;
        int fillMode;
        int strokeMode;
        int radius;
        int strokeWidth;
        int strokeDashSolid;
        int strokeDashSpace;
        int colorStroke;
        int colorDisabled;
        int colorNormal;
        int colorPressed;
        Bitmap bitmap;

        void recycle() {
            bitmap = null;
        }
    }

    public static CommonBackground newStateless() {
        return new CommonBackground();
    }

    public static CommonBackground newStateful() {
        return null;
    }

    public static Drawable fromXml(Context context, AttributeSet attributeSet) {
        AttrSet attrs = obtainAttrs(context, attributeSet);
        if (attrs != null) {
            if (attrs.stateful) {
                StateListDrawable stateList = stateful(attrs);
                attrs.recycle();
                return stateList;
            } else {
                CommonBackground drawable = stateless(attrs);
                attrs.recycle();
                return drawable;
            }
        }
        return null;
    }

    private static AttrSet obtainAttrs(Context context, AttributeSet attributeSet) {
        if (context != null && attributeSet != null) {
            AttrSet attrs = new AttrSet();
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable
                    .common_background_drawable);
            attrs.stateful = a.getBoolean(R.styleable.common_background_drawable_stateful, false);
            attrs.shape = a.getInt(R.styleable.common_background_drawable_shape,
                    CommonBackground.SHAPE_RECT); // 默认直角矩形
            attrs.strokeMode = a.getInt(R.styleable.common_background_drawable_stroke_mode,
                    CommonBackground.STROKE_MODE_NONE); // 默认无描边
            attrs.fillMode = a.getInt(R.styleable.common_background_drawable_fill_mode,
                    CommonBackground.FILL_MODE_SOLID); // 默认颜色填充
            attrs.radius = a.getDimensionPixelSize(R.styleable.common_background_drawable_radius,
                    0);
            attrs.strokeWidth = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_width, 0);
            attrs.strokeDashSolid = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_dash_solid, 0);
            attrs.strokeDashSpace = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_dash_space, 0);
            attrs.colorDisabled = a.getColor(R.styleable.common_background_drawable_color_disabled,
                    Color.LTGRAY); // disabled状态默认使用浅灰色
            attrs.colorNormal = a.getColor(R.styleable.common_background_drawable_color_normal,
                    Color.WHITE); // normal状态默认使用白色
            attrs.colorPressed = a.getColor(R.styleable.common_background_drawable_color_pressed,
                    attrs.colorNormal); // pressed状态默认与normal状态相同
            attrs.colorStroke = a.getColor(R.styleable.common_background_drawable_color_stroke,
                    Color.TRANSPARENT); // 描边默认使用透明
            int bitmapResId = a.getResourceId(R.styleable.common_background_drawable_bitmap,
                    R.mipmap.ic_launcher);
            attrs.bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapResId);

            a.recycle();
            return attrs;
        }
        return null;
    }

    private static CommonBackground stateless(AttrSet attrs) {
        return new CommonBackground()
                .shape(attrs.shape)
                .fillMode(attrs.fillMode)
                .strokeMode(attrs.strokeMode)
                .strokeWidth(attrs.strokeWidth)
                .strokeDashSolid(attrs.strokeDashSolid)
                .strokeDashSpace(attrs.strokeDashSpace)
                .radius(attrs.radius)
                .colorStroke(attrs.colorStroke)
                .colorFill(attrs.colorNormal)
                .bitmap(attrs.bitmap);
    }

    private static StateListDrawable stateful(AttrSet attrs) {
        CommonBackground[] drawables = new CommonBackground[STATE_COUNT];
        for (int i = 0; i < STATE_COUNT; i++) {
            drawables[i] = new CommonBackground()
                    .shape(attrs.shape)
                    .fillMode(attrs.fillMode)
                    .strokeMode(attrs.strokeMode)
                    .strokeWidth(attrs.strokeWidth)
                    .strokeDashSolid(attrs.strokeDashSolid)
                    .strokeDashSpace(attrs.strokeDashSpace)
                    .colorStroke(attrs.colorStroke)
                    .radius(attrs.radius)
                    .bitmap(attrs.bitmap);
        }
        drawables[STATE_DISABLED].colorFill(attrs.colorDisabled);
        drawables[STATE_NORMAL].colorFill(attrs.colorNormal);
        drawables[STATE_PRESSED].colorFill(attrs.colorPressed);

        StateListDrawable stateList = new StateListDrawable();
        // 以下顺序不可更改
        // View.PRESSED_ENABLED_STATE_SET
        stateList.addState(new int[]{android.R.attr.state_pressed, android.R.attr
                .state_enabled}, drawables[STATE_PRESSED]);
        // View.ENABLED_FOCUSED_STATE_SET
        stateList.addState(new int[]{android.R.attr.state_enabled, android.R.attr
                .state_focused}, drawables[STATE_NORMAL]);
        // View.ENABLED_STATE_SET
        stateList.addState(new int[]{android.R.attr.state_enabled}, drawables[STATE_NORMAL]);
        // View.FOCUSED_STATE_SET
        stateList.addState(new int[]{android.R.attr.state_focused}, drawables[STATE_NORMAL]);
        // View.EMPTY_STATE_SET
        stateList.addState(new int[]{}, drawables[STATE_NORMAL]);
        // View.WINDOW_FOCUSED_STATE_SET
        stateList.addState(new int[]{android.R.attr.state_window_focused},
                drawables[STATE_DISABLED]);
        return stateList;
    }
}
