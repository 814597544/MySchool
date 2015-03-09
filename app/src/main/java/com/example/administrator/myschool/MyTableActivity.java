package com.example.administrator.myschool;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/3/6.
 */
public class MyTableActivity extends Activity{
    TextView title;
    RelativeLayout my_table_list,update_my_table,add_my_table;
    ImageView my_table__list_image0,my_table__list_image1,update_my_table_image0,update_my_table_image1,add_my_table_image;
    TextView my_table_list_num,update_my_table_num;
    ListView mytable_list,update_mytable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table);
      findId();
    }

    private void findId() {
        title= (TextView) findViewById(R.id.title);
        title.setText("课 表");
        my_table_list= (RelativeLayout) findViewById(R.id.my_table_list);
        update_my_table= (RelativeLayout) findViewById(R.id.update_my_table);
        add_my_table= (RelativeLayout) findViewById(R.id.add_my_table);
        my_table__list_image0= (ImageView) findViewById(R.id.my_table__list_image0);
        my_table__list_image1= (ImageView) findViewById(R.id.my_table__list_image1);
        update_my_table_image0= (ImageView) findViewById(R.id.update_my_table_image0);
        update_my_table_image1= (ImageView) findViewById(R.id.update_my_table_image1);
        add_my_table_image= (ImageView) findViewById(R.id.add_my_table_image);
        my_table_list_num= (TextView) findViewById(R.id.my_table_list_num);
        update_my_table_num= (TextView) findViewById(R.id.update_my_table_num);
        mytable_list= (ListView) findViewById(R.id.mytable_list);
        update_mytable= (ListView) findViewById(R.id.update_mytable);

    }
}
