package com.example.mareu.Utils;

import com.example.mareu.Models.Meeting;
import com.example.mareu.Services.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InitApi
{

    public static MeetingApiService service;
    private Calendar c;


    public void initMeetings(){
        List<String> emails = new ArrayList<>();
        String lamzone = "@lamzone.fr";
        for(int i = 0;i < 6;i++)
            emails.add("test" + i + lamzone);

        char meetingRoomLetter = 'A';
        for(int i = 0;i < 6;i++,meetingRoomLetter++) {

            Meeting testMeeting = new Meeting("MeetingTest"+i, "nothing", "meetingroom"+meetingRoomLetter, emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) + i, c.get(Calendar.MINUTE) + 15, c.get(Calendar.HOUR_OF_DAY) + i+1, c.get(Calendar.MINUTE) + 15);
            service.addMeeting(testMeeting);
        }
    }
}
