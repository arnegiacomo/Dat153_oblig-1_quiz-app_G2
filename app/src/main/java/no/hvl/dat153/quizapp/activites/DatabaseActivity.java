package no.hvl.dat153.quizapp.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.db.AnimalRepository;
import no.hvl.dat153.quizapp.model.Animal;
import no.hvl.dat153.quizapp.util.Util;
import no.hvl.dat153.quizapp.view.AnimalAdapter;

public class DatabaseActivity extends AppCompatActivity {

    private AnimalAdapter animalAdapter;
    private AnimalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        repository = new AnimalRepository(getApplication());
        animalAdapter = new AnimalAdapter(repository);

        repository.getAllAnimals().observe(this,
                animals -> {
                    animals.sort(Comparator.comparing(Animal::getName));
                    animalAdapter.setAnimals(animals);
                    animalAdapter.notifyDataSetChanged();
                });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalAdapter);

        View deletebtn = findViewById(R.id.deletebtn);
        deletebtn.setOnClickListener(view -> {
            animalAdapter.updateAdapter();
            repository.getAllAnimals().observe(this,
                    animals -> {
                        animalAdapter.setAnimals(animals);
                        animalAdapter.notifyDataSetChanged();
                    });
        });

        View addbtn = findViewById(R.id.addbtn);
        addbtn.setOnClickListener(view -> Util.startActivity(this, AddEntryActivity.class));

        View sortbtn = findViewById(R.id.sort);
        sortbtn.setOnClickListener(view -> {
           sort();
        });


    }

    /**
     * Flips the aplhabetical sorting in the list
     */
    private void sort() {
        List<Animal> animals = animalAdapter.getAnimals();
        Stack stack = new Stack();

        for(Animal x : animals) {
            stack.add(x);
        }

        animals = new ArrayList<>();

        while (!stack.empty()) {
            animals.add((Animal) stack.pop());
        }
        animalAdapter.setAnimals(animals);
        animalAdapter.notifyDataSetChanged();
    }

}