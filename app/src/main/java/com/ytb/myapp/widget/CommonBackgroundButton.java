package com.ytb.myapp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Administrator on 2016-10-26.
 */

public class CommonBackgroundButton extends Button {

    public CommonBackgroundButton(Context context) {
        this(context, null);
    }

    public CommonBackgroundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBackgroundButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseBackground(context, attrs);
    }

    private void parseBackground(Context context, AttributeSet attrs) {
        Drawable background = CommonBackgroundFactory.fromXml(context, attrs);
        if (background != null) {
            setBackground(background);
            setClickable(true);
        }
    }
}
