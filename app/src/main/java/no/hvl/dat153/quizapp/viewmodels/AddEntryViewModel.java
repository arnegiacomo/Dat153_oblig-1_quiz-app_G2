package no.hvl.dat153.quizapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import no.hvl.dat153.quizapp.db.AnimalRepository;
import no.hvl.dat153.quizapp.model.Animal;

public class AddEntryViewModel extends AndroidViewModel {

    private AnimalRepository repository;
    private LiveData<List<Animal>> allAnimals;

    public AddEntryViewModel(Application application) {
        super(application);
        repository = new AnimalRepository(application);
        allAnimals = repository.getAllAnimals();
    }


    LiveData<List<Animal>> getAllAnimals() {
        return allAnimals;
    }

    public void insertAnimal(Animal animal) {
        repository.insertAnimal(animal);
    }

    public void findAnimal(String name) {
        repository.findAnimal(name);
    }

    public void deleteAnimal(Animal animal) {
        repository.deleteAnimal(animal);
    }

}