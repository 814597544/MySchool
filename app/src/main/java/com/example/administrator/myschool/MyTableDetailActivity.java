package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lodingdialog.LoadingDialog;
import com.rao.MySchool.adapter.TableDetailAdapter;
import com.rao.MySchool.been.DatabaseHelper;
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
    String titleName,Week;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table_detail);

        findView();
        Log.e("2222","222222222");
        loadData();

    }

    private void loadData() {

        dialog.show();

        new Thread(){
            @Override
            public void run() {
                Message msg=new Message();

                Cursor cursor = sqLiteDatabase.rawQuery("select coursename,courseaddress,coursetime " +
                        "from mytable where week=? and tablename=?;",new String[]{Week, titleName});

                if (cursor.getCount()==0){
                    for (int i=0;i<10;i++){
                        mycourse=new MyCourse();
                        courseList.add(mycourse);
                    }
                }else{
                    while (cursor.moveToNext()) {
                        mycourse=new MyCourse();
                        mycourse.setCourseName(cursor.getString(0));
                        mycourse.setCourseAddress(cursor.getString(1));
                        mycourse.setCourseTime(cursor.getString(2));
                        courseList.add(mycourse);
                    }

                }

                tableDetailAdapter=new TableDetailAdapter(MyTableDetailActivity.this,courseList);
                table2_listview.setAdapter(tableDetailAdapter);

                msg.what=1;
                handler.sendMessageDelayed(msg, 3000);
            }
        }.start();

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                dialog.dismiss();

                Toast.makeText(getApplicationContext(),
                        "加载完毕", Toast.LENGTH_SHORT).show();


            }else{
                Toast.makeText(getApplicationContext(),
                        "加载失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void findView() {
        Intent intent=getIntent();
        titleName=intent.getStringExtra("titleName");
        Week=intent.getStringExtra("week");
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        dialog = new LoadingDialog(this);

        title= (TextView) findViewById(R.id.title);
        title_return= (LinearLayout) findViewById(R.id.title_return);
        table2_listview= (ListView) findViewById(R.id.table2_listview);

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
