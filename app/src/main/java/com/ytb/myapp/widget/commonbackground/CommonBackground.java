package com.ytb.myapp.widget.commonbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * 通用背景Drawable
 *
 * @author yintaibing
 * @date 2016/10/28
 */
public class CommonBackground extends Drawable implements ICommonBackground {
    public static final int SHAPE_RECT = 0;             // 矩形
    public static final int SHAPE_ROUND_RECT = 1;       // 圆角矩形
    public static final int SHAPE_SIDE_CIRCLE_RECT = 2; // 圆头矩形
    public static final int SHAPE_CIRCLE = 3;           // 圆形

    public static final int FILL_MODE_SOLID = 1;        // 纯色填充
    public static final int FILL_MODE_BITMAP = 2;       // 图片填充

    public static final int SCALE_TYPE_CENTER = 0;        // 无缩放
    public static final int SCALE_TYPE_CENTER_CROP = 1;     // 以控件较长边为准进行缩放
    public static final int SCALE_TYPE_FIT_CENTER = 2;    // 以控件较短边为准进行缩放
    public static final int SCALE_TYPE_FIT_XY = 3;      // fitXY

    public static final int STROKE_MODE_NONE = 0;       // 无描边
    public static final int STROKE_MODE_SOLID = 1;      // 实线描边
    public static final int STROKE_MODE_DASH = 2;       // 断续线描边

    // user data
    private Bitmap mBitmap;
    private int mShape;
    private int mFillMode;
    private int mScaleType;
    private int mStrokeMode;
    private int mColorFill = Color.WHITE;
    private int mColorStroke = Color.TRANSPARENT;
    private float mStrokeWidth;       // px
    private float[] mStrokeDash;      // px
    private float mRadius;            // px

    // inner data
    private BitmapShader mShader;
    private ColorMatrixColorFilter mColorFilter;
    private Paint mPaint;
    private RectF mBounds;

    CommonBackground() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    /**
     * 显示到你的View上
     *
     * @param yourView 你的View
     */
    @Override
    public void showOn(View yourView) {
        if (yourView != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                yourView.setBackground(this);
            } else {
                yourView.setBackgroundDrawable(this);
            }
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
    public CommonBackground shape(int shape) {
        mShape = shape;
        return this;
    }

    /**
     * 设置填充模式
     *
     * @param fillMode 填充模式
     * @return this
     */
    @Override
    public CommonBackground fillMode(int fillMode) {
        mFillMode = fillMode;
        return this;
    }

    /**
     * 设置缩放类型
     *
     * @param scaleType 缩放类型
     * @return this
     */
    @Override
    public CommonBackground scaleType(int scaleType) {
        mScaleType = scaleType;
        return this;
    }

    /**
     * 设置描边模式
     *
     * @param strokeMode 描边模式
     * @return this
     */
    @Override
    public CommonBackground strokeMode(int strokeMode) {
        mStrokeMode = strokeMode;
        return this;
    }

    /**
     * 设置描边宽度
     *
     * @param strokeWidth 设置描边宽度
     * @return this
     */
    @Override
    public CommonBackground strokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
        return this;
    }

    /**
     * 设置虚线描边时，单个实线的长度
     *
     * @param strokeDashSolid 单个实线的长度
     * @return this
     */
    @Override
    public CommonBackground strokeDashSolid(int strokeDashSolid) {
        if (mStrokeDash == null) {
            mStrokeDash = new float[2];
        }
        mStrokeDash[0] = strokeDashSolid;
        return this;
    }

    /**
     * 设置虚线描边时，单个空白的长度
     *
     * @param strokeDashSpace 单个空白的长度
     * @return this
     */
    @Override
    public CommonBackground strokeDashSpace(int strokeDashSpace) {
        if (mStrokeDash == null) {
            mStrokeDash = new float[2];
        }
        mStrokeDash[1] = strokeDashSpace;
        return this;
    }

    /**
     * 设置圆角或圆形的半径
     *
     * @param radius 圆角或圆形的半径
     * @return this
     */
    @Override
    public CommonBackground radius(int radius) {
        mRadius = radius;
        return this;
    }

