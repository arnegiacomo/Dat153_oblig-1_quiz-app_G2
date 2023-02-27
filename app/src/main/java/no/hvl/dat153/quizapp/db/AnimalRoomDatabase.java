package no.hvl.dat153.quizapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import no.hvl.dat153.quizapp.model.Animal;

@Database(entities = {Animal.class}, version = 1)
public abstract class AnimalRoomDatabase extends RoomDatabase {

    public abstract AnimalDAO animalDAO();
    private static AnimalRoomDatabase INSTANCE;

    public static AnimalRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AnimalRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    AnimalRoomDatabase.class,
                                    "animals").build();
                }
            }
        }
        return INSTANCE;
    }

}
