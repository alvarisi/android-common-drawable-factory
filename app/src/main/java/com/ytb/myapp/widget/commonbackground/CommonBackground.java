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

    public static final int FILL_MODE_SOLID = 1; // 纯色填充
    public static final int FILL_MODE_BITMAP = 2; // 图片填充

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
    private int mFillMode = FILL_MODE_SOLID;
    private int mScaleType = SCALE_TYPE_CENTER;
    private int mStrokeMode = STROKE_MODE_NONE;
    private int mColorFill = Color.TRANSPARENT;
    private int mColorStroke = Color.TRANSPARENT;
    private float mStrokeWidth;       // px
    private float[] mStrokeDash;      // px
    private float mRadius;            // px
    private float[] mRadiusEach;      // px

    // inner data
    private BitmapShader mShader;
    private ColorMatrixColorFilter mColorFilter;
    private Paint mPaint;
    private Path mPath;
    private RectF mBounds;
    private boolean mIsBoundsDirty;

    CommonBackground() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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

    @Override
    public CommonBackground radius(int radiusLeftTop, int radiusRightTop,
                                   int radiusRightBottom, int radiusLeftBottom) {
        if (radiusLeftTop > 0f || radiusRightTop > 0f ||
                radiusRightBottom > 0f || radiusLeftBottom > 0f) {
            if (mRadiusEach == null) {
                mRadiusEach = new float[4];
            }
            mRadiusEach[0] = radiusLeftTop;
            mRadiusEach[1] = radiusRightTop;
            mRadiusEach[2] = radiusRightBottom;
            mRadiusEach[3] = radiusLeftBottom;
        }
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
        // 先绘制描边，再绘制填充
        prepareFillPaint(mPaint);
        drawFill(canvas, mPaint);
        if (needStroke()) {
            prepareStrokePaint(mPaint);
            drawStroke(canvas, mPaint);
        }
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
        return mRadiusEach == null || (mRadiusEach[0] == 0f && mRadiusEach[1] == 0f &&
                mRadiusEach[2] == 0f && mRadiusEach[3] == 0f);
    }

    /**
     * 调整画笔至描边模式
     */
    private void prepareStrokePaint(Paint paint) {
        // STROKE_MODE_NONE
        if (mStrokeMode == STROKE_MODE_NONE) {
            return;
        }

        // STROKE_MODE_SOLID or STROKE_MODE_DASH
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mColorStroke);
        paint.setShader(null);
        paint.setColorFilter(null);
        paint.setStrokeWidth(mStrokeWidth);
