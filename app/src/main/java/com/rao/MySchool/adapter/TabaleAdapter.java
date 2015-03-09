package com.rao.MySchool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myschool.R;

import java.util.List;

/**
 * Created by Administrator on 2015/3/9.
 */
public class TabaleAdapter extends BaseAdapter{
    private LayoutInflater listContainer;// 视图容器
    private List<String> tableList = null; // 数据集合

    public static class ViewHolder {// 视图
        TextView tableitem;

    }

    /**
     * 实例化Adapter
     *
     * @param context

     */
    public TabaleAdapter(Context context, List<String> list) {
        this.listContainer = LayoutInflater.from(context);
        this.tableList = list;

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
            convertView = listContainer.inflate(R.layout.adapter_table_list, null);
            viewHolder = new ViewHolder();
            viewHolder.tableitem = (TextView) convertView.findViewById(R.id.my_table_list_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tableitem.setText(tableList.get(position));

        return convertView;
    }
}
