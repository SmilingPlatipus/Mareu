package com.example.mareu.Services;

import com.example.mareu.Models.Meeting;

import java.util.List;

public interface MeetingApiService
{
    List<Meeting> getMeetings();

    boolean canBeOrganized(Meeting userEntry);

    void addMeeting(Meeting userEntry);

    void removeMeeting(String name);

    String getMeetingName(int ID);

    Meeting getMeeting(String Name);
}
