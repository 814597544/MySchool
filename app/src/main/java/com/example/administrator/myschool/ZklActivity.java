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
import android.os.PowerManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nineoldandroids.animation.Animator;
import com.rao.MySchool.adapter.CircularPagerAdapter;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;
import com.rao.MySchool.inteface.UpdateZKL;
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
    private ImageView add,start_dream,stop_dream,run1,run2,run3;
    private ProgressBar run11,run22,run33;
    private int progress2 = 0,progress1=0;
    private CircularBarPager mCircularBarPager;
    TextView titleName,zkl_show_dream,zkl_show_wast,zkl_show_break,today_finishtime;
    String shownum = null,MyDreamTime=null;


    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private MyApplication myApplication;
    private MyReceiver myReceiver;



    private TimerTask task = null;																			//定时器任务（用于首页Gallery切换）
    private Timer time = null	;
    int TodayFinishTime=0,AllFinish=0,sTime=-1,eTime=1,nTime=0;
    private static final int BAR_ANIMATION_TIME = 1000;

    UpdateZKL mstatus;
    CircularInnerViewActivity mcurActy;
    Cursor cursor3;
    MyBindService.MyBinder binder;
    PowerManager.WakeLock mWakeLock;


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
        initViews();
        updateProgressTwo();
    }


    private void findView() {

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        myApplication= (MyApplication) getApplication();
        getNowTime();
        gainDreamTime();
        if (nTime>eTime){
            isEnd();
        }
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
                        myApplication.setWastTime(13 - Double.parseDouble(cursor.getString(2)) + "");
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
        run1= (ImageView) findViewById(R.id.run1);
        run2= (ImageView) findViewById(R.id.run2);
        run3= (ImageView) findViewById(R.id.run3);
        run11= (ProgressBar) findViewById(R.id.run11);
        run22= (ProgressBar) findViewById(R.id.run22);
        run33= (ProgressBar) findViewById(R.id.run33);
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
                run1.setVisibility(View.VISIBLE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                today_finishtime.setText("00:00:00");
                progressTwo.setProgress(0);

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
                run1.setVisibility(View.VISIBLE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                today_finishtime.setText("00:00:00");
                progressTwo.setProgress(0);

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
             if (nTime<sTime){
               Toast.makeText(getApplicationContext(), "亲，未到开始日期，不能开始梦想！", Toast.LENGTH_SHORT).show();
             }else{
                 startMyDream();
             }
            }
        });
        stop_dream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              stopMyDream();
              updateProgressTwoColor();
            }
        });

        }


  public void startMyDream(){
      myApplication.setRun(true);
    start_dream.setVisibility(View.GONE);
    stop_dream.setVisibility(View.VISIBLE);
    mstatus.updateName("暂停");
    // 绑定服务到当前activity中
    final Intent intent = new Intent(ZklActivity.this,MyBindService.class);
    // 指定开启服务的action
    intent.setAction("furao");

                /*@@@@@@@在普通的activity中绑定和解绑bindservice时用bindservice，但在Tab的activity中要用getApplicationContext().bindService@@@@@@@*/
    getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
     Log.e("start------","start");
     myApplication.setGoPain(true);
     startTimer();
    }

    public  void stopMyDream(){
        myApplication.setRun(false);
        start_dream.setVisibility(View.VISIBLE);
        stop_dream.setVisibility(View.GONE);
        mstatus.updateName("开始");
        AllFinish+=binder.getCount();

        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from mystatus ;",null);
        if (cursor2.getCount()==0){
            sqLiteDatabase.execSQL("insert into mystatus(date,time)  values(?,?);",
                    new Object[]{myApplication.getTodayDate(),""+binder.getCount()});
            myApplication.setTodayTime(binder.getCount());
            Log.e("stop------","stopn");
        }else{
          String lastDate=null;
            while (cursor2.moveToNext()) {
                lastDate=cursor2.getString(0);
                if ( cursor2.getString(0).equals(myApplication.getTodayDate())){
                    TodayFinishTime=Integer.parseInt(cursor2.getString(1));
                    sqLiteDatabase.execSQL("update mystatus set  time =? where date=? ;", new String[]{""+(TodayFinishTime+binder.getCount()),myApplication.getTodayDate()});
                    myApplication.setTodayTime(TodayFinishTime + binder.getCount());

                    Log.e("stop------", "stopu");
                    break;
                }

            }
            if ( !lastDate.equals(myApplication.getTodayDate())){
                sqLiteDatabase.execSQL("insert into mystatus(date,time)  values(?,?);",
                        new Object[]{myApplication.getTodayDate(),""+binder.getCount()});

                myApplication.setTodayTime(binder.getCount());
                Log.e("stop------","stopi");
            }
        }
           // 解除绑定
           binder=null;
           getApplicationContext().unbindService(connection);

           myApplication.setGoPain(false);
           stopTimer();
    }

    private void updateProgressTwo() {
        showAvg();
        showAll();
        todayFinish(TodayFinishTime);
        progressTwo.setProgress(progress2);
        Log.e("zkl","progress1="+progress1);
        mCircularBarPager.getCircularBar().animateProgress(0, progress1, 1000);
        mstatus.updatePer(progress1+"%");
        myApplication.setTodayTime(TodayFinishTime);
        updateProgressTwoColor();

    }

    /*--------计算总梦想的进度-----------*/
    private void showAll() {
        cursor3 = sqLiteDatabase.rawQuery("select time from mystatus ;",null);
        while (cursor3.moveToNext()) {
            AllFinish+=(Double.parseDouble(cursor3.getString(0))-1);

            Log.e("zkl","AllFinish="+AllFinish);
        }
        Cursor cus = sqLiteDatabase.rawQuery("select needtime from mydream ;",null);
        while (cus.moveToNext()) {
            myApplication.setAllTime(Integer.parseInt(cus.getString(0)));
            progress1=AllFinish/(36*myApplication.getAllTime());
        }
    }

    /*-----计算每天的进度条与时间---------*/
    private void showAvg() {
        cursor3 = sqLiteDatabase.rawQuery("select time from mystatus where date=?;",new String[]{myApplication.getTodayDate()});
        if (cursor3.getCount()!=0){

            Cursor c = sqLiteDatabase.rawQuery("select status from mydream ;",null);
            String s=null;
            while (c.moveToNext()) {
                s=c.getString(0);
            }

            if (!"1".equals(s)){
                while (cursor3.moveToNext()){
                    try {
                        TodayFinishTime=(int)(Double.parseDouble(cursor3.getString(0))-1);
                        Log.e("zkl----TodayFinishTime",TodayFinishTime+"");
                        progress2=(int)((TodayFinishTime*10)/(36*Double.parseDouble(myApplication.getDreamTime())));
                    }catch (Exception e){}
                }

            }
    }
}

    private void updateProgressTwoColor() {
        if(progress2 <= 300) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
            if (myApplication.getRun()){
                run1.setVisibility(View.GONE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                run11.setVisibility(View.VISIBLE);
                run22.setVisibility(View.GONE);
                run33.setVisibility(View.GONE);
            }else {
                run1.setVisibility(View.VISIBLE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                run11.setVisibility(View.GONE);
                run22.setVisibility(View.GONE);
                run33.setVisibility(View.GONE);
            }

        } else if(progress2 > 300 && progress2 <= 750) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
            if (myApplication.getRun()){
                run1.setVisibility(View.GONE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                run11.setVisibility(View.GONE);
                run22.setVisibility(View.VISIBLE);
                run33.setVisibility(View.GONE);
            }else {
                run1.setVisibility(View.GONE);
                run2.setVisibility(View.VISIBLE);
                run3.setVisibility(View.GONE);
                run11.setVisibility(View.GONE);
                run22.setVisibility(View.GONE);
                run33.setVisibility(View.GONE);
            }
        } else if(progress2 > 750) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
            if (myApplication.getRun()){
                run1.setVisibility(View.GONE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                run11.setVisibility(View.GONE);
                run22.setVisibility(View.GONE);
                run33.setVisibility(View.VISIBLE);
            }else {
                run1.setVisibility(View.GONE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.VISIBLE);
                run11.setVisibility(View.GONE);
                run22.setVisibility(View.GONE);
                run33.setVisibility(View.GONE);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        releaseWakeLock();
      /*  getNowTime();
        Log.e("time--",myApplication.getTodayDate());*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        acquireWakeLock();
    }

    private void initViews(){

        View[] views = new View[1];
        //views[1] = new CircularInnerViewActivity(this);
        mcurActy=new CircularInnerViewActivity(this);
        views[0] = mcurActy;
        mstatus=(UpdateZKL)mcurActy;
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

    public void gainDreamTime() {
        Cursor cos = sqLiteDatabase.rawQuery("select * from mydream ;",null);
        if (cos.getCount()!=0){
        while (cos.moveToNext()) {
            myApplication.setStartTime(cos.getString(3));
            myApplication.setEndTime(cos.getString(4));
        }
        String s="",e="",n="";
        String[] st=myApplication.getStartTime().split("-");
        String[] et=myApplication.getEndTime().split("-");
        String[] nt=myApplication.getTodayDate().split("-");
        for (int i=0;i<st.length;i++){
            s+=st[i];
            e+=et[i];
            n+=nt[i];
        }

        sTime=Integer.parseInt(s);
        eTime=Integer.parseInt(e);
        nTime=Integer.parseInt(n);
          Log.e("sTime",sTime+"");
           Log.e("eTime",eTime+"");
            Log.e("nTime",nTime+"");
    }}
    public void  isEnd(){
        sqLiteDatabase.execSQL("update mydream  set status=? where status=?;",new String[]{"1","0"});
        myApplication.setStatus("1");
        myApplication.setDreamTime("0");
        myApplication.setBreakTime("0");
        myApplication.setWastTime("0");
        myApplication.setTodayTime(0);
        myApplication.setZklWhter("delete");

        Intent intent1 = new Intent();
        intent1.setAction("com.rao.myproject.Status");
        sendBroadcast(intent1);
    }
    /*广播接受收器*/
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {

            if ("time".equals(myApplication.getZklWhter())) {
                progressTwo.setProgress(progress2);
                mCircularBarPager.getCircularBar().animateProgress(progress1, myApplication.getProGress(), 1000);
                mstatus.updatePer(myApplication.getProGress()+"%");
                updateProgressTwoColor();
                todayFinish(myApplication.getTodayFinishTime());

            }else if ("dreamS".equals(myApplication.getZklWhter())) {
                gainDreamTime();
                updateProgressTwoColor();
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
                        mCircularBarPager.getCircularBar().animateProgress(0, 0, 1000);
                    }else{
                        add.setVisibility(View.GONE);
                        start_dream.setVisibility(View.VISIBLE);
                        stop_dream.setVisibility(View.GONE);
                        mCircularBarPager.setVisibility(View.VISIBLE);
                        mCircularBarPager.getCircularBar().animateProgress(0, 0, 1000);
                        mstatus.updateName("开始");
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
            }
            else {
                if (binder != null) {
                    // 解除绑定
                    binder = null;
                    getApplicationContext().unbindService(connection);
                    stopTimer();
                }
                gainDreamTime();
                myApplication.setProGress(0);
                AllFinish=0;
                mCircularBarPager.getCircularBar().animateProgress(0, 0, 1000);
                mstatus.updatePer("0%");
                run1.setVisibility(View.VISIBLE);
                run2.setVisibility(View.GONE);
                run3.setVisibility(View.GONE);
                myApplication.setGoPain(false);
                today_finishtime.setText("00:00:00");
                progressTwo.setProgress(0);

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
        String nowtime   =   formatter.format(curDate);
        myApplication.setTodayDate(nowtime);
    }

    //启动定时器
    private void startTimer() {
		/* 启动定时器，每5秒自动切换展示图 */
        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {

            Log.e("******",binder.getCount()+"");
            progress1=(AllFinish+binder.getCount())/(36*myApplication.getAllTime());
            myApplication.setProGress( (1+AllFinish+binder.getCount())/(36*myApplication.getAllTime()));
            progress2=(int)(10*(myApplication.getTodayTime()+binder.getCount())/(36*Double.parseDouble(myApplication.getDreamTime())));
                /* ------发送广播------*/
                    myApplication.setZklWhter("time");
                    myApplication.setTodayFinishTime(myApplication.getTodayTime()+binder.getCount());
                    //如果完成当天的目标，则停止计时并自动保存数据
                    if (myApplication.getTodayFinishTime()>3600*Double.parseDouble(myApplication.getDreamTime())){
                        // 解除绑定
                        binder=null;
                        getApplicationContext().unbindService(connection);
                        stopTimer();

                        myApplication.setRun(false);
                        myApplication.setStatus("0");
                        myApplication.setZklWhter("dreamS");
                        myApplication.setGoPain(false);
                        myApplication.setTodayTime(myApplication.getTodayFinishTime());
                        Cursor cursorm = sqLiteDatabase.rawQuery("select time from mystatus where date=?;",new String[]{myApplication.getTodayDate()});
                        if (cursorm.getCount()==0){
                            sqLiteDatabase.execSQL("insert into mystatus(date,time)  values(?,?);",
                                    new Object[]{myApplication.getTodayDate(),""+myApplication.getTodayFinishTime()});
                        }else{
                            sqLiteDatabase.execSQL("update mystatus set  time =? where date=? ;", new String[]{""+myApplication.getTodayFinishTime(),myApplication.getTodayDate()});
                        }
                       // 如果是梦想的最后一天，梦想结束并自动保存数据
                        if (myApplication.getTodayDate().equals(myApplication.getEndTime())){
                            isEnd();
                            myApplication.setStatus("1");
                        }
                    }

                    Log.e("$$$$$$$","progress2="+progress2);
                    Intent intent2 = new Intent();
                    intent2.setAction("com.rao.myproject.Status");
                    sendBroadcast(intent2);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myApplication.setGoPain(false);
        if (binder!=null){
        stopMyDream();
     }
    }

    //申请设备电源锁
    private void acquireWakeLock()
    {
        if (null == mWakeLock)
        {
            PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE,"");
            if (null != mWakeLock)
            {
                mWakeLock.acquire();
            }
        }
    }

    //释放设备电源锁
    private void releaseWakeLock()
    {
        if (null != mWakeLock)
        {
            mWakeLock.release();
            mWakeLock = null;
        }
    }


}
