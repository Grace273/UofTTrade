package entity;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class Listing {
    private String name;
    private List<Category> categories;
    private User owner;
    private int listingId;

    public Listing(String name, List<Category> categories, User owner) {
        this.name = name;
        this.categories = categories;
        this.owner = owner;
        this.listingId = generateListingId();
    }

    //overload
    public Listing(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.listingId = generateListingId();
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public int get_listingId() { return listingId; }

    public User get_owner() { return owner; }

    /**
     * Generates a unique ID for the Listing. Helper to the constructor.
     * @return the generated ID
     */
    private int generateListingId() {
        return owner.get_username().hashCode() + name.hashCode();
    }
}
