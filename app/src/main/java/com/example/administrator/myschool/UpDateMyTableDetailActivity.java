package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rao.MySchool.adapter.TableDetailAdapter;
import com.rao.MySchool.adapter.UpdateTableDetailAdapter;
import com.rao.MySchool.been.MyCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/9.
 */
public class UpDateMyTableDetailActivity extends Activity{
    TextView title;
    ListView update_mytable_detail_listview;
    LinearLayout title_return,finish;
    List<String> list =new ArrayList<String>();
    UpdateTableDetailAdapter updateTableDetailAdapter;
    String titleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mytable_detail);
        findView();
        loadData();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadData() {
        for (int i=0;i<10;i++){
            list.add(""+i);
        }

        updateTableDetailAdapter=new UpdateTableDetailAdapter(this,list);
        update_mytable_detail_listview.setAdapter(updateTableDetailAdapter);
    }

    private void findView() {
        Intent intent=getIntent();
        titleName=intent.getStringExtra("titleName");
        title= (TextView) findViewById(R.id.title);
        title_return= (LinearLayout) findViewById(R.id.title_return);
        finish= (LinearLayout) findViewById(R.id.finish);
        finish.setVisibility(View.VISIBLE);
        update_mytable_detail_listview= (ListView) findViewById(R.id.update_mytable_detail_listview);

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
