package com.examples.ankit.breakpoint.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String REQUIRED = "required";

    private DatePickerDialog.OnDateSetListener mListener;

    public DatePickerFragment() {
        super();
    }

    public void setDateChangeListener(DatePickerDialog.OnDateSetListener mListener) {
        this.mListener = mListener;
    }

    public static DatePickerFragment getInstance(int year, int month, int day, boolean required) {
        DatePickerFragment datePicker = new DatePickerFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        bundle.putInt(DAY, day);
        bundle.putBoolean(REQUIRED, required);

        datePicker.setArguments(bundle);
        return datePicker;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = getArguments().getInt(YEAR);
        int month = getArguments().getInt(MONTH);
        int day = getArguments().getInt(DAY);
        boolean required = getArguments().getBoolean(REQUIRED);
        final DatePickerDialog picker = new DatePickerDialog(getActivity(), null, year, month,
                day) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

            }
        };

        picker.setButton(DialogInterface.BUTTON_POSITIVE, getActivity().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker dp = picker.getDatePicker();
                        try {
                            //clear focus so dp will take values after typing date/month/year also.
                            dp.clearFocus();
                            mListener.onDateSet(dp, dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                        } catch (NullPointerException npe) {
                            npe.printStackTrace();
                        }
                    }
                }
                        );

        if (!required) {
            picker.setButton(DialogInterface.BUTTON_NEGATIVE,
                    getActivity().getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
                            );
        }

        return picker;
    }
}