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
    private TextView selectedTextView;
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
        selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
    }

    @Override
       public void onItemClick(View view, int position, long id, String name) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.action_settings) + " " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(View view, int position, long id, String name) {
        selectedTextView.setText(name);
    }


}
