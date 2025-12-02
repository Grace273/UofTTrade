package use_case.create_listing;

import java.io.IOException;

import entity.Listing;

public interface CreateListingUserDataAccessInterface {
    /**
     * Saves the listing.
     * @param listing the listing to save
     * @throws IOException if an I/O error occurs while saving the listing
     */
    void save(Listing listing) throws IOException;

    /**
     * Returns whether a listing with the given ID exists in persistent storage.
     *
     * @param listingID the ID of the listing
     * @return {@code true} if a listing with the given ID exists; {@code false} otherwise
     * @throws IOException if an I/O error occurs while accessing the listing data
     */
    boolean existById(String listingID) throws IOException;
}
