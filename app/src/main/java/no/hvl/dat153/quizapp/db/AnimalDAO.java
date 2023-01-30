package no.hvl.dat153.quizapp.db;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.model.Animal;

/**
 * Data access object for our animal database
 */
public class AnimalDAO {

    private static AnimalDAO animalDAO;
    private Map<String, Animal> repo = new HashMap<String, Animal>();

    private AnimalDAO() throws URISyntaxException {
        Animal cat = new Animal("Cat", R.drawable.cat);
        Animal dog = new Animal("Dog", R.drawable.dog);
        Animal giantAntEater = new Animal("Giant Ant Eater", R.drawable.anteater);

        repo.put(cat.getName(), cat);
        repo.put(dog.getName(), dog);
        repo.put(giantAntEater.getName(), giantAntEater);
    }

    /**
     * Get all animals
     * @return
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
     * @param image_res_id ID of image to animal
     */
    public void addAnimal(String name, int image_res_id) throws URISyntaxException {
        Animal animal = new Animal(name, image_res_id);
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
        if (animalDAO == null) {
            try {
                animalDAO = new AnimalDAO();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return animalDAO;
    }

}
