package com.example.task_manager_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_manager_app.model.Tasks;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        return  new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(), year, month,day);
    }

//    @Override
//    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        String date = i+"-"+i1+"-"+i2;
//        View view = getLayoutInflater().inflate(R.layout.activity_add_and_edit,null);
//        TextView dateTextView = view.findViewById(R.id.textView_pickDate);
//        dateTextView.setText(date);
//
//        Toast.makeText(getActivity(),"Date set : "+date,Toast.LENGTH_LONG).show();
//    }


}