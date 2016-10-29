package com.ytb.myapp.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016-10-25.
 */

public class CommonBackground extends Drawable {
    public static final int SHAPE_RECT = 0;
    public static final int SHAPE_ROUND_RECT = 1;
    public static final int SHAPE_LEFT_CIRCLE_RECT = 2;
    public static final int SHAPE_RIGHT_CIRCLE_RECT = 3;
    public static final int SHAPE_BOTH_CIRCLE_RECT = 4;
    public static final int SHAPE_CIRCLE = 5;

    public static final int FILL_MODE_SOLID = 1;
    public static final int FILL_MODE_BITMAP = 2;

    public static final int STROKE_MODE_NONE = 0;
    public static final int STROKE_MODE_SOLID = 1;
    public static final int STROKE_MODE_DASH = 2;

    // user data
    private Bitmap mBitmap;
    private BitmapShader mShader;
    private ColorMatrixColorFilter mColorFilter;
    private int mShape;
    private int mFillMode;
    private int mStrokeMode;
    private int mColorFill = Color.WHITE;
    private int mColorStroke = Color.TRANSPARENT;
    private float mStrokeWidth;       // px
    private float[] mStrokeDash;      // px
    private float mRadius;            // px

    // inner data
    private Paint mPaint;
    private RectF mBounds;
    private float mCx, mCy;

    CommonBackground() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public CommonBackground shape(int shape) {
        mShape = shape;
        return this;
    }

    public CommonBackground fillMode(int fillMode) {
        mFillMode = fillMode;
        return this;
    }

    public CommonBackground strokeMode(int strokeMode) {
        mStrokeMode = strokeMode;
        return this;
    }

    public CommonBackground strokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    public CommonBackground strokeDashSolid(int strokeDashSolid) {
        if (mStrokeDash == null) {
            mStrokeDash = new float[2];
        }
        mStrokeDash[0] = strokeDashSolid;
        return this;
    }

    public CommonBackground strokeDashSpace(int strokeDashSpace) {
        if (mStrokeDash == null) {
            mStrokeDash = new float[2];
        }
        mStrokeDash[1] = strokeDashSpace;
        return this;
    }

    public CommonBackground radius(int radius) {
        mRadius = radius;
        return this;
    }

    public CommonBackground colorFill(int colorFill) {
        mColorFill = colorFill;
        return this;
    }

    public CommonBackground colorStroke(int colorStroke) {
        mColorStroke = colorStroke;
        return this;
    }

    public Bitmap bitmap() {
        return mBitmap;
    }

    public CommonBackground bitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = bitmap;
            mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        drawStroke(canvas);
        drawFill(canvas);
    }

    private void drawStroke(Canvas canvas) {
        // STROKE_MODE_NONE
        if (mStrokeMode == STROKE_MODE_NONE) {
            return;
        }

        // STROKE_MODE_DASH or STROKE_MODE_SOLID
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColorStroke);
        mPaint.setShader(null);
        mPaint.setColorFilter(null);
        mPaint.setStrokeWidth(mStrokeWidth);
        if (mStrokeMode == STROKE_MODE_DASH) {
            if (mPaint.getPathEffect() == null) {
                mPaint.setPathEffect(new DashPathEffect(mStrokeDash, 1.0f));
            }
        }
        drawStrokeShape(canvas);
    }

    private void drawStrokeShape(Canvas canvas) {
        final float narrowBy = mStrokeWidth / 2.0f; // 绘图半径需缩小mStrokeWidth/2
        final float strokeRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                strokeRadius = mRadius - narrowBy;
                canvas.drawRoundRect(narrow(mBounds, narrowBy), strokeRadius, strokeRadius, mPaint);
                break;
            case SHAPE_LEFT_CIRCLE_RECT:
                break;
            case SHAPE_RIGHT_CIRCLE_RECT:
                break;
            case SHAPE_BOTH_CIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f; // 自动计算半径
                strokeRadius = mRadius - narrowBy;
                canvas.drawRoundRect(mBounds, strokeRadius, strokeRadius, mPaint);
                break;
            case SHAPE_CIRCLE:
                mCx = (mBounds.left + mBounds.right) / 2.0f;
                mCy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(mCx, mCy);
                strokeRadius = mRadius - narrowBy;
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

        if ((mFillMode & FILL_MODE_BITMAP) != 0) {
            mPaint.setShader(mShader);
            if ((mFillMode & FILL_MODE_SOLID) != 0) {
                if (mColorFilter == null) {
                    mColorFilter = new ColorMatrixColorFilter(parseColorMatrix());
                }
                mPaint.setColorFilter(mColorFilter);
            }
        } else {
            mPaint.setColor(mColorFill);
        }
        drawFillShape(canvas);
    }

    private ColorMatrix parseColorMatrix() {
        float r = Color.red(mColorFill) / 255f;
        float g = Color.green(mColorFill) / 255f;
        float b = Color.blue(mColorFill) / 255f;
        float a = Color.alpha(mColorFill) / 255f;
        return new ColorMatrix(new float[]{
                r, 0, 0, 0, 0,
                0, g, 0, 0, 0,
                0, 0, b, 0, 0,
                0, 0, 0, a, 0
        });
    }

    private void drawFillShape(Canvas canvas) {
        final float narrowBy = mStrokeWidth - 0.5f;
        float fillRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                fillRadius = mRadius - narrowBy;
                canvas.drawRoundRect(narrow(mBounds, narrowBy), fillRadius, fillRadius, mPaint);
                break;
            case SHAPE_LEFT_CIRCLE_RECT:
                break;
            case SHAPE_RIGHT_CIRCLE_RECT:
                break;
            case SHAPE_BOTH_CIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f;
                fillRadius = mRadius - narrowBy;
                canvas.drawRoundRect(mBounds, fillRadius, fillRadius, mPaint);
                break;
            case SHAPE_CIRCLE:
                mCx = (mBounds.left + mBounds.right) / 2.0f;
                mCy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(mCx, mCy);
                fillRadius = mRadius - narrowBy;
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
