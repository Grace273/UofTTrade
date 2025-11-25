package use_case.login;

import entity.User;
import org.json.JSONArray;

import java.io.IOException;

/**
 * Data Access Interface for the login use case
 */
public interface LoginUserDataAccessInterface {

    /**
     * Checks if a user exists in the system based on the username or email inputted
     * @param userIdentifier: Either a string with an email or a username
     */
    boolean userExists(String userIdentifier) throws IOException;

    /**
     * Creates a user object based on their username or email
     * @param userIdentifier: Either a string with an email or a username
     */
    User getUser(String userIdentifier) throws IOException;

    void save(User user) throws IOException;

    void setUsername(String username);

    void setEmail(String email);

    String getUsername();

    String getEmail();

    void setCurrentLoggedInUser(User user);
}