//        paint.setStrokeJoin(mRadius > 0f || !hasSameCorners() ? Paint.Join.ROUND :
//                Paint.Join.MITER);
//        paint.setDither(true);

        // STROKE_MODE_DASH
        if (mStrokeMode == STROKE_MODE_DASH) {
            if (paint.getPathEffect() == null) {
                paint.setPathEffect(new DashPathEffect(mStrokeDash, 1.0f));
            }
        }
    }

    /**
     * 绘制描边
     *
     * @param canvas 画板
     */
    private void drawStroke(Canvas canvas, Paint paint) {
        final float narrowBy = mStrokeWidth / 2.0f; // 绘图半径需缩小mStrokeWidth / 2
        final float strokeRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                if (hasSameCorners()) {
                    strokeRadius = mRadius - narrowBy;
                    canvas.drawRoundRect(inset(mBounds, narrowBy),
                            strokeRadius, strokeRadius, paint);
                } else {
//                    resetPath(mBounds.width(), mBounds.height(), mStrokeWidth, true);
                    resetPath1(mPath, inset(mBounds, narrowBy), mRadiusEach);
                    canvas.drawPath(mPath, paint);
                }
                break;
            case SHAPE_SEMICIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f; // 计算半径
                strokeRadius = mRadius - narrowBy;
                canvas.drawRoundRect(inset(mBounds, narrowBy), strokeRadius, strokeRadius,
                        paint);
                break;
            case SHAPE_CIRCLE:
                // 计算圆心
                float cx = (mBounds.left + mBounds.right) / 2.0f;
                float cy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(cx, cy);
                strokeRadius = mRadius - narrowBy;
                canvas.drawCircle(cx, cy, strokeRadius, paint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(inset(mBounds, narrowBy), paint);
                break;
        }
    }

    /**
     * 调整画笔至填充模式
     */
    private void prepareFillPaint(Paint paint) {
        paint.setStyle(Paint.Style.FILL);

        if ((mFillMode & FILL_MODE_BITMAP) != 0) {
            if (mBitmap != null) {
                Matrix matrix = new Matrix();
                final float viewWidth = mBounds.right - mBounds.left;
                final float viewHeight = mBounds.bottom - mBounds.top;
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
                        scaleX = scaleY = 1.0f;
                        break;
                }
                matrix.postScale(scaleX, scaleY);

                // 图片居中
                diffX = viewWidth - bitmapWidth * scaleX;
                diffY = viewHeight - bitmapHeight * scaleY;
                final float translateX = diffX / 2.0f;
                final float translateY = diffY / 2.0f;
                matrix.postTranslate(translateX, translateY);

                mShader.setLocalMatrix(matrix);
            }
            paint.setShader(mShader);
            if ((mFillMode & FILL_MODE_SOLID) != 0) {
                // 如果fillMode == solid|bitmap，则根据设置图片的颜色蒙层
                if (mColorFilter == null) {
                    mColorFilter = new ColorMatrixColorFilter(parseColorMatrix(mColorFill));
                }
                paint.setColorFilter(mColorFilter);
            }
        } else {
            paint.setColor(mColorFill);
        }
    }

    /**
     * 绘制填充
     *
     * @param canvas 画板
     */
    private void drawFill(Canvas canvas, Paint paint) {
        final float narrowBy = /*((mFillMode & FILL_MODE_BITMAP) == 0 && mStrokeWidth > 1.0f) ?
                (mStrokeWidth - 0.5f) : */mStrokeWidth / 2f; // 当非图片填充时（即仅纯色填充时），-0.5调整误差
        float fillRadius;

        switch (mShape) {
            case SHAPE_ROUND_RECT:
                if (hasSameCorners()) {
                    fillRadius = mRadius - narrowBy;
                    canvas.drawRoundRect(mBounds/*inset(mBounds, narrowBy)*/, fillRadius, fillRadius,
                            paint);
                } else {
//                    resetPath(mBounds.width(), mBounds.height(), mStrokeWidth, false);
                    resetPath1(mPath, inset(mBounds, narrowBy), mRadiusEach);
                    canvas.drawPath(mPath, paint);
                }
                break;
            case SHAPE_SEMICIRCLE_RECT:
                mRadius = (mBounds.top + mBounds.bottom) / 2.0f;
                fillRadius = mRadius - narrowBy;
                canvas.drawRoundRect(inset(mBounds, narrowBy), fillRadius, fillRadius,
                        paint);
                break;
            case SHAPE_CIRCLE:
                // 计算圆心
                float cx = (mBounds.left + mBounds.right) / 2.0f;
                float cy = (mBounds.top + mBounds.bottom) / 2.0f;
                mRadius = Math.min(cx, cy);
                fillRadius = mRadius - narrowBy;
                canvas.drawCircle(cx, cy, fillRadius, paint);
                break;
            case SHAPE_RECT:
            default:
                canvas.drawRect(inset(mBounds, narrowBy), paint);
                break;
        }
    }

    /**
     * 绘制路径。路径会沿着描边的中线走。
     *
     * @param width       绘制区域宽度
     * @param height      绘制区域高度
     * @param strokeWidth 描边宽度
     */
    private void resetPath(float width, float height, float strokeWidth, boolean isStroke) {
        final float pathPadding = isStroke ? strokeWidth / 2f : strokeWidth;
        final RectF corner = new RectF();
        mPath.reset();
        float cornerRadius;

        // 先移动出发点至左边中央
        mPath.moveTo(pathPadding, height / 2f);

        // 左上角
        cornerRadius = mRadiusEach[0];
        if (cornerRadius > 0f) {
            corner.set(pathPadding, pathPadding, cornerRadius, cornerRadius);
            mPath.lineTo(corner.left, corner.bottom);
            mPath.arcTo(corner, 180f, 90f);
        } else {
            mPath.lineTo(pathPadding, pathPadding);
        }

        // 右上角
        cornerRadius = mRadiusEach[1];
        if (cornerRadius > 0f) {
            corner.set(width - pathPadding, pathPadding, width - pathPadding, cornerRadius);
            mPath.lineTo(corner.left, corner.top);
            mPath.arcTo(corner, 270f, 90f);
        } else {
            mPath.lineTo(width - pathPadding, pathPadding);
        }

        // 右下角
        cornerRadius = mRadiusEach[2];
        if (cornerRadius > 0f) {
            corner.set(width - cornerRadius, height - cornerRadius,
                    width - pathPadding, height - pathPadding);
            mPath.lineTo(corner.right, corner.top);
            mPath.arcTo(corner, 0f, 90f);
        } else {
            mPath.lineTo(width - pathPadding, height - pathPadding);
        }

        // 左下角
        cornerRadius = mRadiusEach[3];
        if (cornerRadius > 0f) {
            corner.set(pathPadding, height - cornerRadius, cornerRadius, height - pathPadding);
            mPath.lineTo(corner.right, corner.bottom);
            mPath.arcTo(corner, 90f, 90f);
        } else {
            mPath.lineTo(pathPadding, height - pathPadding);
        }

        // 回到出发点
        mPath.lineTo(pathPadding, height / 2f);
    }

    private void resetPath1(Path path, RectF rect, float[] radius) {
        path.reset();
        path.addRoundRect(rect, new float[] {
                radius[0], radius[0],
                radius[1], radius[1],
                radius[2], radius[2],
                radius[3], radius[3]
        }, Path.Direction.CW);
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
    private RectF inset(RectF src, float by) {
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
