package com.example.mareu;

import com.example.mareu.DI.DI;
import com.example.mareu.Models.Meeting;
import com.example.mareu.Services.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest
{
    private MeetingApiService service;
    private List<Meeting> testList = new ArrayList<>();
    private List<Meeting> serviceList = new ArrayList<>();
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
            testList.add(testMeeting);
        }
    }

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
        c = Calendar.getInstance();
    }

    @Test
    public void setAndGetMeetingsWithSuccess() {

        initMeetings();
        serviceList = service.getMeetings();

        Iterator testListIterator = testList.iterator();
        Iterator serviceListIterator = serviceList.iterator();

        Meeting test1,test2;

        do{
            test1 = (Meeting) serviceListIterator.next();
            test2 = (Meeting) testListIterator.next();

            assertEquals(test1,test2);
        }while(serviceListIterator.hasNext());
        service.clearMeetings();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<String> emails = new ArrayList<>();
        String lamzone = "@lamzone.fr";
        for(int i = 0;i < 6;i++)
            emails.add("test" + i + lamzone);

        initMeetings();

        Meeting test = service.getMeeting("MeetingTest0");
        Meeting test2 = new Meeting("MeetingTest0", "nothing", "meetingroomA", emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE) + 15, c.get(Calendar.HOUR_OF_DAY) +1, c.get(Calendar.MINUTE) + 15);
        assertEquals(test2,test);
        service.clearMeetings();
    }

    @Test
    public void addMeetingWithSuccess() {
        List<String> emails = new ArrayList<>();
        String lamzone = "@lamzone.fr";
        for(int i = 0;i < 6;i++)
            emails.add("test" + i + lamzone);

        Meeting test2 = new Meeting("MeetingTest0", "nothing", "meetingroomA", emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE) + 15, c.get(Calendar.HOUR_OF_DAY) +1, c.get(Calendar.MINUTE) + 15);
        service.addMeeting(test2);
        Meeting test = service.getMeeting("MeetingTest0");
        assertEquals(test2,test);
    }

    @Test
    public void removeMeetingWithSuccess() {
        Meeting test = service.getMeeting("MeetingTest0");
        assertTrue(service.getMeetings().isEmpty());
    }

    @Test
    public void assertMeetingCanBeOrganisedWithSuccess() {
        List<String> emails = new ArrayList<>();
        String lamzone = "@lamzone.fr";
        for(int i = 0;i < 6;i++)
            emails.add("test" + i + lamzone);

        // Exactly the same

        Meeting test = new Meeting("MeetingTest0", "nothing", "meetingroomA", emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE) + 15, c.get(Calendar.HOUR_OF_DAY) +1, c.get(Calendar.MINUTE) + 15);
        Meeting test2 = new Meeting("MeetingTest1", "nothing", "meetingroomA", emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE) + 15, c.get(Calendar.HOUR_OF_DAY) +1, c.get(Calendar.MINUTE) + 15);
        service.addMeeting(test);
        assertFalse(service.canBeOrganized(test2));

        // Other room same time OK

        test2 = new Meeting("MeetingTest1", "nothing", "meetingroomB", emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE) + 15, c.get(Calendar.HOUR_OF_DAY) +1, c.get(Calendar.MINUTE) + 15);
        assertTrue(service.canBeOrganized(test2));

        // Same room, same day, overlapping time

        test2 = new Meeting("MeetingTest1", "nothing", "meetingroomA", emails, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR), c.get(Calendar.HOUR_OF_DAY) , c.get(Calendar.MINUTE) + 30, c.get(Calendar.HOUR_OF_DAY) +1, c.get(Calendar.MINUTE) + 30);
        assertFalse(service.canBeOrganized(test2));

        service.clearMeetings();
    }

}