package use_case.create_listing;

import data_access.UserDataAccessObject;
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
    private String description;
    private List<Category> categories = new ArrayList<>();
    public CreateListingInputData(String name, String description, List<Category> categories){
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    //overload
    public CreateListingInputData(String name) {
        this.name = name;
    }


    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }
    public String get_description() { return description; }

}
