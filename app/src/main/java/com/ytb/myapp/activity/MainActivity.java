package com.ytb.myapp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ytb.myapp.R;
import com.ytb.myapp.widget.commonbackground.CommonBackgroundButton;
import com.ytb.myapp.widget.commonbackground.CommonBackgroundFactory;
import com.ytb.myapp.widget.commonbackground.CommonBackgroundImageView;
import com.ytb.myapp.widget.commonbackground.CommonBackgroundSet;

/**
 * Created by Administrator on 2016-10-26.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    CommonBackgroundImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonBackgroundButton btn1 = (CommonBackgroundButton) findViewById(R.id.btn1);
//        btn1.refreshBackground();
        btn1.setOnClickListener(this);

        iv = (CommonBackgroundImageView) findViewById(R.id.btn6);

        CommonBackgroundFactory.createStateless()
                .shape(0)
                .showOn(iv);

        CommonBackgroundSet set = CommonBackgroundFactory.createStateful();
        set.forEach()
                .shape(0)
                .fillMode(0);
              //...
        set.theDisabled().colorFill(Color.RED);
        set.theNormal().colorFill(Color.GREEN);
        set.thePressed().colorFill(Color.BLUE);
        set.showOn(iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "btn1", Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }
}
