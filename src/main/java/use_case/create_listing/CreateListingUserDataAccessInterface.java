package use_case.create_listing;
import entity.Listing;
import entity.User;

public interface CreateListingUserDataAccessInterface {
    /**
     * Checks if the given listingID exists.
     * @param listingId the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsById(int listingId);

    /**
     * Checks if the given user exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByUsername(String username);

//    /**
//     * Returns the user with the given username.
//     * @param username the username to look up
//     * @return the user with the given username
//     */
//    User getUserByUsername(String username);

    /**
     * Saves the Listing.
     * @param listing the user to save
     */
    void save(Listing listing);

}
