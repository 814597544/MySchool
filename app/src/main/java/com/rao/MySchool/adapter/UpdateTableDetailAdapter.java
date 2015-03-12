package com.rao.MySchool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.myschool.R;
import com.rao.MySchool.been.MyTable;


import java.util.List;

/**
 * Created by Administrator on 2015/3/12.
 */
public class UpdateTableDetailAdapter extends BaseAdapter{
    private Context context;// 当前上下文
    private LayoutInflater listContainer;// 视图容器
    private List<String> tableList = null; // 数据集合

    public static class ViewHolder {// 视图
        TextView tv_num;
        EditText update_course_name;
        EditText update_course_address;
        EditText update_course_time;


    }

    /**
     * 实例化Adapter
     *
     * @param context

     */
    public UpdateTableDetailAdapter(Context context, List<String> list) {
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
            convertView = listContainer.inflate(R.layout.adapter_update_mytable_detail, null);
            viewHolder = new ViewHolder();

            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.update_course_name = (EditText) convertView.findViewById(R.id.update_course_name);
            viewHolder.update_course_address = (EditText) convertView.findViewById(R.id.update_course_address);
            viewHolder.update_course_time = (EditText) convertView.findViewById(R.id.update_course_time);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_num.setText("第"+(position+1)+"节");


        return convertView;
    }
}
