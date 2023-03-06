package no.hvl.dat153.quizapp.activites;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.model.Animal;
import no.hvl.dat153.quizapp.util.Util;
import no.hvl.dat153.quizapp.viewmodels.QuizViewModel;

public class QuizActivity extends AppCompatActivity {

    private String difficulty = "easy";

    private QuizViewModel qViewModel;

    private final int DELAY = 30000; //maximum time in hard mode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        qViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        //Setups that has to be done when the activity is lauched
        Intent intent = getIntent();

        Log.d("ABC", "what you get: " + intent.getStringExtra(Util.DIFFICULTY_MESSAGE));
        if (intent.getStringExtra(Util.DIFFICULTY_MESSAGE) != null) {
            difficulty = intent.getStringExtra(Util.DIFFICULTY_MESSAGE);
        }
        qViewModel.setProgressBar(findViewById(R.id.progressBar));
        update();

        //If the hard mode is active then the inactivity timer should start
        if (difficulty.equals("hard")) {
            startInactivityTimer();
        }

        /*
        Build in onClickListeners for each button
        When a button is clicked the button launches the methods check() (for animation and answer
        checking) and update() (for updating the GUI and selecting a new random photo with possible
        answers)
         */
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(v -> {
            check(0);
            update();
        });

        button2.setOnClickListener(v -> {
            check(1);
            update();
        });

