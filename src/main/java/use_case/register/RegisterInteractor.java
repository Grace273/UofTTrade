package use_case.register;

import entity.User;

import java.io.IOException;

public class RegisterInteractor implements RegisterInputBoundary {

    private final RegisterUserDataAccessInterface userDataAccess;
    private final RegisterOutputBoundary outputBoundary;

    public RegisterInteractor(RegisterUserDataAccessInterface userDataAccess, RegisterOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(RegisterInputData registerInputData) throws IOException {


        if (userDataAccess.userExists(registerInputData.getUsername()) ||
                userDataAccess.userExists(registerInputData.getEmail())) {

            outputBoundary.prepareFailureView("Username or email is already associated to account.");


        } else if (!registerInputData.getPassword().equals(registerInputData.getConfirmPassword())) {

            outputBoundary.prepareFailureView("Enter the same password twice.");

        } else if ("".equals(registerInputData.getUsername()) || "".equals(registerInputData.getEmail())) {

            outputBoundary.prepareFailureView("Please enter a username and email.");

        } else if ("".equals(registerInputData.getConfirmPassword())) {

            outputBoundary.prepareFailureView("Enter a password");

        } else {

            User newUser = new User(registerInputData.getUsername(), registerInputData.getPassword(),
                    registerInputData.getEmail());
            userDataAccess.save(newUser);
            RegisterOutputData outputData = new RegisterOutputData(newUser.get_username(), newUser.get_email());
            outputBoundary.prepareSuccessView(outputData);

        }

    }

    @Override
    public void switchToLoginView() {

        outputBoundary.switchToLoginView();

    }

}
