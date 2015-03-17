package com.example.administrator.myschool;

import android.app.Activity;

import com.deleteItem.DelSlideListView;
import com.deleteItem.ListViewonSingleTapUpListenner;
import com.deleteItem.OnDeleteListioner;
import com.rao.MySchool.adapter.TabaleAdapter;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyTable;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/3/6.
 */
public class MyTableActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,OnDeleteListioner, ListViewonSingleTapUpListenner, DialogInterface.OnCancelListener {

    TextView title;
    String TableName;
    TabaleAdapter tableAdapter;
    TabaleDeleteAdapter tabledeleteAdapter;
    MyTable myTable;
    List<MyTable> tableNameList = null;
    RelativeLayout my_table,update_my_table,add_my_table;
    ImageView my_table_image0,my_table_image1,update_my_table_image0,update_my_table_image1,add_my_table_image;
    TextView my_table_num,update_my_table_num;
    ListView mytable_list;
    DelSlideListView update_mytable_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_table);
      findId();
      loadData();
      layoutClick();



        mytable_list.setOnItemClickListener(new  OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyTableActivity.this,MyTableListActivity.class);
                intent.putExtra("fromId","1");
                intent.putExtra("titleName",tableNameList.get(position).getTableName());
                startActivity(intent);
            }

        });

        update_mytable_list.setOnItemClickListener(new  OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyTableActivity.this,MyTableListActivity.class);
                intent.putExtra("fromId","2");
                intent.putExtra("titleName",tableNameList.get(position).getTableName());
                startActivity(intent);
            }

        });

    }


    private void loadData() {
        Cursor   cursor = sqLiteDatabase.rawQuery("select  tablename from mytable where week=? and num=?;",new String[]{"星期一","1"});
        tableNameList = new ArrayList<MyTable>();
        while (cursor.moveToNext()) {
            myTable=new MyTable();
            myTable.setTableName(cursor.getString(0));
            tableNameList.add(myTable);
        }

        my_table_num.setText(""+tableNameList.size());
        update_my_table_num.setText(""+tableNameList.size());

        tableAdapter=new TabaleAdapter(this,tableNameList);
        tabledeleteAdapter=new TabaleDeleteAdapter(this,tableNameList);

        mytable_list.setAdapter(tableAdapter);
        update_mytable_list.setAdapter(tabledeleteAdapter);

        update_mytable_list.setDeleteListioner(this);
        update_mytable_list.setSingleTapUpListenner(this);

          /*解决listview与下拉刷新的冲突*/
        mytable_list.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i)
            {
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });


        /*解决listview与下拉刷新的冲突*/
        update_mytable_list.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i)
            {
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });
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
                Intent intent=new Intent(MyTableActivity.this,AddMyTableActivity.class);
                startActivity(intent);
            }
        });

    }

    private void findId() {

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();

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

        new Thread(){
            @Override
            public void run() {
                Message msg=new Message();
                loadData();
                msg.what=1;
                handler.sendMessageDelayed(msg, 2000);
            }
        }.start();

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
        private List<MyTable> tableList = null; // 数据集合


        public  class ViewHolder {// 视图
            TextView tableitem,delete_item;


        }




        public TabaleDeleteAdapter(Context context, List<MyTable> list) {
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


        public View getView(final int position, View convertView, ViewGroup parent) {
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
            TableName=tableList.get(position).getTableName();
            viewHolder.tableitem.setText(TableName);

            viewHolder.delete_item.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    tableList.remove(position);
                    tabledeleteAdapter.notifyDataSetChanged();
                    sqLiteDatabase.execSQL("delete from mytable where tablename = ?;",new String[]{TableName});
                    update_my_table_num.setText(""+tableList.size());

                }
            });

            return convertView;
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                loadData();

            /*  停止刷新 */
            swipeRefreshLayout.setRefreshing(false);

            }else{
                Toast.makeText(getApplicationContext(),
                        "刷新失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