        button3.setOnClickListener(v -> {
            check(2);
            update();
        });
    }


    // Handel a press on the back button or any other interruptions
    @Override
    protected void onPause() {
        super.onPause();

        //Stop the Inactivity timer and the progress bar
        mHandler.removeCallbacks(mRunnable);
    }


    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int progress = qViewModel.getProgress() + 100;
            qViewModel.setProgress(progress);
            qViewModel.getProgressBar().setProgress(progress);
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
        qViewModel.getProgressBar().setMax(DELAY);
    }

    //This method resets the values of the inactivity timer and the progress bar and restarts it with startInactivityTimer()
    private void resetInactivityTimer() {
        mHandler.removeCallbacks(mRunnable);
        qViewModel.setProgress(0);
        qViewModel.getProgressBar().setProgress(0);
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
    private void check(int buttonnumber) {

        // Animation of the background color
        View view = findViewById(R.id.QuizLayout);
        int colorFrom = Color.WHITE;
        int colorToCorrect = Color.GREEN;
        int colorToFalse = Color.RED;
        if (qViewModel.getCorrectnameplace() == buttonnumber) {
            ValueAnimator colorAnimationCorrect = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorToCorrect);
            colorAnimationCorrect.setDuration(1000);
            colorAnimationCorrect.addUpdateListener(animation -> view.setBackgroundColor((int) animation.getAnimatedValue()));
            colorAnimationCorrect.start();

            ValueAnimator colorAnimationBack = ValueAnimator.ofObject(new ArgbEvaluator(), colorToCorrect, colorFrom);
            colorAnimationBack.setDuration(1000);
            colorAnimationBack.addUpdateListener(animation -> view.setBackgroundColor((int) animation.getAnimatedValue()));
            colorAnimationBack.start();
        } else {
            ValueAnimator colorAnimationFalse = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorToFalse);
            colorAnimationFalse.setDuration(1000);
            colorAnimationFalse.addUpdateListener(animation -> view.setBackgroundColor((int) animation.getAnimatedValue()));
            colorAnimationFalse.start();

            ValueAnimator colorAnimationBack = ValueAnimator.ofObject(new ArgbEvaluator(), colorToFalse, colorFrom);
            colorAnimationBack.setDuration(1000);
            colorAnimationBack.addUpdateListener(animation -> view.setBackgroundColor((int) animation.getAnimatedValue()));
            colorAnimationBack.start();
        }

        // Animation of the picture
        Animation correctAnimation = AnimationUtils.loadAnimation(this, R.anim.correct_animation);
        Animation incorrectAnimation = AnimationUtils.loadAnimation(this, R.anim.incorrect_animation);
        ImageView image = findViewById(R.id.imageViewQuiz);

        // Checking if button is correct und updating the counters for number of questions and correct answers
        if (buttonnumber == qViewModel.getCorrectnameplace()) {
            qViewModel.setCountercorrect(qViewModel.getCountercorrect() + 1);
            image.startAnimation(correctAnimation);
        } else {
            image.startAnimation(incorrectAnimation);
            Toast.makeText(getApplicationContext(), "Correct answer was: " + qViewModel.getCorrectname(), Toast.LENGTH_SHORT).show();
        }

        qViewModel.setCounter(qViewModel.getCounter() + 1);
        qViewModel.setIndex(-1);
    }


    /*
    This method is responsible for updating the GUI of the Quiz Activity. It updates the text
    with the counter of correct answers, selects a new animal randomly, updates the image
    with the new animal, randomly decides on which button the correct answer is placed,
    selects to random wrong answers from the database and updates the text in the buttons
    withe the correct and the wrong names. If hard mode is active the inactivity timer is
    restarted in the end.
     */
    @SuppressLint("SetTextI18n")
    private void update() {

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        ImageView imageView = findViewById(R.id.imageViewQuiz);
        TextView textView = findViewById(R.id.textViewQuiz);

        //update the textView with the number of correct answers
        textView.setText(qViewModel.getCountercorrect() + " right answers out of " + qViewModel.getCounter() + " questions");

        Random random = new Random();

        //choose a random animal who's picture is shown next
        qViewModel.getAllAnimals().observe(this,
                animals -> {
                    int size = animals.size();
                    if(qViewModel.getIndex() == -1) {
                        qViewModel.setIndex(random.nextInt(size));
                        //generating a random number for where the correct answer should be placed
                        qViewModel.setCorrectnameplace(random.nextInt(3));
                        //generating random numbers for selecting the wrong names from the database
                        qViewModel.setOption1(random.nextInt(size));
                        qViewModel.setOption2(random.nextInt(size));

                        //The inactivity timer is reset after all the graphical stuff is done
                        if (difficulty.equals("hard")) {
                            resetInactivityTimer();
                        }
                    } 
                    Animal animal = Objects.requireNonNull(qViewModel.getAllAnimals().getValue()).get(qViewModel.getIndex());

                    //updating the imageView
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(animal.getBitmap(), 0, animal.getBitmap().length));



            /*
            This logic is getting two random names from the database that are different to each other
            and different to the correct answer
             */
                    if (size > 2) {
                        if (qViewModel.getOption1() == qViewModel.getIndex()) {
                            if (qViewModel.getOption1() < size - 1) {
                                qViewModel.setOption1(qViewModel.getOption1() + 1);
                            } else {
                                qViewModel.setOption1(qViewModel.getOption1() - 1);
                            }

                        }
                        if (qViewModel.getOption2() == qViewModel.getIndex() ||qViewModel.getOption2() ==qViewModel.getOption1()) {
                            if (qViewModel.getOption2() < size - 2) {
                                qViewModel.setOption2(qViewModel.getOption2() + 1);
                                if (qViewModel.getOption2() ==qViewModel.getOption1() ||qViewModel.getOption2() == qViewModel.getIndex()) {
                                    qViewModel.setOption2(qViewModel.getOption2() + 1);
                                }
                            } else if (qViewModel.getOption2() > 1) {
                                qViewModel.setOption2(qViewModel.getOption2() - 1);
                                if (qViewModel.getOption2() ==qViewModel.getOption1() ||qViewModel.getOption2() == qViewModel.getIndex()) {
                                    qViewModel.setOption2(qViewModel.getOption2() - 1);
                                }
                            } else {
                                if (qViewModel.getIndex() == 0 ||qViewModel.getOption1() == 0) {
                                   qViewModel.setOption2(qViewModel.getOption2() + 1);
                                } else {
                                   qViewModel.setOption2(qViewModel.getOption2() - 1);
                                }
                            }
                        }
                    } else if (size == 2) {
                        if (qViewModel.getIndex() == 1) {
                           qViewModel.setOption1(0);
                           qViewModel.setOption2(0);
                        } else {
                            qViewModel.setOption1(1);
                            qViewModel.setOption2(1);
                        }
                    } else if (size == 1) {
                        qViewModel.setOption1(0);
                        qViewModel.setOption2(0);
                    }

                        /*
                        Depending on the shuffle chosen correctnamespace (button number where the correct answer
                        should sit) the text of the buttons is updated
                         */

                    List<String> names = animals.stream().map(Animal::getName).collect(Collectors.toList());
                    qViewModel.setCorrectname(names.get(qViewModel.getIndex()));
                    if (qViewModel.getCorrectnameplace() == 0) {
                        button1.setText(qViewModel.getCorrectname());
                        button2.setText(names.get(qViewModel.getOption1()));
                        button3.setText(names.get(qViewModel.getOption2()));
                    } else if (qViewModel.getCorrectnameplace() == 1) {
                        button1.setText(names.get(qViewModel.getOption1()));
                        button2.setText(qViewModel.getCorrectname());
                        button3.setText(names.get(qViewModel.getOption2()));
                    } else {
                        button1.setText(names.get(qViewModel.getOption1()));
                        button2.setText(names.get(qViewModel.getOption2()));
                        button3.setText(qViewModel.getCorrectname());
                    }
                });


    }


    public int getCorrectButton() {
        return qViewModel.getCorrectnameplace();
    }

    public int getCounter() {
        return qViewModel.getCounter();
    }

    public int getCountercorrect() {
        return qViewModel.getCountercorrect();
    }
}