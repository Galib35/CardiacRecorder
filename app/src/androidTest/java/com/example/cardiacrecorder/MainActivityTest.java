package com.example.cardiacrecorder;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;

import android.view.View;
import android.widget.AbsListView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.Objects;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private final String SYS = "100", SYS_NEW = "65";
    private final String DYS = "60", DYS_NEW = "95";
    private final String HEART = "60", HEART_NEW = "100";
    private final String COMMENT = "No Comment", COMMENT_NEW = "Modified wrong entry";
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testAll() {



        addData();

        testDetails();

        editData();

        testDelete();

    }

    @Test
    public void testDetails(){
        try {
            Thread.sleep(2000); // Delay in milliseconds (e.g., 3000 ms = 3 seconds)
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        int index = getTotalItem()-1;

        onView(withId(R.id.rec_iew)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));//RecyclerViewActions.actionOnItemAtPosition(index, click())

        onView(withId(R.id.exp_sbp)).check(matches(isDisplayed()));

        onView(withText(SYS +" mm Hg")).check(matches(isDisplayed()));
        onView(withText(DYS +" mm Hg")).check(matches(isDisplayed()));
        onView(withText(HEART +" bpm")).check(matches(isDisplayed()));
        onView(withText(COMMENT)).check(matches(isDisplayed()));

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        Espresso.pressBack();

    }

    @Test
    public void addData(){

        onView(withId(R.id.add_btn)).perform(click());

        onView(withId(R.id.sys_edt)).perform(ViewActions.typeText(SYS));

        onView(withId(R.id.dia_edt)).perform(ViewActions.typeText(DYS));

        onView(withId(R.id.hr_edt)).perform(ViewActions.typeText(HEART));

        onView(withId(R.id.date_pick)).perform(click());
        onView(ViewMatchers.withText("OK")).perform(click());

        onView(withId(R.id.time_pick)).perform(click());
        onView(ViewMatchers.withText("OK")).perform(click());

        onView(withId(R.id.cmnt_edt)).perform(ViewActions.typeText(COMMENT));

        Espresso.pressBack(); //hide keyboard

        int prevCount = getTotalItem();

        onView(withId(R.id.save_btn)).perform(click());

        Espresso.pressBack();
        //safeSleep(2);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        int curCount = getTotalItem();

        assertEquals(prevCount,curCount-1);

    }

    @Test
    public void editData(){

        onView(withId(R.id.rec_iew))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,MyViewAction.clickChildViewWithId(R.id.edit_btn)));

        //onView(withText("Edit")).inRoot(isPlatformPopup()).perform(click());

        //onView(withId(R.id.sys_edt)).perform(clearText());
        onView(withId(R.id.sys_edt)).perform(ViewActions.typeText("65"));


        onView(withId(R.id.dia_edt)).perform(clearText());
        onView(withId(R.id.dia_edt)).perform(ViewActions.typeText(DYS_NEW));

        onView(withId(R.id.hr_edt)).perform(clearText());
        onView(withId(R.id.hr_edt)).perform(ViewActions.typeText(HEART_NEW));

        onView(withId(R.id.cmnt_edt)).perform(clearText());
        onView(withId(R.id.cmnt_edt)).perform(ViewActions.typeText(COMMENT_NEW));

        Espresso.pressBack(); //hide keyboard


        int prevCount = getTotalItem();

        onView(withId(R.id.update_btn)).perform(click());

        Espresso.pressBack();
        //safeSleep(2);

        try {
            Thread.sleep(500); // Delay in milliseconds (e.g., 3000 ms = 3 seconds)
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        int curCount = getTotalItem();

        assertEquals(prevCount,curCount);
    }

    @Test
    public void testDelete(){
        try {
            Thread.sleep(2000); // Delay in milliseconds (e.g., 3000 ms = 3 seconds)
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        int prevCount = getTotalItem();
        onView(withId(R.id.rec_iew))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,MyViewAction.clickChildViewWithId(R.id.delete_btn)));

        try {
            Thread.sleep(2000); // Delay in milliseconds (e.g., 3000 ms = 3 seconds)
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        int curCount = getTotalItem();

        assertEquals(prevCount-1,curCount);

    }

    private int getTotalItem(){
        final int[] initialItemCount = new int[1];
        activityScenarioRule.getScenario().onActivity(activity -> {
            if( ((RecyclerView)activity.findViewById(R.id.rec_iew)).getAdapter() != null ) {
                initialItemCount[0] = Objects.requireNonNull(((RecyclerView) activity.findViewById(R.id.rec_iew)).getAdapter()).getItemCount();
            }
        });
        return initialItemCount[0];
    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return ViewMatchers.isAssignableFrom(RecyclerView.class);
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }





}