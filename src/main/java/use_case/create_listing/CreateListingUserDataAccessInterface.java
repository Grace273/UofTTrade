package use_case.create_listing;

import entity.Listing;

import java.io.IOException;

public interface CreateListingUserDataAccessInterface {
    /**
     * Saves the listing.
     * @param listing the listing to save
     */
    void save(Listing listing) throws IOException;

    /**
     * Returns if listing with the give listingId exists
     * @param listingID the id of the listing
     */
    boolean existById(String listingID) throws IOException;
}