    /**
     * 设置填充颜色
     *
     * @param colorFill 填充颜色
     * @return this
     */
    @Override
    public CommonBackground colorFill(int colorFill) {
        mColorFill = colorFill;
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
    public ICommonBackground colorFill(Context context, int colorFillResId) {
        mColorFill = ContextCompat.getColor(context, colorFillResId);
        return this;
    }

    /**
     * 设置描边颜色
     *
     * @param colorStroke 描边颜色
     * @return this
     */
    @Override
    public CommonBackground colorStroke(int colorStroke) {
        mColorStroke = colorStroke;
        return this;
    }

    /**
     * 设置描边颜色（通过颜色资源ID）
     *
     * @param context          Context
     * @param colorStrokeResId 描边颜色资源ID
     * @return this
     */
    @Override
    public ICommonBackground colorStroke(Context context, int colorStrokeResId) {
        mColorStroke = ContextCompat.getColor(context, colorStrokeResId);
        return this;
    }

    /**
     * 设置填充位图
     *
     * @param bitmap 填充位图
     * @return this
     */
    @Override
    public CommonBackground bitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = bitmap;
            mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        return this;
    }

    /**
     * 获取填充位图
     *
     * @return 填充位图
     */
    @SuppressWarnings("unused")
    public Bitmap bitmap() {
        return mBitmap;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // 先绘制描边，再缩小边界，绘制填充
        drawStroke(canvas);
        drawFill(canvas);
    }

