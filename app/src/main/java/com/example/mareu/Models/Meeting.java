package com.example.mareu.Models;

import android.content.Context;
import android.widget.Toast;


import java.util.List;
import java.util.Objects;

import static com.example.mareu.Activities.MainActivity.mMeetingService;
import static com.example.mareu.Activities.MainActivity.today;

public class Meeting
{
    String name,description,room;
    List<String> emails;
    int day,month,year,startHour,endHour,startMinutes,endMinutes;


    public Meeting(String name, String description, String room, List<String> emails, int day, int month, int year, int startHour, int endHour, int startMinutes, int endMinutes) {
        this.name = name;
        this.description = description;
        this.room = room;
        this.emails = emails;
        this.day = day;
        this.month = month;
        this.year = year;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinutes = startMinutes;
        this.endMinutes = endMinutes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails.add(emails);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public static boolean isAValidMeeting(Context context,Meeting currentMeeting)
    {
        // Derniers tests pour s'assurer que tout a été rempli et que les valeurs soient correctes

        if (currentMeeting.getName().isEmpty()){
            Toast.makeText(context, "Veuillez entrer un nom de réunion", Toast.LENGTH_LONG).show();
            return false;
        }
        if (currentMeeting.getEmails().isEmpty()){
            Toast.makeText(context,"Veuillez au moins entrer un participant",Toast.LENGTH_LONG).show();
            return false;
        }
        if((currentMeeting.getDay() == 0) || (currentMeeting.getMonth() == 0) || (currentMeeting.getYear() == 0) || (currentMeeting.getStartHour() == 0) || (currentMeeting.getEndHour() == 0)) {
            Toast.makeText(context, "Veuillez renseigner la date, l'heure de début et de fin", Toast.LENGTH_LONG).show();
            return false;
        }
        if (currentMeeting.getStartHour() > currentMeeting.getEndHour() || (currentMeeting.getStartHour() == currentMeeting.getEndHour() && currentMeeting.getStartMinutes() > currentMeeting.getEndMinutes())) {
            Toast.makeText(context, "L'heure de début doit précéder l'heure de fin", Toast.LENGTH_LONG).show();
            return false;
        }
        if (currentMeeting.getYear() <= today.YEAR)
            if (currentMeeting.getMonth() <= today.MONTH)
                if (currentMeeting.getDay() < today.DAY_OF_MONTH){
                    Toast.makeText(context, "Veuillez saisir une date correcte", Toast.LENGTH_LONG).show();
                    return false;
                }
        // Ajout de la réunion à la liste, si elle remplit les conditions
        if (mMeetingService.canBeOrganized(currentMeeting)) {
            mMeetingService.addMeeting(currentMeeting);
            Toast.makeText(context, "Réunion enregistrée", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(context, "La réunion entre en conflit avec une précédente", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Meeting meeting = (Meeting) o;
        return day == meeting.day &&
                month == meeting.month &&
                year == meeting.year &&
                startHour == meeting.startHour &&
                endHour == meeting.endHour &&
                startMinutes == meeting.startMinutes &&
                endMinutes == meeting.endMinutes &&
                Objects.equals(name, meeting.name) &&
                Objects.equals(description, meeting.description) &&
                Objects.equals(room, meeting.room) &&
                Objects.equals(emails, meeting.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, room, emails, day, month, year, startHour, endHour, startMinutes, endMinutes);
    }
}
