package com.example.administrator.myschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.UMWXHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;



import java.util.Random;

/**
 * Created by Administrator on 2015/3/9.
 */
public class YaoYiYaoActivity extends Activity{
    TextView title,title_right;
    LinearLayout title_return,finish;
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
        shareData();

        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);
                mController.openShare(YaoYiYaoActivity.this, false);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }
    public void init(){

        title= (TextView) findViewById(R.id.title);
        title.setText("摇一摇");
        title_return= (LinearLayout) findViewById(R.id.title_return);
        title_return.setVisibility(View.VISIBLE);
        title_right= (TextView) findViewById(R.id.title_right);
        title_right.setText("分享");
        finish= (LinearLayout) findViewById(R.id.finish);
        finish.setVisibility(View.VISIBLE);


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
            textgain.setText("客官，惊喜马上就来！");
            startVibrator();
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

    /**
     * 一键分享
     */
    private void shareData() {

        SocializeConstants.APPKEY = "550a2455fd98c5b388001b58";
        // 首先在您的Activity中添加如下成员变量
        final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);

        // 设置分享内容
        mController.setShareContent("我在水月先生的毕业设计摇一摇中获得收获：与其临渊羡鱼，不如退而结网！");


        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appID = "wx88818f8c48a95eb4";
        // 微信图文分享必须设置一个url
        String contentUrl = "http://www.umeng.com/social";
        // 添加微信平台，参数1为当前Activity, 参数2为用户申请的AppID, 参数3为点击分享内容跳转到的目标url
         UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(this,appID, contentUrl);

        //设置分享标题
        wxHandler.setWXTitle("MyGraduateProject");
        // 支持微信朋友圈
        UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(this,appID, contentUrl) ;
        circleHandler.setCircleTitle("我在水月先生的毕业设计摇一摇中获得收获：有志者自有千计万计，无志者只感千难万难！");
        circleHandler.setToCircle(true);

        //  参数1为当前Activity， 参数2为用户点击分享内容时跳转到的目标地址
        mController.getConfig().supportQQPlatform(this, "http://www.umeng.com/social");

        mController.getConfig().setSsoHandler(new QZoneSsoHandler(this));

        //设置腾讯微博SSO handler
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",RequestType.SOCIAL);

        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
