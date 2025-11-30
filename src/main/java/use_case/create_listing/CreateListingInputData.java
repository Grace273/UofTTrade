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
    private List<Category> categories = new ArrayList<>();
    private User owner;
    public CreateListingInputData(String name, List<Category> categories){
        this.name = name;
        this.categories = categories;
    }

    //overload
    public CreateListingInputData(String name) {
        this.name = name;
    }


    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public User get_owner() { return owner; }

}
