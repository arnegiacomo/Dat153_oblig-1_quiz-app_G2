package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.InputStream;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.util.Util;
import no.hvl.dat153.quizapp.view.AnimalAdapter;

public class DatabaseActivity extends AppCompatActivity {

    private final AnimalAdapter animalAdapter = new AnimalAdapter();

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
        addbtn.setOnClickListener(view -> Util.startActivity(this, AddEntryActivity.class));

        View sortbtn = findViewById(R.id.sort);
        sortbtn.setOnClickListener(view -> {
                    AnimalDAO.get().sort();
                    animalAdapter.notifyDataSetChanged();
        });


        animalAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        animalAdapter.notifyDataSetChanged();
    }

}