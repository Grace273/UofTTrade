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
    private final User owner;
    private final int listingId;


    public CreateListingInputData(String name, BufferedImage photo, List<Category> categories, User owner) {
        this.name = name;
        this.categories = categories;
        this.photo = photo;
        this.owner = owner;
        listingId = generateListingId();
    }

    // overloaded constructor for when no categories are provided
    public CreateListingInputData(String name, BufferedImage photo, User owner) {
        this.name = name;
        this.photo = photo;
        this.owner = owner;
        listingId = generateListingId();
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public BufferedImage get_img() { return photo; }

    public User get_owner() { return owner; }

    public int get_listing_id() { return listingId; }

    /**
     * Generates a unique ID for the Listing. Helper to the constructor.
     * @return the generated ID
     */
    private int generateListingId() {
        int result = owner.get_username().hashCode() + name.hashCode();
        return result;
    }
}
