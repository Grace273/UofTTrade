package use_case.create_listing;

import entity.Category;
import entity.User;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CreateListingOutputData {
    private String name;
    private BufferedImage photo;
    private User owner;
    private List<Category> categories = new ArrayList<>();
    private final int listingId;


    public CreateListingOutputData(String name, BufferedImage photo, List<Category> categories, User owner, int listingId) {
        this.name = name;
        this.photo = photo;
        this.categories = categories;
        this.owner = owner;
        this.listingId = listingId;
    }

    //overloaded
    public CreateListingOutputData(String name, BufferedImage photo, User owner, int listingId) {
        this.name = name;
        this.photo = photo;
        this.owner = owner;
        this.listingId = listingId;
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public BufferedImage get_img() { return photo; }

    public User get_owner() { return owner; }
}
