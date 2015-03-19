package com.example.administrator.myschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/3/4.
 */
public class ChartToday extends Activity{
    RoundCornerProgressBar progress_myDream,progress_myNecessary,progress_myWaste;
    String  nowtime;
    TextView today_date;
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zkl_chart);

        getNowTime();
        findView();



    }

    private void findView() {

        today_date= (TextView) findViewById(R.id.today_date);
        today_date.setText(nowtime);

        back= (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progress_myDream= (RoundCornerProgressBar) findViewById(R.id.progress_myDream);
        progress_myNecessary=(RoundCornerProgressBar) findViewById(R.id.progress_myNecessary);
        progress_myWaste= (RoundCornerProgressBar) findViewById(R.id.progress_myWaste);

        progress_myDream.setBackgroundColor(getResources().getColor(R.color.custom_progress_my_background));
        progress_myNecessary.setBackgroundColor(getResources().getColor(R.color.custom_progress_my_background));
        progress_myWaste.setBackgroundColor(getResources().getColor(R.color.custom_progress_my_background));

        progress_myDream.setProgress(10);
        progress_myNecessary.setProgress(8);
        progress_myWaste.setProgress(5);

        progress_myDream.setProgressColor(getResources().getColor(R.color.custom_progress_myDream));
        progress_myNecessary.setProgressColor(getResources().getColor(R.color.custom_progress_myNecessary));
        progress_myWaste.setProgressColor(getResources().getColor(R.color.custom_progress_myWaste));
    }

    public void getNowTime() {
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
        nowtime   =   formatter.format(curDate);
    }
}
