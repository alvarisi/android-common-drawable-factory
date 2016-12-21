package com.ytb.myapp.widget.commonbackground;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * 支持XML配置的通用背景CheckBox
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundCheckBox extends CheckBox {
    public CommonBackgroundCheckBox(Context context) {
        this(context, null);
    }

    public CommonBackgroundCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBackgroundCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CommonBackgroundFactory.fromXml(this, attrs);
    }
}
