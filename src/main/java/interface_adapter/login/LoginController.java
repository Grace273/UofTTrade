package interface_adapter.login;

import use_case.login.LoginInputData;
import use_case.login.LoginInputBoundary;

/**
 * The controller for the login use case
 */

public class LoginController {

    private final LoginInputBoundary inputBoundary;

    public LoginController(LoginInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Executes the user's login attempt
     * @param username: String with the username of the user logging in
     * @param password: String with the password of the user logging in
     * @param email: String with the email of the user logging in
     */

    public void executeLogin(String username, String password, String email) {

        final LoginInputData  loginInputData = new LoginInputData(username, password, email);
        inputBoundary.execute(loginInputData);

    }

}
