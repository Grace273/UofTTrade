package use_case.create_listing;

import entity.Category;
import entity.User;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CreateListingOutputData {
    private String name;
    private BufferedImage photo;
    private User owner;
    private List<Category> categories = new ArrayList<>();

    public CreateListingOutputData(String name, BufferedImage photo, List<Category> categories) {
        this.name = name;
        this.photo = photo;
        this.categories = categories;
        //NEED A WAY TO ATTACH USER CREATING THE LISTING AS OWNER
//        this.owner = owner;
    }

    //overloaded
    public CreateListingOutputData(String name, BufferedImage photo) {
        this.name = name;
        this.photo = photo;
        //NEED A WAY TO ATTACH USER CREATING THE LISTING AS OWNER
//        this.owner = owner;
    }
}
