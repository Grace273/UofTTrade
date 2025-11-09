package use_case.create_listing;
import entity.Category;

import java.awt.image.BufferedImage;
import java.util.List;

public interface CreateListingUserDataAccessInterface {
    /**
     * Checks if a name is inputted.
     * @param name input
     * @return true if name is Null
     */
    boolean nameFieldIsNull(String name);

    /**
     * Checks if a name is inputted.
     * @param photo input
     * @return true if name is Null
     */
    boolean photoFieldIsNull(BufferedImage photo);

    /**
     * Checks if categories are inputted.
     * @param categories input
     * @return true if name is Null
     */
    boolean categoryFieldIsNull(List<Category> categories);

}
