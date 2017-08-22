package com.ytb.myapp.widget.commonbackground;

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
public class CommonBackgroundSet {
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
     * 返回当前集合的迭代器
     *
     * @return this
     */
    public CommonBackgroundIterator forEach() {
        return new CommonBackgroundIterator();
    }

    /**
     * 返回对应disabled状态的CommonBackground对象
     *
     * @return 对应disabled状态的CommonBackground对象
     */
    public ICommonBackground theDisabled() {
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
    public ICommonBackground theNormal() {
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
    public ICommonBackground thePressed() {
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
    public ICommonBackground theUnchecked() {
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
    public ICommonBackground theChecked() {
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

    public class CommonBackgroundIterator implements ICommonBackground {
        CommonBackgroundIterator() {
        }

        /**
         * 设置形状
         *
         * @param shape 形状
         * @return this
         */
        @Override
        public CommonBackgroundIterator shape(int shape) {
            for (ICommonBackground drawable : mDrawables) {
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
        public CommonBackgroundIterator fillMode(int fillMode) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.fillMode(fillMode);
            }
            return this;
        }

        /**
         * 设置缩放类型
         *
         * @param scaleType 缩放类型
         * @return this
         */
        @Override
        public CommonBackgroundIterator scaleType(int scaleType) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.scaleType(scaleType);
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
        public CommonBackgroundIterator strokeMode(int strokeMode) {
            for (ICommonBackground drawable : mDrawables) {
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
        public CommonBackgroundIterator strokeWidth(int strokeWidth) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.strokeWidth(strokeWidth);
            }
            return this;
        }

        /**
         * 设置虚线描边时，单个实线的长度
         *
         * @param strokeDashSolid 单个实线的长度
         * @return this
         */
        @Override
        public CommonBackgroundIterator strokeDashSolid(int strokeDashSolid) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.strokeDashSolid(strokeDashSolid);
            }
            return this;
        }

        /**
         * 设置虚线描边时，单个空白的长度
         *
         * @param strokeDashSpace 单个空白的长度
         * @return this
         */
        @Override
        public CommonBackgroundIterator strokeDashSpace(int strokeDashSpace) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.strokeDashSpace(strokeDashSpace);
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
        public CommonBackgroundIterator radius(int radius) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.radius(radius);
            }
            return this;
        }

        @Override
        public CommonBackgroundIterator radius(int radiusLeftTop, int radiusRightTop,
                                               int radiusRightBottom, int radiusLeftBottom) {
            if (radiusLeftTop > 0f || radiusRightTop > 0f ||
                    radiusRightBottom > 0f || radiusLeftBottom > 0f) {
                for (ICommonBackground drawable : mDrawables) {
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
        public CommonBackgroundIterator colorFill(int colorFill) {
            for (ICommonBackground drawable : mDrawables) {
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
        public CommonBackgroundIterator colorFill(Context context, int colorFillResId) {
            for (ICommonBackground drawable : mDrawables) {
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
        public CommonBackgroundIterator colorStroke(int colorStroke) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.colorStroke(colorStroke);
            }
            return this;
        }

        /**
         * 设置描边颜色（通过颜色资源ID）
         *
         * @param context        Context
         * @param colorStrokeResId 填充颜色资源ID
         * @return this
         */
        @Override
        public CommonBackgroundIterator colorStroke(Context context, int colorStrokeResId) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.colorStroke(context, colorStrokeResId);
            }
            return this;
        }

        /**
         * 设置填充位图
         *
         * @param bitmap 填充位图
         * @return this
         */
        @Override
        public CommonBackgroundIterator bitmap(Bitmap bitmap) {
            for (ICommonBackground drawable : mDrawables) {
                drawable.bitmap(bitmap);
            }
            return this;
        }

        @Override
        public void showOn(View yourView) {
            if (yourView != null) {
                CommonBackgroundSet.this.showOn(yourView);
            }
        }
    }
}
