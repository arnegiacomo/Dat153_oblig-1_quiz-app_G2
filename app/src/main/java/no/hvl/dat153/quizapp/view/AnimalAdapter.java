package no.hvl.dat153.quizapp.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import no.hvl.dat153.quizapp.R;
import no.hvl.dat153.quizapp.db.AnimalDAO;
import no.hvl.dat153.quizapp.model.Animal;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private AnimalDAO animalDAO;
    private Activity activity;

    public AnimalAdapter(Activity activity) {
        this.animalDAO = AnimalDAO.get();
        this.activity = activity;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_layout, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        Animal animal = animalDAO.getAllAnimals().get(position);
        holder.textView.setText(animal.getName());

        holder.imageView.setImageResource(animal.getImage());
    }

    @Override
    public int getItemCount() {
        return animalDAO.getAllAnimals().size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
