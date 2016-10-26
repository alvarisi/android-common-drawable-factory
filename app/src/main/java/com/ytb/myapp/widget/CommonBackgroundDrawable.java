package com.ytb.myapp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

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

    private Canvas mCanvasFill;
    private Paint mPaintFill;
    private Paint mPaintStroke;
    private Bitmap mBitmap;
    private BitmapShader mShader;
//    private DisplayMetrics mDisplayMetrics;
    private int mShape;
    private int mFillMode;
    private int mStrokeMode;
    private RectF mBounds;
    private int mColorFill;
    private int mColorStroke;
    private int mStrokeWidth;       // px
    private int mStrokeDashSpace;   // px
    private int mRadius;            // px
    private int[] mPaddings;        // px

    public CommonBackgroundDrawable() {
        mCanvasFill = new Canvas();
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

    public CommonBackgroundDrawable strokeDashSpace(int strokeDashSpace) {
        mStrokeDashSpace = strokeDashSpace;
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

    public CommonBackgroundDrawable padding(int unit, int padding) {
        return padding(unit, padding, padding, padding, padding);
    }

    public CommonBackgroundDrawable padding(int unit, int paddingLeft, int paddingTop, int
            paddingRight, int paddingBottom) {
        mPaddings = new int[4];
//        mPaddings[0] = (int) TypedValue.applyDimension(unit, paddingLeft, mDisplayMetrics);
//        mPaddings[1] = (int) TypedValue.applyDimension(unit, paddingTop, mDisplayMetrics);
//        mPaddings[2] = (int) TypedValue.applyDimension(unit, paddingRight, mDisplayMetrics);
//        mPaddings[3] = (int) TypedValue.applyDimension(unit, paddingBottom, mDisplayMetrics);
        return this;
    }

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
        mBitmap = bitmap;
        mPaintFill.setShader(new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode
                .CLAMP));
        return this;
    }

    @Override
    public void draw(Canvas canvasStroke) {
        drawFill(mCanvasFill);
        drawStroke(canvasStroke);
    }

    private void drawFill(Canvas canvasFill) {
        if (mPaintFill == null) {
            mPaintFill = new Paint();
            mPaintFill.setAntiAlias(true);
        }
        if (mShader == null && mBitmap != null) {
            mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }

        initPaintFill();

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                drawFillRoundRect(canvasFill);
                break;
            case SHAPE_LEFT_CIRCLE_RECT:
                break;
            case SHAPE_RIGHT_CIRCLE_RECT:
                break;
            case SHAPE_BOTH_CIRCLE_RECT:
                drawFillBothCircle(canvasFill);
                break;
            case SHAPE_CIRCLE:
                drawFillCircle(canvasFill);
                break;
            case SHAPE_RECT:
            default:
                break;
        }
    }

    private void drawFillRoundRect(Canvas canvasFill) {
        canvasFill.drawRoundRect(mBounds, mRadius, mRadius, mPaintFill);
    }

    private void drawFillBothCircle(Canvas canvasFill) {
        float radius = (mBounds.top + mBounds.bottom) / 2.0f;
        canvasFill.drawRoundRect(mBounds, radius, radius, mPaintFill);
    }

    private void drawFillCircle(Canvas canvasFill) {
        float cx = (mBounds.left + mBounds.right) / 2.0f;
        float cy = (mBounds.top + mBounds.bottom) / 2.0f;
        canvasFill.drawCircle(cx, cy, mRadius, mPaintFill);
    }

    private void drawStroke(Canvas canvasStroke) {
        if (mPaintStroke == null) {
            mPaintStroke = new Paint();
            mPaintStroke.setAntiAlias(true);
            mPaintStroke.setStyle(Paint.Style.STROKE);
            mPaintStroke.setColor(mColorStroke);
        }

        switch (mStrokeMode) {
            case STROKE_MODE_SOLID:
                break;
            case STROKE_MODE_DASH:
                break;
            case STROKE_MODE_NONE:
            default:
                break;
        }
    }

    private void initPaintFill() {
        switch (mFillMode) {
            case FILL_MODE_BITMAP:
                mPaintFill.setShader(mShader);
                break;
            case FILL_MODE_SOLID:
            default:
                mPaintFill.setStyle(Paint.Style.FILL);
                mPaintFill.setColor(mColorFill);
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
    public void setAlpha(int alpha) {
        mPaintFill.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaintFill.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
