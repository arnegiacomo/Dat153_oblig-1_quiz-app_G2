package no.hvl.dat153.quizapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Animal object with a name and image
 */
@Data
public class Animal {

    @NonNull
    private String name;
    @NonNull
    private Integer image_res_id;
    private boolean marked_for_delete = false;

}
