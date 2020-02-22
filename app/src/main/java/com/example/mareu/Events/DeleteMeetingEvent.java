package com.example.mareu.Events;

import com.example.mareu.Models.Meeting;

public class DeleteMeetingEvent
{
    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting){this.meeting = meeting;}
}
