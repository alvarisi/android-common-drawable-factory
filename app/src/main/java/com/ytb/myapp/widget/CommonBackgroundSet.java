package com.ytb.myapp.widget;

import android.graphics.drawable.StateListDrawable;
import android.view.View;

/**
 * CommonBackground的集合
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundSet {
    private static final int STATE_COUNT = 3;
    private static final int STATE_DISABLED = 0;
    private static final int STATE_NORMAL = 1;
    private static final int STATE_PRESSED = 2;

    private CommonBackground[] mDrawables;

    CommonBackgroundSet() {
        mDrawables = new CommonBackground[STATE_COUNT];
    }

    /**
     * 返回对应disabled状态的CommonBackground对象
     *
     * @return 对应disabled状态的CommonBackground对象
     */
    public CommonBackground theDisabled() {
        mDrawables[STATE_DISABLED] = new CommonBackground();
        return mDrawables[STATE_DISABLED];
    }

    /**
     * 返回对应normal状态的CommonBackground对象
     *
     * @return 对应normal状态的CommonBackground对象
     */
    public CommonBackground theNormal() {
        mDrawables[STATE_NORMAL] = new CommonBackground();
        return mDrawables[STATE_NORMAL];
    }

    /**
     * 返回对应pressed状态的CommonBackground对象
     *
     * @return 对应pressed状态的CommonBackground对象
     */
    public CommonBackground thePressed() {
        mDrawables[STATE_PRESSED] = new CommonBackground();
        return mDrawables[STATE_PRESSED];
    }

    /**
     * 显示到你的View上
     *
     * @param yourView 你的View
     */
    public void showOn(View yourView) {
        if (yourView != null) {
            StateListDrawable stateList = new StateListDrawable();
            // 以下顺序不可更改
            // View.PRESSED_ENABLED_STATE_SET
            stateList.addState(new int[]{android.R.attr.state_pressed, android.R.attr
                    .state_enabled}, mDrawables[STATE_PRESSED]);
            // View.ENABLED_FOCUSED_STATE_SET
            stateList.addState(new int[]{android.R.attr.state_enabled, android.R.attr
                    .state_focused}, mDrawables[STATE_NORMAL]);
            // View.ENABLED_STATE_SET
            stateList.addState(new int[]{android.R.attr.state_enabled}, mDrawables[STATE_NORMAL]);
            // View.FOCUSED_STATE_SET
            stateList.addState(new int[]{android.R.attr.state_focused}, mDrawables[STATE_NORMAL]);
            // View.EMPTY_STATE_SET
            stateList.addState(new int[]{}, mDrawables[STATE_NORMAL]);
            // View.WINDOW_FOCUSED_STATE_SET
            stateList.addState(new int[]{android.R.attr.state_window_focused},
                    mDrawables[STATE_DISABLED]);

            yourView.setBackground(stateList);
            yourView.setClickable(true);
        }
    }
}
