package com.example.administrator.myschool;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/26.
 */
public class DreamingActivity  extends Activity {
    TextView titleName;
    LinearLayout title_return;
    MagnificentChart magnificentChart;
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

        titleName= (TextView) findViewById(R.id.title);
        titleName.setText("图 表");
        title_return= (LinearLayout) findViewById(R.id.title_return);
        title_return.setVisibility(View.VISIBLE);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        MagnificentChartItem firstItem = new MagnificentChartItem("first", 30, Color.parseColor("#19C5EB"));
        MagnificentChartItem secondItem = new  MagnificentChartItem("second", 40, Color.parseColor("#1FB88A"));
        MagnificentChartItem thirdItem = new  MagnificentChartItem("third", 30, Color.parseColor("#F85737"));
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









}
