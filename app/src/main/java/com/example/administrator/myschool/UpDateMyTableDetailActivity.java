package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rao.MySchool.been.DatabaseHelper;

/**
 * Created by Administrator on 2015/3/13.
 */
public class UpdateMyTableDetailActivity  extends Activity {
    TextView title;
    LinearLayout title_return,finish;
    String titleName,Week;
    EditText name1,name2,name3,name4,name5,name6,name7,name8,name9,name10;
    EditText address1,address2,address3,address4,address5,address6,address7,address8,address9,address10;
    EditText time1,time2,time3,time4,time5,time6,time7,time8,time9,time10;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mytable_detail);
        findView();


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqLiteDatabase.execSQL("update mytable set coursename = name1.getText().toString(), courseaddress =address1.getText().toString()," +
                        " coursetime= time1.getText().toString() where tablename=titleName and week=Week and num="+1+";");




                Toast.makeText(getApplicationContext(),
                        "保存成功", Toast.LENGTH_SHORT).show();

                finish();
            }

        });
    }



    private void findView() {
        Intent intent=getIntent();
        titleName=intent.getStringExtra("titleName");
        Week=intent.getStringExtra("week");
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        title= (TextView) findViewById(R.id.title);
        title_return= (LinearLayout) findViewById(R.id.title_return);
        finish= (LinearLayout) findViewById(R.id.finish);
        finish.setVisibility(View.VISIBLE);

        name1= (EditText) findViewById(R.id.update_course_name1);
        name2= (EditText) findViewById(R.id.update_course_name2);
        name3= (EditText) findViewById(R.id.update_course_name3);
        name4= (EditText) findViewById(R.id.update_course_name4);
        name5= (EditText) findViewById(R.id.update_course_name5);
        name6= (EditText) findViewById(R.id.update_course_name6);
        name7= (EditText) findViewById(R.id.update_course_name7);
        name8= (EditText) findViewById(R.id.update_course_name8);
        name9= (EditText) findViewById(R.id.update_course_name9);
        name10= (EditText) findViewById(R.id.update_course_name10);

        address1= (EditText) findViewById(R.id.update_course_address1);
        address2= (EditText) findViewById(R.id.update_course_address2);
        address3= (EditText) findViewById(R.id.update_course_address3);
        address4= (EditText) findViewById(R.id.update_course_address4);
        address5= (EditText) findViewById(R.id.update_course_address5);
        address6= (EditText) findViewById(R.id.update_course_address6);
        address7= (EditText) findViewById(R.id.update_course_address7);
        address8= (EditText) findViewById(R.id.update_course_address8);
        address9= (EditText) findViewById(R.id.update_course_address9);
        address10= (EditText) findViewById(R.id.update_course_address10);

        time1= (EditText) findViewById(R.id.update_course_time1);
        time2= (EditText) findViewById(R.id.update_course_time2);
        time3= (EditText) findViewById(R.id.update_course_time3);
        time4= (EditText) findViewById(R.id.update_course_time4);
        time5= (EditText) findViewById(R.id.update_course_time5);
        time6= (EditText) findViewById(R.id.update_course_time6);
        time7= (EditText) findViewById(R.id.update_course_time7);
        time8= (EditText) findViewById(R.id.update_course_time8);
        time9= (EditText) findViewById(R.id.update_course_time9);
        time10= (EditText) findViewById(R.id.update_course_time10);


        title.setText(titleName+"-"+Week);
        title_return.setVisibility(View.VISIBLE);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
