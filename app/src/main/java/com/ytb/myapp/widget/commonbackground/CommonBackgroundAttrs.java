package com.ytb.myapp.widget.commonbackground;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 通用背景属性集
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundAttrs implements Serializable {
    boolean stateful;
    int stateMode;
    int shape;
    int fillMode;
    int scaleType;
    int strokeMode;
    int radius;
    int strokeWidth;
    int strokeDashSolid;
    int strokeDashSpace;
    int colorStroke;
    int colorDisabled;
    int colorNormal;
    int colorPressed;
    int colorUnchecked;
    int colorChecked;
    Bitmap bitmap;

    void recycle() {
        bitmap = null;
    }
}
