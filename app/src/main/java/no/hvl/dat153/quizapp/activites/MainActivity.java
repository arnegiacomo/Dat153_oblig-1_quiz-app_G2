package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.util.Util;

public class MainActivity extends AppCompatActivity {

    private String difficulty = "easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addInitialEntries();

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch difficultySwitch = findViewById(R.id.difficultySwitch);
        difficultySwitch.setOnCheckedChangeListener(this::setDifficulty);

        Button startQuizButton = findViewById(R.id.playquiz);
        startQuizButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, QuizActivity.class, Util.DIFFICULTY_MESSAGE, difficulty));

        Button databaseButton = findViewById(R.id.db);
        databaseButton.setOnClickListener(view -> Util.startActivity(MainActivity.this, DatabaseActivity.class));

        Button add = findViewById(R.id.add);
        add.setOnClickListener(view -> Util.startActivity(MainActivity.this, AddEntryActivity.class));
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

    public void addInitialEntries() {
        Thread thread = new Thread(() -> {
            AnimalDAO.get().addAnimal("Cat", BitmapFactory.decodeResource(getResources(),
                    R.drawable.cat));
            AnimalDAO.get().addAnimal("Dog", BitmapFactory.decodeResource(getResources(),
                    R.drawable.dog));
            AnimalDAO.get().addAnimal("Giant Anteater", BitmapFactory.decodeResource(getResources(),
                    R.drawable.anteater));
        });
        thread.start();
    }

}