package entity;
import java.awt.image.BufferedImage;
import java.util.List;

public class Listing {
    private String name;
    private BufferedImage photo;
    private List<Category> categories;
    private User owner;
    private int listingId;

    public Listing(String name, BufferedImage photo, List<Category> categories, int listingId, User owner) {
        this.name = name;
        this.photo = photo;
        this.categories = categories;
        this.owner = owner;
        this.listingId = generateListingId();
    }

    //overload
    public Listing(String name, BufferedImage photo, int listingId, User owner) {
        this.name = name;
        this.photo = photo;
        this.listingId = listingId;
        this.owner = owner;
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

    /**
     * Generates a unique ID for the Listing. Helper to the constructor.
     * @return the generated ID
     */
    private int generateListingId() {
        int result = owner.get_username().hashCode() + name.hashCode();
        return result;
    }
}
