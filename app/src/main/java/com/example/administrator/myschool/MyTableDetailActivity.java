package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rao.MySchool.adapter.TableDetailAdapter;
import com.rao.MySchool.been.MyCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/11.
 */
public class MyTableDetailActivity extends Activity{
    TextView title;
    ListView table2_listview;
    LinearLayout title_return;
    MyCourse mycourse;
    List<MyCourse> courseList =new ArrayList<MyCourse>();
    TableDetailAdapter tableDetailAdapter;
    String titleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table_detail);

        findView();
        loadData();

    }

    private void loadData() {
     for (int i=0;i<10;i++){
         mycourse=new MyCourse();
         mycourse.setCourseName("第"+(i+1)+"节");
         mycourse.setCourseAddress("第"+(i+1)+"节");
         mycourse.setCourseTime("第"+(i+1)+"节");
         courseList.add(mycourse);
     }
        tableDetailAdapter=new TableDetailAdapter(this,courseList);
        table2_listview.setAdapter(tableDetailAdapter);
    }

    private void findView() {
        Intent intent=getIntent();
        titleName=intent.getStringExtra("titleName");
        title= (TextView) findViewById(R.id.title);
        title_return= (LinearLayout) findViewById(R.id.title_return);
        table2_listview= (ListView) findViewById(R.id.table2_listview);

        title.setText(titleName);
        title_return.setVisibility(View.VISIBLE);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
