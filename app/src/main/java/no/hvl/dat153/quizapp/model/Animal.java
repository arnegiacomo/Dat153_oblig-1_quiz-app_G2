package no.hvl.dat153.quizapp.model;

/**
 * Animal object with a name and image
 */
public class Animal {

    private String name;
    private int image_res_id;

    public Animal(String name, int image_res_id) {
        this.name = name;
        this.image_res_id = image_res_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image_res_id;
    }

    public void setImage(int image_res_id) {
        this.image_res_id = image_res_id;
    }
}
