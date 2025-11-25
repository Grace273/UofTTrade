package entity;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class Listing {
    private String name;
    private String photoInBase64;
    private List<Category> categories;
    private User owner;
//    private int listingId;

    public Listing(String name, String photoInBase64, List<Category> categories, User owner) {
        this.name = name;
        this.photoInBase64 = photoInBase64;
        this.categories = categories;
        this.owner = owner;
//        listingId = generateListingId();
    }

    //overload
    public Listing(String name, String photoInBase64, User owner) {
        this.name = name;
        this.photoInBase64 = photoInBase64;
        this.owner = owner;
//        listingId = generateListingId();
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

//    public int get_listingId() { return listingId; }

    /**
     * Returns the listing image as a Buffered Image
     * @return return the image as a Buffered Image
     */
    public BufferedImage get_img() throws IOException {
        // reduce image into bytes
        byte[] bytes = Base64.getDecoder().decode(this.photoInBase64);
        //convert to buffered image
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        return image;
    }

    public User get_owner() { return owner; }

//    /**
//     * Generates a unique ID for the Listing. Helper to the constructor.
//     * @return the generated ID
//     */
//    private int generateListingId() {
//        int result = owner.get_username().hashCode() + name.hashCode();
//        return result;
//    }

    public String get_img_in_Base64() { return photoInBase64; }
}
