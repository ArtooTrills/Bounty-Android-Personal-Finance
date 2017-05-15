package com.example.earthshaker.moneybox.common;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tiny on 1/9/2016.
 */
public class DateEditText extends AppCompatEditText implements DatePickerDialog.OnDateSetListener {
    CustomClickListener customClickListener;
    private DatePickerDialog datePicker;
    private int mAccentColor;

    public DateEditText(Context context) {
        super(context);
        setupClickListener(context);
    }

    public DateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupClickListener(context);
    }

    public DateEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupClickListener(context);
    }

    private void setupClickListener(final Context context) {
        setFocusable(false);
        setClickable(false);

        setOnClickListener(view -> {
            Calendar now = Calendar.getInstance();
            if (!StringUtils.isBlank(getText().toString())) {
                try {
                    Date date = DateFormatterConstants.reminderDateFormat.parse(getText().toString());
                    now.setTime(date);
                } catch (ParseException e) {
                    Log.e("TrakrTimeEditText", e.toString());
                }
            }
            datePicker = DatePickerDialog.newInstance(DateEditText.this, now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            if (mAccentColor != 0) {
                datePicker.setAccentColor(mAccentColor);
            }
            assert (getActivity(context)) != null;
            AppCompatActivity activity = ((AppCompatActivity) getActivity(context));
            if (activity != null)
                datePicker.show(activity.getFragmentManager(), "Datepickerdialog");
            if (customClickListener != null) {
                customClickListener.onCustomClick();
            }
        });
    }

    public static Context getActivity(Context context) {
        if (context instanceof Activity) {
            return context;
        } else if (context instanceof ContextThemeWrapper) {
            return getActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
    }


    public void setColor(int color) {
        mAccentColor = color;
    }

    interface CustomClickListener {
        void onCustomClick();
    }
}
