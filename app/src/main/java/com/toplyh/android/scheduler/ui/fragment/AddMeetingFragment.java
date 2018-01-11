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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Meeting;
import com.toplyh.android.scheduler.service.entity.remote.MetAndMem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 我 on 2017/12/30.
 */

public class AddMeetingFragment extends DialogFragment {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText mNameEdit;
    private EditText mMemberEdit;
    private ImageView mAddMember;
    private TextView mShowText;
    private TimeCallback callbackValue;
    private List<String> mMembers;

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
        mNameEdit=(EditText)view.findViewById(R.id.meeting_name);
        mMemberEdit=(EditText)view.findViewById(R.id.meeting_add_member);
        mShowText=(TextView)view.findViewById(R.id.meeting_show_members);
        mAddMember=(ImageView)view.findViewById(R.id.meeting_add_member_image);
        mMembers=new ArrayList<>();

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

        mAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMemberEdit.getText().toString().equals("")){
                    mShowText.setText(mShowText.getText()+mMemberEdit.getText().toString()+";");
                    mMembers.add(mMemberEdit.getText().toString());
                    mMemberEdit.getText().clear();
                }
            }
        });
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

                calendar.set(year, month, day, hour,minute);
                Date currentDate = calendar.getTime();
                String name=mNameEdit.getText().toString();
                if (name.equals("")||mMembers.size()==0){
                    Toast.makeText(getActivity(), "名称和成员没有", Toast.LENGTH_SHORT).show();
                    return;
                }
                MetAndMem metAndMem=new MetAndMem();
                metAndMem.setDate(currentDate.getTime());
                metAndMem.setName(name);
                metAndMem.setMembers(mMembers);
                callbackValue.sendDateAndTime(metAndMem);
            }
        }).setNegativeButton("取消", null);
        return builder.create();
    }

    public interface TimeCallback {
        public void sendDateAndTime(MetAndMem metAndMem);
    }
}
