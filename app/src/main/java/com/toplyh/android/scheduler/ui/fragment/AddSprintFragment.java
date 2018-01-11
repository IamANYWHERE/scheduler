package com.toplyh.android.scheduler.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Sprint;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 我 on 2017/12/30.
 */

public class AddSprintFragment extends DialogFragment {

    private EditText mSprintName;
    private EditText mSprintContent;
    private EditText mSprintWorkTime;
    private EditText mSprintUser;
    private DatePicker mDatePicker;
    private OnAddSprintFragmentInteractionListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_sprint,null);
        mSprintName=(EditText) view.findViewById(R.id.sprint_name);
        mSprintContent=(EditText)view.findViewById(R.id.sprint_content);
        mSprintWorkTime=(EditText)view.findViewById(R.id.sprint_worktime);
        mSprintUser=(EditText)view.findViewById(R.id.sprint_user);
        mDatePicker=(DatePicker)view.findViewById(R.id.sprint_date_picker);

        final Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year,month,day,null);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("添加sprint：")
                .setNegativeButton("取消",null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mSprintName.getText().toString().equals("")||
                                mSprintContent.getText().toString().equals("")||
                                mSprintWorkTime.getText().toString().equals("")||
                                mSprintUser.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "输入栏为空！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int year=mDatePicker.getYear();
                        int month=mDatePicker.getMonth();
                        int day=mDatePicker.getDayOfMonth();
                        calendar.set(year,month,day);
                        Date selectDate=calendar.getTime();
                        Sprint sprint=new Sprint();
                        sprint.setName(mSprintName.getText().toString());
                        sprint.setContent(mSprintContent.getText().toString());
                        sprint.setStatus(Sprint.SprintStatus.BB);
                        sprint.setDdl(selectDate.getTime());
                        sprint.setWorkTime(Integer.valueOf(mSprintWorkTime.getText().toString()));
                        sprint.setUsername(mSprintUser.getText().toString());
                        mListener.onFragmentInteraction(sprint);
                    }
                })
                .create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddSprintFragmentInteractionListener) {
            mListener = (OnAddSprintFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnAddSprintFragmentInteractionListener{
        void onFragmentInteraction(Sprint sprint);
    }
}
