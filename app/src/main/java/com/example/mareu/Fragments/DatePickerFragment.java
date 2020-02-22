package com.example.mareu.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;

import static com.example.mareu.Activities.MainActivity.today;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
{
    public static Calendar c = null;
    public TextView date;
    private DatePickerFragmentCallBack fragmentCaller;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (i2 < today.get(Calendar.DAY_OF_MONTH) && i1 <= today.get(Calendar.MONTH) && i <= today.get(Calendar.YEAR))
            Toast.makeText(this.getContext(), "Veuillez entrer une date valide", Toast.LENGTH_LONG).show();
        else {
            c.set(i, i1, i2);
            date.setText(i2 + "/" + (i1 + 1) + "/" + i);
            onDateSelected(i2, i1 + 1, i);
        }
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
