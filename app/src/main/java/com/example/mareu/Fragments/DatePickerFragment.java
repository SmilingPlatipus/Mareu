package com.example.mareu.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
{
    public Calendar c;
    public TextView date;
    private DatePickerFragmentCallBack fragmentCaller;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        c.set(i,i1,i2);
        date.setText(i2+"/"+(i1+1)+"/"+i);
        onDateSelected(i2,i1+1,i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCaller = (DatePickerFragmentCallBack) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        c =  Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        date = getActivity().findViewById(R.id.TextView_Date);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public interface DatePickerFragmentCallBack{
        public void onDateSelected(int day,int month,int year);
}

    public void onDateSelected(int day,int month,int year){
        fragmentCaller.onDateSelected(day,month,year);
    }
}
