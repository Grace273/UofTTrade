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
    private User owner;
    private List<Category> categories = new ArrayList<>();

    public CreateListingOutputData(String name, List<Category> categories, User owner) {
        this.name = name;
        this.categories = categories;
        this.owner = owner;
    }

    //overloaded
    public CreateListingOutputData(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public User get_owner() {
        return owner;
    }
}
