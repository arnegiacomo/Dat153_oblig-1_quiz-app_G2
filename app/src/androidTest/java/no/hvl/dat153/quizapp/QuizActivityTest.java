package no.hvl.dat153.quizapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.app.Activity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import no.hvl.dat153.quizapp.activites.QuizActivity;


@RunWith(AndroidJUnit4.class)

public class QuizActivityTest {

    private ActivityScenario<QuizActivity> activityScenario;

    @Before
    public void setUp() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), QuizActivity.class);
        intent.putExtra("DIFFICULTY_MESSAGE", "easy");
        activityScenario = ActivityScenario.launch(intent);
    }


    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule = new ActivityScenarioRule<>(QuizActivity.class);


    @Test
    public void testQuiz() {
        activityScenario.onActivity(activity -> {
            int correctnamebutton = activity.getCorrectButton();
            int counter = activity.getCounter();
            int countercorrect = activity.getCountercorrect();

            if (correctnamebutton==0){
                onView(withId(R.id.button1)).perform(click());
            }else if (correctnamebutton==1){
                onView(withId(R.id.button2)).perform(click());
            }else{
                onView(withId(R.id.button3)).perform(click());
            }

            counter++;
            countercorrect++;
            onView(withId(R.id.textViewQuiz)).check(matches(withText(countercorrect+" right answers out of "+counter+" questions")));

            correctnamebutton = activity.getCorrectButton();

            if (correctnamebutton==0){
                onView(withId(R.id.button2)).perform(click());
            }else if (correctnamebutton==1){
                onView(withId(R.id.button3)).perform(click());
            }else{
                onView(withId(R.id.button1)).perform(click());
            }

            counter++;
            onView(withId(R.id.textViewQuiz)).check(matches(withText(countercorrect+" right answers out of "+counter+" questions")));

        });
    }


    @After
    public void tearDown() {
        activityScenario.close();
    }

}
