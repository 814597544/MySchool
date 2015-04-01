package com.example.administrator.myschool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBindService extends Service {
    private final static String TAG = "main";
    private int count;
    private boolean quit;

    private Thread thread;
    private MyBinder binder=new MyBinder();

    // ����һ��Binder��
    public class MyBinder extends Binder
    {
        // ����һ����������count��¶���ⲿ����
        public int getCount(){
            return count;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service is Created");
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                // ÿ���һ��count��1 ��ֱ��quitΪtrue��
                while(!quit){
                    try{
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        });
        thread.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Service is Unbinded");
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service is started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service is Destroyed");
        this.quit=true;

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service is Binded");
        return binder;
    }
}
