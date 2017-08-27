package com.ytb.commonbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

/**
 * CommonBackground的集合
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundSet implements ICommonBackground {
    public static final int STATE_MODE_NONE = 0;        // 无状态
    public static final int STATE_MODE_CLICK = 1;       // 点击模式（类似Button）
    public static final int STATE_MODE_CHECK = 2;       // 选择模式（类似CheckBox）

    private static final int STATE_COUNT_CLICK = 3;
    private static final int CLICK_STATE_DISABLED = 0;
    private static final int CLICK_STATE_NORMAL = 1;
    private static final int CLICK_STATE_PRESSED = 2;

    private static final int STATE_COUNT_CHECK = 3;
    private static final int CHECK_STATE_DISABLED = 0;
    private static final int CHECK_STATE_UNCHECKED = 1;
    private static final int CHECK_STATE_CHECKED = 2;

    private int mStateMode;
    private CommonBackground[] mDrawables;

    CommonBackgroundSet() {
        this(STATE_MODE_CLICK);
    }

    CommonBackgroundSet(int stateMode) {
        mStateMode = stateMode;
        int size = mStateMode == STATE_MODE_CLICK ? STATE_COUNT_CLICK :
                mStateMode == STATE_MODE_CHECK ? STATE_COUNT_CHECK : 0;
        mDrawables = new CommonBackground[size];
        for (int i = 0; i < size; i++) {
            mDrawables[i] = new CommonBackground();
        }
    }

    /**
     * 返回对应disabled状态的CommonBackground对象
     *
     * @return 对应disabled状态的CommonBackground对象
     */
    public CommonBackground theDisabled() {
        if (mStateMode == STATE_MODE_CLICK) {
            return mDrawables[CLICK_STATE_DISABLED];
        } else if (mStateMode == STATE_MODE_CHECK) {
            return mDrawables[CHECK_STATE_DISABLED];
        }
        return null;
    }

    /**
     * 返回对应normal状态的CommonBackground对象
     *
     * @return 对应normal状态的CommonBackground对象
     */
    public CommonBackground theNormal() {
        if (mStateMode != STATE_MODE_CLICK) {
            return null;
        }
        return mDrawables[CLICK_STATE_NORMAL];
    }

    /**
     * 返回对应pressed状态的CommonBackground对象
     *
     * @return 对应pressed状态的CommonBackground对象
     */
    public CommonBackground thePressed() {
        if (mStateMode != STATE_MODE_CLICK) {
            return null;
        }
        return mDrawables[CLICK_STATE_PRESSED];
    }

    /**
     * 返回对应unchecked状态的CommonBackground对象
     *
     * @return 对应pressed状态的CommonBackground对象
     */
    public CommonBackground theUnchecked() {
        if (mStateMode != STATE_MODE_CHECK) {
            return null;
        }
        return mDrawables[CHECK_STATE_UNCHECKED];
    }

    /**
     * 返回对应checked状态的CommonBackground对象
     *
     * @return 对应pressed状态的CommonBackground对象
     */
    public CommonBackground theChecked() {
        if (mStateMode != STATE_MODE_CHECK) {
            return null;
        }
        return mDrawables[CHECK_STATE_CHECKED];
    }

    /**
     * 显示到你的View上
     *
     * @param yourView 你的View
     */
    public void showOn(View yourView) {
        if (yourView != null) {
            StateListDrawable stateList = new StateListDrawable();
            if (mStateMode == STATE_MODE_CLICK) {
                // 以下顺序不可更改
                // when disabled
                stateList.addState(new int[]{-android.R.attr.state_enabled},
                        mDrawables[CLICK_STATE_DISABLED]);
                // View.PRESSED_ENABLED_STATE_SET
                stateList.addState(new int[]{android.R.attr.state_pressed,
                                android.R.attr.state_enabled},
                        mDrawables[CLICK_STATE_PRESSED]);
                // View.ENABLED_FOCUSED_STATE_SET
                stateList.addState(new int[]{android.R.attr.state_enabled,
                                android.R.attr.state_focused},
                        mDrawables[CLICK_STATE_NORMAL]);
                // View.ENABLED_STATE_SET
                stateList.addState(new int[]{android.R.attr.state_enabled},
                        mDrawables[CLICK_STATE_NORMAL]);
                // View.FOCUSED_STATE_SET
                stateList.addState(new int[]{android.R.attr.state_focused},
                        mDrawables[CLICK_STATE_NORMAL]);
                // View.EMPTY_STATE_SET
                stateList.addState(new int[]{}, mDrawables[CLICK_STATE_NORMAL]);
                // View.WINDOW_FOCUSED_STATE_SET
                stateList.addState(new int[]{android.R.attr.state_window_focused},
                        mDrawables[CLICK_STATE_DISABLED]);
            } else if (mStateMode == STATE_MODE_CHECK) {
                // 以下顺序不可更改
                // when disabled
                stateList.addState(new int[]{-android.R.attr.state_enabled},
                        mDrawables[CHECK_STATE_DISABLED]);
                stateList.addState(new int[]{android.R.attr.state_checked,
                                android.R.attr.state_enabled},
                        mDrawables[CHECK_STATE_CHECKED]);
                stateList.addState(new int[]{-android.R.attr.state_checked,
                                android.R.attr.state_enabled},
                        mDrawables[CHECK_STATE_UNCHECKED]);
                stateList.addState(new int[]{}, mDrawables[CHECK_STATE_UNCHECKED]);
                stateList.addState(new int[]{android.R.attr.state_window_focused},
                        mDrawables[CLICK_STATE_DISABLED]);
            }

            yourView.setBackgroundDrawable(stateList);
            yourView.setClickable(true);
        }
    }

    /**
     * 设置形状
     *
     * @param shape 形状
     * @return this
     */
    @Override
    public CommonBackgroundSet shape(int shape) {
        for (CommonBackground drawable : mDrawables) {
            drawable.shape(shape);
        }
        return this;
    }

    /**
     * 设置填充模式
     *
     * @param fillMode 填充模式
     * @return this
     */
    @Override
    public CommonBackgroundSet fillMode(int fillMode) {
        for (CommonBackground drawable : mDrawables) {
            drawable.fillMode(fillMode);
        }
        return this;
    }

    /**
     * 设置描边模式
     *
     * @param strokeMode 描边模式
     * @return this
     */
    @Override
    public CommonBackgroundSet strokeMode(int strokeMode) {
        for (CommonBackground drawable : mDrawables) {
            drawable.strokeMode(strokeMode);
        }
        return this;
    }

    /**
     * 设置描边宽度
     *
     * @param strokeWidth 设置描边宽度
     * @return this
     */
    @Override
    public CommonBackgroundSet strokeWidth(int strokeWidth) {
        for (CommonBackground drawable : mDrawables) {
            drawable.strokeWidth(strokeWidth);
        }
        return this;
    }

    /**
     * 设置虚线描边时，单个实线的长度
     *
     * @param strokeDashSolid 单个实线的长度
     * @param strokeDashSpace 单个空白的长度
     * @return this
     */
    @Override
    public CommonBackgroundSet strokeDash(int strokeDashSolid, int strokeDashSpace) {
        for (CommonBackground drawable : mDrawables) {
            drawable.strokeDash(strokeDashSolid, strokeDashSpace);
        }
        return this;
    }

    /**
     * 设置圆角或圆形的半径
     *
     * @param radius 圆角或圆形的半径
     * @return this
     */
    @Override
    public CommonBackgroundSet radius(int radius) {
        for (CommonBackground drawable : mDrawables) {
            drawable.radius(radius);
        }
        return this;
    }

    @Override
    public CommonBackgroundSet radius(int radiusLeftTop, int radiusRightTop,
                                      int radiusRightBottom, int radiusLeftBottom) {
        if (radiusLeftTop > 0f || radiusRightTop > 0f ||
                radiusRightBottom > 0f || radiusLeftBottom > 0f) {
            for (CommonBackground drawable : mDrawables) {
                drawable.radius(radiusLeftTop, radiusRightTop,
                        radiusRightBottom, radiusLeftBottom);
            }
        }
        return this;
    }

    /**
     * 设置填充颜色
     *
     * @param colorFill 填充颜色
     * @return this
     */
    @Override
    public CommonBackgroundSet colorFill(int colorFill) {
        for (CommonBackground drawable : mDrawables) {
            drawable.colorFill(colorFill);
        }
        return this;
    }

    /**
     * 设置填充颜色（通过颜色资源ID）
     *
     * @param context        Context
     * @param colorFillResId 填充颜色资源ID
     * @return this
     */
    @Override
    public CommonBackgroundSet colorFill(Context context, int colorFillResId) {
        for (CommonBackground drawable : mDrawables) {
            drawable.colorFill(context, colorFillResId);
        }
        return this;
    }

    /**
     * 设置描边颜色
     *
     * @param colorStroke 描边颜色
     * @return this
     */
    @Override
    public CommonBackgroundSet colorStroke(int colorStroke) {
        for (CommonBackground drawable : mDrawables) {
            drawable.colorStroke(colorStroke);
        }
        return this;
    }

    /**
     * 设置描边颜色（通过颜色资源ID）
     *
     * @param context          Context
     * @param colorStrokeResId 填充颜色资源ID
     * @return this
     */
    @Override
    public CommonBackgroundSet colorStroke(Context context, int colorStrokeResId) {
        for (CommonBackground drawable : mDrawables) {
            drawable.colorStroke(context, colorStrokeResId);
        }
        return this;
    }

    @Override
    public CommonBackgroundSet linearGradient(int startColor, int endColor, int orientation) {
        for (CommonBackground drawable : mDrawables) {
            drawable.linearGradient(startColor, endColor, orientation);
        }
        return this;
    }

    @Override
    public CommonBackgroundSet linearGradient(Context context, int startColorResId,
                                              int endColorResId, int orientation) {
        for (CommonBackground drawable : mDrawables) {
            drawable.linearGradient(context, startColorResId, endColorResId, orientation);
        }
        return this;
    }

    /**
     * 设置填充位图
     *
     * @param bitmap    填充位图
     * @param scaleType 缩放类型
     * @return this
     */
    @Override
    public CommonBackgroundSet bitmap(Bitmap bitmap, int scaleType) {
        for (CommonBackground drawable : mDrawables) {
            drawable.bitmap(bitmap, scaleType);
        }
        return this;
    }
}
