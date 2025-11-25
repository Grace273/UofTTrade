package use_case.create_listing;

import entity.Listing;

import java.io.IOException;

public interface CreateListingUserDataAccessInterface {
//    /**
//     * Checks if the given name exists.
//     * @param ListingID the listingID to look for
//     * @return true if a user with the given name exists; false otherwise
//     */
//    boolean existsByListingID(int ListingID) throws IOException;

    /**
     * Saves the lisiting.
     * @param listing the listing to save
     */
    void save(Listing listing) throws IOException;
}
