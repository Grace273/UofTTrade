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
    private String name;
    private User owner;
    private String nameError;
    private List<Category> categories;
    private String successMessage;

    public String get_name() { return name; }
    public User get_owner() { return owner; }
    public List<Category> get_categories() { return categories; }

    public void set_name(String name) { this.name = name; }
    public void set_owner(User owner) { this.owner = owner; }
    public void set_categories(List<Category> categories) { this.categories = categories; }


    public void set_name_error(String nameError) { this.nameError = nameError; }
    public void set_successMessage(String successMessage) {this.successMessage = successMessage; }

    public String get_name_error() { return nameError; }
    public String get_successMessage() { return successMessage; }
}
