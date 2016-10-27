package com.ytb.myapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.ytb.myapp.R;
import com.ytb.myapp.util.LogUtils;

/**
 * Created by Administrator on 2016-10-26.
 */

public class CommonBackgroundButton extends Button {
    private StateListDrawable mStateList;
    private int shape;
    private int fillMode;
    private int strokeMode;
    private int radius;
    private int strokeWidth;
    private int strokeDashSpace;
    private int colorDisabled;
    private int colorNormal;
    private int colorPressed;
    private int colorStroke;
    private Bitmap bitmap;


    public CommonBackgroundButton(Context context) {
        this(context, null);
    }

    public CommonBackgroundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBackgroundButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        obtainAttrs(context, attrs);
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable
                    .common_background_drawable);
            shape = a.getInt(R.styleable.common_background_drawable_shape,
                    CommonBackgroundDrawable.SHAPE_RECT);
            fillMode = a.getInt(R.styleable.common_background_drawable_fill_mode,
                    CommonBackgroundDrawable.FILL_MODE_SOLID);
            strokeMode = a.getInt(R.styleable.common_background_drawable_stroke_mode,
                    CommonBackgroundDrawable.STROKE_MODE_NONE);
            radius = a.getDimensionPixelSize(R.styleable.common_background_drawable_radius,
                    0);
            strokeWidth = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_width, 0);
            strokeDashSpace = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_dash_space, 0);
            colorDisabled = a.getColor(R.styleable.common_background_drawable_color_disabled,
                    Color.TRANSPARENT);
            colorNormal = a.getColor(R.styleable.common_background_drawable_color_normal,
                    Color.TRANSPARENT);
            colorPressed = a.getColor(R.styleable.common_background_drawable_color_pressed,
                    Color.TRANSPARENT);
            colorStroke = a.getColor(R.styleable.common_background_drawable_color_stroke,
                    Color.TRANSPARENT);
            int bitmapResId = a.getResourceId(R.styleable.common_background_drawable_fill_bitmap,
                    R.mipmap.ic_launcher);
            bitmap = BitmapFactory.decodeResource(getResources(), bitmapResId);

            a.recycle();
        }
    }

    public void refreshBackground() {
        /**
         * 0 disabled
         * 1 normal
         * 2 pressed
         */
        CommonBackgroundDrawable[] drawables = new CommonBackgroundDrawable[3];
        mStateList = new StateListDrawable();

        for (int i = 0; i < 3; i++) {
            drawables[i] = new CommonBackgroundDrawable();
            drawables[i].shape(shape)
                    .fillMode(fillMode)
                    .strokeMode(strokeMode)
                    .radius(radius)
                    .strokeWidth(strokeWidth)
                    .strokeDashSpace(strokeDashSpace)
                    .colorStroke(colorStroke);
            LogUtils.e("shape=" + shape);
            if (bitmap != null) {
                LogUtils.e("fill_mode=" + fillMode);
                LogUtils.e("bitmapH=" + bitmap.getHeight());
                drawables[i].bitmap(bitmap);
            }

            if (i == 0) {
                drawables[i].colorFill(colorDisabled);
            }
            if (i == 1) {
                drawables[i].colorFill(colorNormal);
            }
            if (i == 2) {
                drawables[i].colorFill(colorPressed);
            }
        }

        // View.PRESSED_ENABLED_STATE_SET
        mStateList.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, drawables[2]);
        // View.ENABLED_FOCUSED_STATE_SET
        mStateList.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, drawables[1]);
        // View.ENABLED_STATE_SET
        mStateList.addState(new int[] { android.R.attr.state_enabled }, drawables[1]);
        // View.FOCUSED_STATE_SET
        mStateList.addState(new int[] { android.R.attr.state_focused }, drawables[1]);
        // View.WINDOW_FOCUSED_STATE_SET
        mStateList.addState(new int[] { android.R.attr.state_window_focused }, drawables[0]);
        // View.EMPTY_STATE_SET
        mStateList.addState(new int[] {}, drawables[1]);

        setBackground(mStateList);
    }
}
