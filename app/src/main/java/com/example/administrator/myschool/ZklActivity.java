package com.example.administrator.myschool;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.github.OrangeGangsters.circularbarpager.library.CircularBarPager;
import com.nineoldandroids.animation.Animator;
import com.rao.MySchool.adapter.CircularPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import static com.nineoldandroids.animation.Animator.*;

/**
 * Created by Administrator on 2015/3/6.
 */
public class ZklActivity extends Activity{
    private RoundCornerProgressBar progressTwo;


    private int progress2 = 5;
    private CircularBarPager mCircularBarPager;
    TextView titleName;
    /**
     * The animation time in milliseconds that we take to display the steps taken
     */
    private static final int BAR_ANIMATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zkl);
        titleName=(TextView) findViewById(R.id.title);
        titleName.setText("自控力");
        Toast.makeText(getApplicationContext(), "oncreat", Toast.LENGTH_SHORT).show();
        progressTwo = (RoundCornerProgressBar) findViewById(R.id.progress_two);
        progressTwo.setBackgroundColor(getResources().getColor(R.color.custom_progress_background));

        updateProgressTwo();
        initViews();
    }
    private void updateProgressTwo() {
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
        mCircularBarPager.getCircularBar().animateProgress(0, 75, 1000);
    }

    private void initViews(){
        mCircularBarPager = (CircularBarPager) findViewById(R.id.circularBarPager);

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
}