    /**
     * 绘制描边（进调整画笔）
     *
     * @param canvas 画板
     */
    private void drawStroke(Canvas canvas) {
        // STROKE_MODE_NONE
        if (mStrokeMode == STROKE_MODE_NONE) {
            return;
        }

        // STROKE_MODE_SOLID or STROKE_MODE_DASH
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColorStroke);
        mPaint.setShader(null);
        mPaint.setColorFilter(null);
        mPaint.setStrokeWidth(mStrokeWidth);
        // STROKE_MODE_DASH
        if (mStrokeMode == STROKE_MODE_DASH) {
            if (mPaint.getPathEffect() == null) {
                mPaint.setPathEffect(new DashPathEffect(mStrokeDash, 1.0f));
            }
        }
        drawStrokeShape(canvas);
    }

    /**
     * 绘制描边（调整画笔后，实际绘制出形状）
     *
     * @param canvas 画板
     */
    private void drawStrokeShape(Canvas canvas) {
        final float narrowBy = mStrokeWidth / 2.0f; // 绘图半径需缩小mStrokeWidth / 2
        final float strokeRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                strokeRadius = mRadius - narrowBy;
                canvas.drawRoundRect(narrowBounds(mBounds, narrowBy), strokeRadius, strokeRadius,
                        mPaint);
                break;
            case SHAPE_SIDE_CIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f; // 自动计算半径
                strokeRadius = mRadius - narrowBy;
                canvas.drawRoundRect(narrowBounds(mBounds, narrowBy), strokeRadius, strokeRadius,
                        mPaint);
                break;
            case SHAPE_CIRCLE:
                // 计算圆心
                float cx = (mBounds.left + mBounds.right) / 2.0f;
                float cy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(cx, cy);
                strokeRadius = mRadius - narrowBy;
                canvas.drawCircle(cx, cy, strokeRadius, mPaint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(narrowBounds(mBounds, narrowBy), mPaint);
                break;
        }
    }

    /**
     * 绘制填充（仅调整画笔）
     *
     * @param canvas 画板
     */
    private void drawFill(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);

        if ((mFillMode & FILL_MODE_BITMAP) != 0) {
            if (mBitmap != null) {
                Matrix matrix = new Matrix();
                final float viewWidth = mBounds.right - mBounds.left;
                final float viewHeight = mBounds.bottom - mBounds.top;
                float diffX = Math.abs(viewWidth - mBitmap.getWidth());     // 控件与bitmap的宽度差
                float diffY = Math.abs(viewHeight - mBitmap.getHeight());   // 控件与bitmap的高度差

                // 设置缩放
                float scaleX, scaleY;
                switch (mScaleType) {
                    case SCALE_TYPE_CENTER_CROP:
                        if (viewWidth < mBitmap.getWidth() && viewHeight < mBitmap.getHeight()) {
                            // 控件长宽均比bitmap小，以差值小的边为准
                            scaleX = scaleY = diffX < diffY ? viewWidth / mBitmap.getWidth() :
                                    viewHeight / mBitmap.getHeight();
                        } else if (viewWidth < mBitmap.getWidth()) {
                            scaleX = scaleY = viewHeight / mBitmap.getHeight();
                        } else if (viewHeight < mBitmap.getHeight()) {
                            scaleX = scaleY = viewWidth / mBitmap.getWidth();
                        } else {
                            // 控件长宽均比bitmap大，以差值大的边为准
                            scaleX = scaleY = diffX < diffY ? viewHeight / mBitmap.getHeight() :
                                    viewWidth / mBitmap.getWidth();
                        }
                        break;
                    case SCALE_TYPE_FIT_CENTER:
                        if (viewWidth < mBitmap.getWidth() && viewHeight < mBitmap.getHeight()) {
                            // 控件长宽均比bitmap小，以差值大的边为准
                            scaleX = scaleY = diffX < diffY ? viewHeight / mBitmap.getHeight() :
                                    viewWidth / mBitmap.getWidth();
                        } else if (viewWidth < mBitmap.getWidth()) {
                            scaleX = scaleY = viewWidth / mBitmap.getWidth();
                        } else if (viewHeight < mBitmap.getHeight()) {
                            scaleX = scaleY = viewHeight / mBitmap.getHeight();
                        } else {
                            // 控件长宽均比bitmap大，以差值小的边为准
                            scaleX = scaleY = diffX < diffY ? viewWidth / mBitmap.getWidth() :
                                    viewHeight / mBitmap.getHeight();
                        }
                        break;
                    case SCALE_TYPE_FIT_XY:
                        scaleX = viewWidth / mBitmap.getWidth();
                        scaleY = viewHeight / mBitmap.getHeight();
                        break;
                    case SCALE_TYPE_CENTER:
                    default:
                        scaleX = scaleY = 1.0f;
                        break;
                }
                matrix.postScale(scaleX, scaleY);

                // 图片居中
                diffX = viewWidth - mBitmap.getWidth() * scaleX;
                diffY = viewHeight - mBitmap.getHeight() * scaleY;
                final float translateX, translateY;
//                if (scaleX != 1.0f || scaleY != 1.0f) {
//                    // 如果图片进行了缩放，图片的尺寸就一定>=控件的尺寸，平移量肯定均为正
//                    translateX = Math.abs(diffX) / 2.0f;
//                    translateY = Math.abs(diffY) / 2.0f;
//                } else {
                translateX = diffX / 2.0f;
                translateY = diffY / 2.0f;
//                }
                matrix.postTranslate(translateX, translateY);

                mShader.setLocalMatrix(matrix);
            }
            mPaint.setShader(mShader);
            if ((mFillMode & FILL_MODE_SOLID) != 0) {
                // 如果fillMode == solid|bitmap，则根据设置图片的颜色蒙层
                if (mColorFilter == null) {
                    mColorFilter = new ColorMatrixColorFilter(parseColorMatrix(mColorFill));
                }
                mPaint.setColorFilter(mColorFilter);
            }
        } else {
            mPaint.setColor(mColorFill);
        }
        drawFillShape(canvas);
    }

    /**
     * 绘制填充（调整画笔后，实际绘制出填充形状）
     *
     * @param canvas 画板
     */
    private void drawFillShape(Canvas canvas) {
        final float narrowBy = mStrokeWidth > 0.5f ? (mStrokeWidth - 0.5f) : 0; // -0.5是调整误差
        float fillRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                fillRadius = mRadius - narrowBy;
                canvas.drawRoundRect(narrowBounds(mBounds, narrowBy), fillRadius, fillRadius,
                        mPaint);
                break;
            case SHAPE_SIDE_CIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f;
                fillRadius = mRadius - narrowBy;
                canvas.drawRoundRect(narrowBounds(mBounds, narrowBy), fillRadius, fillRadius,
                        mPaint);
                break;
            case SHAPE_CIRCLE:
                // 计算圆心
                float cx = (mBounds.left + mBounds.right) / 2.0f;
                float cy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(cx, cy);
                fillRadius = mRadius - narrowBy;
                canvas.drawCircle(cx, cy, fillRadius, mPaint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(narrowBounds(mBounds, narrowBy), mPaint);
                break;
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mBounds = new RectF(left, top, right, bottom);
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
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

    /**
     * 缩小边界
     *
     * @param src 原边界大小
     * @param by  缩小的距离
     * @return 缩小后得到的边界
     */
    private RectF narrowBounds(RectF src, float by) {
        return new RectF(src.left + by, src.top + by, src.right - by, src.bottom - by);
    }

    /**
     * 根据颜色解析蒙层的ColorMatrix
     *
     * @param color 蒙层所需颜色
     * @return 蒙层的ColorMatrix
     */
    private ColorMatrix parseColorMatrix(int color) {
        float r = Color.red(color) / 255.0f;
        float g = Color.green(color) / 255.0f;
        float b = Color.blue(color) / 255.0f;
        float a = Color.alpha(color) / 255.0f;
        return new ColorMatrix(new float[]{
                r, 0, 0, 0, 0,
                0, g, 0, 0, 0,
                0, 0, b, 0, 0,
                0, 0, 0, a, 0
        });
    }
}
