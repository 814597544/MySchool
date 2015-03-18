package com.example.administrator.myschool;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

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

    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuOverlay = (ExpandableMenuOverlay) findViewById(R.id.button_menu);

        //logo提示字体闪烁
        tv= (TextView) findViewById(R.id.tv_hint);
        Animation ani = new AlphaAnimation(0f, 1f);
        ani.setDuration(2000);
        ani.setRepeatMode(Animation.REVERSE);
        ani.setRepeatCount(Animation.INFINITE);
        tv. startAnimation(ani);

        tabHost = getTabHost();
        setTabs();
        menuOverlay.setOnMenuButtonClickListener(new ExpandableButtonMenu.OnMenuButtonClick() {
            @Override
            public void onClick(ExpandableButtonMenu.MenuButton action) {
                switch (action) {
                    case MID:
                        startActivity2();
                        break;
                   /* case LEFT:
                        startActivity1();
                        break;*/
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


  /*  private void startActivity1(){
        Intent intent=new Intent(HomeActivity.this,EditTimeActivity.class);
        startActivity(intent);

    }*/
    private void startActivity2(){
        Intent intent=new Intent(MainActivity.this,YaoYiYaoActivity.class);
        startActivity(intent);

    }
    private void startActivity3(){
        Intent intent=new Intent(MainActivity.this,ChartToday.class);
        startActivity(intent);

    }
}