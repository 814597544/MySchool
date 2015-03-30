package com.rao.MySchool.been;

import android.app.Application;

/**
 * Created by Administrator on 2015/3/27.
 */
public class MyApplication extends Application{
    String Status;
    String DreamTime;
    String BreakTime;
    String WastTime;

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

    public String getDreamTime() {
        return DreamTime;
    }

    public void setDreamTime(String dreamTime) {
        DreamTime = dreamTime;

    }

    public String getBreakTime() {
        return BreakTime;
    }

    public void setBreakTime(String breakTime) {
        BreakTime = breakTime;
    }

    public String getWastTime() {
        return WastTime;
    }

    public void setWastTime(String wastTime) {
        WastTime = wastTime;
    }
}
