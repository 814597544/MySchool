package com.rao.MySchool.been;

import android.app.Application;

/**
 * Created by Administrator on 2015/3/27.
 */
public class MyApplication extends Application{
    String Status;

    @Override
    public void onCreate() {
        super.onCreate();
        setStatus("-1");
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
