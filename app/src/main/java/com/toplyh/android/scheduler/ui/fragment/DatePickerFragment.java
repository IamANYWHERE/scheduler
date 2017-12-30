package com.toplyh.android.scheduler.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.toplyh.android.scheduler.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 我 on 2017/12/28.
 */

public class DatePickerFragment extends DialogFragment{

    private static final String ARG_DATE="date";
    public static final String EXTRA_DATE="extra_date";
    private DatePicker mDatePicker;
    private Button mButton;

    public static DatePickerFragment newInstance(){
        DatePickerFragment fragment=new DatePickerFragment();
        return fragment;
    }

    private void sendResult(int resultCode,Date date){
        Intent intent=new Intent();
        intent.putExtra(EXTRA_DATE,date);
        if (getTargetFragment()==null){
            getActivity().setResult(resultCode,intent);
        }else {
            getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity())
                .inflate(R.layout.ddl,null);
        mDatePicker=(DatePicker)v.findViewById(R.id.dialog_date_date_picker);
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year,month,day,null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("时间：")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year=mDatePicker.getYear();
                        int month=mDatePicker.getMonth();
                        int day=mDatePicker.getDayOfMonth();
                        calendar.set(year,month,day);
                        Date currentDate=calendar.getTime();
                        sendResult(Activity.RESULT_OK,currentDate);
                    }
                }).create();
    }
}
