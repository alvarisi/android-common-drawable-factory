package com.ytb.myapp.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.ytb.myapp.util.LogUtils;

/**
 * Created by Administrator on 2016-10-25.
 */

public class CommonBackgroundDrawable extends Drawable {
    public static final int SHAPE_RECT = 0;
    public static final int SHAPE_ROUND_RECT = 1;
    public static final int SHAPE_LEFT_CIRCLE_RECT = 2;
    public static final int SHAPE_RIGHT_CIRCLE_RECT = 3;
    public static final int SHAPE_BOTH_CIRCLE_RECT = 4;
    public static final int SHAPE_CIRCLE = 5;

    public static final int FILL_MODE_SOLID = 0;
    public static final int FILL_MODE_BITMAP = 1;

    public static final int STROKE_MODE_NONE = 0;
    public static final int STROKE_MODE_SOLID = 1;
    public static final int STROKE_MODE_DASH = 2;

    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader mShader;
    private int mShape;
    private int mFillMode;
    private int mStrokeMode;
    private RectF mBounds;
    private int mColorFill = Color.WHITE;
    private int mColorStroke = Color.WHITE;
    private float mStrokeWidth;       // px
    private float[] mStrokeDash;      // px
    private float mRadius;            // px
    private float mCx, mCy;

    public CommonBackgroundDrawable() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public CommonBackgroundDrawable shape(int shape) {
        mShape = shape;
        return this;
    }

    public CommonBackgroundDrawable fillMode(int fillMode) {
        mFillMode = fillMode;
        return this;
    }

    public CommonBackgroundDrawable strokeMode(int strokeMode) {
        mStrokeMode = strokeMode;
        return this;
    }

    public CommonBackgroundDrawable strokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    public CommonBackgroundDrawable strokeDashSolid(int strokeDashSolid) {
        if (mStrokeDash == null) {
            mStrokeDash = new float[2];
        }
        mStrokeDash[0] = strokeDashSolid;
        return this;
    }

    public CommonBackgroundDrawable strokeDashSpace(int strokeDashSpace) {
        if (mStrokeDash == null) {
            mStrokeDash = new float[2];
        }
        mStrokeDash[1] = strokeDashSpace;
        return this;
    }

    public CommonBackgroundDrawable radius(int radius) {
//        return radius(unit, radius, radius, radius, radius);
        mRadius = radius;
        return this;
    }
//
//    public CommonBackgroundDrawable radius(int unit, int radiusLeftTop, int radiusRightTop, int
//            radiusRightBottom, int radiusLeftBottom) {
//        mRadius = new int[4];
//        mRadius[0] = (int) TypedValue.applyDimension(unit, radiusLeftTop, mDisplayMetrics);
//        mRadius[1] = (int) TypedValue.applyDimension(unit, radiusRightTop, mDisplayMetrics);
//        mRadius[2] = (int) TypedValue.applyDimension(unit, radiusRightBottom, mDisplayMetrics);
//        mRadius[3] = (int) TypedValue.applyDimension(unit, radiusLeftBottom, mDisplayMetrics);
//        return this;
//    }

//    public CommonBackgroundDrawable padding(int unit, int padding) {
//        return padding(unit, padding, padding, padding, padding);
//    }
//
//    public CommonBackgroundDrawable padding(int unit, int paddingLeft, int paddingTop, int
//            paddingRight, int paddingBottom) {
//        mPaddings = new int[4];
////        mPaddings[0] = (int) TypedValue.applyDimension(unit, paddingLeft, mDisplayMetrics);
////        mPaddings[1] = (int) TypedValue.applyDimension(unit, paddingTop, mDisplayMetrics);
////        mPaddings[2] = (int) TypedValue.applyDimension(unit, paddingRight, mDisplayMetrics);
////        mPaddings[3] = (int) TypedValue.applyDimension(unit, paddingBottom, mDisplayMetrics);
//        return this;
//    }

    public CommonBackgroundDrawable colorFill(int colorFill) {
        mColorFill = colorFill;
        return this;
    }

