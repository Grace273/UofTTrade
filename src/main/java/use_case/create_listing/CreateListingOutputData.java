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

public class CreateListingOutputData {
    private String name;
    private String photoInBase64;
    private User owner;
    private List<Category> categories = new ArrayList<>();

    public CreateListingOutputData(String name, String photoInBase64, List<Category> categories, User owner) {
        this.name = name;
        this.photoInBase64 = photoInBase64;
        this.categories = categories;
        this.owner = owner;
    }

    //overloaded
    public CreateListingOutputData(String name, String photoInBase64, User owner) {
        this.name = name;
        this.photoInBase64 = photoInBase64;
        this.owner = owner;
    }

    public String get_img_in_Base64() { return photoInBase64; }

    public BufferedImage get_img() throws IOException {
        // reduce image into bytes
        byte[] bytes = Base64.getDecoder().decode(this.photoInBase64);
        //convert to buffered image
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));

        return image;
    }

    public User get_owner() {
        return owner;
    }
}
