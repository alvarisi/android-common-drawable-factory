package com.ytb.myapp.widget.commonbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ytb.myapp.util.ImageUtils;

/**
 * 支持XML配置的通用背景ImageView
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundImageView extends ImageView {

    private static final String STATE_SUPER = "state_super";
    private static final String STATE_COMMON_BACKGROUND = "state_common_background";

    private CommonBackgroundAttrs mCommonBackgroundAttrs;

    public CommonBackgroundImageView(Context context) {
        this(context, null);
    }

    public CommonBackgroundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBackgroundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCommonBackgroundAttrs = CommonBackgroundFactory.obtainAttrs(context, attrs);
        CommonBackgroundFactory.fromAttrSet(this, mCommonBackgroundAttrs);
    }

    @Override
    public void setImageResource(int resId) {
        mCommonBackgroundAttrs.bitmap = BitmapFactory.decodeResource(getResources(), resId);
        resetFillMode(mCommonBackgroundAttrs);
        CommonBackgroundFactory.fromAttrSet(this, mCommonBackgroundAttrs);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mCommonBackgroundAttrs.bitmap = ImageUtils.drawableToBitmap(drawable);
        resetFillMode(mCommonBackgroundAttrs);
        CommonBackgroundFactory.fromAttrSet(this, mCommonBackgroundAttrs);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        mCommonBackgroundAttrs.bitmap = bitmap;
        resetFillMode(mCommonBackgroundAttrs);
        CommonBackgroundFactory.fromAttrSet(this, mCommonBackgroundAttrs);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        bundle.putSerializable(STATE_COMMON_BACKGROUND, mCommonBackgroundAttrs);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER));
            mCommonBackgroundAttrs = (CommonBackgroundAttrs) bundle.getSerializable
                    (STATE_COMMON_BACKGROUND);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mCommonBackgroundAttrs != null && mCommonBackgroundAttrs.bitmap != null) {
            mCommonBackgroundAttrs.bitmap.recycle();
        }
    }

    /**
     * 重设fillMode
     * solid -> bitmap
     * bitmap -> bitmap
     * solid|bitmap -> solid|bitmap
     *
     * @param attrSet 源属性集
     */
    private void resetFillMode(CommonBackgroundAttrs attrSet) {
        if ((CommonBackground.FILL_MODE_BITMAP & attrSet.fillMode) == 0) {
            attrSet.fillMode = CommonBackground.FILL_MODE_BITMAP;
        }
    }
}
