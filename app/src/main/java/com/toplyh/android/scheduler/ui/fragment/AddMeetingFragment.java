package com.toplyh.android.scheduler.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.toplyh.android.scheduler.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 我 on 2017/12/30.
 */

public class AddMeetingFragment extends DialogFragment {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private TimeCallback callbackValue;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimeCallback) {
            callbackValue = (TimeCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_meeting, null);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        timePicker = (TimePicker)view.findViewById(R.id.time_picker);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        datePicker.init(year, month, day, null);
        timePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT<23){
            timePicker.setCurrentHour(Integer.valueOf(hour));
            timePicker.setCurrentMinute(Integer.valueOf(minute));
        }else {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        }
        builder.setView(view).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                int hour;
                int minute;
                if (Build.VERSION.SDK_INT<23){
                    hour=timePicker.getCurrentHour();
                    minute=timePicker.getCurrentMinute();
                }else {
                    hour=timePicker.getHour();
                    minute=timePicker.getMinute();
                }
                calendar.set(year, month, day, hour, minute);
                Date currentDate = calendar.getTime();
                callbackValue.sendDateAndTime(currentDate);
            }
        }).setNegativeButton("取消", null);
        return builder.create();
    }

    public interface TimeCallback {
        public void sendDateAndTime(Date date);
    }
}
