package com.example.administrator.myschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nineoldandroids.animation.Animator;
import com.rao.MySchool.adapter.CircularPagerAdapter;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;
import com.rao.MySchool.service.BindService;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.nineoldandroids.animation.Animator.*;

/**
 * Created by Administrator on 2015/3/6.
 */
public class ZklActivity extends Activity{
    private RoundCornerProgressBar progressTwo;
    private ImageView add;

    private int progress2 = 0;
    private CircularBarPager mCircularBarPager;
    TextView titleName,zkl_show_dream,zkl_show_wast,zkl_show_break;
    String shownum = null,nowtime,MyDreamTime=null;
    boolean MyBoolean=false;

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private MyApplication myApplication;
    private MyReceiver myReceiver;
    /**
     * The animation time in milliseconds that we take to display the steps taken
     */
    private static final int BAR_ANIMATION_TIME = 1000;

    BindService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("zkl", "--Service Disconnected--");
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 取得Service对象中的Binder对象
            binder = (BindService.MyBinder) service;
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
        mCircularBarPager = (CircularBarPager) findViewById(R.id.circularBarPager);

        /*----查找数据库，判断梦想状态*/
        Cursor cursor1 = sqLiteDatabase.rawQuery("select status from mydream ;",null);
            while (cursor1.moveToNext()) {
            myApplication.setStatus(cursor1.getString(0));
            shownum=myApplication.getStatus();
            }
          Log.e("@@@@@@@@","shownum="+shownum);
            if (shownum=="0"||"0".equals(shownum)){
                add.setVisibility(View.GONE);
                mCircularBarPager.setVisibility(View.VISIBLE);

            }else if(shownum=="1"||"1".equals(shownum)){
                add.setVisibility(View.VISIBLE);
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
                                        sqLiteDatabase.execSQL("delete from mydream where status=?;",new String[]{"1"});
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
            mCircularBarPager.setVisibility(View.GONE);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ZklActivity.this,AddDreamActivity.class);
                    startActivity(intent);
                }
            });
            }
        }


    private void updateProgressTwo() {
        getNowTime();
        Cursor cursor3 = sqLiteDatabase.rawQuery("select time from mystatus where date=?;",new String[]{nowtime});

        if (cursor3.getCount()==0){
            try{
                Log.e("111111","========"+Integer.parseInt(MyDreamTime));
                progress2=binder.getCount()/(3600*Integer.parseInt(MyDreamTime));
                Log.e("22222","2222222222222"+progress2);
            }catch (Exception e){}

        }else{
            while (cursor3.moveToNext()){
                try {
                    progress2=(Integer.parseInt(cursor3.getString(0))+binder.getCount())/(3600*Integer.parseInt(myApplication.getDreamTime()));
                             /* ------发送广播------*/
                    Log.e("$$$$$$$","progress2="+progress2);
                    Intent intent2 = new Intent();
                    intent2.setAction("com.rao.myproject.Status");
                    sendBroadcast(intent2);
                }catch (Exception e){}
            }
        }

        progressTwo.setProgress(progress2);
        updateProgressTwoColor();

    }

    private void updateProgressTwoColor() {
        if(progress2 <= 3) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress2 > 3 && progress2 <= 6) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress2 > 6) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCircularBarPager.getCircularBar().animateProgress(0, 0, 1000);

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
       /*----------开始和停止梦想--------*/
        mCircularBarPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "zkl", Toast.LENGTH_SHORT).show();
                getNowTime();

                if (MyBoolean==false){
                    final Intent intentg = new Intent();
                    // 指定开启服务的action
                    intentg.setAction("com.bgxt.BindServiceDemo");
                    // 绑定服务到当前activity中
                    bindService(intentg, conn, Service.BIND_AUTO_CREATE);
                    MyBoolean=true;

                }else{
                    Cursor cursor2 = sqLiteDatabase.rawQuery("select date from mystatus ;",null);
                    while (cursor2.moveToNext()) {
                        if ( cursor2.getString(0).equals(nowtime)){
                            binder=null;
                            unbindService(conn);

                 sqLiteDatabase.execSQL("update mystatus set  time =? where date=? ;", new String[]{""+binder.getCount(),nowtime});
                            MyBoolean=false;
                            break;
                        }else{
                            // 解除绑定
                            binder=null;
                            unbindService(conn);

                            sqLiteDatabase.execSQL("insert into mystatus(date,time)  values(?,?);",
                                    new Object[]{nowtime,""+binder.getCount()});

                            MyBoolean=false;
                            break;
                        }

                    }
                }


            }
        });



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
            progressTwo.setProgress(progress2);
            shownum=myApplication.getStatus();
            zkl_show_dream.setText(myApplication.getDreamTime()+"小时");
            zkl_show_break.setText(myApplication.getBreakTime()+"小时");
            zkl_show_wast.setText(myApplication.getWastTime() + "小时");
        if (shownum=="0"||"0".equals(shownum)){
            add.setVisibility(View.GONE);
            mCircularBarPager.setVisibility(View.VISIBLE);

        }else if(shownum=="1"||"1".equals(shownum)){
            add.setVisibility(View.VISIBLE);
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
                                    sqLiteDatabase.execSQL("delete from mydream where status=?;",new String[]{"1"});
                                    myApplication.setStatus("-1");
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
        }else{
                add.setVisibility(View.VISIBLE);
                mCircularBarPager.setVisibility(View.GONE);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ZklActivity.this,AddDreamActivity.class);
                        startActivity(intent);
                    }
                });
       }
    }
}

    public void getNowTime() {
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
        nowtime   =   formatter.format(curDate);
    }

}
