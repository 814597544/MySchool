package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rao.MySchool.been.DatabaseHelper;

/**
 * Created by Administrator on 2015/3/9.
 */
public class AddMyTableActivity extends Activity {
    TextView title,next;
    EditText add_table_name;
    LinearLayout title_return;
    String addTableName=null;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mytable);

        findView();
    }

    private void findView() {

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        title= (TextView) findViewById(R.id.title);
        next= (TextView) findViewById(R.id.next);
        add_table_name= (EditText) findViewById(R.id.add_table_name);
        title_return= (LinearLayout) findViewById(R.id.title_return);

        title.setText("添加课表");
        title_return.setVisibility(View.VISIBLE);

        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTableName=add_table_name.getText().toString().trim();//trim()可以去掉输入字符串的前后空格
                cursor = sqLiteDatabase.rawQuery("select * from mytable where tablename=?;", new String[]{addTableName});

        if (addTableName.equals("")){

            Toast.makeText(getApplicationContext(),
                    "课程名称不能为空", Toast.LENGTH_SHORT).show();

        }else if (0!=cursor.getCount()){
            Toast.makeText(getApplicationContext(),
                    "改课程表已存在，请在编辑课表中修改", Toast.LENGTH_SHORT).show();
        }
        else{
           Intent intent=new Intent(AddMyTableActivity.this,MyTableListActivity.class);
           intent.putExtra("fromId","3");
           intent.putExtra("titleName",addTableName);
           startActivity(intent);
}

            }
        });
    }
}
