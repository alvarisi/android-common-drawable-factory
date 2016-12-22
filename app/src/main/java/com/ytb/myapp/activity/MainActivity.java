package com.ytb.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ytb.myapp.R;
import com.ytb.myapp.widget.commonbackground.CommonBackgroundImageView;

/**
 * Created by Administrator on 2016-10-26.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    CommonBackgroundImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
