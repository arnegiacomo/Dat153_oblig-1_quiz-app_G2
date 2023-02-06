package no.hvl.dat153.quizapp.db;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.hvl.dat153.quizapp.model.Animal;

/**
 * Data access object for our animal database
 */
public class AnimalDAO {

    private static AnimalDAO animalDAO;
    private final Map<String, Animal> repo = new HashMap<>();

    /**
     * Populates the database
     */
    private AnimalDAO() {

    }

    /**
     * Get all animals
     * @return All animals
     */
    public List<Animal> getAllAnimals() {
        return new ArrayList<>(repo.values());
    }

    /**
     * Get all animal names
     *
     * @return List of all anmal names
     */
    public List<String> getAllNames() {
        return new ArrayList<>(repo.keySet());
    }

    /**
     * Add new animal to database
     *
     * @param name Animal name
     * @param bitmap bitmap of image to animal
     */
    public void addAnimal(String name, Bitmap bitmap) {
        Animal animal = new Animal(name, bitmap);
        repo.put(animal.getName(), animal);
    }

    /**
     * Remove an animal from DB
     *
     * @param name name of animal to be deleted
     */
    public void removeAnimal(String name) {
        repo.remove(name);
    }

    /**
     * Singleton static access
     *
     * @return singleton instance
     */
    public static AnimalDAO get() {
        if (animalDAO == null)
            animalDAO = new AnimalDAO();

        return animalDAO;
    }
}
