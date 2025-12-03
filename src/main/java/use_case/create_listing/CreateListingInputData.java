package use_case.create_listing;

import java.util.ArrayList;
import java.util.List;

import entity.Category;

public class CreateListingInputData {
    private String name;
    private String description;
    private List<Category> categories = new ArrayList<>();

    public CreateListingInputData(String name, String description, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    // overload
    public CreateListingInputData(String name) {
        this.name = name;
    }

    /**
     * Returns the list of categories associated with this listing.
     *
     * @return a list of categories for this listing
     */
    public List<Category> get_categories() {
        return categories;
    }

    /**
     * Returns the name of this listing.
     *
     * @return the listing name
     */
    public String get_name() {
        return name;
    }

    /**
     * Returns the description of this listing.
     *
     * @return the listing description
     */
    public String get_description() {
        return description;
    }
}
