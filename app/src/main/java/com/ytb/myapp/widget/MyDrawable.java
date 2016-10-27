package com.ytb.myapp.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016-10-27.
 */

public class MyDrawable extends Drawable {
    private Paint mPaint;
    private Bitmap mBitmap;

    private RectF rectF;

    public MyDrawable() {
//        mBitmap = bitmap;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    int color;
    public void color(int color) {
        this.color = color;
    }

    public void bitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(10);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }

//    @Override
//    public int getIntrinsicWidth() {
//        return mBitmap.getWidth();
//    }
//
//    @Override
//    public int getIntrinsicHeight() {
//        return mBitmap.getHeight();
//    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
