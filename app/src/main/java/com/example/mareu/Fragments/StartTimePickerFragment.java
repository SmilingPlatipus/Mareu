package com.example.mareu.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;

import java.util.Calendar;


public class StartTimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public Calendar c;
    public TextView editTextStartTime;
    private StartTimePickerFragmentCallback fragmentCaller;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCaller = (StartTimePickerFragmentCallback) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        editTextStartTime = getActivity().findViewById(R.id.TextViewStartTime);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                                    DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),hourOfDay,minute);
        editTextStartTime.setText(hourOfDay+":"+minute);
        onStartTimeSelected(hourOfDay,minute);
    }

    public interface StartTimePickerFragmentCallback{
        public void onStartTimeSelected(int hour,int minute);
    }

    public void onStartTimeSelected(int hour, int minute){
        fragmentCaller.onStartTimeSelected(hour,minute);
    }
}
