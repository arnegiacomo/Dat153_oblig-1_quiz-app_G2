package no.hvl.dat153.quizapp.db;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import no.hvl.dat153.quizapp.model.Animal;

/**
 * Data access object for our animal database
 */
@Dao
public interface AnimalDAO {

    @Insert
    void insertAll(Animal... animals);

    @Delete
    void delete(Animal animal);

    /**
     * Get all animals
     * @return All animals
     */
    @Query("SELECT * FROM animals")
    LiveData<List<Animal>> getAll();

    /**
     * Get all animal names
     *
     * @return List of all anmal names
     */
    @Query("SELECT name FROM animals")
    LiveData<List<String>> getNames();

    @Query("SELECT * FROM animals WHERE name = :name")
    List<Animal> find(String name);
}
