package use_case.create_listing;

import entity.Category;
import entity.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CreateListingInputData {
    private String name;
    private String photoInBase64;
    private List<Category> categories = new ArrayList<>();
//    private int listingId;
    private User owner;
    public CreateListingInputData(String name, String photo, List<Category> categories){
        this.name = name;
        this.categories = categories;
        this.photoInBase64 = photo;
    }

    //overload
    public CreateListingInputData(String name, String photo) {
        this.name = name;
        this.photoInBase64 = photo;
    }

//    /**
//     * Generates a listingID using the owner and the listing name
//     * @param owner
//     */
//    public void set_owner_and_listingID(User owner) {
//        this.owner = owner;
//        listingId = generateListingId();
//    }

    public BufferedImage get_img(BufferedImage new_img) throws IOException {
        // reduce image into bytes
        byte[] bytes = Base64.getDecoder().decode(this.photoInBase64);
        //convert to buffered image
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        return image;
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public String get_img_in_Base64() { return photoInBase64; }

//    public int get_listing_id() { return listingId; }

    public User get_owner() { return owner; }

//    private int generateListingId() {
//        int result = owner.get_username().hashCode() + name.hashCode();
//        return result;
//    }

}
