package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rao.MySchool.shake.ShakeListenerUtils;

import java.util.Random;

/**
 * Created by Administrator on 2015/3/9.
 */
public class YaoYiYaoActivity extends Activity{
    TextView title;
    LinearLayout title_return;
    private Vibrator vibrator;//震动
    /** 摇之前 遥之后 ,隐藏的 */
    private ImageView imView, imcount;
    private TextView textgain;
    /**监听*/
    private ShakeListenerUtils shakeListener;
    private Button btnBack;

    private	int icon[] = { R.drawable.image_left, R.drawable.image_middle,R.drawable.image_right };

    private	int index = 0;

    private int randomC=0;

    private Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yaoyiyao_activity);
        init();

        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void init(){
        title= (TextView) findViewById(R.id.title);
        title.setText("摇一摇");
        title_return= (LinearLayout) findViewById(R.id.title_return);
        title_return.setVisibility(View.VISIBLE);

        vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        imView = (ImageView) findViewById(R.id.imgmiddle);
        textgain = (TextView) findViewById(R.id.textgain);
        random=new Random();
        shakeListener = new ShakeListenerUtils(this);
        shakeListener.setOnShake(onShake);
    }


    /** 重力感应 */
    private ShakeListenerUtils.OnShakeListener onShake = new ShakeListenerUtils.OnShakeListener() {

        @Override
        public void onShake() {
            textgain.setText("官人，惊喜马上就来！");
           // startVibrator();
            shakeListener.stop();

            handler.sendEmptyMessageDelayed(1, 200);
            handler.sendEmptyMessageDelayed(2, 2000);

            randomC=random.nextInt(13);
            Log.e("--", "---"+randomC);

        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (index < icon.length - 1) {
                    index++;
                } else {
                    index = 0;
                }
                imView.setBackgroundResource(icon[index]);
                handler.sendEmptyMessageDelayed(1, 200);
            } else {
                imView.setBackgroundResource(R.drawable.lottery_result);
                handler.removeMessages(1);
                shakeListener.start();
                if(randomC==1){
                    textgain.setText(R.string.gain_1);

                }
               else if(randomC==2){
                    textgain.setText(R.string.gain_2);

                }
                else if(randomC==3){
                    textgain.setText(R.string.gain_3);

                }
                else if(randomC==4){
                    textgain.setText(R.string.gain_4);

                }
                else if(randomC==5){
                    textgain.setText(R.string.gain_5);

                }
                else if(randomC==6){
                    textgain.setText(R.string.gain_6);

                }
                else if(randomC==7){
                    textgain.setText(R.string.gain_7);

                }
                else if(randomC==8){
                    textgain.setText(R.string.gain_8);

                }
                else if(randomC==9){
                    textgain.setText(R.string.gain_9);

                }
                else if(randomC==10){
                    textgain.setText(R.string.gain_10);

                }
                else if(randomC==11){
                    textgain.setText(R.string.gain_11);

                }
                else if(randomC==12){
                    textgain.setText(R.string.gain_12);

                }else{
                    textgain.setText(R.string.gain_nothing);

                }

            }
        }

    };

    /**
     * 播放振动效果
     */
    public void startVibrator() {
        vibrator.vibrate(new long[] { 500, 300, 500, 300 }, -1);
    }
}
