package com.ytb.myapp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 支持XML配置的通用背景ImageView
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackgroundImageView extends ImageView {

    private CommonBackgroundFactory.AttrSet mAttrSet;

    public CommonBackgroundImageView(Context context) {
        this(context, null);
    }

    public CommonBackgroundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBackgroundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAttrSet = CommonBackgroundFactory.obtainAttrs(context, attrs);
        CommonBackgroundFactory.fromAttrSet(this, mAttrSet);
    }

    @Override
    public void setImageResource(int resId) {
        mAttrSet.bitmap = BitmapFactory.decodeResource(getResources(), resId);
        CommonBackgroundFactory.fromAttrSet(this, mAttrSet);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mAttrSet.bitmap = ((BitmapDrawable) drawable).getBitmap();
        CommonBackgroundFactory.fromAttrSet(this, mAttrSet);
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        mAttrSet.bitmap = bitmap;
        CommonBackgroundFactory.fromAttrSet(this, mAttrSet);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mAttrSet != null && mAttrSet.bitmap != null) {
            mAttrSet.bitmap.recycle();
        }
    }
}
