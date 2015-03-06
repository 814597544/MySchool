package com.example.administrator.myschool;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableButtonMenu;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableMenuOverlay;

public class MainActivity extends ActionBarActivity {
    private ExpandableMenuOverlay menuOverlay;
/*----test----*/
    private PopupWindow popupWindow;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    menuOverlay = (ExpandableMenuOverlay) findViewById(R.id.button_menu);
    tabHost = getTabHost();
    setTabs();
    menuOverlay.setOnMenuButtonClickListener(new ExpandableButtonMenu.OnMenuButtonClick() {
        @Override
        public void onClick(ExpandableButtonMenu.MenuButton action) {
            switch (action) {
                case MID:
                    startActivity3();
                    break;
                case LEFT:
                    startActivity1();
                    break;
                case RIGHT:
                    startActivity2();
                    break;
            }
        }
    });
}
    private void setTabs()
    {
        addTab("自控力", R.drawable.tab_search, ZKLActivity.class);
        addTab("课表", R.drawable.tab_search, ChartToday.class);
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

    private void initPopupWindows(View v){

        View pop_view=getLayoutInflater().inflate(R.layout.edit_time,null,false);
        popupWindow=new PopupWindow(pop_view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true );
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAsDropDown(v);


    }
    private void startActivity1(){
        Intent intent=new Intent(MainActivity.this,EditTimeActivity.class);
        startActivity(intent);

    }
    private void startActivity2(){
        Intent intent=new Intent(MainActivity.this,CalendarActivity.class);
        startActivity(intent);

    }
    private void startActivity3(){
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);

    }

}
