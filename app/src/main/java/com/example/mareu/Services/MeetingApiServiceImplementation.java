package com.example.mareu.Services;

import com.example.mareu.Models.Meeting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MeetingApiServiceImplementation implements MeetingApiService
{
    List<Meeting> meetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public boolean canBeOrganized(Meeting userEntry) {
        Iterator meetingIterator = meetings.iterator();
        Meeting currentMeeting;

        // On vérifie les conditions pour qu'une nouvelle réunion puisse être créée

        // Temps prévu de début et fin, en minutes
        int sMeeting = userEntry.getStartHour() + userEntry.getStartMinutes();
        int eMeeting = userEntry.getEndHour() + userEntry.getEndMinutes();
        if (meetings.isEmpty())
            return true;

        do{
            currentMeeting = (Meeting) meetingIterator.next();


            // Les noms de réunion doivent être uniques
            if(userEntry.getName() == currentMeeting.getName())
                return false;
            // Vérification que le même jour/mois/année, il n'y a pas une autre réunion dans la même salle de réunion
            if(currentMeeting.getRoom() == userEntry.getRoom())
            {
                if (currentMeeting.getDay() == userEntry.getDay() && currentMeeting.getMonth() == userEntry.getMonth() && currentMeeting.getYear() == userEntry.getYear())
                {
                    int pStart = currentMeeting.getStartHour()+currentMeeting.getStartMinutes();
                    int pEnd = currentMeeting.getEndHour()+currentMeeting.getEndMinutes();

                    if ((sMeeting <= pStart && eMeeting >= pStart) || (sMeeting <= pEnd && eMeeting >= pEnd) || (sMeeting <= pStart && eMeeting >= pEnd))
                        return false;
                }
            }
        }while(meetingIterator.hasNext());

        return true;
    }

    @Override
    public void addMeeting(Meeting userEntry) {
        if (canBeOrganized(userEntry))
            meetings.add(userEntry);
    }

    @Override
    public void removeMeeting(String name) {
        Iterator meetingIterator = meetings.iterator();
        Meeting currentMeeting;
        do{
            currentMeeting = (Meeting) meetingIterator.next();
            if(currentMeeting.getName() == name)
                meetings.remove(currentMeeting);
        }while(meetingIterator.hasNext());
    }

    @Override
    public String getMeetingName(int ID) {
        return meetings.get(ID).getName();
    }

    @Override
    public Meeting getMeeting(String Name) {
        Iterator meetingIterator = meetings.iterator();
        Meeting currentMeeting;

        if(!meetings.isEmpty()){
            do{
                currentMeeting = (Meeting) meetingIterator.next();
                if (currentMeeting.getName() == Name)
                    return currentMeeting;
            }while(meetingIterator.hasNext());
        }

        return null;

    }
}
