package com.example.administrator.myschool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/3/6.
 */
public class MyTableActivity extends Activity{
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table);
        title= (TextView) findViewById(R.id.title);
    }
}
