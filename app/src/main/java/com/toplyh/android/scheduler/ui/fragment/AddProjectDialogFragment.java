package com.toplyh.android.scheduler.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.android.scheduler.R;
import com.toplyh.android.scheduler.service.entity.remote.Project;
import com.toplyh.android.scheduler.service.entity.remote.ProjectAndMember;
import com.toplyh.android.scheduler.service.presenter.HomePagePresenter;
import com.toplyh.android.scheduler.service.view.HomePageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 我 on 2017/12/27.
 */

public class AddProjectDialogFragment extends DialogFragment{

    final static private String DDLDIALOGFRAGMENT = "ddlDialogFragment";
    private static final String DIALOG_DATE="dialog_date";
    private static final int REQUEST_DATE=1;

    private TextView mShowMemberText;
    private EditText mAddMemberText;
    private ImageView addMemberButton;
    private TextView ddlText;
    private EditText projectNameText;
    private ProjectAndMember mProject;
    private OnAddFragmentInteractionListener mListener;


    public static AddProjectDialogFragment newInstance(){
        AddProjectDialogFragment fragment= new AddProjectDialogFragment();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.selfdialog,null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        builder.setTitle("新建项目");

        mShowMemberText=(TextView)view.findViewById(R.id.showmember);
        mAddMemberText=(EditText)view.findViewById(R.id.addmember);
        addMemberButton=(ImageView)view.findViewById(R.id.dialogaddmembericon);
        ddlText=(TextView)view.findViewById(R.id.projectddllabel);
        projectNameText=(EditText)view.findViewById(R.id.projectName);
        mProject=new ProjectAndMember();

        ddlText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mChooseDDLDialogFragment=new ChooseDDLDialogFragment();
                mChooseDDLDialogFragment.show(getChildFragmentManager(),DDLDIALOGFRAGMENT);*/
                android.app.FragmentManager fm=getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance();
                dialog.setTargetFragment(AddProjectDialogFragment.this,REQUEST_DATE);
                dialog.show(fm,DIALOG_DATE);
            }
        });

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddMemberText.getText().length()!=0){
                    mProject.getMembers().add(mAddMemberText.getText().toString());
                    mShowMemberText.setText(mShowMemberText.getText()+"\n"+mAddMemberText.getText());
                    mAddMemberText.getText().clear();
                }
            }
        });


        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mProject.setProjectName(projectNameText.getText().toString());
                if (mProject.getProjectName()==null||mProject.getProjectName().equals("")){
                    Toast.makeText(getActivity(), "项目名为空！", Toast.LENGTH_SHORT).show();
                }else if (mProject.getDdl()==0){
                    Toast.makeText(getActivity(), "ddl为空！", Toast.LENGTH_SHORT).show();
                }else{
                    mListener.onFragmentInteraction(mProject);
                    AddProjectDialogFragment.this.getDialog().cancel();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddProjectDialogFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date=(Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mProject.setDdl(date.getTime());
            updateUI();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddFragmentInteractionListener) {
            mListener = (OnAddFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnAddFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(ProjectAndMember projectAndMember);
    }

    private void updateUI(){
        Date date=new Date(mProject.getDdl());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        month+=1;
        ddlText.setText(year+"年"+month+"月"+day+"日");
    }
}

