package use_case.update_listing;

public interface UpdateListingUserDataAccessInterface {
    /**
     * Updates the persistent record of the listing with the given ID.
     *
     * <p>
     * Implementations of this method delete listing associated with given ID and
     * syncing in-memory changes to the underlying data store.
     * </p>
     *
     * @param listingId the unique identifier of the listing to update in storage
     */
    void updateListing(int listingId);
}
