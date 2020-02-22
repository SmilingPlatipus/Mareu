package com.example.mareu.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mareu.DI.DI;
import com.example.mareu.Events.DeleteMeetingEvent;
import com.example.mareu.Fragments.MeetingFragment;
import com.example.mareu.R;
import com.example.mareu.Services.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity
{
    FragmentManager fragmentManager;
    MeetingFragment mMeetingFragment;


    public static MeetingApiService mMeetingService;
    public static final Calendar today = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMeetingService = DI.getMeetingApiService();
        mMeetingService.getMeetings();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMeetingFragment = MeetingFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_activity_layout, mMeetingFragment);
        fragmentTransaction.commit();

        FloatingActionButton fab = findViewById(R.id.buttonAddMeeting);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.example.mareu.Activities.DetailMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().replace(R.id.main_activity_layout,MeetingFragment.newInstance()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.date_filter) {
            return true;
        }

        if (id == R.id.room_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(DeleteMeetingEvent event){
        fragmentManager.beginTransaction().replace(R.id.main_activity_layout,MeetingFragment.newInstance()).commit();

    }

}
