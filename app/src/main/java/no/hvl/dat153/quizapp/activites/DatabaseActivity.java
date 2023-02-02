package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.util.Util;
import no.hvl.dat153.quizapp.view.AnimalAdapter;

public class DatabaseActivity extends AppCompatActivity {

    private AnimalAdapter animalAdapter = new AnimalAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);

        View deletebtn = findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(view -> animalAdapter.updateAdapter());

        View addbtn = findViewById(R.id.addbtn);
        addbtn.setOnClickListener(view -> Util.startActivity(DatabaseActivity.this, AddEntryActivity.class));
    }
}