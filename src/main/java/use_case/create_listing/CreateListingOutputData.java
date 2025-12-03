package use_case.create_listing;

import java.util.ArrayList;
import java.util.List;

import entity.Category;
import entity.User;

public class CreateListingOutputData {
    private String name;
    private String description;
    private User owner;
    private List<Category> categories = new ArrayList<>();
    private int listingID;

    public CreateListingOutputData(String name, String description, List<Category> categories, User owner) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.owner = owner;
        this.listingID = generateListingId();
    }

    // overloaded
    public CreateListingOutputData(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.listingID = generateListingId();
    }

    /**
     * Returns the owner of this listing.
     *
     * @return the user who owns this listing
     */
    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getListingID() {
        return listingID;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Generates a unique ID for the Listing. Helper to the constructor.
     * @return the generated ID
     */
    private int generateListingId() {
        return owner.get_username().hashCode() + name.hashCode();
    }
}
