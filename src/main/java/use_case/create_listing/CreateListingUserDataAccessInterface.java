package use_case.create_listing;

import entity.Listing;

import java.io.IOException;

public interface CreateListingUserDataAccessInterface {
    /**
     * Saves the listing.
     * @param listing the listing to save
     */
    void save(Listing listing) throws IOException;
}
