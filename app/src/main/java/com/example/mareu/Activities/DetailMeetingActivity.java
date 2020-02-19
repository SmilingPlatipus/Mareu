package com.example.mareu.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mareu.Fragments.DatePickerFragment;
import com.example.mareu.Fragments.EndTimePickerFragment;
import com.example.mareu.Fragments.StartTimePickerFragment;
import com.example.mareu.R;

public class DetailMeetingActivity extends AppCompatActivity
{
    private EditText meetingName, newMeetingEmail,meetingDescription;
    private LinearLayout emailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meeting);
        meetingName = findViewById(R.id.detail_meeting_name);
        newMeetingEmail = findViewById(R.id.detail_meeting_add_email);
        meetingDescription = findViewById(R.id.detail_meeting_description);
        emailList = findViewById(R.id.detail_meeting_emails);

        newMeetingEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (isValidEmailAddress(editable.toString()))
                    newMeetingEmail.setOnEditorActionListener(new EditText.OnEditorActionListener()
                    {

                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)){
                                TextView addedEmail = new TextView(getApplicationContext());
                                addedEmail.setTextColor(getResources().getColor(R.color.colorAccent));
                                addedEmail.setText(editable.toString());
                                emailList.addView(addedEmail);
                                return true;
                            }
                            else
                                return false;
                        }
                    });
            }
        });

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showStartTimePickerDialog(View v) {
        DialogFragment newFragment = new StartTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "StartTimePicker");
    }

    public void showEndTimePickerDialog(View v) {
        DialogFragment newFragment = new EndTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "EndTimePicker");
    }

    private boolean isValidEmailAddress(String emailToTest){

        if ((!emailToTest.isEmpty()) && Patterns.EMAIL_ADDRESS.matcher(emailToTest).matches())
        {
            return true;
        }
        else
            return false;

    }
}
