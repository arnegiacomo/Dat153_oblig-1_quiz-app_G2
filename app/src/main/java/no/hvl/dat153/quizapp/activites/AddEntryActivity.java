package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;

public class AddEntryActivity extends AppCompatActivity {

    private final AnimalDAO animalDAO = AnimalDAO.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
    }
}