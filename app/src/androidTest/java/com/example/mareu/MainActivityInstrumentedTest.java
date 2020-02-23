package com.example.mareu;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.Activities.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest
{
    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }


    @Test
    public void meetingsList_shouldBeEmpty() {
        ViewMatchers.withId(R.layout.empty_recyclerview).matches(isDisplayed());
    }


    @Test
    public void meetingsList_addMeetingShouldAddOneMeetingToRecyclerView_ThenRemoveIt() {
        onView(ViewMatchers.withId(R.id.buttonAddMeeting)).perform(click());
        onView(ViewMatchers.withId(R.id.detail_meeting_name)).perform(click(),typeText("MeetingTest"),pressImeActionButton());

        onView(ViewMatchers.withId(R.id.button_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 3, 30));
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
        onView(ViewMatchers.withId(R.id.detail_meeting_add_email)).perform(typeText("email@lamzone.fr"),pressImeActionButton());
        onView(ViewMatchers.withId(R.id.main_scrollview)).perform(swipeUp());
        onView(ViewMatchers.withId(R.id.button_ok)).perform(click());

        onView(ViewMatchers.withId(R.id.recyclerview)).check(matches(hasChildCount(1)));
        onView(ViewMatchers.withId(R.id.meeting_delete_button)).perform(click());
        ViewMatchers.withId(R.layout.empty_recyclerview).matches(isDisplayed());
    }
/*
    @Test
    public void meetingsList_addMeetingsToRecyclerView_ThenSortThem() {
        InitApi.initMeetings();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getContext());
        onView(withText(R.string.action_date)).perform(click());

        for(int i=0;i<6;i++)
            onView(RecyclerViewMatcher.withRecyclerView(R.id.recyclerview)
                .atPositionOnView(0, R.id.meeting_summary))
                .check(matches(hasDescendant(withText(Integer.toString(InitApi.c.get((Calendar.DAY_OF_MONTH)+i))+"/"+InitApi.c.get(Calendar.MONTH)+"/"+InitApi.c.get(Calendar.YEAR)))));
    }
*/
}
