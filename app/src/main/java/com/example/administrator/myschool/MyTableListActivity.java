package com.example.administrator.myschool;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.circlemenu.CircleImageView;
import com.circlemenu.CircleLayout;


/**
 * Created by Administrator on 2015/3/9.
 */
public class MyTableListActivity extends Activity   implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener {
    private TextView selectedTextView,title;
    private CircleLayout circleMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table_list);
        findView();
    }

    private void findView() {
        circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);

        selectedTextView = (TextView)findViewById(R.id.main_selected_textView);
        title = (TextView)findViewById(R.id.title);
        circleMenu.setOnItemSelectedListener(this);
        circleMenu.setOnItemClickListener(this);

        selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
        title.setText("课表名称");
    }

    @Override
       public void onItemClick(View view, int position, long id, String name) {
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(View view, int position, long id, String name) {
        selectedTextView.setText(name);
    }


}
