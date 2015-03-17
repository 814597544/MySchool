package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.lodingdialog.LoadingDialog;
import com.rao.MySchool.been.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/9.
 */
public class AddMyTableDetailActivity extends Activity{
    TextView title;
    LinearLayout title_return,finish;
    String titleName,Week;
    EditText name1,name2,name3,name4,name5,name6,name7,name8,name9,name10;
    EditText address1,address2,address3,address4,address5,address6,address7,address8,address9,address10;
    EditText time1,time2,time3,time4,time5,time6,time7,time8,time9,time10;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mytable_detail);
        findView();


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                new Thread(){
                    @Override
                    public void run() {
                        Message msg=new Message();
                        loadData();
                        msg.what=1;
                        handler.sendMessageDelayed(msg, 2000);
                    }
                }.start();


              }

        });
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                Toast.makeText(getApplicationContext(),
                     "保存成功", Toast.LENGTH_SHORT).show();

                dialog.dismiss();

                finish();
            }else{
                Toast.makeText(getApplicationContext(),
                        "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void loadData(){


        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"1",name1.getText().toString().trim(),address1.getText().toString().trim(),
                        time1.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"2",name2.getText().toString().trim(),address2.getText().toString().trim(),
                        time2.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"3",name3.getText().toString().trim(),address3.getText().toString().trim(),
                        time3.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"4",name4.getText().toString().trim(),address4.getText().toString().trim(),
                        time4.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"5",name5.getText().toString().trim(),address5.getText().toString().trim(),
                        time5.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"6",name6.getText().toString().trim(),address6.getText().toString().trim(),
                        time6.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"7",name7.getText().toString().trim(),address7.getText().toString().trim(),
                        time7.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"8",name8.getText().toString().trim(),address8.getText().toString().trim(),
                        time8.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"9",name9.getText().toString().trim(),address9.getText().toString().trim(),
                        time9.getText().toString().trim()});

        sqLiteDatabase.execSQL("insert into mytable(tablename,week,num,coursename,courseaddress,coursetime)" +
                        " values(?,?,?,?,?,?);",
                new Object[]{titleName,Week,"10",name10.getText().toString().trim(),address10.getText().toString().trim(),
                        time10.getText().toString().trim()});
    }


    private void findView() {
        Intent intent=getIntent();
        titleName=intent.getStringExtra("titleName");
        Week=intent.getStringExtra("week");
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        dialog = new LoadingDialog(this);

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
