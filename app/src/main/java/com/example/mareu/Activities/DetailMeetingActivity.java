package com.example.mareu.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.mareu.DI.DI;
import com.example.mareu.Fragments.DatePickerFragment;
import com.example.mareu.Fragments.EndTimePickerFragment;
import com.example.mareu.Fragments.StartTimePickerFragment;
import com.example.mareu.Models.Meeting;
import com.example.mareu.R;
import com.example.mareu.Services.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class DetailMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerFragment.DatePickerFragmentCallBack,
                                                                        StartTimePickerFragment.StartTimePickerFragmentCallback, EndTimePickerFragment.EndTimePickerFragmentCallback
{
    private EditText meetingName, newMeetingEmail,meetingDescription;
    private TableLayout emailList;
    private Spinner spinnerMeetingRoomList;
    private Button ok,cancel;
    private Meeting currentMeeting;
    String name,description,room;
    List<String> emails = new ArrayList<>();
    int day,month,year,startHour,endHour,startMinutes,endMinutes;
    public static MeetingApiService mMeetingService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meeting);
        meetingName = findViewById(R.id.detail_meeting_name);
        newMeetingEmail = findViewById(R.id.detail_meeting_add_email);
        meetingDescription = findViewById(R.id.detail_meeting_description);
        emailList = findViewById(R.id.detail_meeting_emails);
        spinnerMeetingRoomList = findViewById(R.id.spinner_roomlist);
        ok = findViewById(R.id.button_ok);
        cancel = findViewById(R.id.button_cancel);
        mMeetingService = DI.getMeetingApiService();
        mMeetingService.getMeetings();
        day = month = year = startHour = endHour = 0;

        // Initialisation du spinner

        initEmailsSpinner();

        // Gestion des entrées dans l'editText de saisie du nom de la réunion

        initMeetingName();

        // Gestion des entrées dans l'editText de saisie des emails des participants

        newMeetingEmail.setOnEditorActionListener(new OnEditorActionListener()
        {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) ){
                    if (isValidEmailAddress(textView.getText().toString())) {
                        Button emailButton = new Button(getApplicationContext());
                        TableLayout.LayoutParams p = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        p.weight = 1;
                        emailButton.setLayoutParams(p);
                        emailButton.setText(textView.getText().toString());
                        emailButton.setTextColor(Color.BLACK);
                        Drawable closeButton = getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
                        closeButton.setBounds(0, 0, 50, 50);
                        closeButton.setTint(Color.BLACK);
                        emailButton.setCompoundDrawablesRelative(null, null, closeButton, null);
                        emailButton.setBackground(getDrawable(R.drawable.email_button_shape));
                        emails.add(textView.getText().toString());
                        emailList.addView(emailButton);
                        newMeetingEmail.getText().clear();
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Adresse email invalide",Toast.LENGTH_LONG).show();
                    return true;
                }

                else
                    return false;
            }
        });






        // Gestion des entrées dans l'editText description (Je n'arrive pas à faire apparaître de bouton send ou done)

        initDescription();

        // Gestion de la validation des données à l'aide des boutons ok et cancel

        initValidationButtons();

    }

    private void initValidationButtons() {
        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                currentMeeting = new Meeting(meetingName.getText().toString(),meetingDescription.toString(),room,emails,day,month,year,startHour,endHour,startMinutes,endMinutes);
                if (meetingName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Veuillez entrer un nom de réunion",Toast.LENGTH_LONG).show();
                    return;
                }
                if (emails.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Veuillez au moins entrer un participant",Toast.LENGTH_LONG).show();
                    return;
                }
                if((day == 0) || (month == 0) || (year == 0) || (startHour == 0) || (endHour == 0)) {
                    Toast.makeText(getApplicationContext(), "Veuillez renseigner la date, l'heure de début et de fin", Toast.LENGTH_LONG).show();
                    return;
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDescription() {
        meetingDescription.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                meetingDescription.setOnEditorActionListener(new OnEditorActionListener()
                {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if ((i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT)){
                            description = meetingDescription.toString();
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
            public void afterTextChanged(final Editable editable) {
                if(isValidMeetingName(editable.toString())) {
                    meetingName.setOnEditorActionListener(new OnEditorActionListener()
                    {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if ((i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT)) {
                                meetingName.setText(editable.toString());
                                name = editable.toString();
                                return true;
                            }
                            else
                                return false;
                        }
                    });
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
        room = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this,room+" sélectionnée",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this,R.string.spinner_meeting_room_nothing_selected,Toast.LENGTH_LONG);
    }

    @Override
    public void onDateSelected(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public void onStartTimeSelected(int hour, int minute) {
        this.startHour = hour;
        this.startMinutes = minute;
    }

    @Override
    public void onEndTimeSelected(int hour, int minute) {
        this.endHour = hour;
        this.endMinutes = minute;
    }
}
