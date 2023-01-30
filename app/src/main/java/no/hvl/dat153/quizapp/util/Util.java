package no.hvl.dat153.quizapp.util;

import android.content.Context;
import android.content.Intent;

/**
 * Class with helper functions
 */
public abstract class Util {

    public final static String DIFFICULTY_MESSAGE = "difficulty";

    /**
     * Start activity
     *
     * @param currentActivity Current activity
     * @param activityToStart activity to start
     */
    public static void startActivity(Context currentActivity, Class activityToStart) {
        Intent intent = new Intent(currentActivity, activityToStart);
        currentActivity.startActivity(intent);
    }

    /**
     * Start activity with intent parameter
     *
     * @param currentActivity Current activity
     * @param activityToStart activity to start
     */
    public static void startActivity(Context currentActivity, Class activityToStart, String messageKey, String messageValue) {
        Intent intent = new Intent(currentActivity, activityToStart);
        intent.putExtra(messageKey, messageValue);
        currentActivity.startActivity(intent);
    }
}
