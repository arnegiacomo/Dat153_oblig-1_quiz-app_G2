package no.hvl.dat153.quizapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.model.Animal;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private AnimalDAO animalDAO;
    private List<AnimalViewHolder> animalViewHolderList = new ArrayList<AnimalViewHolder>();

    public AnimalAdapter() {
        this.animalDAO = AnimalDAO.get();
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_layout, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        Animal animal = animalDAO.getAllAnimals().get(position);
        holder.animal = animal;
        holder.textView.setText(animal.getName());
        holder.imageView.setImageResource(animal.getImage_res_id());

        animalViewHolderList.add(holder);
    }

    /**
     *  Removes all animals that are marked for delete, updates adapter accordingly
     */
    public void updateAdapter() {
        animalDAO.getAllAnimals().forEach(animal -> {
            if (animal.isMarked_for_delete())
                animalDAO.removeAnimal(animal.getName());
        });
        notifyDataSetChanged();

        animalViewHolderList.forEach(holder -> holder.markfordelete.setChecked(false));
    }

    @Override
    public int getItemCount() {
        return animalDAO.getAllAnimals().size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {

        Animal animal;
        TextView textView;
        ImageView imageView;
        CheckBox markfordelete;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
            markfordelete = itemView.findViewById(R.id.markedfordelete);

            markfordelete.setOnClickListener(view -> animal.setMarked_for_delete(true));
        }

        public void setUnckecked() {
            markfordelete.setChecked(false);
        }
    }
}
