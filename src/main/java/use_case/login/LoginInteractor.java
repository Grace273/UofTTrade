package use_case.login;

import entity.User;

/**
 * The interactor for the login use case.
 */

public class LoginInteractor implements LoginInputBoundary {

    private final LoginUserDataAccessInterface userDataAccess;
    private final LoginOutputBoundary outputBoundary;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {

        String username =  loginInputData.getUsername();
        String password = loginInputData.getPassword();
        String email = loginInputData.getEmail();

        if (!userDataAccess.userExists(username) && !userDataAccess.userExists(email)) {
            outputBoundary.prepareFailView("Account does not exist.");
        } else {
            String dbPassword = userDataAccess.getUser(username).get_password();
            if (!password.equals(dbPassword)) {
                outputBoundary.prepareFailView("Incorrect password.");
            } else {

                User currentUser = userDataAccess.getUser(username);
                LoginOutputData outputData = new LoginOutputData(currentUser.get_username(), currentUser.get_email());
                outputBoundary.prepareSuccessView(outputData);

            }
        }

    }

}
