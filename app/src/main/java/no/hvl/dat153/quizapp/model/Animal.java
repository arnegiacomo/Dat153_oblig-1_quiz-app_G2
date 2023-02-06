package no.hvl.dat153.quizapp.model;

import android.graphics.Bitmap;

import lombok.Data;
import lombok.NonNull;

/**
 * Animal object with a name and image
 */
@Data
public class Animal {

    @NonNull
    private String name;
    @NonNull
    private Bitmap bitmap;
    private boolean marked_for_delete = false;

}
