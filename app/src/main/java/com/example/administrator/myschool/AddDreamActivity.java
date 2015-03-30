package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lodingdialog.LoadingDialog;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;

/**
 * Created by Administrator on 2015/3/26.
 */
public class AddDreamActivity extends Activity{
    TextView titleName;
    LinearLayout title_return;
    EditText add_dream_name,add_dream_alltime,add_dream_avgtime;
    Button add_finish;
    String dreamName,allTime,avgTime;
    String startDate=null,endDate=null;

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    LoadingDialog dialog;
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dream_layout);

        findView();
    }

    private void findView() {
        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        dialog = new LoadingDialog(this);
        myApplication= (MyApplication) getApplication();

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
        add_dream_avgtime= (EditText) findViewById(R.id.add_dream_avgtime);
        add_finish= (Button) findViewById(R.id.add_finish);
        add_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dreamName=add_dream_name.getText().toString().trim();
                allTime=add_dream_alltime.getText().toString().trim();
                avgTime=add_dream_avgtime.getText().toString().trim();
                int angt=8;
                try {
                    angt=Integer.parseInt(avgTime);
                }catch (Exception e){}

                if (dreamName.equals("")||allTime.equals("")||avgTime.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "请补全信息", Toast.LENGTH_SHORT).show();
                }else if(angt<=0||angt>12){
                    Toast.makeText(getApplicationContext(),
                            "每天奋斗的时间只能在0-12之间", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.show();
                    sqLiteDatabase.execSQL("insert into mydream(dreamname,needtime,avgtime,begaintime,endtime,status)" +
                                    " values(?,?,?,?,?,?);",
                            new Object[]{dreamName,allTime,avgTime,startDate,endDate, "0"});

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
                myApplication.setDreamTime(avgTime);
                myApplication.setBreakTime("11");
                myApplication.setWastTime(13 - Integer.parseInt(avgTime)+"");
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
}
