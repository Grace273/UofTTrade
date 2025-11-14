package interface_adapter.create_listing;

import entity.User;

import java.awt.image.BufferedImage;

/**
 * The state for the Create Listing View Model.
 */
public class CreateListingState {
    private String name;
    private BufferedImage photo;
    private User owner;
    private String createListingError;

    public String get_name() { return name; }
    public BufferedImage get_photo() { return photo; }
    public User get_owner() { return owner; }
    public String get_createListingError() { return createListingError; }

    public void set_name(String name) { this.name = name; }
    public void set_photo(BufferedImage photo) { this.photo = photo; }
    public void set_owner(User owner) { this.owner = owner; }
    public void set_createListingError(String inputError) { this.createListingError = inputError; }

}
