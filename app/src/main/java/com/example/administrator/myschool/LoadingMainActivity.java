package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.waterLoading.Titanic;
import com.waterLoading.TitanicTextView;
import com.waterLoading.Typefaces;

/**
 * Created by Administrator on 2015/3/16.
 */
public class LoadingMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterloading_main);

        new Thread(){
            @Override
            public void run() {
                Message msg=new Message();
                msg.what=1;
                handler.sendMessageDelayed(msg, 6000);
            }
        }.start();

        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface
        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);

    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                Intent intent=new Intent(LoadingMainActivity.this,MainActivity.class);
                startActivity(intent);

                finish();
            }
        }
    };


}
