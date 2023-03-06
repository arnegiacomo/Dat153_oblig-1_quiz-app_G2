package no.hvl.dat153.quizapp.view;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.db.AnimalRepository;
import no.hvl.dat153.quizapp.model.Animal;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private final List<AnimalViewHolder> animalViewHolderList = new ArrayList<>();
    private final AnimalRepository repository;
    private List<Animal> animals = new ArrayList<>();


    public AnimalAdapter(AnimalRepository repository) {
        super();
        this.repository = repository;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_layout, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        if(animals.size() == 0) return;
        Animal animal = animals.get(position);
        holder.textView.setText(animal.getName());
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(animal.getBitmap(), 0, animal.getBitmap().length));

        holder.markedForDelete.setOnClickListener(view -> animal.setMarked_for_delete(true));
        if (animal.isMarked_for_delete()) holder.markedForDelete.setChecked(true);
        animalViewHolderList.add(holder);

    }

    /**
     *  Removes all animals that are marked for delete, updates adapter accordingly
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter() {
        animalViewHolderList.forEach(holder -> holder.markedForDelete.setChecked(false));
        new Thread(() -> {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i).isMarked_for_delete()) {
                    repository.deleteAnimal(animals.get(i));
                }
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        CheckBox markedForDelete;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            markedForDelete = itemView.findViewById(R.id.markedfordelete);
        }
    }
}
