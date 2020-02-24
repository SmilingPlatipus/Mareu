package com.example.mareu.Utils;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.example.mareu.R;

import org.hamcrest.Matchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MeetingRecyclerViewFiller
{
    public static void meetingsList_addMeeting(String meetingName,String meetingRoom,int day) {

        onView(ViewMatchers.withId(R.id.buttonAddMeeting)).perform(click());
        onView(ViewMatchers.withId(R.id.detail_meeting_name)).perform(click(),typeText(meetingName),pressImeActionButton());

        onView(ViewMatchers.withId(R.id.spinner_roomlist)).perform(click());
        onView(withText(meetingRoom)).perform(click());

        onView(ViewMatchers.withId(R.id.main_scrollview)).perform(swipeDown());

        onView(ViewMatchers.withId(R.id.button_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 3, 10-day));
        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(ViewMatchers.withId(R.id.button_start_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 0));
        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(ViewMatchers.withId(R.id.button_end_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12,30));
        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());


        onView(ViewMatchers.withId(R.id.main_scrollview)).perform(swipeUp());
        onView(ViewMatchers.withId(R.id.detail_meeting_add_email)).perform(typeText("email@lamzone.fr"), pressImeActionButton());
        onView(ViewMatchers.withId(R.id.main_scrollview)).perform(swipeUp());
        onView(ViewMatchers.withId(R.id.button_ok)).perform(click());
    }
}
