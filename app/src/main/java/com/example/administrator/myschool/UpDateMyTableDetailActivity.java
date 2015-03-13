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

                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name1.getText().toString(),address1.getText().toString(),time1.getText().toString(),titleName,Week,"1"});


                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name2.getText().toString(),address2.getText().toString(),time2.getText().toString(),titleName,Week,"2"});

                        sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                                "num=?;",new String[]{name3.getText().toString(),address3.getText().toString(),time3.getText().toString(),titleName,Week,"3"});


                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name4.getText().toString(),address4.getText().toString(),time4.getText().toString(),titleName,Week,"4"});

                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name5.getText().toString(),address5.getText().toString(),time5.getText().toString(),titleName,Week,"5"});

                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name6.getText().toString(),address6.getText().toString(),time6.getText().toString(),titleName,Week,"6"});


                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name7.getText().toString(),address7.getText().toString(),time7.getText().toString(),titleName,Week,"7"});

                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name8.getText().toString(),address8.getText().toString(),time8.getText().toString(),titleName,Week,"8"});


                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name9.getText().toString(),address9.getText().toString(),time9.getText().toString(),titleName,Week,"9"});


                sqLiteDatabase.execSQL("update mytable set coursename = ?, courseaddress =?, coursetime= ? where tablename=? and week=? and " +
                        "num=?;",new String[]{name10.getText().toString(),address10.getText().toString(),time10.getText().toString(),titleName,Week,"10"});




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
