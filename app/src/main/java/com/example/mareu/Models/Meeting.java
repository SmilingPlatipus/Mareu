package com.example.mareu.Models;

import java.util.List;

public class Meeting
{
    String name,description,room;
    List<String> emails;
    int day,month,year,startHour,endHour,startMinutes,endMinutes;


    public Meeting(String name, String description, String room, List<String> emails, int day, int month, int year, int startHour, int endHour, int startMinutes, int endMinutes) {
        this.name = name;
        this.description = description;        this.room = room;
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
}
