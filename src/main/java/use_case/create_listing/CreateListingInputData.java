package use_case.create_listing;

import entity.Category;
import entity.User;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CreateListingInputData {
    private String name;
    private BufferedImage photo;
    private List<Category> categories = new ArrayList<>();
    private User owner;

    public CreateListingInputData(String name, BufferedImage photo, List<Category> categories) {
        this.name = name;
        this.categories = categories;
        this.photo = photo;
        //NEED A WAY TO ATTACH USER CREATING THE LISTING AS OWNER
//        this.owner = owner;
    }

    //overload
    public CreateListingInputData(String name, BufferedImage photo) {
        this.name = name;
        this.photo = photo;
        //NEED A WAY TO ATTACH USER CREATING THE LISTING AS OWNER
//        this.owner = owner;
    }

    public void change_category(List<Category> new_categories) {
        this.categories = new_categories;
    }

    public void change_name(String new_name) {
        this.name = new_name;
    }

    public void change_img(BufferedImage new_img) {
        this.photo = new_img;
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public BufferedImage get_img() { return photo; }

    public User get_owner() { return owner; }
}
