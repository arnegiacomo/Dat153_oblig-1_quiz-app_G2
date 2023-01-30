package no.hvl.dat153.quizapp.util;

import android.content.Context;
import android.content.Intent;

import no.hvl.dat153.quizapp.activites.DatabaseActivity;
import no.hvl.dat153.quizapp.activites.MainActivity;

/**
 * Class with helper functions
 */
public class Util {

    /**
     * Util class with only static methods, no need for a constructor
     */
    private Util() {}

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
}
