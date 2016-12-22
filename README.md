# Project
Android通用控件背景Drawable，支持多种形状、多种状态、多种描边和填充模式。旨在减少书写drawable.xml，提高代码重用。
\r\nAndroid common-background drawable, supports multi shapes, multi states, multi stroke mode, and multi fill mode. Aiming to reduce drawable.xml, and increase reusability.

# Preview
![image](https://github.com/yintaibing/CommonBackgroundWidget/blob/master/screenshot/preview.png)

# Usage
- Usage in XML(see all configurable attributes in /res/values/attrs.xml):
```xml
<com.ytb.myapp.widget.commonbackground.CommonBackgroundXXX
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="5dp"
            android:gravity="center"
            android:padding="5dp"
            android:text="solid|bitmap\n(click me)"
            app:bitmap="@mipmap/ic_launcher"
            app:colorNormal="#FFFFFFFF"
            app:colorPressed="#88FFFFFF"
            app:fillMode="solid|bitmap"
            app:shape="rect"
            app:stateful="true"/>
```
supports TextView, Button, ImageView, CheckBox, FrameLayout, LinearLayout, RelativeLayout.

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
                .shape(CommonBackground.SHAPE_SIDE_CIRCLE_RECT);
        // then use theXXX() methods to set different attributes
        set.theDisabled().colorFill(Color.DKGRAY);
        set.theNormal().colorFill(Color.WHITE);
        set.thePressed().colorFill(Color.LTGRAY);
        // set.theUnchecked()...
        // set.theChecked()...
        set.showOn(yourView);
```