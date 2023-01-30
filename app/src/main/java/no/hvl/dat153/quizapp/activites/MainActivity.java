package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.util.Util;

public class MainActivity extends AppCompatActivity {

    private String difficulty = "easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch difficultySwitch = findViewById(R.id.difficultySwitch);
        difficultySwitch.setOnCheckedChangeListener(this::setDifficulty);

        Button startQuizButton = findViewById(R.id.playquiz);
        startQuizButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, QuizActivity.class, Util.DIFFICULTY_MESSAGE, difficulty));

        Button databaseButton = findViewById(R.id.db);
        databaseButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, DatabaseActivity.class));

        Button addEntryButton = findViewById(R.id.add);
        addEntryButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, AddEntryActivity.class));
    }

    /**
     * Set difficulty
     */
    public void setDifficulty(View view, boolean isChecked) {
        if (isChecked) {
            difficulty = "hard";
        } else {
            difficulty = "easy";
        }
    }

}