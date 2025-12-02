package use_case.register;

import data_access.UserDataAccessObject;
import entity.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegisterInteractorTest {

    /* To Replicate the test, you need to change the username and email fields but the test did work with
       the original values inputted.
     */
    @Test
    void successTest() throws IOException {
        RegisterInputData inputData = new RegisterInputData("Benny", "benny@gmail.com",
                "password", "password");
        RegisterUserDataAccessInterface userRepository = new UserDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        RegisterOutputBoundary successPresenter = new RegisterOutputBoundary() {
            @Override
            public void prepareSuccessView(RegisterOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("Benny", user.getUsername());
                assertEquals("benny@gmail.com", user.getEmail());
            }

            @Override
            public void prepareFailureView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        RegisterInputBoundary interactor = new RegisterInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() throws IOException {
        RegisterInputData inputData = new RegisterInputData("Terry", "terry@gmail.com",
                "password", "wrong");
        RegisterUserDataAccessInterface userRepository = new UserDataAccessObject();

        // This creates a presenter that tests whether the test case is as we expect.
        RegisterOutputBoundary failurePresenter = new RegisterOutputBoundary() {
            @Override
            public void prepareSuccessView(RegisterOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailureView(String error) {
                assertEquals("Enter the same password twice.", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        RegisterInputBoundary interactor = new RegisterInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() throws IOException {
        RegisterInputData inputData = new RegisterInputData("Mike", "mike@gmail.com",
                "password", "password");
        RegisterUserDataAccessInterface userRepository = new UserDataAccessObject();

        // Add user to the repo so that when we check later they already exist
        User user = new User("Mike", "password", "mike@gmail.com");
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        RegisterOutputBoundary failurePresenter = new RegisterOutputBoundary() {
            @Override
            public void prepareSuccessView(RegisterOutputData user) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailureView(String error) {
                assertEquals("Username or email is already associated to account.", error);
            }

            @Override
            public void switchToLoginView() {
                // This is expected
            }
        };

        RegisterInputBoundary interactor = new RegisterInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }
}
