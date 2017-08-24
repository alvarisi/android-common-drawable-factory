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
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
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
    public static final int SHAPE_RECT = 0; // 矩形
    public static final int SHAPE_ROUND_RECT = 1; // 圆角矩形
    public static final int SHAPE_SEMICIRCLE_RECT = 2; // 圆头矩形
    public static final int SHAPE_CIRCLE = 3; // 圆形

    public static final int FILL_MODE_COLOR = 1; // 纯色填充
    public static final int FILL_MODE_BITMAP = 2; // 图片填充
    public static final int FILL_MODE_LINEAR_GRADIENT = 4; // 线性渐变填充

    public static final int STROKE_MODE_NONE = 0; // 无描边
    public static final int STROKE_MODE_SOLID = 1; // 实线描边
    public static final int STROKE_MODE_DASH = 2; // 断续线描边

    public static final int SCALE_TYPE_CENTER = 0;
    public static final int SCALE_TYPE_CENTER_CROP = 1;
    public static final int SCALE_TYPE_FIT_CENTER = 2;
    public static final int SCALE_TYPE_FIT_XY = 3;

    // user data
    private Bitmap mBitmap;
    private int mShape = SHAPE_RECT;
    private int mFillMode = FILL_MODE_COLOR;
    private int mScaleType = SCALE_TYPE_CENTER;
    private int mStrokeMode = STROKE_MODE_NONE;
    private int mColorFill = Color.TRANSPARENT;
    private int mColorStroke = Color.TRANSPARENT;
    private float mStrokeWidth;       // px
    private float[] mStrokeDash;      // px
    private float mRadius;            // px
    private float[] mRadiusArray;     // px

    // inner data
    private BitmapShader mShader;
    private ColorMatrixColorFilter mColorFilter;
    private final Paint mFillPaint;
    private Paint mStrokePaint;
    private Path mPath;
    private RectF mRect;
    private boolean mIsRectDirty = true;
    private boolean mIsPathDirty = true;
    private boolean mIsFillPaintDirty = true, mIsStrokePaintDirty = true;

    CommonBackground() {
        mRect = new RectF();
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
    }

    /**
     * 显示到你的View上
     *
     * @param yourView 你的View
     */
    @Override
    public void showOn(View yourView) {
        if (yourView != null) {
            yourView.setBackgroundDrawable(this);
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
        markPathDirty();
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
        markFillPaintDirty();
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
        markFillPaintDirty();
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
        markStrokePaintDirty();
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
        markStrokePaintDirty();
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
        markStrokePaintDirty();
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
        markStrokePaintDirty();
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
        markPathDirty();
        return this;
    }

    @Override
    public CommonBackground radius(int radiusLeftTop, int radiusRightTop,
                                   int radiusRightBottom, int radiusLeftBottom) {
        if (radiusLeftTop > 0f || radiusRightTop > 0f ||
                radiusRightBottom > 0f || radiusLeftBottom > 0f) {
            if (mRadiusArray == null) {
                mRadiusArray = new float[8];
            }
            mRadiusArray[0] = mRadiusArray[1] = radiusLeftTop;
            mRadiusArray[2] = mRadiusArray[3] = radiusRightTop;
            mRadiusArray[4] = mRadiusArray[5] = radiusRightBottom;
            mRadiusArray[6] = mRadiusArray[7] = radiusLeftBottom;
        }
        markPathDirty();
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
        markFillPaintDirty();
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
        markFillPaintDirty();
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
        markStrokePaintDirty();
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
        markStrokePaintDirty();
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
        markFillPaintDirty();
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
        // 准备绘制区域
        prepareRect();

        // 先绘制描边，再绘制填充
        prepareFillPaint();
        drawFill(canvas);
        if (needStroke()) {
            prepareStrokePaint();
            drawStroke(canvas);
        }
    }

    /**
     * 准备绘制区域
     */
    private void prepareRect() {
        if (!mIsRectDirty) {
            return;
        }
        mIsRectDirty = false;

        final Rect bounds = getBounds();
        final float inset = mStrokeWidth / 2f;
        mRect.set(bounds);
        mRect.inset(inset, inset);
    }

    /**
     * 是否有描边
     *
     * @return true 需要描边，false 不需要描边
     */
    private boolean needStroke() {
        return (mStrokeMode & STROKE_MODE_SOLID) != 0 || (mStrokeMode & STROKE_MODE_DASH) != 0;
    }

    /**
     * 四个角半径是否相同
     *
     * @return
     */
    private boolean hasSameCorners() {
        return mRadiusArray == null;
    }

    /**
     * 调整画笔至填充模式
     */
    private void prepareFillPaint() {
        if (!mIsFillPaintDirty) {
            return;
        }
        mIsFillPaintDirty = false;

        mFillPaint.setStyle(Paint.Style.FILL);

        if ((mFillMode & FILL_MODE_BITMAP) != 0) {
            if (mBitmap != null) {
                Matrix matrix = new Matrix();
                final float bitmapInset = mStrokeWidth / 2f;
                final float viewWidth = mRect.width() - bitmapInset;
                final float viewHeight = mRect.height() - bitmapInset;
                final float bitmapWidth = mBitmap.getWidth();
                final float bitmapHeight = mBitmap.getHeight();
                float diffX = Math.abs(viewWidth - bitmapWidth);     // 控件与bitmap的宽度差
                float diffY = Math.abs(viewHeight - bitmapHeight);   // 控件与bitmap的高度差

                // 计算缩放
                final float scaleX, scaleY;
                float ratioX = viewWidth / bitmapWidth;
                float ratioY = viewHeight / bitmapHeight;
                switch (mScaleType) {
                    case SCALE_TYPE_CENTER_CROP:
                        if (viewWidth < bitmapWidth && viewHeight < bitmapHeight) {
                            // 控件长宽均比bitmap小，以差值小的边为准
                            scaleX = scaleY = diffX < diffY ? ratioX : ratioY;
                        } else if (viewWidth < bitmapWidth) {
                            scaleX = scaleY = ratioY;
                        } else if (viewHeight < bitmapHeight) {
                            scaleY = scaleX = ratioX;
                        } else {
                            // 控件长宽均比bitmap大，以差值大的边为准
                            scaleX = scaleY = diffX < diffY ? ratioY : ratioX;
                        }
                        break;
                    case SCALE_TYPE_FIT_CENTER:
                        if (viewWidth < bitmapWidth && viewHeight < bitmapHeight) {
                            // 控件长宽均比bitmap小，以差值大的边为准
                            scaleX = scaleY = diffX < diffY ? ratioY : ratioX;
                        } else if (viewWidth < bitmapWidth) {
                            scaleY = scaleX = ratioX;
                        } else if (viewHeight < bitmapHeight) {
                            scaleX = scaleY = ratioY;
                        } else {
                            // 控件长宽均比bitmap大，以差值小的边为准
                            scaleX = scaleY = diffX < diffY ? ratioX : ratioY;
                        }
                        break;
                    case SCALE_TYPE_FIT_XY:
                        scaleX = ratioX;
                        scaleY = ratioY;
                        break;
                    case SCALE_TYPE_CENTER:
                    default:
                        scaleX = scaleY = 1f;
                        break;
                }
                matrix.postScale(scaleX, scaleY);

                // 图片居中
                diffX = viewWidth - bitmapWidth * scaleX;
                diffY = viewHeight - bitmapHeight * scaleY;
                final float translateX = diffX / 2f;
                final float translateY = diffY / 2f;
                matrix.postTranslate(translateX, translateY);

                mShader.setLocalMatrix(matrix);
            }
            mFillPaint.setShader(mShader);
            if ((mFillMode & FILL_MODE_COLOR) != 0) {
                // 如果fillMode == solid|bitmap，则根据设置图片的颜色蒙层
                if (mColorFilter == null) {
                    mColorFilter = new ColorMatrixColorFilter(parseColorMatrix(mColorFill));
                }
                mFillPaint.setColorFilter(mColorFilter);
            }
        } else {
            mFillPaint.setColor(mColorFill);
        }
    }

    /**
     * 绘制填充
     *
     * @param canvas 画板
     */
    private void drawFill(Canvas canvas) {
        switch (mShape) {
            case SHAPE_ROUND_RECT:
                if (hasSameCorners()) {
                    canvas.drawRoundRect(mRect, mRadius, mRadius, mFillPaint);
                } else {
                    preparePath(mRect, mRadiusArray);
                    canvas.drawPath(mPath, mFillPaint);
                }
                break;
            case SHAPE_SEMICIRCLE_RECT:
                mRadius = mRect.height() / 2f;
                canvas.drawRoundRect(mRect, mRadius, mRadius, mFillPaint);
                break;
            case SHAPE_CIRCLE:
                // 计算圆心
                float cx = mRect.width() / 2f;
                float cy = mRect.height() / 2f;
                mRadius = Math.min(cx, cy);
                canvas.drawCircle(cx, cy, mRadius, mFillPaint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(mRect, mFillPaint);
                break;
        }
    }

    /**
     * 调整画笔至描边模式
     */
    private void prepareStrokePaint() {
        // STROKE_MODE_NONE
        if (mStrokeMode == STROKE_MODE_NONE) {
            return;
        }

        if (!mIsStrokePaintDirty) {
            return;
        }
        mIsStrokePaintDirty = false;

        if (mStrokePaint == null) {
            mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        // STROKE_MODE_SOLID or STROKE_MODE_DASH
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mColorStroke);
        mStrokePaint.setShader(null);
        mStrokePaint.setColorFilter(null);
        mStrokePaint.setStrokeWidth(mStrokeWidth);

        // STROKE_MODE_DASH
        if (mStrokeMode == STROKE_MODE_DASH) {
            if (mStrokePaint.getPathEffect() == null) {
                mStrokePaint.setPathEffect(new DashPathEffect(mStrokeDash, 1f));
            }
        }
    }

    /**
     * 绘制描边
     *
     * @param canvas 画板
     */
    private void drawStroke(Canvas canvas) {
        switch (mShape) {
            case SHAPE_ROUND_RECT:
                if (hasSameCorners()) {
                    canvas.drawRoundRect(mRect, mRadius, mRadius, mStrokePaint);
                } else {
                    preparePath(mRect, mRadiusArray);
                    canvas.drawPath(mPath, mStrokePaint);
                }
                break;
            case SHAPE_SEMICIRCLE_RECT:
                mRadius = mRect.height() / 2f; // 计算半径
                canvas.drawRoundRect(mRect, mRadius, mRadius, mStrokePaint);
                break;
            case SHAPE_CIRCLE:
                // 计算圆心
                float cx = mRect.width() / 2f;
                float cy = mRect.height() / 2f;
                mRadius = Math.min(cx, cy);
                canvas.drawCircle(cx, cy, mRadius, mStrokePaint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(mRect, mStrokePaint);
                break;
        }
    }

    private void preparePath(RectF rect, float[] radius) {
        if (!mIsPathDirty) {
            return;
        }
        mIsPathDirty = false;

        mPath.reset();
        mPath.addRoundRect(rect, radius, Path.Direction.CW);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        markRectDirty();
        markPathDirty();
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
        markRectDirty();
        markPathDirty();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        markRectDirty();
        markPathDirty();
    }

    @Override
    protected boolean onLevelChange(int level) {
        markRectDirty();
        markPathDirty();
        return super.onLevelChange(level);
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
        mFillPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mFillPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    /**
     * 根据颜色解析蒙层的ColorMatrix
     *
     * @param color 蒙层所需颜色
     * @return 蒙层的ColorMatrix
     */
    private ColorMatrix parseColorMatrix(int color) {
        float r = Color.red(color) / 255f;
        float g = Color.green(color) / 255f;
        float b = Color.blue(color) / 255f;
        float a = Color.alpha(color) / 255f;
        return new ColorMatrix(new float[]{
                r, 0, 0, 0, 0,
                0, g, 0, 0, 0,
                0, 0, b, 0, 0,
                0, 0, 0, a, 0
        });
    }

    /**
     * 标记rect脏位
     */
    private void markRectDirty() {
        mIsRectDirty = true;
    }

    /**
     * 标记path脏位
     */
    private void markPathDirty() {
        mIsPathDirty = true;
    }

    /**
     * 标记填充paint脏位
     */
    private void markFillPaintDirty() {
        mIsFillPaintDirty = true;
    }

    /**
     * 标记描边paint脏位
     */
    private void markStrokePaintDirty() {
        mIsStrokePaintDirty = true;
    }
}
