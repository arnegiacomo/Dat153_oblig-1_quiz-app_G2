package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.db.AnimalRepository;
import no.hvl.dat153.quizapp.model.Animal;

public class AddEntryActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private String name;
    private ImageView image;

    private AnimalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        repository = new AnimalRepository( getApplication());

        View gallerybtn = findViewById(R.id.gallery);
        gallerybtn.setOnClickListener(view -> pickImageFromGallery());

        EditText nameInput = findViewById(R.id.addAnimalName);
        nameInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
        });

        image = findViewById(R.id.imageanimal);

        View add = findViewById(R.id.addentrybtn);

        add.setOnClickListener(view -> {

            new Thread(() -> {
                // Convert the bitmap to a byte array
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                byte[] byteArray = stream1.toByteArray();
                repository.insertAnimal(new Animal(name, byteArray));
            }).start();
            finish();
        });



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
            this.bitmap = bitmap;
            this.image.setImageBitmap(bitmap);
        }
    }
}