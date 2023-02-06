package no.hvl.dat153.quizapp.model;

import android.graphics.Bitmap;

import lombok.Data;
import lombok.NonNull;

/**
 * Animal object with a name and image
 */
public class Animal {

    private String name;
    private Bitmap bitmap;
    private boolean marked_for_delete = false;

    public Animal(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isMarked_for_delete() {
        return marked_for_delete;
    }

    public void setMarked_for_delete(boolean marked_for_delete) {
        this.marked_for_delete = marked_for_delete;
    }
}
