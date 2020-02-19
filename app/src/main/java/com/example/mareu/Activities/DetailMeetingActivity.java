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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mareu.Fragments.DatePickerFragment;
import com.example.mareu.Fragments.EndTimePickerFragment;
import com.example.mareu.Fragments.StartTimePickerFragment;
import com.example.mareu.Models.Meeting;
import com.example.mareu.R;

public class DetailMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private EditText meetingName, newMeetingEmail,meetingDescription;
    private LinearLayout emailList;
    private Spinner spinnerMeetingRoomList;
    private Meeting currentMeeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meeting);
        meetingName = findViewById(R.id.detail_meeting_name);
        newMeetingEmail = findViewById(R.id.detail_meeting_add_email);
        meetingDescription = findViewById(R.id.detail_meeting_description);
        emailList = findViewById(R.id.detail_meeting_emails);
        spinnerMeetingRoomList = findViewById(R.id.spinner_roomlist);

        // Initialisation du spinner

        initEmailsSpinner();

        // Gestion des entrées dans l'editText de saisie du nom de la réunion

        initMeetingName();

        // Gestion des entrées dans l'editText de saisie des emails des participants

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
                            if ((i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT)){
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

    private void initMeetingName() {
        meetingName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isValidMeetingName(editable.toString())) {
                    meetingName.setText(editable);
                    currentMeeting.setName(editable.toString());
                }
            }
        });
    }

    private void initEmailsSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                                                             R.array.meetingrooms, R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMeetingRoomList.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
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

    private boolean isValidMeetingName(String roomNameToTest){

        // A implémenter : test d'unicité du nom

        if (!roomNameToTest.isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentMeeting.setRoom(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this,R.string.spinner_meeting_room_nothing_selected,Toast.LENGTH_LONG);
    }
}
