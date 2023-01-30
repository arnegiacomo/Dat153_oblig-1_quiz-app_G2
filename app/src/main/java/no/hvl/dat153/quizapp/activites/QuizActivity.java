package no.hvl.dat153.quizapp.activites;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.model.Animal;

public class QuizActivity extends AppCompatActivity {


    private int correctnameplace;
    private int index;
    private int counter;
    private int countercorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

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

        private void check(int buttonnumber){
            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);

            if (correctnameplace==0){
                button1.setBackgroundColor(Color.parseColor("#228B22"));
                button2.setBackgroundColor(Color.parseColor("#FF0000"));
                button3.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            else if (correctnameplace==1){
                button1.setBackgroundColor(Color.parseColor("#FF0000"));
                button2.setBackgroundColor(Color.parseColor("#228B22"));
                button3.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            else{
                button1.setBackgroundColor(Color.parseColor("#FF0000"));
                button2.setBackgroundColor(Color.parseColor("#FF0000"));
                button3.setBackgroundColor(Color.parseColor("#228B22"));
            }

            if (buttonnumber==correctnameplace){
                countercorrect++;
            }

            counter++;

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    button1.setBackgroundColor(Color.parseColor("#0000FF"));
                    button2.setBackgroundColor(Color.parseColor("#0000FF"));
                    button3.setBackgroundColor(Color.parseColor("#0000FF"));
                }
            };
            timer.schedule(task, 2000);

        }


        private void update() {

            Button button1 = (Button) findViewById(R.id.button1);
            Button button2 = (Button) findViewById(R.id.button2);
            Button button3 = (Button) findViewById(R.id.button3);
            ImageView imageView = findViewById(R.id.imageViewQuiz);

            Random random = new Random();

            int size = AnimalDAO.getAllNames().size();
            index = random.nextInt(size);
            Animal animal = AnimalDAO.getAllAnimals().get(index);


            imageView.setImageResource(animal.getImage());

            correctnameplace = random.nextInt(3);
            int option1=random.nextInt(size);
            int option2=random.nextInt(size);

            if(option1==index){
                if (option1<size-1){option1++;}else{option1--;};
            }
            if(option2==index){
                if (option2<size-2){option2++;if (option2==option1){option2++;}}
                else{option2--;if (option2==option1){option2--;}};
            }

            if (correctnameplace ==0){
                button1.setText(AnimalDAO.getAllNames().get(index));
                button2.setText(AnimalDAO.getAllNames().get(option1));
                button3.setText(AnimalDAO.getAllNames().get(option2));
            }
            else if (correctnameplace ==1){
                button1.setText(AnimalDAO.getAllNames().get(option1));
                button2.setText(AnimalDAO.getAllNames().get(index));
                button3.setText(AnimalDAO.getAllNames().get(option2));
            }
            else{
                button1.setText(AnimalDAO.getAllNames().get(option1));
                button2.setText(AnimalDAO.getAllNames().get(option2));
                button3.setText(AnimalDAO.getAllNames().get(index));
            }

        }

    }