package com.ytb.myapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.ytb.myapp.R;

/**
 * Created by Administrator on 2016-10-26.
 */

public class CommonBackgroundButton extends Button {
    private StateListDrawable mStateListDrawable;


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
            int shape = a.getInt(R.styleable.common_background_drawable_shape,
                    CommonBackgroundDrawable.SHAPE_RECT);
            int fillMode = a.getInt(R.styleable.common_background_drawable_fill_mode,
                    CommonBackgroundDrawable.FILL_MODE_SOLID);
            int strokeMode = a.getInt(R.styleable.common_background_drawable_stroke_mode,
                    CommonBackgroundDrawable.STROKE_MODE_NONE);
            int radius = a.getDimensionPixelSize(R.styleable.common_background_drawable_radius,
                    0);
            int strokeWidth = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_width, 0);
            int strokeDashSpace = a.getDimensionPixelSize(
                    R.styleable.common_background_drawable_stroke_dash_space, 0);
            int colorDisabled = a.getColor(R.styleable.common_background_drawable_color_disabled,
                    -1);
            int colorNormal = a.getColor(R.styleable.common_background_drawable_color_normal,
                    -1);
            int colorPressed = a.getColor(R.styleable.common_background_drawable_color_pressed,
                    -1);
            int colorStroke = a.getColor(R.styleable.common_background_drawable_color_stroke,
                    context.getResources().getColor(android.R.color.black));
            Drawable bitmap = a.getDrawable(R.styleable.common_background_drawable_fill_bitmap);

            a.recycle();

            mStateListDrawable = new StateListDrawable();
            if (colorNormal > 0) {
                CommonBackgroundDrawable drawableNormal = new CommonBackgroundDrawable();
                drawableNormal.shape(shape)
                        .fillMode(fillMode)
                        .strokeMode(strokeMode)
                        .radius(radius)
                        .strokeWidth(strokeWidth)
                        .strokeDashSpace(strokeDashSpace)
                        .colorFill(colorNormal)
                        .colorStroke(colorStroke)
                        .bitmap(((BitmapDrawable) bitmap).getBitmap());
                mStateListDrawable.addState(new int[]{android.R.attr.state_enabled},
                        drawableNormal);
                mStateListDrawable.addState(new int[]{}, drawableNormal);
            }
            if (colorDisabled > 0) {
                CommonBackgroundDrawable drawableDisabled = new CommonBackgroundDrawable();
                drawableDisabled.shape(shape)
                        .fillMode(fillMode)
                        .strokeMode(strokeMode)
                        .radius(radius)
                        .strokeWidth(strokeWidth)
                        .strokeDashSpace(strokeDashSpace)
                        .colorFill(colorDisabled)
                        .colorStroke(colorStroke)
                        .bitmap(((BitmapDrawable) bitmap).getBitmap());
                mStateListDrawable.addState(new int[]{android.R.attr.state_window_focused},
                        drawableDisabled);
            }
            if (colorPressed > 0) {
                CommonBackgroundDrawable drawablePressed = new CommonBackgroundDrawable();
                drawablePressed.shape(shape)
                        .fillMode(fillMode)
                        .strokeMode(strokeMode)
                        .radius(radius)
                        .strokeWidth(strokeWidth)
                        .strokeDashSpace(strokeDashSpace)
                        .colorFill(colorPressed)
                        .colorStroke(colorStroke)
                        .bitmap(((BitmapDrawable) bitmap).getBitmap());
                mStateListDrawable.addState(new int[]{android.R.attr.state_pressed,
                        android.R.attr.state_enabled}, drawablePressed);
            }

            setBackground(mStateListDrawable);
        }
    }
}
