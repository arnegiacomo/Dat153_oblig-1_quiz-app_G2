package no.hvl.dat153.quizapp.db;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import java.util.stream.Collectors;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import no.hvl.dat153.quizapp.model.Animal;

public class AnimalRepository {

    private MutableLiveData<List<Animal>> searchResults =
            new MutableLiveData<>();
    private LiveData<List<Animal>> allAnimals;
    private AnimalDAO animalDAO;

    public LiveData<List<Animal>> getAllAnimals() {
        return allAnimals;
    }

    public MutableLiveData<List<Animal>> getSearchResults() {
        return searchResults;
    }

    public AnimalRepository(Application application) {
        AnimalRoomDatabase db;
        db = AnimalRoomDatabase.getDatabase(application);
        animalDAO = db.animalDAO();
        allAnimals = animalDAO.getAll();
    }

    public void insertAnimal(Animal animal) {
        InsertAsyncTask task = new InsertAsyncTask(animalDAO);
        task.execute(animal);
    }

    public void deleteAnimal(Animal animal) {
        DeleteAsyncTask task = new DeleteAsyncTask(animalDAO);
        task.execute(animal);
    }

    public void findAnimal(String name) {
        QueryAsyncTask task = new QueryAsyncTask(animalDAO);
        task.delegate = this;
        task.execute(name);
    }

    private void asyncFinished(List<Animal> results) {
        searchResults.setValue(results);
    }

    private static class QueryAsyncTask extends
            AsyncTask<String, Void, List<Animal>> {

        private AnimalDAO asyncTaskDao;
        private AnimalRepository delegate = null;

        QueryAsyncTask(AnimalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected List<Animal> doInBackground(final String... params) {
            return asyncTaskDao.find(params[0]);
        }

        @Override
        protected void onPostExecute(List<Animal> result) {
            delegate.asyncFinished(result);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Animal, Void, Void> {

        private AnimalDAO asyncTaskDao;

        InsertAsyncTask(AnimalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Animal... params) {
            asyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Animal, Void, Void> {

        private AnimalDAO asyncTaskDao;

        DeleteAsyncTask(AnimalDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Animal... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
