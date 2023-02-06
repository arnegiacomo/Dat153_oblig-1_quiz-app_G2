package no.hvl.dat153.quizapp.activites;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        difficulty = intent.getStringExtra(Util.DIFFICULTY_MESSAGE);

        List<String> database = AnimalDAO.get().getAllNames();
        Collections.shuffle(database);
        counter=0;
        countercorrect=0;
        update();

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



    // Handel a press on the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



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

            if (buttonnumber==correctnameplace){
                countercorrect++;
                image.startAnimation(correctAnimation);
            } else {image.startAnimation(incorrectAnimation);}

            counter++;

        }


        private void update() {

            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);
            ImageView imageView = findViewById(R.id.imageViewQuiz);
            TextView textView = findViewById(R.id.textViewQuiz);

            textView.setText(countercorrect+" right answers out of "+counter+" questions");

            Random random = new Random();

            int size = animalDAO.getAllNames().size();
            index = random.nextInt(size);
            Animal animal = animalDAO.getAllAnimals().get(index);


            imageView.setImageBitmap(animal.getBitmap());

            correctnameplace = random.nextInt(3);
            int option1=random.nextInt(size);
            int option2=random.nextInt(size);

            if(option1==index){
                if (option1<size-1){option1++;}else{option1--;};
            }
            if(option2==index||option2==option1){
                if (option2<size-2){option2++;if (option2==option1||option2==index){option2++;}}
                else if(option2>1){option2--;if (option2==option1||option2==index){option2--;}}
                else {if(index==0||option1==0){option2++;}else{option2--;}}
            }

            if (correctnameplace ==0){
                button1.setText(animalDAO.getAllNames().get(index));
                button2.setText(animalDAO.getAllNames().get(option1));
                button3.setText(animalDAO.getAllNames().get(option2));
            }
            else if (correctnameplace ==1){
                button1.setText(animalDAO.getAllNames().get(option1));
                button2.setText(animalDAO.getAllNames().get(index));
                button3.setText(animalDAO.getAllNames().get(option2));
            }
            else{
                button1.setText(animalDAO.getAllNames().get(option1));
                button2.setText(animalDAO.getAllNames().get(option2));
                button3.setText(animalDAO.getAllNames().get(index));
            }

        }

    }