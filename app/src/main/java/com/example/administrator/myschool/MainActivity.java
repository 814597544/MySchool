package com.example.administrator.myschool;

import android.app.TabActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;

import lt.lemonlabs.android.expandablebuttonmenu.ExpandableButtonMenu;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableMenuOverlay;


/**
 * @author Adil Soomro
 *
 */
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
    private ExpandableMenuOverlay menuOverlay;

    TabHost tabHost;
    TextView tv;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private MyApplication myApplication;

    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuOverlay = (ExpandableMenuOverlay) findViewById(R.id.button_menu);
        myApplication= (MyApplication) getApplication();
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

        //logo提示字体闪烁
        tv= (TextView) findViewById(R.id.tv_hint);
        Animation ani = new AlphaAnimation(0f, 1f);
        ani.setDuration(2000);
        ani.setRepeatMode(Animation.REVERSE);
        ani.setRepeatCount(Animation.INFINITE);
        tv. startAnimation(ani);

        tabHost = getTabHost();
        setTabs();

        /*menu中的点击事件*/
        menuOverlay.setOnMenuButtonClickListener(new ExpandableButtonMenu.OnMenuButtonClick() {
            @Override
            public void onClick(ExpandableButtonMenu.MenuButton action) {
                switch (action) {
                    case MID:
                        startActivity2();
                        break;
                    case LEFT:
                        startActivity1();
                        break;
                    case RIGHT:
                        startActivity3();
                        break;
                }
            }
        });
    }
    private void setTabs()
    {
        addTab("自控力", R.drawable.zkl_search, ZklActivity.class);
        addTab("课  表", R.drawable.schooltimetable_search, MyTableActivity.class);
    }
	private void addTab(String labelId, int drawableId, Class<?> c)
    {
        Intent intent = new Intent(this, c);
        TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);
        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(labelId);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);
        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }


    private void startActivity1(){
        if (myApplication.getStatus().equals("0")){
            Toast.makeText(getApplicationContext(),
                    "梦想正在进行中，不能添加", Toast.LENGTH_SHORT).show();
        }else if (myApplication.getStatus().equals("1")){
            sqLiteDatabase.execSQL("delete from mydream where status=?;",new String[]{"1"});
            myApplication.setStatus("-1");
              /*------发送广播------*/
            myApplication.setStatus("0");
            Intent intent1 = new Intent();
            intent1.setAction("com.rao.myproject.Status");
            sendBroadcast(intent1);

            Intent intent=new Intent(MainActivity.this,AddDreamActivity.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(MainActivity.this,AddDreamActivity.class);
            startActivity(intent);
        }
    }
    private void startActivity2(){
        Intent intent=new Intent(MainActivity.this,YaoYiYaoActivity.class);
        startActivity(intent);

    }
    private void startActivity3(){
        Intent intent=new Intent(MainActivity.this,DreamingActivity.class);
        startActivity(intent);

    }
}