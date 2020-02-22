package com.example.mareu.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;

import static com.example.mareu.Activities.MainActivity.today;
import static com.example.mareu.Fragments.DatePickerFragment.c;


public class EndTimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public TextView editTextEndTime;
    private EndTimePickerFragmentCallback fragmentCaller;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCaller = (EndTimePickerFragmentCallback) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        editTextEndTime = getActivity().findViewById(R.id.TextViewEndTime);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                                    DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (c.get(Calendar.YEAR) == today.get(Calendar.YEAR)&&c.get(Calendar.MONTH) == today.get(Calendar.MONTH)&&c.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
            if (hourOfDay * 60 + minute < (today.get(Calendar.HOUR_OF_DAY) * 60 + today.get(Calendar.MINUTE)))
                Toast.makeText(this.getContext(), "Veuillez entrer une heure de fin valide", Toast.LENGTH_LONG).show();
            else{
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), hourOfDay, minute);
                editTextEndTime.setText(hourOfDay + ":" + minute);
                onEndTimeSelected(hourOfDay, minute);
            }
        }
        else
        {
            if (c.get(Calendar.YEAR) <= today.get(Calendar.YEAR)&&c.get(Calendar.MONTH) <= today.get(Calendar.MONTH)&&c.get(Calendar.DAY_OF_MONTH) < today.get(Calendar.DAY_OF_MONTH))
                Toast.makeText(this.getContext(), "Veuillez entrer une heure de fin valide", Toast.LENGTH_LONG).show();
            else{
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), hourOfDay, minute);
                editTextEndTime.setText(hourOfDay + ":" + minute);
                onEndTimeSelected(hourOfDay, minute);
            }
        }
    }

    public interface EndTimePickerFragmentCallback{
        public void onEndTimeSelected(int hour,int minute);
    }

    public void onEndTimeSelected(int hour, int minute){
        fragmentCaller.onEndTimeSelected(hour,minute);
    }
}
