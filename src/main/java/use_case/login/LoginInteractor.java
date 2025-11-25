package use_case.login;

import entity.User;

import java.io.IOException;

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
    public void execute(LoginInputData loginInputData) throws IOException {

        String userIdentifier =  loginInputData.getUserIdentifier();
        String password = loginInputData.getPassword();


        if (!userDataAccess.userExists(userIdentifier)) {
            outputBoundary.prepareFailView("Account does not exist.");
        } else {
            String dbPassword = userDataAccess.getUser(userIdentifier).get_password();
            if (!password.equals(dbPassword)) {
                outputBoundary.prepareFailView("Incorrect password.");
            } else {

                User currentUser = userDataAccess.getUser(userIdentifier);

                userDataAccess.setCurrentLoggedInUser(currentUser);

                LoginOutputData outputData = new LoginOutputData(currentUser.get_username(), currentUser.get_email());
                outputBoundary.prepareSuccessView(outputData);

            }
        }

    }

    @Override
    public void switchToRegisterView() {
        outputBoundary.switchToRegisterView();
    }

}