    public CommonBackgroundDrawable colorStroke(int colorStroke) {
        mColorStroke = colorStroke;
        return this;
    }

    public Bitmap bitmap() {
        return mBitmap;
    }

    public CommonBackgroundDrawable bitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = bitmap;
            mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode
                    .CLAMP);
        }
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        drawStroke(canvas);
        drawFill(canvas);
    }

    private void drawStroke(Canvas canvas) {
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setStrokeMiter(5.0f);

        switch (mStrokeMode) {
            case STROKE_MODE_SOLID:
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorStroke);
                mPaint.setStrokeWidth(mStrokeWidth);
                drawStrokeShape(canvas);
                break;
            case STROKE_MODE_DASH:
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setColor(mColorStroke);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setPathEffect(new DashPathEffect(mStrokeDash, 1.0f));
                drawStrokeShape(canvas);
                break;
            case STROKE_MODE_NONE:
            default:
                break;
        }
    }

    private void drawStrokeShape(Canvas canvas) {
        final float narrowBy = mStrokeWidth / 2.0f;
        float strokeRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                strokeRadius = mRadius - mStrokeWidth / 2.0f;
                canvas.drawRoundRect(narrow(mBounds, narrowBy), strokeRadius, strokeRadius, mPaint);
                break;
            case SHAPE_LEFT_CIRCLE_RECT:
                break;
            case SHAPE_RIGHT_CIRCLE_RECT:
                break;
            case SHAPE_BOTH_CIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f;
                strokeRadius = mRadius - mStrokeWidth / 2.0f;
                canvas.drawRoundRect(mBounds, strokeRadius, strokeRadius, mPaint);
                break;
            case SHAPE_CIRCLE:
                mCx = (mBounds.left + mBounds.right) / 2.0f;
                mCy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(mCx, mCy);
                strokeRadius = mRadius - mStrokeWidth / 2.0f;
                canvas.drawCircle(mCx, mCy, strokeRadius, mPaint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(narrow(mBounds, narrowBy), mPaint);
                break;
        }
    }

    private void drawFill(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);

        switch (mFillMode) {
            case FILL_MODE_BITMAP:
                mPaint.setShader(mShader);
                drawFillShape(canvas);
                break;
            case FILL_MODE_SOLID:
            default:
                mPaint.setColor(mColorFill);
                drawFillShape(canvas);
                break;
        }
    }

    private void drawFillShape(Canvas canvas) {
        final float narrowBy = mStrokeWidth - 0.5f;
        float fillRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                fillRadius = mRadius - mStrokeWidth + 0.5f;
                canvas.drawRoundRect(narrow(mBounds, narrowBy), fillRadius, fillRadius, mPaint);
                break;
            case SHAPE_LEFT_CIRCLE_RECT:
                break;
            case SHAPE_RIGHT_CIRCLE_RECT:
                break;
            case SHAPE_BOTH_CIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f;
                fillRadius = mRadius - mStrokeWidth + 0.5f;
                canvas.drawRoundRect(mBounds, fillRadius, fillRadius, mPaint);
                break;
            case SHAPE_CIRCLE:
                mCx = (mBounds.left + mBounds.right) / 2.0f;
                mCy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(mCx, mCy);
                fillRadius = mRadius - mStrokeWidth + 0.5f;
                canvas.drawCircle(mCx, mCy, fillRadius, mPaint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(narrow(mBounds, narrowBy), mPaint);
                break;
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mBounds = new RectF(left, top, right, bottom);
    }

    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
        mBounds = new RectF(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    @Override
    public int getIntrinsicWidth() {
        if (mBitmap != null) {
            return mBitmap.getWidth();
        }
        return super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        if (mBitmap != null) {
            return mBitmap.getHeight();
        }
        return super.getIntrinsicHeight();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    private RectF narrow(RectF src, float by) {
        return new RectF(src.left + by, src.top + by, src.right - by, src.bottom - by);
    }
}
