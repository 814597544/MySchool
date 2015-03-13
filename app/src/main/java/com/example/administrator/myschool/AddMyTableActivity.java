package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/3/9.
 */
public class AddMyTableActivity extends Activity {
    TextView title,next;
    EditText add_table_name;
    LinearLayout title_return;
    String addTableName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mytable);

        findView();
    }

    private void findView() {
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
                addTableName=add_table_name.getText().toString();
        if (addTableName.equals("")||null==addTableName){

            Toast.makeText(getApplicationContext(),
                    "课程名称不能为空", Toast.LENGTH_SHORT).show();

        }else{
           Intent intent=new Intent(AddMyTableActivity.this,MyTableListActivity.class);
           intent.putExtra("fromId","3");
           intent.putExtra("titleName",addTableName);
           startActivity(intent);
}

            }
        });
    }
}
