# Deprecated
This project is deprecated, plz go to my another project below.
https://github.com/yintaibing/UniversalDrawable

# Project
Android通用控件背景Drawable，支持多种形状、多种状态、多种描边和填充模式。旨在减少书写drawable.xml，提高代码重用。
Android common-background drawable, supports multi shapes, multi states, multi stroke mode, and multi fill mode. Aiming to reduce drawable.xml, and increase reusability.

# Preview
![image](https://github.com/yintaibing/CommonBackgroundWidget/blob/master/screenshot/preview.png)

# Compile
gradle
```groovy
compile "com.ytb:commonbackground:1.0.1"
```

# Usage
- Usage in XML(see all configurable attributes in attrs.xml below
```xml
<com.ytb.commonbackground.CommonBackgroundXXX
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="5dp"
            android:gravity="center"
            android:padding="5dp"
            android:text="solid|bitmap\n(click me)"
            app:bg_bitmap="@mipmap/ic_launcher"
            app:bg_colorNormal="#FFFFFFFF"
            app:bg_colorPressed="#88FFFFFF"
            app:bg_fillMode="solid|bitmap"
            app:bg_shape="rect"
            app:bg_stateful="true"/>
```
CommonBackgroundXXX supports TextView, Button, ImageView, CheckBox, FrameLayout, LinearLayout, RelativeLayout.

- Usage in java:
```java
        // stateless
        CommonBackgroundFactory.createStateless()
                .shape(CommonBackground.SHAPE_ROUND_RECT)
                .colorFill(Color.BLUE)
                // set more attributes
                .showOn(yourView);
        // clickable and checkable
        CommonBackgroundSet set = CommonBackgroundFactory.createClickable();// or createCheckable()
        set.forEach() // use forEach() to set the common attributes of the drawables
                .shape(CommonBackground.SHAPE_semicircle_rect);
        // then use theXXX() methods to set different attributes
        set.theDisabled().colorFill(Color.DKGRAY);
        set.theNormal().colorFill(Color.WHITE);
        set.thePressed().colorFill(Color.LTGRAY);
        // set.theUnchecked()...
        // set.theChecked()...
        set.showOn(yourView);// set.toDrawable()...
```

# attrs.xml
```xml
    <!-- 通用背景图属性定义 -->
    <declare-styleable name="CommonBackground">
        <!-- 状态的区分模式-->
        <attr name="bg_stateMode">
            <enum name="none" value="0"/><!-- 无状态 -->
            <enum name="click" value="1"/><!-- 点击模式，类似于Button -->
            <enum name="check" value="2"/><!-- 选择模式，类似于CheckBox -->
        </attr>

        <!-- 形状 -->
        <attr name="bg_shape">
            <enum name="rect" value="0"/>
            <enum name="round_rect" value="1"/><!-- 圆角矩形 -->
            <enum name="semicircle_rect" value="2"/><!-- 圆头矩形（自动计算半径） -->
            <enum name="circle" value="3"/>
        </attr>

        <!-- 填充模式，若为solid|bitmap，此时填充色将作为图片蒙层，注意设置填充色的透明度 -->
        <attr name="bg_fillMode">
            <flag name="color" value="1"/><!-- 纯色填充 -->
            <flag name="bitmap" value="2"/><!-- 图片填充 -->
            <flag name="linearGradient" value="4"/><!-- 线性渐变填充 -->
        </attr>

        <!-- 填充图的缩放类型 -->
        <attr name="bg_scaleType">
            <enum name="center" value="0"/>
            <enum name="centerCrop" value="1"/><!-- 对齐控件width/height中的大者 -->
            <enum name="fitCenter" value="2"/><!-- 对齐控件width/height中的小者 -->
            <enum name="fitXY" value="3"/><!-- 对齐控件长宽 -->
        </attr>

        <!-- 描边模式 -->
        <attr name="bg_strokeMode">
            <enum name="none" value="0"/>
            <enum name="solid" value="1"/><!-- 实线描边 -->
            <enum name="dash" value="2"/><!-- 断续线描边 -->
        </attr>
        <attr name="bg_radius" format="dimension"/><!-- 圆角半径/形状为圆时的半径 -->
        <attr name="bg_radiusLeftTop" format="dimension"/>
        <attr name="bg_radiusLeftBottom" format="dimension"/>
        <attr name="bg_radiusRightTop" format="dimension"/>
        <attr name="bg_radiusRightBottom" format="dimension"/>
        <attr name="bg_strokeWidth" format="dimension"/><!-- 描边宽度 -->
        <attr name="bg_strokeDashSolid" format="dimension"/><!-- 断续线描边时，每一段实线的长度 -->
        <attr name="bg_strokeDashSpace" format="dimension"/><!-- 断续线描边时，每一段空白的长度 -->
        <attr name="bg_colorDisabled" format="color"/><!-- 各种状态的颜色 -->
        <attr name="bg_colorNormal" format="color"/>
        <attr name="bg_colorPressed" format="color"/>
        <attr name="bg_colorUnchecked" format="color"/>
        <attr name="bg_colorChecked" format="color"/>
        <attr name="bg_colorStroke" format="color"/><!-- 描边颜色 -->
        <attr name="bg_gradientStartColor" format="color"/>
        <attr name="bg_gradientEndColor" format="color"/>
        <attr name="bg_linearGradientOrientation">
            <enum name="left_right" value="0"/>
            <enum name="top_bottom" value="1"/>
            <enum name="right_left" value="2"/>
            <enum name="bottom_top" value="3"/>
        </attr>
        <attr name="bg_bitmap" format="reference"/><!-- 填充图资源id -->
    </declare-styleable>
