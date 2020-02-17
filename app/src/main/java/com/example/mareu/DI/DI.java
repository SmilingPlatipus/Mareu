package com.example.mareu.DI;

import com.example.mareu.Services.MeetingApiService;
import com.example.mareu.Services.MeetingApiServiceImplementation;

public class DI
{
    private static MeetingApiService service = new MeetingApiServiceImplementation();

    public static MeetingApiService getMeetingApiService(){return service;}

    public static MeetingApiService getNewMeetingApiService(){return new MeetingApiServiceImplementation();}
}
