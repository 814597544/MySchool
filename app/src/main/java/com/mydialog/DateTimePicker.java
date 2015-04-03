package com.mydialog;

import android.content.Context;
import android.text.InputType;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.example.administrator.myschool.R;

import java.util.Calendar;


public class DateTimePicker extends FrameLayout
{
	private final NumberPicker mDateSpinner;
	private Calendar mDate;
    private String[] mDateDisplayValues = new String[7];
    private OnDateTimeChangedListener mOnDateTimeChangedListener;
    private TextView config_hidden;
    public DateTimePicker(Context context)
	{
    	super(context);
    	 mDate = Calendar.getInstance();
    	 inflate(context, R.layout.datedialog, this);

    	 mDateSpinner=(NumberPicker)this.findViewById(R.id.np_date);
         config_hidden = (TextView) this.findViewById(R.id.config_hidden);
         mDateSpinner.setFocusable(false);
        ((EditText) mDateSpinner.getChildAt(0)).setInputType(InputType.TYPE_NULL);

        config_hidden.requestFocus();
        mDateSpinner.setMinValue(0);
         mDateSpinner.setMaxValue(6);
         updateDateControl();
    	 mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);
	}
    
    private OnValueChangeListener mOnDateChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
			updateDateControl();
			onDateTimeChanged();
		}
	};
	private void updateDateControl() 
    {
	 	Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, -7 / 2 - 1);
        mDateSpinner.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i) 
        {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM.dd EEEE", cal);
        }
        mDateSpinner.setDisplayedValues(mDateDisplayValues);
        mDateSpinner.setValue(7 / 2);
        mDateSpinner.invalidate();
    }
	
	  public interface OnDateTimeChangedListener 
	  {
	        void onDateTimeChanged(DateTimePicker view, int year, int month, int day, int hour, int minute);
	  }
	
	  public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) 
	  {
	        mOnDateTimeChangedListener = callback;
	   }
	  
	  private void onDateTimeChanged() 
	  {
	        if (mOnDateTimeChangedListener != null)
	        {
	            mOnDateTimeChangedListener.onDateTimeChanged(this, mDate.get(Calendar.YEAR),
	            		mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH),0, 0);
	        }
	    }
}
