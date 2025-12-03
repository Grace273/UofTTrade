package interface_adapter.create_listing;

import entity.Category;
import entity.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * The state for the create listing View Model.
 */
public class CreateListingState {
    private String name = "";
    private String description = "";
    private User owner;
    private String nameError;
    private List<Category> categories;
    private String successMessage;

    /**
     * Returns the name of this listing.
     *
     * @return the listing name
     */
    public String get_name() {
        return name;
    }

    /**
     * Returns the owner of this listing.
     *
     * @return the user who owns this listing
     */
    public User get_owner() {
        return owner;
    }

    /**
     * Returns the list of categories associated with this listing.
     *
     * @return the categories for this listing
     */
    public List<Category> get_categories() {
        return categories;
    }

    /**
     * Sets the name of this listing.
     *
     * @param name the new name for the listing
     */
    public void set_name(String name) {
        this.name = name;
    }

    /**
     * Sets the owner of this listing.
     *
     * @param owner the new owner of the listing
     */
    public void set_owner(User owner) {
        this.owner = owner;
    }

    /**
     * Sets the list of categories associated with this listing.
     *
     * @param categories the new categories for this listing
     */
    public void set_categories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Sets the error message associated with the listing name.
     *
     * @param nameError the error message for the name field
     */
    public void set_name_error(String nameError) {
        this.nameError = nameError;
    }

    /**
     * Sets the success message for the current operation.
     *
     * @param successMessage the success message to display
     */
    public void set_successMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * Returns the error message associated with the listing name.
     *
     * @return the name error message, or {@code null} if none is set
     */
    public String get_name_error() {
        return nameError;
    }

    /**
     * Returns the success message for the current operation.
     *
     * @return the success message, or {@code null} if none is set
     */
    public String get_successMessage() {
        return successMessage;
    }

    /**
     * Sets the description of the listing.
     *
     * @param description the description to set for the listing
     */
    public void set_description(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the listing.
     *
     * @return the listing description
     */
    public String get_description() {
        return description;
    }
}
