package no.hvl.dat153.quizapp.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.io.ByteArrayOutputStream;
import java.util.List;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.db.AnimalRepository;
import no.hvl.dat153.quizapp.model.Animal;
import no.hvl.dat153.quizapp.util.Util;
import no.hvl.dat153.quizapp.viewmodels.MainViewModel;

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

        Button add = findViewById(R.id.add);
        add.setOnClickListener(view -> Util.startActivity(MainActivity.this, AddEntryActivity.class));
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getAllAnimals().observe(this,
                animals -> {
//                    animals.forEach(animal -> mViewModel.deleteAnimal(animal));
                    if(animals.size() < 3)
                        addInitialEntries();
                });
    }
    private MainViewModel mViewModel;

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
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.cat);
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            byte[] byteArray = stream1.toByteArray();
            mViewModel.insertAnimal(new Animal("Cat", byteArray));
            bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.dog);
            stream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            byteArray = stream1.toByteArray();
            mViewModel.insertAnimal(new Animal("Dog", byteArray));
            bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.anteater);
            stream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            byteArray = stream1.toByteArray();
            mViewModel.insertAnimal(new Animal("Anteater", byteArray));
    }

}