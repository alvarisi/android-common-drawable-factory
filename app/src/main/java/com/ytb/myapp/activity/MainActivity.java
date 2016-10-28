package com.ytb.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ytb.myapp.R;
import com.ytb.myapp.widget.CommonBackgroundButton;

/**
 * Created by Administrator on 2016-10-26.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonBackgroundButton btn1 = (CommonBackgroundButton) findViewById(R.id.btn1);
//        btn1.refreshBackground();
        btn1.setOnClickListener(this);

        CommonBackgroundButton btn2 = (CommonBackgroundButton) findViewById(R.id.btn2);
//        btn2.refreshBackground();
        btn2.setOnClickListener(this);

        CommonBackgroundButton btn3 = (CommonBackgroundButton) findViewById(R.id.btn3);
//        btn3.refreshBackground();
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "btn1", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
