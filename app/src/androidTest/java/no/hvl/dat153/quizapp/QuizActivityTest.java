package no.hvl.dat153.quizapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import no.hvl.dat153.quizapp.activites.DatabaseActivity;
import no.hvl.dat153.quizapp.activites.MainActivity;
import no.hvl.dat153.quizapp.activites.QuizActivity;
import no.hvl.dat153.quizapp.util.Util;



class MyIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdle = false;

    @Override
    public String getName() {
        return "MyIdlingResource";
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}



@RunWith(AndroidJUnit4.class)

public class QuizActivityTest {

    private ActivityScenario<QuizActivity> QuizActivityScenario;
    private ActivityScenario<MainActivity> mainScenario;
    private DatabaseActivity DatabaseActivity;
    private MyIdlingResource idlingResource;




    @Test
    public void testMainActivity() {

        // Launch the main activity
        mainScenario = ActivityScenario.launch(MainActivity.class);

        // Wait for the main activity to start and the play quiz button to be displayed
        onView(withId(R.id.playquiz)).check(matches(isDisplayed()));

        // Perform click on the play quiz button
        onView(withId(R.id.playquiz)).perform(click());

        // Wait for the quiz activity to start and the first answer button to be displayed
        onView(withId(R.id.button1)).check(matches(isDisplayed()));

        // Perform click on the first answer button
        onView(withId(R.id.button1)).perform(click());

        mainScenario.close();


    }





    @Test
    public void testQuizActivity() {

        idlingResource = new MyIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

        Intent Quizintent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), QuizActivity.class);
        Quizintent.putExtra(Util.DIFFICULTY_MESSAGE, "easy");

        QuizActivityScenario = ActivityScenario.launch(Quizintent);

        QuizActivityScenario.onActivity(activity -> {
            // Perform long-running operation on background thread
            new Thread(() -> {
                int correctnamebutton = activity.getCorrectButton();
                int counter = activity.getCounter();
                int countercorrect = activity.getCountercorrect();

                if (correctnamebutton == 0) {
                    onView(withId(R.id.button1)).perform(click());
                } else if (correctnamebutton == 1) {
                    onView(withId(R.id.button2)).perform(click());
                } else {
                    onView(withId(R.id.button3)).perform(click());
                }

                counter++;
                countercorrect++;
                onView(withId(R.id.textViewQuiz)).check(matches(withText(countercorrect + " right answers out of " + counter + " questions")));

                correctnamebutton = activity.getCorrectButton();

                if (correctnamebutton == 0) {
                    onView(withId(R.id.button2)).perform(click());
                } else if (correctnamebutton == 1) {
                    onView(withId(R.id.button3)).perform(click());
                } else {
                    onView(withId(R.id.button1)).perform(click());
                }

                counter++;
                onView(withId(R.id.textViewQuiz)).check(matches(withText(countercorrect + " right answers out of " + counter + " questions")));

                // Set the IdlingResource to idle
                idlingResource.setIdle(true);
            }).start();

            // Set the IdlingResource to busy
            idlingResource.setIdle(false);
        });

        QuizActivityScenario.close();
    }





    @After
    public void tearDown() {



        IdlingRegistry.getInstance().unregister(idlingResource);
    }

}
