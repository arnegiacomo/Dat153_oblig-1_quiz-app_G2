package no.hvl.dat153.quizapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.atomic.AtomicInteger;

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
// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

    private ActivityScenario<QuizActivity> QuizActivityScenario;
    private ActivityScenario<MainActivity> mainScenario;
    private ActivityScenario<DatabaseActivity> DatabaseScenario;
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

//        idlingResource = new MyIdlingResource();
//        IdlingRegistry.getInstance().register(idlingResource);

        Intent Quizintent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), QuizActivity.class);
        Quizintent.putExtra(Util.DIFFICULTY_MESSAGE, "easy");

        QuizActivityScenario = ActivityScenario.launch(Quizintent);

        final int[] correctnamebutton = new int[1];
        final int[] counter = new int[1];
        final int[] countercorrect = new int[1];
        QuizActivityScenario.onActivity(activity -> {
            // Perform long-running operation on background thread
//            new Thread(() -> {
            correctnamebutton[0] = activity.getCorrectButton();
            counter[0] = activity.getCounter();
            countercorrect[0] = activity.getCountercorrect();
        });
                if (correctnamebutton[0] == 0) {
                    onView(withId(R.id.button1)).perform(click());
                } else if (correctnamebutton[0] == 1) {
                    onView(withId(R.id.button2)).perform(click());
                } else {
                    onView(withId(R.id.button3)).perform(click());
                }

                counter[0]++;
                countercorrect[0]++;
                onView(withId(R.id.textViewQuiz)).check(matches(withText(countercorrect[0] + " right answers out of " + counter[0] + " questions")));

        QuizActivityScenario.onActivity(activity -> {
            // Perform long-running operation on background thread
//            new Thread(() -> {
            correctnamebutton[0] = activity.getCorrectButton();
            counter[0] = activity.getCounter();
            countercorrect[0] = activity.getCountercorrect();
        });

                if (correctnamebutton[0] == 0) {
                    onView(withId(R.id.button2)).perform(click());
                } else if (correctnamebutton[0] == 1) {
                    onView(withId(R.id.button3)).perform(click());
                } else {
                    onView(withId(R.id.button1)).perform(click());
                }

                counter[0]++;
                onView(withId(R.id.textViewQuiz)).check(matches(withText(countercorrect[0] + " right answers out of " + counter[0] + " questions")));

                // Set the IdlingResource to idle
//                idlingResource.setIdle(true);
//            }).start();

            // Set the IdlingResource to busy
//            idlingResource.setIdle(false);
//        });

        QuizActivityScenario.close();
    }


    @Test
    public void testDatabase() {
        Intents.init();


        DatabaseScenario = DatabaseScenario.launch(DatabaseActivity.class);
        AtomicInteger initialCount = new AtomicInteger(-1);
        int num = 0;
        DatabaseScenario.onActivity(activity -> {

            activity.getAll().observe(() -> activity.getLifecycle(), animals -> initialCount.set(animals.size()));

                });

        while (initialCount.get() == -1) {
            ; // do nothing
        }

        num = initialCount.get();
        // Add an entry with an image
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Resources resources = context.getResources();
        int resID = resources.getIdentifier("sample", "drawable", context.getPackageName());
        Uri imageUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resID);
        Intent dbintent = new Intent();

        System.out.println(imageUri);
        dbintent.setData(imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, dbintent);
        intending(hasAction(Intent.ACTION_PICK)).respondWith(result);
        onView(withId(R.id.addbtn)).perform(click());
        onView(withId(R.id.gallery)).perform(click());
        onView(withId(R.id.addentrybtn)).perform(click());

        AtomicInteger afterCount = new AtomicInteger(-1);
        DatabaseScenario.onActivity(activity -> {

            activity.getAll().observe(() -> activity.getLifecycle(), animals -> afterCount.set(animals.size()));

        });

        while (afterCount.get() == -1) {
            ; // do nothing
        }

        Assert.assertEquals(num +1, afterCount.get());

        DatabaseScenario.close();

    }




    @After
    public void tearDown() {



        IdlingRegistry.getInstance().unregister(idlingResource);
    }

}
