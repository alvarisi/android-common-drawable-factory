package com.ytb.commonbackground;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 支持XML配置的通用背景LinearLayout
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundLinearLayout extends LinearLayout {
    public CommonBackgroundLinearLayout(Context context) {
        this(context, null, 0);
    }

    public CommonBackgroundLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CommonBackgroundLinearLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        CommonBackgroundFactory.fromXml(this, attributeSet);
    }
}
