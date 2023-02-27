package no.hvl.dat153.quizapp.activites;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.model.Animal;
import no.hvl.dat153.quizapp.util.Util;

public class QuizActivity extends AppCompatActivity {

    private String difficulty;

    private final AnimalDAO animalDAO = AnimalDAO.get();

    private int correctnameplace;
    private int index;
    private int counter;
    private int countercorrect;
    private String correctname;
    private ProgressBar progressBar;
    private int progress = 0;
    private final int DELAY = 30000; //maximum time in hard mode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        //Setups that has to be done when the activity is lauched
        Intent intent = getIntent();
        difficulty = intent.getStringExtra(Util.DIFFICULTY_MESSAGE);
        List<String> database = AnimalDAO.get().getAllNames();
        Collections.shuffle(database);
        counter=0;
        countercorrect=0;
        progressBar = findViewById(R.id.progressBar);
        update();

        //If the hard mode is active then the inactivity timer should start
        if (difficulty.equals("hard")){
            startInactivityTimer();
        }

        /*
        Build in onClickListeners for each button
        When a button is clicked the button launches the methods check() (for animation and answer
        checking) and update() (for updating the GUI and selecting a new random photo with possible
        answers)
         */
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(0);
                update();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(1);
                update();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(2);
                update();
            }
        });
    }



    // Handel a press on the back button or any other interruptions
    @Override
    protected void onPause() {
        super.onPause();

        //Finishing the activity
        finish();

        //Reset the Inactivity timer and the progress bar
        mHandler.removeCallbacks(mRunnable);
        progress = 0;
        progressBar.setProgress(0);

    }


    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            progress += 100;
            progressBar.setProgress(progress);
            if (progress < DELAY) {
                mHandler.postDelayed(this, 100);
            } else {
                Toast.makeText(getApplicationContext(), "Timer run out", Toast.LENGTH_SHORT).show();
                check(-1);
                update();
            }

        }
    };

    //With this method you can start the inactivity timer
    private void startInactivityTimer() {
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 100);
        progressBar.setMax(DELAY);
    }

    //This method resets the values of the inactivity timer and the progress bar and restarts it with startInactivityTimer()
    private void resetInactivityTimer() {
        mHandler.removeCallbacks(mRunnable);
        progress = 0;
        progressBar.setProgress(0);
        startInactivityTimer();
    }


        /*
        This method is checking if the correct button is clicked by comparing the value
        in the number of the correct button with the number of the button that is clicked.
        Depending on this the methods lauches either an animation for a correct answer or
        one for the false answer. An animation consists of two seperated parts: first the
        picture is increasing (correct answer) or decreasing (false answer), second the
        background color is set for a duration of two second eiter on green or red
         */
        private void check(int buttonnumber){
            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);

            // Animation of the background color
            View view =findViewById(R.id.QuizLayout);
            int colorFrom = Color.WHITE;
            int colorToCorrect = Color.GREEN;
            int colorToFalse = Color.RED;
            if (correctnameplace==buttonnumber){
                ValueAnimator colorAnimationCorrect = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorToCorrect);
                colorAnimationCorrect.setDuration(1000);
                colorAnimationCorrect.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                            view.setBackgroundColor((int) animation.getAnimatedValue());
                    }
                });colorAnimationCorrect.start();

                ValueAnimator colorAnimationBack = ValueAnimator.ofObject(new ArgbEvaluator(), colorToCorrect,colorFrom);
                colorAnimationBack.setDuration(1000);
                colorAnimationBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        view.setBackgroundColor((int) animation.getAnimatedValue());
                    }
                });colorAnimationBack.start();
            }
            else{
                ValueAnimator colorAnimationFalse = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorToFalse);
                colorAnimationFalse.setDuration(1000);
                colorAnimationFalse.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                            view.setBackgroundColor((int) animation.getAnimatedValue());
                    }
                 });colorAnimationFalse.start();

                 ValueAnimator colorAnimationBack = ValueAnimator.ofObject(new ArgbEvaluator(), colorToFalse,colorFrom);
                colorAnimationBack.setDuration(1000);
                colorAnimationBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                         view.setBackgroundColor((int) animation.getAnimatedValue());
                    }
                });colorAnimationBack.start();
            }

            // Animation of the picture
            Animation correctAnimation = AnimationUtils.loadAnimation(this, R.anim.correct_animation);
            Animation incorrectAnimation = AnimationUtils.loadAnimation(this, R.anim.incorrect_animation);
            ImageView image = findViewById(R.id.imageViewQuiz);

            // Checking if button is correct und updating the counters for number of questions and correct answers
            if (buttonnumber==correctnameplace){
                countercorrect++;
                image.startAnimation(correctAnimation);
            } else {
                image.startAnimation(incorrectAnimation);
                Toast.makeText(getApplicationContext(), "Correct answer was: "+correctname, Toast.LENGTH_SHORT).show();
            }

            counter++;
        }


        /*
        This method is responsible for updating the GUI of the Quiz Activity. It updates the text
        with the counter of correct answers, selects a new animal randomly, updates the image
        with the new animal, randomly decides on which button the correct answer is placed,
        selects to random wrong answers from the database and updates the text in the buttons
        withe the correct and the wrong names. If hard mode is active the inactivity timer is
        restarted in the end.
         */
        private void update() {

            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);
            ImageView imageView = findViewById(R.id.imageViewQuiz);
            TextView textView = findViewById(R.id.textViewQuiz);

            //update the textView with the number of correct answers
            textView.setText(countercorrect+" right answers out of "+counter+" questions");

            Random random = new Random();

            //choose a random animal who's picture is shown next
            int size = animalDAO.getAllNames().size();
            index = random.nextInt(size);
            Animal animal = animalDAO.getAllAnimals().get(index);

            //updating the imageView
            imageView.setImageBitmap(animal.getBitmap());

            //generating a random number for where the correct answer should be placed
            correctnameplace = random.nextInt(3);
            //generating random numbers for selecting the wrong names from the database
            int option1=random.nextInt(size);
            int option2=random.nextInt(size);

            /*
            This logic is getting two random names from the database that are different to each other
            and different to the correct answer
             */
            if (size>2){
                if(option1==index){
                    if (option1<size-1){option1++;}else{option1--;};
                }
                if(option2==index||option2==option1){
                    if (option2<size-2){option2++;if (option2==option1||option2==index){option2++;}}
                    else if(option2>1){option2--;if (option2==option1||option2==index){option2--;}}
                    else {if(index==0||option1==0){option2++;}else{option2--;}}
                }
            }else if(size==2){
                if (index==1){option1=0;option2=0;}
                else{option1=1;option2=1;}
            }else if (size==1) {
                option1=0;option2=0;
            }

            /*
            Depending on the shuffle chosen correctnamespace (button number where the correct answer
            should sit) the text of the buttons is updated
             */
            correctname=animalDAO.getAllNames().get(index);
            if (correctnameplace ==0){
                button1.setText(correctname);
                button2.setText(animalDAO.getAllNames().get(option1));
                button3.setText(animalDAO.getAllNames().get(option2));
            }
            else if (correctnameplace ==1){
                button1.setText(animalDAO.getAllNames().get(option1));
                button2.setText(correctname);
                button3.setText(animalDAO.getAllNames().get(option2));
            }
            else{
                button1.setText(animalDAO.getAllNames().get(option1));
                button2.setText(animalDAO.getAllNames().get(option2));
                button3.setText(correctname);
            }

            //The inactivity timer is reset after all the graphical stuff is done
            if (difficulty.equals("hard")){
                resetInactivityTimer();
            }
        }

    }