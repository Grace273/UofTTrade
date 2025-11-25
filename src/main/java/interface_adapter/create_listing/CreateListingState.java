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
 * The state for the Login View Model.
 */
public class CreateListingState {
    private String name;
    private String photoInBase64;
    private User owner;
    private String nameError;
    private String photoError;
    private List<Category> categories;

    public String get_name() { return name; }
    public String get_img_in_Base64() { return photoInBase64; }
    public BufferedImage get_img() throws IOException {
        // reduce image into bytes
        byte[] bytes = Base64.getDecoder().decode(this.photoInBase64);
        //convert to buffered image
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        return image;
    }
    public User get_owner() { return owner; }
    public List<Category> get_categories() { return categories; }

    public void set_name(String name) { this.name = name; }
    public void set_photo(String photo) { this.photoInBase64 = photo; }
    public void set_owner(User owner) { this.owner = owner; }
    public void set_categories(List<Category> categories) { this.categories = categories; }


    public void set_name_error(String ownerError) { this.nameError = nameError; }
    public void set_photo_error(String photoError) { this.photoError = photoError; }

    public String get_name_error() { return nameError; }
    public String get_photo_error() { return photoError; }
}
