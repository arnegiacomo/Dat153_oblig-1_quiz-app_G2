package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button databaseButton = findViewById(R.id.db);
        databaseButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, DatabaseActivity.class));

        Button startQuizButton = findViewById(R.id.playquiz);
        startQuizButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, QuizActivity.class));
    }
}