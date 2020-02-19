package com.example.mareu.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;


public class EndTimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public Calendar c;
    public TextView editTextEndTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        editTextEndTime = getActivity().findViewById(R.id.TextViewEndTime);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                                    DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),hourOfDay,minute);
        editTextEndTime.setText(hourOfDay+":"+minute);
    }
}
