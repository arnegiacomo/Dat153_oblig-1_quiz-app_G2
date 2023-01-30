package no.hvl.dat153.quizapp.model;

/**
 * Animal object with a name and image
 */
public class Animal {

    private String name;
    private String image;

    public Animal(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
