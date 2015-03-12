package com.example.administrator.myschool;

import android.app.Activity;

import com.deleteItem.DelSlideListView;
import com.deleteItem.ListViewonSingleTapUpListenner;
import com.deleteItem.OnDeleteListioner;
import com.rao.MySchool.adapter.TabaleAdapter;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/3/6.
 */
public class MyTableActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,OnDeleteListioner, ListViewonSingleTapUpListenner, DialogInterface.OnCancelListener {

    TextView title;
    TabaleAdapter tableAdapter;
    TabaleDeleteAdapter tabledeleteAdapter;
    List<String> tableNameList = new ArrayList<String>();
    RelativeLayout my_table,update_my_table,add_my_table;
    ImageView my_table_image0,my_table_image1,update_my_table_image0,update_my_table_image1,add_my_table_image;
    TextView my_table_num,update_my_table_num;
    ListView mytable_list;
    DelSlideListView update_mytable_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table);
      findId();
      loadData();
      layoutClick();

        update_mytable_list.setDeleteListioner(this);
        update_mytable_list.setSingleTapUpListenner(this);

        mytable_list.setOnItemClickListener(new  OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyTableActivity.this,MyTableListActivity.class);
                startActivity(intent);
            }

        });

        update_mytable_list.setOnItemClickListener(new  OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyTableActivity.this,MyTableListActivity.class);
                startActivity(intent);
            }

        });

    }

    private void loadData() {
        tableNameList.add("大一课表");
        tableNameList.add("大二课表");
        tableNameList.add("大三课表");
        tableNameList.add("大四课表");

        tableAdapter=new TabaleAdapter(this,tableNameList);
        tabledeleteAdapter=new TabaleDeleteAdapter(this,tableNameList);

        mytable_list.setAdapter(tableAdapter);
        update_mytable_list.setAdapter(tabledeleteAdapter);

        update_mytable_list.setDeleteListioner(this);
        update_mytable_list.setSingleTapUpListenner(this);
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

        add_my_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyTableActivity.this,MyTableListActivity.class);
                startActivity(intent);
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
        update_mytable_list= (DelSlideListView) findViewById(R.id.update_mytable_list);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onRefresh() {
       loadData();
     /*  停止刷新 */

        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onSingleTapUp() {

    }

    /**
     * This method will be invoked when the dialog is canceled.
     *
     * @param dialog The dialog that was canceled will be passed into the
     *               method.
     */
    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public boolean isCandelete(int position) {
        return true;
    }

    @Override
    public void onDelete(int ID) {

    }

    @Override
    public void onBack() {

    }

    @Override
    public void onClick(int position) {

    }


    /*    适配器   */
    public class TabaleDeleteAdapter extends BaseAdapter {
        private Context context;// 当前上下文
        private LayoutInflater listContainer;// 视图容器
        private List<String> tableList = null; // 数据集合


        public  class ViewHolder {// 视图
            TextView tableitem,delete_item;


        }




        public TabaleDeleteAdapter(Context context, List<String> list) {
            this.listContainer = LayoutInflater.from(context);
            this.tableList = list;
            this.context = context;

        }

        public int getCount() {
            return tableList.size();
        }

        public Object getItem(int arg0) {
            return tableList.get(arg0);
        }

        public long getItemId(int arg0) {
            return arg0;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                // 加载视图文件
                convertView = listContainer.inflate(R.layout.adapter_update_table, null);
                viewHolder = new ViewHolder();
                viewHolder.tableitem = (TextView) convertView.findViewById(R.id.my_table_list_name);
                viewHolder.delete_item = (TextView) convertView.findViewById(R.id.delete_item);


                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }



            viewHolder.delete_item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                  tabledeleteAdapter.notifyDataSetChanged();

                }
            });

            viewHolder.tableitem.setText(tableList.get(position));


            return convertView;
        }
    }
}
