package com.ytb.commonbackground;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 支持XML配置的通用背景FrameLayout
 *
 * @author yintaibing
 */
public class CommonBackgroundFrameLayout extends FrameLayout {
    public CommonBackgroundFrameLayout(Context context) {
        this(context, null, 0);
    }

    public CommonBackgroundFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CommonBackgroundFrameLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        CommonBackgroundFactory.fromXml(this, attributeSet);
    }
}
