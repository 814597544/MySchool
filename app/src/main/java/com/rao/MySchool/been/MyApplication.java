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
    String TodayDate;
    String EndTime,StartTime;
    int TodayFinishTime=0,todayTime=0,allTime=0,proGress;
    boolean GoPain=false;
    boolean isRun=false;

    public boolean getRun() {
        return isRun;
    }

    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getTodayDate() {
        return TodayDate;
    }

    public void setTodayDate(String todayDate) {
        TodayDate = todayDate;
    }

    public int getProGress() {
        return proGress;
    }

    public void setProGress(int proGress) {
        this.proGress = proGress;
    }

    public int getAllTime() {
        return allTime;
    }

    public void setAllTime(int allTime) {
        this.allTime = allTime;
    }

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
        setRun(false);
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
