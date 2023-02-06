package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.InputStream;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.model.Animal;
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
        addbtn.setOnClickListener(view -> pickImageFromGallery());

        animalAdapter.notifyDataSetChanged();
    }


    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            Bitmap bitmap = null;
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                // Handle the error
            }
            AnimalDAO.get().addAnimal("Test", bitmap);
            animalAdapter.notifyDataSetChanged();
        }
    }
}