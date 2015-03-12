package com.rao.MySchool.been;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String name="mytable";//数据库名称
    private static final int version=1;//数据库版本
    public DatabaseHelper(Context context){
        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的
        //工厂类，设置为null，代表使用系统默认的工厂类
        super(context, name, null, version);
    }
    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table if not exists person(" +
                "tablename varchar(20) primary key autoincrement,week varchar(10),num varchar(10)," +
                "coursename varchar(50),courseaddress varchar(20),coursetime varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // 往表中增加一列
       // db.execSQL("alter table person add phone varchar(12) null");
        //drop table if exists person删除表
    }

}

