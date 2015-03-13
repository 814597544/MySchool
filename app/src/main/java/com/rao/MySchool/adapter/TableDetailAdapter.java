package com.rao.MySchool.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.myschool.R;
import com.rao.MySchool.been.MyCourse;

import java.util.List;

/**
 * Created by Administrator on 2015/3/11.
 */
public class TableDetailAdapter  extends BaseAdapter{

        private Context context;// 当前上下文
        private LayoutInflater listContainer;// 视图容器
        private List<MyCourse> bookList = null; // 数据集合

        public static class ViewHolder {// 视图
            TextView tv_num;
            TextView book_name;
            TextView book_address;
            TextView book_time;

        }

        /**
         * 实例化Adapter
         *
         * @param context

         */
        public TableDetailAdapter(Context context, List<MyCourse> list) {
            this.listContainer = LayoutInflater.from(context);
            this.bookList = list;
            this.context = context;

        }

        public int getCount() {
            return bookList.size();
        }

        public Object getItem(int arg0) {
            return bookList.get(arg0);
        }

        public long getItemId(int arg0) {
            return arg0;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                // 加载视图文件
                convertView = listContainer.inflate(R.layout.adapter_mytable_detail, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
                viewHolder.book_name = (TextView) convertView.findViewById(R.id.course_name);
                viewHolder.book_address = (TextView) convertView.findViewById(R.id.course_address);
                viewHolder.book_time = (TextView) convertView.findViewById(R.id.course_time);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_num.setText("第"+(position+1)+"节");

            if (bookList.get(position).getCourseName()==null){
                viewHolder.book_name.setText("课程名称:");
            }
            else{
                viewHolder.book_name.setText("课程名称:"+"\n"+bookList.get(position).getCourseName());
            }

            if (bookList.get(position).getCourseAddress()==null){
                viewHolder.book_address.setText("课程地点:");
            }
            else{
                viewHolder.book_address.setText("课程地点:"+"\n"+bookList.get(position).getCourseAddress());
            }

            if (bookList.get(position).getCourseTime()==null){
                viewHolder.book_time.setText("课程时间:");
            }
            else{
                viewHolder.book_time.setText("课程时间:"+"\n"+bookList.get(position).getCourseTime());

            }


            return convertView;
        }
    }


