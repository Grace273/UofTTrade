package use_case.login;

import entity.User;
import org.json.JSONArray;

import java.io.IOException;

public interface LoginUserDataAccessInterface {

    boolean userExists(String userIdentifier);

    User getUser(String userIdentifier);

    void setUsername(String username);

    void setEmail(String email);

    String getUsername();

    String getEmail();
}
