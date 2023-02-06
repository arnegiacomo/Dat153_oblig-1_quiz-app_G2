package no.hvl.dat153.quizapp.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.model.Animal;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private final AnimalDAO animalDAO;
    private final List<AnimalViewHolder> animalViewHolderList = new ArrayList<>();

    public AnimalAdapter() {
        this.animalDAO = AnimalDAO.get();
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_layout, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        Animal animal = animalDAO.getAllAnimals().get(position);
        holder.textView.setText(animal.getName());
        holder.imageView.setImageResource(animal.getImage_res_id());
        holder.markedForDelete.setOnClickListener(view -> animal.setMarked_for_delete(true));

        animalViewHolderList.add(holder);
    }

    /**
     *  Removes all animals that are marked for delete, updates adapter accordingly
     */
    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter() {
        animalDAO.getAllAnimals().forEach(animal -> {
            if (animal.isMarked_for_delete())
                animalDAO.removeAnimal(animal.getName());
        });
        notifyDataSetChanged();

        animalViewHolderList.forEach(holder -> holder.markedForDelete.setChecked(false));
    }

    @Override
    public int getItemCount() {
        return animalDAO.getAllAnimals().size();
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
