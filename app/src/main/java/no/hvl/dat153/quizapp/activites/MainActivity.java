package no.hvl.dat153.quizapp.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimalDAO dao = AnimalDAO.get();
        dao.getAllNames().forEach( name -> Log.println(Log.INFO, "test", name));

        Button databaseButton = findViewById(R.id.db);
        databaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(intent);
            }
        });
    }


}