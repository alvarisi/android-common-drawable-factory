package com.ytb.commonbackground;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 支持XML配置的通用背景RelativeLayout
 *
 * @author yintaibing
 */
public class CommonBackgroundRelativeLayout extends RelativeLayout {
    public CommonBackgroundRelativeLayout(Context context) {
        this(context, null, 0);
    }

    public CommonBackgroundRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CommonBackgroundRelativeLayout(Context context, AttributeSet attributeSet,
                                          int defStyle) {
        super(context, attributeSet, defStyle);
        CommonBackgroundFactory.fromXml(this, attributeSet);
    }
}
