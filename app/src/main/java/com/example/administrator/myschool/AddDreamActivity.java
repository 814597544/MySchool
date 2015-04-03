package com.example.administrator.myschool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mydialog.DateTimePickerDialog;
import com.mydialog.LoadingDialog;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;
import com.rao.MySchool.been.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/3/26.
 */
public class AddDreamActivity extends Activity{
    TextView titleName,add_dream_avgtime,start_rili_value,end_rili_value;
    LinearLayout title_return,dream_endtime;
    EditText add_dream_name,add_dream_alltime;
    Button add_finish;
    String dreamName,allTime;
    RelativeLayout dream_starttime;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    LoadingDialog dialog;
    private MyApplication myApplication;
    private static final int START_RILI=1;
    private static final int END_RILI=2;
    private String startData,endData;
    private int betweenDays;
    double angt=8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dream_layout);

        findView();
        Click();
    }

    private void Click() {
        add_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dreamName=add_dream_name.getText().toString().trim();
                allTime=add_dream_alltime.getText().toString().trim();
                try {
                    betweenDays=daysBetween(startData, endData);
                    angt=Double.parseDouble(add_dream_alltime.getText().toString())/betweenDays;
                }catch (Exception e){}

                if (dreamName.equals("")||allTime.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请补全信息", Toast.LENGTH_SHORT).show();
                }else if(angt<=0||angt>12){
                    Toast.makeText(getApplicationContext(),
                            "每天奋斗的时间只能在0-12之间,请修改日期或梦想时间", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.show();

                    sqLiteDatabase.execSQL("insert into mydream(dreamname,needtime,avgtime,begaintime,endtime,status)" +
                                    " values(?,?,?,?,?,?);",
                            new Object[]{dreamName,allTime,format(angt)+"",startData,endData, "0"});

                    new Thread(){
                        @Override
                        public void run() {
                            Message msg=new Message();
                            msg.what=1;
                            handler.sendMessageDelayed(msg, 2000);

                        }
                    }.start();
                }

            }
        });

        dream_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(START_RILI);
            }
        });

        dream_endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StringUtils.isEmpty(start_rili_value.getText().toString())&&!StringUtils.isEmpty(add_dream_alltime.getText().toString().trim())) {
                    show(END_RILI);
                }else
                {
                    Toast.makeText(getApplicationContext(),"先编辑所需时间",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findView() {
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        dialog = new LoadingDialog(this);
        myApplication= (MyApplication) getApplication();

        dream_starttime= (RelativeLayout) findViewById(R.id.dream_starttime);
        dream_endtime= (LinearLayout) findViewById(R.id.dream_endtime);
        titleName= (TextView) findViewById(R.id.title);
        titleName.setText("添加梦想");
        title_return= (LinearLayout) findViewById(R.id.title_return);
        title_return.setVisibility(View.VISIBLE);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_dream_name= (EditText) findViewById(R.id.add_dream_name);
        add_dream_alltime= (EditText) findViewById(R.id.add_dream_alltime);
        add_dream_avgtime= (TextView) findViewById(R.id.add_dream_avgtime);
        start_rili_value= (TextView) findViewById(R.id.start_rili_value);
        end_rili_value= (TextView) findViewById(R.id.end_rili_value);
        add_finish= (Button) findViewById(R.id.add_finish);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        "添加成功", Toast.LENGTH_SHORT).show();

       /*------发送广播------*/
                myApplication.setStatus("0");
                myApplication.setDreamTime(format(angt)+"");
                myApplication.setBreakTime("11");
                myApplication.setZklWhter("dreamS");
                myApplication.setWastTime(13 - format(angt)+"");
                Intent intent = new Intent();
                intent.setAction("com.rao.myproject.Status");
                sendBroadcast(intent);

                finish();
            }else{
                Toast.makeText(getApplicationContext(),
                        "添加失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     *字符串的日期格式的计算
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24)+1;

        return Integer.parseInt(String.valueOf(between_days));
    }


    private boolean isBefore(String curTime,String selectTime) throws ParseException{

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Date bt=sdf.parse(curTime);

        Date et=sdf.parse(selectTime);

        if (bt.before(et)||bt.equals(et)){

            return true;

        }else{

            return false;

        }



    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd
     *
     */
    public  String getStringDate(Long date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        return dateString;
    }

/*-------------计算每天要完成的梦想时间*/
    private void goalTime(){
        try {
            betweenDays=daysBetween(startData, endData);
            double d=Double.parseDouble(add_dream_alltime.getText().toString())/betweenDays;
            add_dream_avgtime.setText(format(d)+"");

        }catch (ParseException e){

        }
    }
/*----------选择日期-----------------------*/
    public void show(final int flag )
    {
        DateTimePickerDialog dialog  = new DateTimePickerDialog(AddDreamActivity.this, System.currentTimeMillis());
        dialog.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener()
        {
            public void OnDateTimeSet(AlertDialog dialog, long date)
            {   try {
                if (flag==START_RILI){

                    if (isBefore(getStringDate(System.currentTimeMillis()),getStringDate(date))) {
                        start_rili_value.setText(getStringDate(date));
                        startData = getStringDate(date);
                    }else {
                        Toast.makeText(getApplicationContext(),"亲，您不能穿越到过去！",Toast.LENGTH_SHORT).show();
                    }

                }else if (flag==END_RILI){

                    endData=getStringDate(date);

                    int j=Integer.parseInt(add_dream_alltime.getText().toString().trim())/13;
                    int i=Integer.parseInt(add_dream_alltime.getText().toString().trim())%13==0?j:j+1;
                    if(daysBetween(startData, endData)>=i){
                        end_rili_value.setText(getStringDate(date));
                        goalTime();
                    }else {

                        Toast.makeText(getApplicationContext(),"亲，此梦想完成时间不能低于"+i+"天！",Toast.LENGTH_SHORT).show();
                    }



                }
            }catch (Exception e){

            }

            }

        });
        dialog.show();
    }

    public double format(double f){
        BigDecimal b   =   new   BigDecimal(f);
        double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
}
