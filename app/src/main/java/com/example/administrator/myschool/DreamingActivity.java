package com.example.administrator.myschool;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.circlechart.MagnificentChart;
import com.circlechart.MagnificentChartItem;
import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BaseEasingMethod;
import com.db.chart.view.animation.easing.quint.QuintEaseOut;
import com.lodingdialog.LoadingDialog;
import com.rao.MySchool.been.DatabaseHelper;
import com.rao.MySchool.been.MyApplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/26.
 */
public class DreamingActivity  extends Activity {
    TextView titleName,show_dreamName,title_right;
    TextView tv_dream,tv_break,tv_wast;
    LinearLayout title_return,finish,have_dream,no_dream;
    MagnificentChart magnificentChart;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private MyApplication myApplication;
    LoadingDialog dialog1;
    int Dream=0,Break=0,Wast=0;

    private final TimeInterpolator enterInterpolator = new DecelerateInterpolator(1.5f);
    private final TimeInterpolator exitInterpolator = new AccelerateInterpolator();

    private final static int[] beginOrder = {0, 1, 2, 3, 4, 5, 6};
    private final static int[] middleOrder = {3, 2, 4, 1, 5, 0, 6};
    private final static int[] endOrder = {6, 5, 4, 3, 2, 1, 0};
    private static float mCurrOverlapFactor;
    private static int[] mCurrOverlapOrder;
    private static float mOldOverlapFactor;
    private static int[] mOldOverlapOrder;


    /**
     * Ease
     */
    private static ImageButton mEaseBtn;
    private static BaseEasingMethod mCurrEasing;
    private static BaseEasingMethod mOldEasing;


    /**
     * Enter
     */
    private static ImageButton mEnterBtn;
    private static float mCurrStartX;
    private static float mCurrStartY;
    private static float mOldStartX;
    private static float mOldStartY;


    /**
     * Alpha
     */
    private static ImageButton mAlphaBtn;
    private static int mCurrAlpha;
    private static int mOldAlpha;




    /**
     * Line
     */
    private final static int LINE_MAX = 5;
    private final static int LINE_MIN = 0;
    private final static String[] lineLabels = {"2/18", "2/20", "2/22", "2/24", "2/26", "2/28"};
    private final static float[][] lineValues = { {1f, 2f, 2f, 3f, 1f, 5f}};
    private static LineChartView mLineChart;
    private Paint mLineGridPaint;
    private TextView mLineTooltip;

    private final OnEntryClickListener lineEntryListener = new OnEntryClickListener(){
        @Override
        public void onClick(int setIndex, int entryIndex, Rect rect) {

            if(mLineTooltip == null)
                showLineTooltip(setIndex, entryIndex, rect);
            else
                dismissLineTooltip(setIndex, entryIndex, rect);
        }
    };

    private final View.OnClickListener lineClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(mLineTooltip != null)
                dismissLineTooltip(-1, -1, null);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dreaming_layout);

        databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        myApplication= (MyApplication) getApplication();

        titleName= (TextView) findViewById(R.id.title);
        titleName.setText("梦想图表");
        title_return= (LinearLayout) findViewById(R.id.title_return);
        finish= (LinearLayout) findViewById(R.id.finish);
        have_dream= (LinearLayout) findViewById(R.id.have_dream);
        no_dream= (LinearLayout) findViewById(R.id.no_dream);
        title_return.setVisibility(View.VISIBLE);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    /*--------显示数据------*/
        show_dreamName= (TextView) findViewById(R.id.show_dreamName);
        tv_dream= (TextView) findViewById(R.id.tv_dream);
        tv_break= (TextView) findViewById(R.id.tv_break);
        tv_wast= (TextView) findViewById(R.id.tv_wast);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from mydream ;",null);
        if (cursor.getCount()==0){
            finish.setVisibility(View.GONE);
            have_dream.setVisibility(View.GONE);
            no_dream.setVisibility(View.VISIBLE);
        }else {
            finish.setVisibility(View.VISIBLE);
            have_dream.setVisibility(View.VISIBLE);
            no_dream.setVisibility(View.GONE);

            while (cursor.moveToNext()) {
                show_dreamName.setText(cursor.getString(0));
                try {
                    Dream=Integer.parseInt(cursor.getString(2))*100/24;
                    Break=11*100/24;
                    Wast=(100-Dream-Break)/1;
                    tv_dream.setText("梦想"+Dream+"%");
                    tv_wast.setText("虚度"+Wast+"%");
                    tv_break.setText("休息"+Break+"%");
                }catch (Exception e){}
            }
        }


        show_dreamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase.execSQL("update mydream  set status=? where status=?;",new String[]{"1","0"});
                myApplication.setStatus("1");
                myApplication.setDreamTime("0");
                myApplication.setBreakTime("0");
                myApplication.setWastTime("0");

                Intent intent1 = new Intent();
                intent1.setAction("com.rao.myproject.Status");
                sendBroadcast(intent1);
            }
        });

