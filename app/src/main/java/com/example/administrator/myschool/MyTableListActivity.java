package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.circlemenu.CircleImageView;
import com.circlemenu.CircleLayout;
import com.rao.MySchool.been.DatabaseHelper;


/**
 * Created by Administrator on 2015/3/9.
 */
public class MyTableListActivity extends Activity   implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener {
    private TextView selectedTextView,title;
    private CircleLayout circleMenu;
    LinearLayout title_return;
    String fromId,titleName;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table_list);
        findView();

        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findView() {
        Intent intent=getIntent();
        fromId=intent.getStringExtra("fromId");
        titleName=intent.getStringExtra("titleName");

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);
        title_return= (LinearLayout) findViewById(R.id.title_return);
        selectedTextView = (TextView)findViewById(R.id.main_selected_textView);
        title = (TextView)findViewById(R.id.title);
        circleMenu.setOnItemSelectedListener(this);
        circleMenu.setOnItemClickListener(this);

        title_return.setVisibility(View.VISIBLE);
        selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
        title.setText(titleName);

    }
    private void intentTo(Class class1,String name){
        Intent intent=new Intent(MyTableListActivity.this,class1);
        intent.putExtra("titleName",titleName);
        intent.putExtra("week",name);
        startActivity(intent);
    }

    @Override
       public void onItemClick(View view, int position, long id, String name) {
       // Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
        switch (fromId){

            case "1":
               intentTo(MyTableDetailActivity.class,name);
                break;
            case "2":
                cursor = sqLiteDatabase.rawQuery("select * from mytable where week=? and tablename=?;",new String[]{name, titleName});
              if (cursor.getCount()!=0){
                intentTo(UpDateMyTableDetailActivity.class,name);
              }else{
                  Toast.makeText(getApplicationContext(),
                          titleName+"-"+name+"的课表未创建，无法修改"  , Toast.LENGTH_SHORT).show();
              }
                break;
            case "3":
                cursor = sqLiteDatabase.rawQuery("select * from mytable where week=? and tablename=?;",new String[]{name, titleName});
                if (cursor.getCount()==0){
                intentTo(AddMyTableDetailActivity.class,name);
                }else{
                    Toast.makeText(getApplicationContext(),
                            titleName+"-"+name+"的课表已创建，请在编辑课表中修改"  , Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }

    }

    @Override
    public void onItemSelected(View view, int position, long id, String name) {
        selectedTextView.setText(name);
    }


}
