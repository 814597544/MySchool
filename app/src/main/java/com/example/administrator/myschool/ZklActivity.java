package com.example.administrator.myschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nineoldandroids.animation.Animator;
import com.rao.MySchool.adapter.CircularPagerAdapter;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.nineoldandroids.animation.Animator.*;

/**
 * Created by Administrator on 2015/3/6.
 */
public class ZklActivity extends Activity{
    private RoundCornerProgressBar progressTwo;
    private ImageView add,start_dream,stop_dream;

    private int progress2 = 0;
    private CircularBarPager mCircularBarPager;
    TextView titleName,zkl_show_dream,zkl_show_wast,zkl_show_break,today_finishtime;
    String shownum = null,nowtime,MyDreamTime=null;


    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private MyApplication myApplication;
    private MyReceiver myReceiver;



    private TimerTask task = null;																			//定时器任务（用于首页Gallery切换）
    private Timer time = null	;
    int TodayFinishTime=0;
    private static final int BAR_ANIMATION_TIME = 1000;

    Cursor cursor3;
    MyBindService.MyBinder binder;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("zkl", "--Service Disconnected--");
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("zkl", "--Service Connected--");
            // 取得Service对象中的Binder对象
            binder = (MyBindService.MyBinder) service;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zkl);

        findView();
        updateProgressTwo();
        initViews();
    }


    private void findView() {
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        myApplication= (MyApplication) getApplication();
        today_finishtime= (TextView) findViewById(R.id.today_finishtime);

        /*--------广播更新----------*/
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.rao.myproject.Status");
        registerReceiver(myReceiver, filter);
        /*---------显示--------*/
        titleName=(TextView) findViewById(R.id.title);
        titleName.setText("自控力");
        zkl_show_dream=(TextView) findViewById(R.id.zkl_show_dream);
        zkl_show_break=(TextView) findViewById(R.id.zkl_show_break);
        zkl_show_wast=(TextView) findViewById(R.id.zkl_show_wast);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from mydream ;",null);
        if (cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                try {
                    if (!cursor.getString(5).equals("1")) {
                        myApplication.setDreamTime(cursor.getString(2));
                        myApplication.setBreakTime("11");
                        myApplication.setWastTime(13 - Integer.parseInt(cursor.getString(2)) + "");
                        MyDreamTime=myApplication.getDreamTime();
                        zkl_show_dream.setText(myApplication.getDreamTime() + "小时");
                        zkl_show_break.setText(myApplication.getBreakTime() + "小时");
                        zkl_show_wast.setText(myApplication.getWastTime() + "小时");

                    }
                } catch (Exception e) {
                }
            }
        }

        progressTwo = (RoundCornerProgressBar) findViewById(R.id.progress_two);
        progressTwo.setBackgroundColor(getResources().getColor(R.color.custom_progress_background));

        add= (ImageView) findViewById(R.id.add);
        start_dream= (ImageView) findViewById(R.id.start_dream);
        stop_dream= (ImageView) findViewById(R.id.stop_dream);

        mCircularBarPager = (CircularBarPager) findViewById(R.id.circularBarPager);

        /*----查找数据库，判断梦想状态*/
        Cursor cursor1 = sqLiteDatabase.rawQuery("select status from mydream ;",null);
            while (cursor1.moveToNext()) {
            myApplication.setStatus(cursor1.getString(0));
            shownum=myApplication.getStatus();
            }
          Log.e("@@@@@@@@","shownum="+shownum);
            if (shownum=="0"||"0".equals(shownum)){
                if (myApplication.getGoPain()==true){
                    add.setVisibility(View.GONE);
                    start_dream.setVisibility(View.GONE);
                    stop_dream.setVisibility(View.VISIBLE);
                    mCircularBarPager.setVisibility(View.VISIBLE);
                }else{
                    add.setVisibility(View.GONE);
                    start_dream.setVisibility(View.VISIBLE);
                    stop_dream.setVisibility(View.GONE);
                    mCircularBarPager.setVisibility(View.VISIBLE);
                }


            }else if(shownum=="1"||"1".equals(shownum)){
                add.setVisibility(View.VISIBLE);
                start_dream.setVisibility(View.GONE);
                stop_dream.setVisibility(View.GONE);
                mCircularBarPager.setVisibility(View.GONE);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    /*提醒要清除之前数据*/
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                ZklActivity.this,AlertDialog.THEME_HOLO_LIGHT);

                        builder.setIcon(R.drawable.jiazai);
                        builder.setTitle("添加梦想");
                        builder.setMessage("添加新梦想会清除已完成梦想的数据！确定要添加吗？");
                        builder.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {

                                        // 这里添加点击确定后的逻辑
                                        sqLiteDatabase.execSQL("delete from mydream ;");
                                        sqLiteDatabase.execSQL("delete from mystatus ;");
                                        myApplication.setStatus("-1");

                                        myApplication.setDreamTime("0");
                                        myApplication.setBreakTime("0");
                                        myApplication.setWastTime("0");
                               /* ------发送广播------*/
                                        Intent intent1 = new Intent();
                                        intent1.setAction("com.rao.myproject.Status");
                                        sendBroadcast(intent1);

                                        Intent intent=new Intent(ZklActivity.this,AddDreamActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        // 这里添加点击确定后的逻辑

                                    }
                                });
                        builder.create().show();
                    }
                });
            }else {

            add.setVisibility(View.VISIBLE);
            start_dream.setVisibility(View.GONE);
            stop_dream.setVisibility(View.GONE);
            mCircularBarPager.setVisibility(View.GONE);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ZklActivity.this,AddDreamActivity.class);
                    startActivity(intent);
                }
            });
            }

        /*----------开始和停止梦想--------*/
        start_dream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startMyDream();
            }
        });
        stop_dream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              stopMyDream();
            }
        });
        }


  public void startMyDream(){
    start_dream.setVisibility(View.GONE);
    stop_dream.setVisibility(View.VISIBLE);

    // 绑定服务到当前activity中
    final Intent intent = new Intent();
    // 指定开启服务的action
    intent.setAction("furao");

                /*@@@@@@@在普通的activity中绑定和解绑bindservice时用bindservice，但在Tab的activity中要用getApplicationContext().bindService@@@@@@@*/
    getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    Log.e("start------","start");
     myApplication.setGoPain(true);
    startTimer();
    }

    public  void stopMyDream(){
        start_dream.setVisibility(View.VISIBLE);
        stop_dream.setVisibility(View.GONE);
        getNowTime();

        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from mystatus ;",null);
        if (cursor2.getCount()==0){
            sqLiteDatabase.execSQL("insert into mystatus(date,time)  values(?,?);",
                    new Object[]{nowtime,""+binder.getCount()});
            myApplication.setTodayTime(binder.getCount());
            Log.e("stop------","stopn");

            // 解除绑定
            binder=null;
            getApplicationContext().unbindService(connection);
        }else{

            while (cursor2.moveToNext()) {
                if ( cursor2.getString(0).equals(nowtime)){
                    TodayFinishTime=Integer.parseInt(cursor2.getString(1));
                    sqLiteDatabase.execSQL("update mystatus set  time =? where date=? ;", new String[]{""+(TodayFinishTime+binder.getCount()),nowtime});
                    myApplication.setTodayTime(TodayFinishTime + binder.getCount());
                    // 解除绑定
                    binder=null;
                    getApplicationContext().unbindService(connection);
                    Log.e("stop------", "stopu");
                    break;
                }

            }
            if ( !cursor2.getString(0).equals(nowtime)){
                sqLiteDatabase.execSQL("insert into mystatus(date,time)  values(?,?);",
                        new Object[]{nowtime,""+binder.getCount()});

                myApplication.setTodayTime(binder.getCount());
                Log.e("stop------","stopi");

                // 解除绑定
                binder=null;
                getApplicationContext().unbindService(connection);
            }
        }
        myApplication.setGoPain(false);
        stopTimer();
    }

    private void updateProgressTwo() {
        getNowTime();
        cursor3 = sqLiteDatabase.rawQuery("select time from mystatus where date=?;",new String[]{nowtime});
        if (cursor3.getCount()!=0){
            while (cursor3.moveToNext()){
                try {
                    TodayFinishTime=Integer.parseInt(cursor3.getString(0));
                    Log.e("zkl",TodayFinishTime+"");
                    progress2=(TodayFinishTime*10)/(36*Integer.parseInt(myApplication.getDreamTime()));
                }catch (Exception e){}
            }

        }
        todayFinish(TodayFinishTime);
        progressTwo.setProgress(progress2);
        myApplication.setTodayTime(TodayFinishTime);
        updateProgressTwoColor();

    }

    private void updateProgressTwoColor() {
        if(progress2 <= 300) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress2 > 300 && progress2 <= 600) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress2 > 600) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

       mCircularBarPager.getCircularBar().animateProgress(0, 60, 1000);
        findView();
        updateProgressTwo();
        initViews();

    }

    private void initViews(){

        View[] views = new View[2];
        views[0] = new CircularInnerViewActivity(this);
        views[1] = new CircularInnerViewActivity(this);

        mCircularBarPager.setViewPagerAdapter(new CircularPagerAdapter(this, views));

        ViewPager viewPager = mCircularBarPager.getViewPager();
        viewPager.setClipToPadding(true);

        CirclePageIndicator circlePageIndicator = mCircularBarPager.getCirclePageIndicator();
        circlePageIndicator.setFillColor(getResources().getColor(R.color.light_grey));
        circlePageIndicator.setPageColor(getResources().getColor(R.color.very_light_grey));
        circlePageIndicator.setStrokeColor(getResources().getColor(R.color.transparent));

        //Do stuff based on animation

        mCircularBarPager.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //TODO do stuff

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        //Do stuff based on when pages change
        circlePageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(mCircularBarPager!= null && mCircularBarPager.getCircularBar() != null){
                    switch (position){
                        case 0:
                            mCircularBarPager.getCircularBar().animateProgress(-25, 100, BAR_ANIMATION_TIME);
                            break;
                        case 1:
                            mCircularBarPager.getCircularBar().animateProgress(100, -75, BAR_ANIMATION_TIME);
                            break;
                        default:
                            mCircularBarPager.getCircularBar().animateProgress(0, 75, BAR_ANIMATION_TIME);
                            break;
                    }
                }
            }
        });
    }
    /*广播接受收器*/
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            if (myApplication.getZklWhter().equals("time")) {
                progressTwo.setProgress(progress2);
                updateProgressTwoColor();
                todayFinish(myApplication.getTodayFinishTime());

            }
            else if (myApplication.getZklWhter().equals("dreamS")) {
                shownum = myApplication.getStatus();
                zkl_show_dream.setText(myApplication.getDreamTime() + "小时");
                zkl_show_break.setText(myApplication.getBreakTime() + "小时");
                zkl_show_wast.setText(myApplication.getWastTime() + "小时");
                if (shownum == "0" || "0".equals(shownum)) {

                    if (myApplication.getGoPain()==true){
                        add.setVisibility(View.GONE);
                        start_dream.setVisibility(View.GONE);
                        stop_dream.setVisibility(View.VISIBLE);
                        mCircularBarPager.setVisibility(View.VISIBLE);
                    }else{

                        add.setVisibility(View.GONE);
                        start_dream.setVisibility(View.VISIBLE);
                        stop_dream.setVisibility(View.GONE);
                        mCircularBarPager.setVisibility(View.VISIBLE);
                    }

                } else if (shownum == "1" || "1".equals(shownum)) {
                    add.setVisibility(View.VISIBLE);
                    start_dream.setVisibility(View.GONE);
                    stop_dream.setVisibility(View.GONE);
                    mCircularBarPager.setVisibility(View.GONE);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                    /*提醒要清除之前数据*/
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    ZklActivity.this, AlertDialog.THEME_HOLO_LIGHT);

                            builder.setIcon(R.drawable.jiazai);
                            builder.setTitle("添加梦想");
                            builder.setMessage("添加新梦想会清除已完成梦想的数据！确定要添加吗？");
                            builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            // 这里添加点击确定后的逻辑
                                            sqLiteDatabase.execSQL("delete from mydream ;");
                                            sqLiteDatabase.execSQL("delete from mystatus ;");
                                            myApplication.setStatus("-1");
                                            myApplication.setZklWhter("dreamS");
                               /* ------发送广播------*/
                                            Intent intent1 = new Intent();
                                            intent1.setAction("com.rao.myproject.Status");
                                            sendBroadcast(intent1);

                                            Intent intent = new Intent(ZklActivity.this, AddDreamActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            builder.setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            // 这里添加点击确定后的逻辑

                                        }
                                    });
                            builder.create().show();
                        }
                    });
                } else {
                    add.setVisibility(View.VISIBLE);
                    start_dream.setVisibility(View.GONE);
                    stop_dream.setVisibility(View.GONE);
                    mCircularBarPager.setVisibility(View.GONE);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ZklActivity.this, AddDreamActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }else{
                progressTwo.setProgress(0);
                todayFinish(0);
                shownum = myApplication.getStatus();
                zkl_show_dream.setText(myApplication.getDreamTime() + "小时");
                zkl_show_break.setText(myApplication.getBreakTime() + "小时");
                zkl_show_wast.setText(myApplication.getWastTime() + "小时");
                if (shownum == "0" || "0".equals(shownum)) {
                    add.setVisibility(View.GONE);
                    start_dream.setVisibility(View.VISIBLE);
                    stop_dream.setVisibility(View.GONE);
                    mCircularBarPager.setVisibility(View.VISIBLE);

                } else if (shownum == "1" || "1".equals(shownum)) {
                    add.setVisibility(View.VISIBLE);
                    start_dream.setVisibility(View.GONE);
                    stop_dream.setVisibility(View.GONE);
                    mCircularBarPager.setVisibility(View.GONE);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                    /*提醒要清除之前数据*/
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    ZklActivity.this, AlertDialog.THEME_HOLO_LIGHT);

                            builder.setIcon(R.drawable.jiazai);
                            builder.setTitle("添加梦想");
                            builder.setMessage("添加新梦想会清除已完成梦想的数据！确定要添加吗？");
                            builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            // 这里添加点击确定后的逻辑
                                            sqLiteDatabase.execSQL("delete from mydream ;");
                                            sqLiteDatabase.execSQL("delete from mystatus ;");
                                            myApplication.setStatus("-1");
                                            myApplication.setZklWhter("dreamS");
                                            myApplication.setTodayTime(0);
                               /* ------发送广播------*/
                                            Intent intent1 = new Intent();
                                            intent1.setAction("com.rao.myproject.Status");
                                            sendBroadcast(intent1);

                                            Intent intent = new Intent(ZklActivity.this, AddDreamActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                            builder.setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int whichButton) {
                                            // 这里添加点击确定后的逻辑

                                        }
                                    });
                            builder.create().show();
                        }
                    });
                } else {
                    add.setVisibility(View.VISIBLE);
                    start_dream.setVisibility(View.GONE);
                    stop_dream.setVisibility(View.GONE);
                    mCircularBarPager.setVisibility(View.GONE);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ZklActivity.this, AddDreamActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
}

    public void getNowTime() {
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
        nowtime   =   formatter.format(curDate);
    }

    //启动定时器
    private void startTimer() {
		/* 启动定时器，每5秒自动切换展示图 */
        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
            Log.e("******",binder.getCount()+"");
            progress2=10*(myApplication.getTodayTime()+binder.getCount())/(36*Integer.parseInt(myApplication.getDreamTime()));
                /* ------发送广播------*/
                    myApplication.setZklWhter("time");
                    myApplication.setTodayFinishTime(myApplication.getTodayTime()+binder.getCount());

                    Log.e("$$$$$$$","progress2="+progress2);
                    Intent intent2 = new Intent();
                    intent2.setAction("com.rao.myproject.Status");
                    sendBroadcast(intent2);

                    if (myApplication.getTodayFinishTime()>=3600*Integer.parseInt(myApplication.getDreamTime())){

                        // 解除绑定
                        binder=null;
                        getApplicationContext().unbindService(connection);
                        stopTimer();

                        myApplication.setStatus("0");
                        myApplication.setZklWhter("dreamS");

                        myApplication.setTodayTime(Integer.parseInt(myApplication.getDreamTime()));
                        sqLiteDatabase.execSQL("update mystatus set  time =? where date=? ;", new String[]{""+Integer.parseInt(myApplication.getDreamTime()),nowtime});
                    }
                }
            };
        }

        if (time == null) {
            time = new Timer();
        }
        time.schedule(task, 1000, 1000);
    }
    //关闭定时器
    private void stopTimer() {
		/* 暂停定时器 */
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (time != null) {
            time.cancel();
            time = null;
        }
    }


    private void todayFinish(int time){
        int H=time/3600;
        int M=(time%3600)/60;
        int S=(time%3600)%60;
        String HH,MM, SS;
        if (H<10){HH="0"+H;}else{HH=""+H;}
        if (M<10){MM="0"+M;}else{MM=""+M;}
        if (S<10){SS="0"+S;}else{SS=""+S;}
        today_finishtime.setText(HH+":"+MM+":"+SS);
    }
}
