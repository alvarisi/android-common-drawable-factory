package com.ytb.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ytb.myapp.R;
import com.ytb.myapp.widget.CommonBackgroundButton;
import com.ytb.myapp.widget.MyDrawable;

/**
 * Created by Administrator on 2016-10-26.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        MyDrawable drawable = new MyDrawable();
        drawable.color(getResources().getColor(R.color.lighter_blue));
        btn.setBackground(drawable);
        btn.setOnClickListener(this);

        CommonBackgroundButton btn1 = (CommonBackgroundButton) findViewById(R.id.btn1);
        btn1.refreshBackground();
        btn1.setOnClickListener(this);

        CommonBackgroundButton btn2 = (CommonBackgroundButton) findViewById(R.id.btn2);
        btn2.refreshBackground();
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Toast.makeText(this, "btn", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn1:
                Toast.makeText(this, "btn1", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
