package com.example.administrator.myschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/3/26.
 */
public class AddDreamActivity extends Activity{
    TextView titleName;
    LinearLayout title_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dream_layout);

        findView();
    }

    private void findView() {
        titleName= (TextView) findViewById(R.id.title);
        titleName.setText("添加梦想");
        title_return= (LinearLayout) findViewById(R.id.title_return);
        title_return.setVisibility(View.VISIBLE);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
