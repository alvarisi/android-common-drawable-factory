package com.ytb.commonbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 支持XML配置的通用背景ImageView
 *
 * @author yintaibing
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
        mCommonBackgroundAttrs.bitmap = drawableToBitmap(drawable);
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
            attrSet.fillMode |= CommonBackground.FILL_MODE_BITMAP;
        }
    }

    /**
     * Drawable转Bitmap
     *
     * @param drawable Drawable
     * @return Bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        if (w <= 0) {
            w = 50;
        }
        if (h <= 0) {
            h = 50;
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap
                        .Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