/*-------删除梦想--------*/
        title_right= (TextView) findViewById(R.id.title_right);
        title_right.setText("删除");
        dialog1 = new LoadingDialog(this);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        DreamingActivity.this,AlertDialog.THEME_HOLO_LIGHT);

                builder.setIcon(R.drawable.jiazai);
                builder.setTitle("删除梦想");
                builder.setMessage("确定要删除这个梦想吗？");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 这里添加点击确定后的逻辑
                                dialog.dismiss();
                                dialog1.show();
                                sqLiteDatabase.execSQL("delete from mydream where status = ? or status=?;",new String[]{"0","1"});
                                myApplication.setStatus("-1");
                                myApplication.setDreamTime("0");
                                myApplication.setBreakTime("0");
                                myApplication.setWastTime("0");
                                new Thread(){
                                    @Override
                                    public void run() {
                                        Message msg=new Message();
                                        msg.what=1;
                                        handler.sendMessageDelayed(msg, 2000);
                                    }
                                }.start();


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





        mCurrOverlapFactor = 1;
        mCurrEasing = new QuintEaseOut();
        mCurrStartX = -1;
        mCurrStartY = 0;
        mCurrAlpha = -1;

        mOldOverlapFactor = 1;
        mOldEasing = new QuintEaseOut();
        mOldStartX = -1;
        mOldStartY = 0;
        mOldAlpha = -1;
        MagnificentChartItem firstItem = new MagnificentChartItem("first", Dream, Color.parseColor("#19C5EB"));
        MagnificentChartItem secondItem = new  MagnificentChartItem("second", Break, Color.parseColor("#1FB88A"));
        MagnificentChartItem thirdItem = new  MagnificentChartItem("third", Wast, Color.parseColor("#F85737"));
        List<MagnificentChartItem> chartItemsList = new ArrayList<MagnificentChartItem>();
        chartItemsList.add(firstItem);
        chartItemsList.add(secondItem);
        chartItemsList.add(thirdItem);
        magnificentChart = (MagnificentChart) findViewById(R.id.magnificentChart);
        magnificentChart.setChartItemsList(chartItemsList);
        magnificentChart.setMaxValue(100);
        magnificentChart.setAnimationState(true);

        initLineChart();
        updateLineChart();

    }









	/*------------------------------------*
	 *              LINECHART             *
	 *------------------------------------*/

    private void initLineChart(){

        mLineChart = (LineChartView) findViewById(R.id.linechart);
        mLineChart.setOnEntryClickListener(lineEntryListener);
        mLineChart.setOnClickListener(lineClickListener);

        mLineGridPaint = new Paint();
        mLineGridPaint.setColor(this.getResources().getColor(R.color.line_grid));
        mLineGridPaint.setPathEffect(new DashPathEffect(new float[] {5,5}, 0));
        mLineGridPaint.setStyle(Paint.Style.STROKE);
        mLineGridPaint.setAntiAlias(true);
        mLineGridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
    }


    private void updateLineChart(){

        mLineChart.reset();

        LineSet dataSet = new LineSet();
        dataSet.addPoints(lineLabels, lineValues[0]);
        dataSet.setDots(true)
                .setDotsColor(this.getResources().getColor(R.color.line_bg))
                .setDotsRadius(Tools.fromDpToPx(5))
                .setDotsStrokeThickness(Tools.fromDpToPx(2))
                .setDotsStrokeColor(this.getResources().getColor(R.color.white))
                .setLineColor(this.getResources().getColor(R.color.white))
                .setLineThickness(Tools.fromDpToPx(3))
                .beginAt(0).endAt(lineLabels.length);
        mLineChart.addData(dataSet);



        mLineChart.setBorderSpacing(Tools.fromDpToPx(6))
                .setGrid(LineChartView.GridType.HORIZONTAL, mLineGridPaint)
                .setXAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYAxis(false)
                .setYLabels(YController.LabelPosition.OUTSIDE)
                .setAxisBorderValues(LINE_MIN, LINE_MAX, 1)
                .setLabelsFormat(new DecimalFormat("##'h'"))
                .show(getAnimation(true).setEndAction(null))

        ;


    }


    @SuppressLint("NewApi")
    private void showLineTooltip(int setIndex, int entryIndex, Rect rect){

        mLineTooltip = (TextView) getLayoutInflater().inflate(R.layout.circular_tooltip, null);
        mLineTooltip.setText(Integer.toString((int)lineValues[setIndex][entryIndex]));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)Tools.fromDpToPx(35), (int)Tools.fromDpToPx(35));
        layoutParams.leftMargin = rect.centerX() - layoutParams.width/2;
        layoutParams.topMargin = rect.centerY() - layoutParams.height/2;
        mLineTooltip.setLayoutParams(layoutParams);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
            mLineTooltip.setPivotX(layoutParams.width/2);
            mLineTooltip.setPivotY(layoutParams.height/2);
            mLineTooltip.setAlpha(0);
            mLineTooltip.setScaleX(0);
            mLineTooltip.setScaleY(0);
            mLineTooltip.animate()
                    .setDuration(150)
                    .alpha(1)
                    .scaleX(1).scaleY(1)
                    .rotation(360)
                    .setInterpolator(enterInterpolator);
        }

        mLineChart.showTooltip(mLineTooltip);
    }


    @SuppressLint("NewApi")
    private void dismissLineTooltip(final int setIndex, final int entryIndex, final Rect rect){

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            mLineTooltip.animate()
                    .setDuration(100)
                    .scaleX(0).scaleY(0)
                    .alpha(0)
                    .setInterpolator(exitInterpolator).withEndAction(new Runnable(){
                @Override
                public void run() {
                    mLineChart.removeView(mLineTooltip);
                    mLineTooltip = null;
                    if(entryIndex != -1)
                        showLineTooltip(setIndex, entryIndex, rect);
                }
            });
        }else{
            mLineChart.dismissTooltip(mLineTooltip);
            mLineTooltip = null;
            if(entryIndex != -1)
                showLineTooltip(setIndex, entryIndex, rect);
        }
    }


    private void updateValues(LineChartView chartView){

        chartView.updateValues(0, lineValues[1]);
        chartView.updateValues(1, lineValues[0]);
        chartView.notifyDataUpdate();
    }



	/*------------------------------------*
	 *               GETTERS              *
	 *------------------------------------*/

    private Animation getAnimation(boolean newAnim){
        if(newAnim)
            return new Animation()
                    .setAlpha(mCurrAlpha)
                    .setEasing(mCurrEasing)
                    .setOverlap(mCurrOverlapFactor, mCurrOverlapOrder)
                    .setStartPoint(mCurrStartX, mCurrStartY);
        else
            return new Animation()
                    .setAlpha(mOldAlpha)
                    .setEasing(mOldEasing)
                    .setOverlap(mOldOverlapFactor, mOldOverlapOrder)
                    .setStartPoint(mOldStartX, mOldStartY);
    }




    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==1){
                have_dream.setVisibility(View.GONE);
                no_dream.setVisibility(View.VISIBLE);
                dialog1.dismiss();
                Toast.makeText(getApplicationContext(),
                        "删除成功", Toast.LENGTH_SHORT).show();

      /* ------发送广播------*/
                Intent intent1 = new Intent();
                intent1.setAction("com.rao.myproject.Status");
                sendBroadcast(intent1);
            }else{
                Toast.makeText(getApplicationContext(),
                        "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    };




}
