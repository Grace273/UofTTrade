package use_case.view_profile;


import java.util.List;

public interface ViewProfileUserDataAccessInterface {
    /** Returns the username of the currently logged-in user */
    String getCurrentLoggedInUsername();

    /** Returns a list of listing names for the given username */
    List<String> getUserListings(String username);
}
