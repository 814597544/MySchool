package com.rao.MySchool.been;

import android.app.Application;

/**
 * Created by Administrator on 2015/3/27.
 */
public class MyApplication extends Application{
    String Status;
    String ZklWhter;
    String DreamTime;
    String BreakTime;
    String WastTime;
    int TodayFinishTime=0,todayTime=0;
    boolean GoPain=false;

    public boolean getGoPain() {
        return GoPain;
    }

    public void setGoPain(boolean goPain) {
        GoPain = goPain;
    }

    public int getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(int todayTime) {
        this.todayTime = todayTime;
    }

    public int getTodayFinishTime() {
        return TodayFinishTime;
    }

    public void setTodayFinishTime(int todayFinishTime) {
        TodayFinishTime = todayFinishTime;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setStatus("-1");
        setGoPain(false);
        setTodayTime(0);
        setTodayFinishTime(0);
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

    public String getZklWhter() {
        return ZklWhter;
    }

    public void setZklWhter(String zklWhter) {
        ZklWhter = zklWhter;
    }
}
