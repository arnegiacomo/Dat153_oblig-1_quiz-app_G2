package no.hvl.dat153.quizapp.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import no.hvl.dat153.quizapp.model.Animal;

public class AnimalDAO {

    private static AnimalDAO animalDAO;
    private Map<String, Animal> repo = new HashMap<String, Animal>();

    private AnimalDAO() {
        Animal cat = new Animal("Cat", "res/images/cat.webp");
        Animal dog = new Animal("Dog", "res/images/dog.jpeg");
        Animal giantAntEater = new Animal("Giant Ant Eater", "res/images/anteater.JPG");

        repo.put(cat.getName(), cat);
        repo.put(dog.getName(), dog);
        repo.put(giantAntEater.getName(), giantAntEater);
    }

    public List<String> getAllNames() {
        return new ArrayList<>(repo.keySet());
    }

    public void addAnimal(String name, String URI) {
        Animal animal = new Animal(name, URI);
        repo.put(animal.getName(), animal);
    }

    public static AnimalDAO get() {
        if (animalDAO == null) {
            animalDAO = new AnimalDAO();
        }

        return animalDAO;
    }

}