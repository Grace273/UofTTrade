package use_case.view_profile;

import entity.Listing;
import entity.User;
import java.util.List;

public interface ViewProfileUserDataAccessInterface {

    /**
     * Returns the currently logged-in User entity
     */
    User getCurrentLoggedInUser();

    /**
     * Returns a list of listing names for this user
     */
    List<String> getUserListings(String username);


}