package use_case.login;

import java.io.IOException;

/**
 * The output boundary for the login use case.
 */

public interface LoginOutputBoundary {

    /**
     * Prepares the view for when a login is successful
     * @param loginOutputData: The data attributed to a user who logs in
     */
    void prepareSuccessView(LoginOutputData loginOutputData);

    /**
     * Prepares the view for a failed login attempt
     * @param failMessage: The reason why the login attempt failed.
     */
    void prepareFailView(String failMessage);

}
