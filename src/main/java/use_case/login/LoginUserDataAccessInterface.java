package use_case.login;

import entity.User;
import org.json.JSONArray;

/**
 * Data Access Interface for the login use case
 */
public interface LoginUserDataAccessInterface {

    /**
     * Checks if a user exists in the system based on the username or email inputted
     * @param userIdentifier: Either a string with an email or a username
     */
    boolean userExists(String userIdentifier);

    /**
     * Creates a user object based on their username or email
     * @param userIdentifier: Either a string with an email or a username
     */
    User getUser(String userIdentifier);

    void setUsername(String username);

    void setEmail(String email);

    String getUsername();

    String getEmail();
}
