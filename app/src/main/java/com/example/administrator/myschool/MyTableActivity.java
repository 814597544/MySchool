package com.example.administrator.myschool;

import android.app.Activity;
import com.rao.MySchool.adapter.TabaleAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/6.
 */
public class MyTableActivity extends Activity{
    TextView title;
    TabaleAdapter tableAdapter;
    List<String> tableNameList = new ArrayList<String>();
    RelativeLayout my_table,update_my_table,add_my_table;
    ImageView my_table_image0,my_table_image1,update_my_table_image0,update_my_table_image1,add_my_table_image;
    TextView my_table_num,update_my_table_num;
    ListView mytable_list,update_mytable_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table);
      findId();
      layoutClick();

    }

    private void layoutClick() {
        my_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mytable_list.getVisibility() == View.VISIBLE){
                    my_table_image0.setVisibility(View.VISIBLE);
                    my_table_image1.setVisibility(View.GONE);
                    mytable_list.setVisibility(View.GONE);
                }else{
                    my_table_image0.setVisibility(View.GONE);
                    my_table_image1.setVisibility(View.VISIBLE);
                    mytable_list.setVisibility(View.VISIBLE);
                }



            }
        });
        update_my_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update_mytable_list.getVisibility() == View.VISIBLE){
                    update_my_table_image0.setVisibility(View.VISIBLE);
                    update_my_table_image1.setVisibility(View.GONE);
                    update_mytable_list.setVisibility(View.GONE);
                }else{
                    update_my_table_image0.setVisibility(View.GONE);
                    update_my_table_image1.setVisibility(View.VISIBLE);
                    update_mytable_list.setVisibility(View.VISIBLE);
                }



            }
        });
    }

    private void findId() {
        title= (TextView) findViewById(R.id.title);
        title.setText("课 表");
        my_table= (RelativeLayout) findViewById(R.id.my_table);
        update_my_table= (RelativeLayout) findViewById(R.id.update_my_table);
        add_my_table= (RelativeLayout) findViewById(R.id.add_my_table);
        my_table_image0= (ImageView) findViewById(R.id.my_table_image0);
        my_table_image1= (ImageView) findViewById(R.id.my_table_image1);
        update_my_table_image0= (ImageView) findViewById(R.id.update_my_table_image0);
        update_my_table_image1= (ImageView) findViewById(R.id.update_my_table_image1);
        add_my_table_image= (ImageView) findViewById(R.id.add_my_table_image);
        my_table_num= (TextView) findViewById(R.id.my_table_num);
        update_my_table_num= (TextView) findViewById(R.id.update_my_table_num);
        mytable_list= (ListView) findViewById(R.id.mytable_list);
        update_mytable_list= (ListView) findViewById(R.id.update_mytable_list);

        tableNameList.add("大一课表");
        tableNameList.add("大二课表");
        tableNameList.add("大三课表");
        tableNameList.add("大四课表");
        tableAdapter=new TabaleAdapter(this,tableNameList);
        mytable_list.setAdapter(tableAdapter);
        update_mytable_list.setAdapter(tableAdapter);
    }
}
